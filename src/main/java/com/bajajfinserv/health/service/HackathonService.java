package com.bajajfinserv.health.service;

import com.bajajfinserv.health.dto.WebhookRequest;
import com.bajajfinserv.health.dto.WebhookResponse;
import com.bajajfinserv.health.entity.SqlProblem;
import com.bajajfinserv.health.repository.SqlProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HackathonService {
    
    private static final Logger log = LoggerFactory.getLogger(HackathonService.class);
    
    private final ApiService apiService;
    private final SqlProblemSolver sqlProblemSolver;
    private final SqlProblemRepository sqlProblemRepository;
    
    @Value("${participant.name}")
    private String participantName;
    
    @Value("${participant.regNo}")
    private String participantRegNo;
    
    @Value("${participant.email}")
    private String participantEmail;
    
    public HackathonService(ApiService apiService, 
                          SqlProblemSolver sqlProblemSolver,
                          SqlProblemRepository sqlProblemRepository) {
        this.apiService = apiService;
        this.sqlProblemSolver = sqlProblemSolver;
        this.sqlProblemRepository = sqlProblemRepository;
    }
    
    /**
     * Executes the complete hackathon flow
     */
    public void executeHackathonFlow() {
        try {
            log.info("Starting Bajaj Finserv Health Hackathon flow...");
            
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            
            // Step 2: Solve SQL problem
            String problemDescription = sqlProblemSolver.getProblemDescription(participantRegNo);
            String solution = sqlProblemSolver.solveSqlProblem(participantRegNo);
            
            // Step 3: Store problem and solution
            SqlProblem sqlProblem = storeProblemAndSolution(webhookResponse, problemDescription, solution);
            
            // Step 4: Submit solution
            submitSolution(webhookResponse, solution, sqlProblem);
            
            log.info("Hackathon flow completed successfully!");
            
        } catch (Exception e) {
            log.error("Error in hackathon flow: {}", e.getMessage(), e);
            throw new RuntimeException("Hackathon flow failed", e);
        }
    }
    
    /**
     * Step 1: Generate webhook by sending POST request
     */
    private WebhookResponse generateWebhook() {
        log.info("Step 1: Generating webhook for participant: {}", participantName);
        
        WebhookRequest request = new WebhookRequest();
        request.setName(participantName);
        request.setRegNo(participantRegNo);
        request.setEmail(participantEmail);
        
        WebhookResponse response = apiService.generateWebhook(request);
        
        if (response == null || !response.isSuccess()) {
            throw new RuntimeException("Failed to generate webhook. Response: " + response);
        }
        
        log.info("Webhook generated successfully. Webhook URL: {}", response.getWebhook());
        return response;
    }
    
    /**
     * Step 2 & 3: Store problem and solution in database
     */
    private SqlProblem storeProblemAndSolution(WebhookResponse webhookResponse, 
                                             String problemDescription, 
                                             String solution) {
        log.info("Step 2 & 3: Storing problem and solution for registration number: {}", participantRegNo);
        
        SqlProblem sqlProblem = new SqlProblem();
        sqlProblem.setRegNo(participantRegNo);
        sqlProblem.setProblemDescription(problemDescription);
        sqlProblem.setSolutionQuery(solution);
        sqlProblem.setWebhookUrl(webhookResponse.getWebhook());
        sqlProblem.setAccessToken(webhookResponse.getAccessToken());
        sqlProblem.setSubmissionStatus("PENDING");
        
        SqlProblem savedProblem = sqlProblemRepository.save(sqlProblem);
        log.info("Problem and solution stored successfully with ID: {}", savedProblem.getId());
        
        return savedProblem;
    }
    
    /**
     * Step 4: Submit solution to webhook URL
     */
    private void submitSolution(WebhookResponse webhookResponse, String solution, SqlProblem sqlProblem) {
        log.info("Step 4: Submitting solution to webhook URL");
        
        ResponseEntity<String> response = apiService.submitSolution(
            webhookResponse.getWebhook(),
            webhookResponse.getAccessToken(),
            solution
        );
        
        // Update submission status
        sqlProblem.setSubmissionStatus("SUBMITTED");
        sqlProblem.setSubmittedAt(LocalDateTime.now());
        sqlProblemRepository.save(sqlProblem);
        
        log.info("Solution submitted successfully. Response: {}", response.getBody());
    }
}
