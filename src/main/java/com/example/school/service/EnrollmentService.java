package com.example.school.service;

import com.example.school.dto.EnrollmentDTO;
import com.example.school.mapper.EnrollmentMapper;
import com.example.school.model.Course;
import com.example.school.model.Enrollment;
import com.example.school.model.Student;
import com.example.school.repository.CourseRepository;
import com.example.school.repository.EnrollmentRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    public List<EnrollmentDTO.Response> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO.Response getEnrollmentById(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Enrollment not found with id: " + id));
        return enrollmentMapper.toResponse(e);
    }

    public EnrollmentDTO.Response createEnrollment(EnrollmentDTO.Request request) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                request.getStudentId(), request.getCourseId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student already enrolled in this course");
        }

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id: " + request.getStudentId()));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found with id: " + request.getCourseId()));

        Enrollment e = enrollmentMapper.toEntity(request, student, course);
        e = enrollmentRepository.save(e);
        return enrollmentMapper.toResponse(e);
    }

    public EnrollmentDTO.Response updateEnrollment(Long id, EnrollmentDTO.Request request) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Enrollment not found with id: " + id));

        if (request.getStatus() != null) {
            e.setStatus(Enrollment.Status.valueOf(request.getStatus().toUpperCase()));
        }
        // Note: changing studentId/courseId on an existing enrollment is usually not allowed

        e = enrollmentRepository.save(e);
        return enrollmentMapper.toResponse(e);
    }

    public void deleteEnrollment(Long id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Enrollment not found with id: " + id));
        enrollmentRepository.delete(e);
    }

    public boolean isCurrentUser(Long enrollmentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) return false;
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();

        Enrollment e = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Enrollment not found with id: " + enrollmentId));

        return e.getStudent().getUser().getId().equals(userId);
    }
}
