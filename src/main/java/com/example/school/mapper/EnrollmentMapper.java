package com.example.school.mapper;

import com.example.school.dto.EnrollmentDTO;
import com.example.school.model.Course;
import com.example.school.model.Enrollment;
import com.example.school.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class, GradeMapper.class})
public interface EnrollmentMapper {
    
    EnrollmentDTO.Response toResponse(Enrollment enrollment);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "student")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "enrolledAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(com.example.school.model.Enrollment.Status.ACTIVE)")
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Enrollment toEntity(EnrollmentDTO.Request request, Student student, Course course);
}