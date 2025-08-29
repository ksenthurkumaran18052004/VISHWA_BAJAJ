package com.bajajfinserv.health;

import com.bajajfinserv.health.service.HackathonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BajajHealthHackathonApplication {
    
    private static final Logger log = LoggerFactory.getLogger(BajajHealthHackathonApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(BajajHealthHackathonApplication.class, args);
    }
    
    /**
     * CommandLineRunner bean to execute the hackathon flow on application startup
     * This ensures the flow runs automatically without requiring any controller endpoint
     */
    @Bean
    public CommandLineRunner hackathonRunner(HackathonService hackathonService) {
        return args -> {
            log.info("Bajaj Finserv Health Hackathon Application Starting...");
            log.info("Executing hackathon flow on startup...");
            
            try {
                // Add a small delay to ensure all beans are properly initialized
                Thread.sleep(2000);
                
                hackathonService.executeHackathonFlow();
                
                log.info("Hackathon flow execution completed!");
                log.info("Application is ready to serve requests.");
                
            } catch (Exception e) {
                log.error("Failed to execute hackathon flow on startup: {}", e.getMessage(), e);
                // Don't exit the application, just log the error
            }
        };
    }
}
