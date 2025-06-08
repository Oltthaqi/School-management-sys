// src/main/java/com/example/school/service/GradeService.java
package com.example.school.service;

import com.example.school.dto.GradeDTO;
import com.example.school.mapper.GradeMapper;
import com.example.school.model.Enrollment;
import com.example.school.model.Grade;
import com.example.school.repository.EnrollmentRepository;
import com.example.school.repository.GradeRepository;
import com.example.school.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private GradeMapper gradeMapper;

    public List<GradeDTO.Response> getAllGrades() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public GradeDTO.Response getGradeById(Long id) {
        Grade g = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grade not found with id: " + id));
        return gradeMapper.toResponse(g);
    }

    public GradeDTO.Response createGrade(GradeDTO.Request request) {
        // one grade per enrollment
        if (gradeRepository.findByEnrollmentId(request.getEnrollmentId()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Grade already exists for enrollment: " + request.getEnrollmentId());
        }

        Enrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Enrollment not found with id: " + request.getEnrollmentId()));

        Grade g = gradeMapper.toEntity(request, enrollment);
        g = gradeRepository.save(g);
        return gradeMapper.toResponse(g);
    }

    public GradeDTO.Response updateGrade(Long id, GradeDTO.Request request) {
        Grade g = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grade not found with id: " + id));

        if (request.getGradeValue() != null) {
            g.setGradeValue(request.getGradeValue());
        }
        if (request.getLetterGrade() != null) {
            g.setLetterGrade(request.getLetterGrade());
        }
        if (request.getComments() != null) {
            g.setComments(request.getComments());
        }
        // refresh gradedAt
        g.setGradedAt(LocalDateTime.now());

        g = gradeRepository.save(g);
        return gradeMapper.toResponse(g);
    }

    public void deleteGrade(Long id) {
        Grade g = gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grade not found with id: " + id));
        gradeRepository.delete(g);
    }

    public boolean isCurrentUser(Long gradeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) return false;
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();

        Grade g = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grade not found with id: " + gradeId));

        return g.getEnrollment().getStudent().getUser().getId().equals(userId);
    }
}
