package com.example.school.mapper;

import com.example.school.dto.StudentDTO;
import com.example.school.model.Student;
import com.example.school.model.User;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    StudentDTO.Response toResponse(Student student);
    
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    StudentDTO.Summary toSummary(Student student);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Student toEntity(StudentDTO.Request request, User user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User createUser(StudentDTO.Request request, String hashedPassword, Set<User.Role> roles);
}