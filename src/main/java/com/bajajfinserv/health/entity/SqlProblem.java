package com.bajajfinserv.health.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sql_problems")
public class SqlProblem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reg_no")
    private String regNo;
    
    @Column(name = "problem_description", columnDefinition = "TEXT")
    private String problemDescription;
    
    @Column(name = "solution_query", columnDefinition = "TEXT")
    private String solutionQuery;
    
    @Column(name = "webhook_url")
    private String webhookUrl;
    
    @Column(name = "access_token")
    private String accessToken;
    
    @Column(name = "submission_status")
    private String submissionStatus;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRegNo() {
        return regNo;
    }
    
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
    
    public String getProblemDescription() {
        return problemDescription;
    }
    
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
    
    public String getSolutionQuery() {
        return solutionQuery;
    }
    
    public void setSolutionQuery(String solutionQuery) {
        this.solutionQuery = solutionQuery;
    }
    
    public String getWebhookUrl() {
        return webhookUrl;
    }
    
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getSubmissionStatus() {
        return submissionStatus;
    }
    
    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
