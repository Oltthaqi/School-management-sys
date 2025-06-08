package com.example.school.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

public class AdmissionDTO {
    
    @Data
    public static class Request {
        @NotNull(message = "Student ID is required")
        private Long studentId;
        
        @NotBlank(message = "Application number is required")
        private String applicationNumber;
        
        private String status;
        private String notes;
    }
    
    @Data
    public static class Response {
        private Long id;
        private StudentDTO.Summary student;
        private String applicationNumber;
        private String status;
        private LocalDateTime appliedAt;
        private LocalDateTime decisionAt;
        private String notes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}