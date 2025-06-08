package com.example.school.mapper;

import com.example.school.dto.AdmissionDTO;
import com.example.school.model.Admission;
import com.example.school.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StudentMapper.class})
public interface AdmissionMapper {
    
    AdmissionDTO.Response toResponse(Admission admission);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "student")
    @Mapping(target = "appliedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(com.example.school.model.Admission.Status.PENDING)")
    @Mapping(target = "decisionAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Admission toEntity(AdmissionDTO.Request request, Student student);
}