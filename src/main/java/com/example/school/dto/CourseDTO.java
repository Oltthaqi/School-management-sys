package com.example.school.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

public class CourseDTO {
    
    @Data
    public static class Request {
        @NotBlank(message = "Course code is required")
        private String courseCode;
        
        @NotBlank(message = "Course name is required")
        private String name;
        
        private String description;
        private Integer creditHours;
        
        @NotNull(message = "Teacher ID is required")
        private Long teacherId;
    }
    
    @Data
    public static class Response {
        private Long id;
        private String courseCode;
        private String name;
        private String description;
        private Integer creditHours;
        private TeacherDTO.Summary teacher;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
    
    @Data
    public static class Summary {
        private Long id;
        private String courseCode;
        private String name;
        private Integer creditHours;
    }
}