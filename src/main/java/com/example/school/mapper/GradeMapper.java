package com.example.school.mapper;

import com.example.school.dto.GradeDTO;
import com.example.school.model.Enrollment;
import com.example.school.model.Grade;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    
    @Mapping(target = "enrollmentId", source = "enrollment.id")
    GradeDTO.Response toResponse(Grade grade);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", source = "enrollment")
    @Mapping(target = "gradedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Grade toEntity(GradeDTO.Request request, Enrollment enrollment);
}