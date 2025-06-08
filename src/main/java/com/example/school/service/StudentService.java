package com.example.school.service;

import com.example.school.dto.StudentDTO;
import com.example.school.mapper.StudentMapper;
import com.example.school.model.Student;
import com.example.school.model.User;
import com.example.school.repository.StudentRepository;
import com.example.school.repository.UserRepository;
import com.example.school.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<StudentDTO.Response> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StudentDTO.Response getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return studentMapper.toResponse(student);
    }

    public StudentDTO.Response createStudent(StudentDTO.Request request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        if (studentRepository.existsByStudentId(request.getStudentId())) {
            throw new RuntimeException("Student ID already exists: " + request.getStudentId());
        }

        String hashedPassword = passwordEncoder.encode("defaultPassword123"); // Default password
        Set<User.Role> roles = Set.of(User.Role.ROLE_STUDENT);
        User user = studentMapper.createUser(request, hashedPassword, roles);
        user = userRepository.save(user);

        // Create student
        Student student = studentMapper.toEntity(request, user);
        student = studentRepository.save(student);

        return studentMapper.toResponse(student);
    }

    public StudentDTO.Response updateStudent(Long id, StudentDTO.Request request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        // Update user information
        User user = student.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        // Update student information
        student.setStudentId(request.getStudentId());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setAddress(request.getAddress());
        student = studentRepository.save(student);

        return studentMapper.toResponse(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    public boolean isCurrentUser(Long studentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Student student = studentRepository.findByUserId(userPrincipal.getId()).orElse(null);
            return student != null && student.getId().equals(studentId);
        }
        return false;
    }
}