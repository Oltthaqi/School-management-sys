package com.example.school.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentDTO {
    
    @Data
    public static class Request {
        @NotBlank(message = "First name is required")
        private String firstName;
        
        @NotBlank(message = "Last name is required")
        private String lastName;
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @NotBlank(message = "Student ID is required")
        private String studentId;
        
        private LocalDate dateOfBirth;
        private LocalDate admissionDate;
        private String phoneNumber;
        private String address;
    }
    
    @Data
    public static class Response {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String studentId;
        private LocalDate dateOfBirth;
        private LocalDate admissionDate;
        private String phoneNumber;
        private String address;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    @Data
    public static class Summary {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String studentId;
    }
}