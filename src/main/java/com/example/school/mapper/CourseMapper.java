package com.example.school.mapper;

import com.example.school.dto.CourseDTO;
import com.example.school.model.Course;
import com.example.school.model.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class})
public interface CourseMapper {
    
    CourseDTO.Response toResponse(Course course);
    CourseDTO.Summary toSummary(Course course);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseDTO.Request request, Teacher teacher);
}