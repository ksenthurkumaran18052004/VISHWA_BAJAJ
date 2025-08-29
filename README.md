# Bajaj Finserv Health Hackathon - Spring Boot Application

## Project Overview

This is a Spring Boot application built for the Bajaj Finserv Health Hackathon (Qualifier 1 - JAVA). The application automatically executes the complete hackathon flow on startup without requiring any manual intervention.

## Features

- **Automatic Webhook Generation**: Sends POST request to generate webhook on application startup
- **SQL Problem Solving**: Determines and solves SQL problems based on registration number
- **Database Storage**: Stores problem details and solutions in H2 in-memory database
- **JWT Authentication**: Uses JWT tokens for secure API communication
- **Solution Submission**: Automatically submits SQL solutions to the webhook URL

## Requirements

- Java 17 or higher
- Maven 3.6 or higher
- Internet connection for API calls

## Project Structure

```
src/main/java/com/bajajfinserv/health/
├── BajajHealthHackathonApplication.java    # Main application class
├── config/
│   └── RestTemplateConfig.java            # RestTemplate configuration
├── dto/
│   ├── WebhookRequest.java                # Webhook generation request DTO
│   ├── WebhookResponse.java               # Webhook generation response DTO
│   └── SolutionRequest.java               # Solution submission request DTO
├── entity/
│   └── SqlProblem.java                    # Database entity for SQL problems
├── repository/
│   └── SqlProblemRepository.java          # Data access layer
└── service/
    ├── ApiService.java                    # HTTP API communication service
    ├── HackathonService.java              # Main orchestration service
    └── SqlProblemSolver.java              # SQL problem solving logic
```

## Configuration

The application is configured through `application.properties`:

- **Participant Information**: Name, Registration Number, Email
- **API Endpoints**: Base URL and paths for webhook generation and submission
- **Database**: H2 in-memory database configuration
- **Timeouts**: HTTP request timeout settings

## How It Works

### 1. Application Startup
- Spring Boot application starts
- `CommandLineRunner` automatically triggers the hackathon flow
- No manual endpoint triggering required

### 2. Webhook Generation
- Sends POST request to: `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
- Request body contains participant details (name, regNo, email)
- Receives webhook URL and access token

### 3. SQL Problem Solving
- Determines question based on last two digits of registration number:
  - **Even digits** → Question 2 (Department with highest average salary)
  - **Odd digits** → Question 1 (Second highest salary)
- Generates appropriate SQL solution

### 4. Solution Storage
- Stores problem description, solution, and webhook details in database
- Tracks submission status and timestamps

### 5. Solution Submission
- Sends POST request to webhook URL with SQL solution
- Uses JWT token in Authorization header
- Updates submission status upon completion

## Running the Application

### Prerequisites
1. Ensure Java 17+ is installed
2. Ensure Maven is installed
3. Internet connection available

### Build and Run

1. **Clone or download the project**

2. **Navigate to project directory**
   ```bash
   cd bajaj-viswa-hack
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

### Alternative: Using JAR file

1. **Build JAR file**
   ```bash
   mvn clean package
   ```

2. **Run JAR file**
   ```bash
   java -jar target/health-hackathon-1.0.0.jar
   ```

## Expected Output

When the application starts, you should see logs similar to:

```
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.BajajHealthHackathonApplication : Bajaj Finserv Health Hackathon Application Starting...
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.BajajHealthHackathonApplication : Executing hackathon flow on startup...
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Starting Bajaj Finserv Health Hackathon flow...
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Step 1: Generating webhook for participant: John Doe
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.ApiService : Generating webhook from URL: https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Webhook generated successfully. Webhook URL: [webhook-url]
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Step 2 & 3: Storing problem and solution for registration number: REG12347
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Problem and solution stored successfully with ID: 1
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Step 4: Submitting solution to webhook URL
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Solution submitted successfully. Response: [response-body]
2024-XX-XX XX:XX:XX.XXX  INFO 12345 --- [main] c.b.h.service.HackathonService : Hackathon flow completed successfully!
```

## Database Access

The application uses H2 in-memory database. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Troubleshooting

### Common Issues

1. **Connection Timeout**: Check internet connection and API endpoint availability
2. **JWT Token Issues**: Verify the access token format and expiration
3. **Database Issues**: Check H2 console for data verification
4. **Build Issues**: Ensure Java 17+ and Maven are properly installed

### Logs

The application provides detailed logging at DEBUG level. Check the console output for:
- API request/response details
- Database operations
- Error messages and stack traces

## Technical Details

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Database**: H2 (in-memory)
- **HTTP Client**: RestTemplate
- **JWT Library**: jjwt
- **Build Tool**: Maven

## Compliance with Requirements

✅ **RestTemplate/WebClient**: Uses RestTemplate for HTTP communication  
✅ **No Controller Endpoints**: Flow triggers automatically on startup  
✅ **JWT Authorization**: Uses JWT tokens in Authorization header  
✅ **Automatic Execution**: No manual intervention required  
✅ **Database Storage**: Stores problem and solution data  
✅ **Error Handling**: Comprehensive error handling and logging  

## Support

For any issues or questions, please refer to the application logs or check the H2 database console for data verification.
