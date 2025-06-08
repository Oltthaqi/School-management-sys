package com.example.school.mapper;

import com.example.school.dto.TeacherDTO;
import com.example.school.model.Teacher;
import com.example.school.model.User;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    TeacherDTO.Response toResponse(Teacher teacher);
    
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    TeacherDTO.Summary toSummary(Teacher teacher);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Teacher toEntity(TeacherDTO.Request request, User user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "firstName", source = "request.firstName")
    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User createUser(TeacherDTO.Request request, String hashedPassword, Set<User.Role> roles);
}