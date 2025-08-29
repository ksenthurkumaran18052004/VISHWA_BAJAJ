package com.bajajfinserv.health.service;

import com.bajajfinserv.health.dto.SolutionRequest;
import com.bajajfinserv.health.dto.WebhookRequest;
import com.bajajfinserv.health.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    
    private static final Logger log = LoggerFactory.getLogger(ApiService.class);
    
    private final RestTemplate restTemplate;
    
    @Value("${api.base-url}")
    private String baseUrl;
    
    @Value("${api.generate-webhook-path}")
    private String generateWebhookPath;
    
    @Value("${api.test-webhook-path}")
    private String testWebhookPath;
    
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    /**
     * Generates webhook by sending POST request to the API
     * @param request Webhook request with participant details
     * @return Webhook response with webhook URL and access token
     */
    public WebhookResponse generateWebhook(WebhookRequest request) {
        try {
            String url = baseUrl + generateWebhookPath;
            log.info("Generating webhook from URL: {}", url);
            log.info("Request payload: {}", request);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                WebhookResponse.class
            );
            
            log.info("Webhook generation response: {}", response.getBody());
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error generating webhook: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate webhook", e);
        }
    }
    
    /**
     * Submits the SQL solution to the webhook URL
     * @param webhookUrl Webhook URL to submit the solution
     * @param accessToken JWT token for authorization
     * @param solution SQL query solution
     * @return Response from the webhook
     */
    public ResponseEntity<String> submitSolution(String webhookUrl, String accessToken, String solution) {
        try {
            log.info("Submitting solution to webhook: {}", webhookUrl);
            log.info("Solution: {}", solution);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            
                         SolutionRequest request = new SolutionRequest();
             request.setFinalQuery(solution);
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                entity,
                String.class
            );
            
            log.info("Solution submission response: {}", response.getBody());
            return response;
            
        } catch (Exception e) {
            log.error("Error submitting solution: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit solution", e);
        }
    }
}
