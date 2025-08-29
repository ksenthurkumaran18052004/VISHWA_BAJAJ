package com.bajajfinserv.health.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlProblemSolverTest {

    @Autowired
    private SqlProblemSolver sqlProblemSolver;

    @Test
    void testEvenRegistrationNumber_ShouldReturnQuestion2() {
        // Test with even registration number (REG12346 - ends with 46)
        String regNo = "REG12346";
        
        String problemDescription = sqlProblemSolver.getProblemDescription(regNo);
        String solution = sqlProblemSolver.solveSqlProblem(regNo);
        
        assertNotNull(problemDescription);
        assertNotNull(solution);
        assertTrue(problemDescription.contains("Question 2"));
        assertTrue(problemDescription.contains("department with the highest average salary"));
        assertTrue(solution.contains("GROUP BY department"));
    }

    @Test
    void testOddRegistrationNumber_ShouldReturnQuestion1() {
        // Test with odd registration number (REG12347 - ends with 47)
        String regNo = "REG12347";
        
        String problemDescription = sqlProblemSolver.getProblemDescription(regNo);
        String solution = sqlProblemSolver.solveSqlProblem(regNo);
        
        assertNotNull(problemDescription);
        assertNotNull(solution);
        assertTrue(problemDescription.contains("Question 1"));
        assertTrue(problemDescription.contains("second highest salary"));
        assertTrue(solution.contains("MAX(salary)"));
    }

    @Test
    void testRegistrationNumberWithZero_ShouldReturnQuestion2() {
        // Test with registration number ending in 00 (even)
        String regNo = "REG12300";
        
        String problemDescription = sqlProblemSolver.getProblemDescription(regNo);
        String solution = sqlProblemSolver.solveSqlProblem(regNo);
        
        assertNotNull(problemDescription);
        assertNotNull(solution);
        assertTrue(problemDescription.contains("Question 2"));
    }
}
