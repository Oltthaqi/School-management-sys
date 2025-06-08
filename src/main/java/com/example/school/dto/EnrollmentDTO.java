package com.example.school.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

public class EnrollmentDTO {
    
    @Data
    public static class Request {
        @NotNull(message = "Student ID is required")
        private Long studentId;
        
        @NotNull(message = "Course ID is required")
        private Long courseId;
        
        private String status;
    }
    
    @Data
    public static class Response {
        private Long id;
        private StudentDTO.Summary student;
        private CourseDTO.Summary course;
        private LocalDateTime enrolledAt;
        private String status;
        private GradeDTO.Response grade;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}