package com.example.school.service;

import com.example.school.dto.TeacherDTO;
import com.example.school.mapper.TeacherMapper;
import com.example.school.model.Teacher;
import com.example.school.model.User;
import com.example.school.repository.TeacherRepository;
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
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<TeacherDTO.Response> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TeacherDTO.Response getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        return teacherMapper.toResponse(teacher);
    }

    public TeacherDTO.Response createTeacher(TeacherDTO.Request request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        if (teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists: " + request.getEmployeeId());
        }

        String hashedPassword = passwordEncoder.encode("defaultTeacherPass"); // or generate/prompt
        Set<User.Role> roles = Set.of(User.Role.ROLE_TEACHER);
        User user = teacherMapper.createUser(request, hashedPassword, roles);
        user = userRepository.save(user);

        Teacher teacher = teacherMapper.toEntity(request, user);
        teacher = teacherRepository.save(teacher);

        return teacherMapper.toResponse(teacher);
    }

    public TeacherDTO.Response updateTeacher(Long id, TeacherDTO.Request request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));

        User user = teacher.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setDepartment(request.getDepartment());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setSpecialization(request.getSpecialization());
        teacher = teacherRepository.save(teacher);

        return teacherMapper.toResponse(teacher);
    }

    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        teacherRepository.delete(teacher);
    }

    public boolean isCurrentUser(Long teacherId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) return false;
        Long currentUserId = ((UserPrincipal) auth.getPrincipal()).getId();

        return teacherRepository.findByUserId(currentUserId)
                .map(t -> t.getId().equals(teacherId))
                .orElse(false);
    }
}
