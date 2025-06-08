package com.example.school.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.time.LocalDateTime;

public class GradeDTO {
    
    @Data
    public static class Request {
        @NotNull(message = "Enrollment ID is required")
        private Long enrollmentId;
        
        @NotNull(message = "Grade value is required")
        @DecimalMin(value = "0.0", message = "Grade must be at least 0.0")
        @DecimalMax(value = "100.0", message = "Grade must not exceed 100.0")
        private Double gradeValue;
        
        private String letterGrade;
        private String comments;
    }
    
    @Data
    public static class Response {
        private Long id;
        private Long enrollmentId;
        private Double gradeValue;
        private String letterGrade;
        private LocalDateTime gradedAt;
        private String comments;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}