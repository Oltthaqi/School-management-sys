package com.example.school.controller;

import com.example.school.dto.StudentDTO;
import com.example.school.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<StudentDTO.Response>> getAllStudents() {
        List<StudentDTO.Response> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or (hasRole('STUDENT') and @studentService.isCurrentUser(#id))")
    public ResponseEntity<StudentDTO.Response> getStudentById(@PathVariable Long id) {
        StudentDTO.Response student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO.Response> createStudent(@Valid @RequestBody StudentDTO.Request request) {
        StudentDTO.Response student = studentService.createStudent(request);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO.Response> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO.Request request) {
        StudentDTO.Response student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}