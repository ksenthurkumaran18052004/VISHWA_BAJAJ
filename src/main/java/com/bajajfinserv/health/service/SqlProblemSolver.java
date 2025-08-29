package com.bajajfinserv.health.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {
    
    private static final Logger log = LoggerFactory.getLogger(SqlProblemSolver.class);
    
    /**
     * Determines the SQL problem based on the last two digits of registration number
     * @param regNo Registration number
     * @return Problem description
     */
    public String getProblemDescription(String regNo) {
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastDigits = Integer.parseInt(lastTwoDigits);
        
        if (lastDigits % 2 == 0) {
            return getQuestion2Description();
        } else {
            return getQuestion1Description();
        }
    }
    
    /**
     * Solves the SQL problem based on the registration number
     * @param regNo Registration number
     * @return SQL query solution
     */
    public String solveSqlProblem(String regNo) {
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastDigits = Integer.parseInt(lastTwoDigits);
        
        if (lastDigits % 2 == 0) {
            return solveQuestion2();
        } else {
            return solveQuestion1();
        }
    }
    
    private String getQuestion1Description() {
        return """
            Question 1: Write a SQL query to find the second highest salary from the employees table.
            
            Table: employees
            Columns: id, name, salary, department
            
            Sample Data:
            id | name    | salary | department
            1  | John    | 50000  | IT
            2  | Jane    | 60000  | HR
            3  | Bob     | 45000  | IT
            4  | Alice   | 70000  | Finance
            5  | Charlie | 55000  | IT
            
            Expected Output: 60000 (second highest salary)
            """;
    }
    
    private String getQuestion2Description() {
        return """
            Question 2: Write a SQL query to find the department with the highest average salary.
            
            Table: employees
            Columns: id, name, salary, department
            
            Sample Data:
            id | name    | salary | department
            1  | John    | 50000  | IT
            2  | Jane    | 60000  | HR
            3  | Bob     | 45000  | IT
            4  | Alice   | 70000  | Finance
            5  | Charlie | 55000  | IT
            6  | Diana   | 65000  | Finance
            7  | Eve     | 40000  | HR
            
            Expected Output: Finance (average salary: 67500)
            """;
    }
    
    private String solveQuestion1() {
        return """
            SELECT MAX(salary) as second_highest_salary
            FROM employees
            WHERE salary < (SELECT MAX(salary) FROM employees)
            """;
    }
    
    private String solveQuestion2() {
        return """
            SELECT department, AVG(salary) as avg_salary
            FROM employees
            GROUP BY department
            ORDER BY avg_salary DESC
            LIMIT 1
            """;
    }
}
