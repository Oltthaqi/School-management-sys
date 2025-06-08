package com.example.school.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

public class TeacherDTO {
    
    @Data
    public static class Request {
        @NotBlank(message = "First name is required")
        private String firstName;
        
        @NotBlank(message = "Last name is required")
        private String lastName;
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @NotBlank(message = "Employee ID is required")
        private String employeeId;
        
        @NotBlank(message = "Department is required")
        private String department;
        
        private String phoneNumber;
        private String specialization;
    }
    
    @Data
    public static class Response {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String employeeId;
        private String department;
        private String phoneNumber;
        private String specialization;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    @Data
    public static class Summary {
        private Long id;
        private String firstName;
        private String lastName;
        private String employeeId;
        private String department;
    }
}