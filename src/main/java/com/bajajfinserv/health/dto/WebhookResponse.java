package com.bajajfinserv.health.dto;

public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private String message;
    private boolean success;
    
    public WebhookResponse() {}
    
    public WebhookResponse(String webhook, String accessToken, String message, boolean success) {
        this.webhook = webhook;
        this.accessToken = accessToken;
        this.message = message;
        this.success = success;
    }
    
    public String getWebhook() {
        return webhook;
    }
    
    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
