package com.example.school.controller;

import com.example.school.dto.TeacherDTO;
import com.example.school.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeacherDTO.Response>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @teacherService.isCurrentUser(#id))")
    public ResponseEntity<TeacherDTO.Response> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDTO.Response> createTeacher(
            @Valid @RequestBody TeacherDTO.Request request
    ) {
        return ResponseEntity.ok(teacherService.createTeacher(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDTO.Response> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherDTO.Request request
    ) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalTeachers() {
        Long total = teacherService.countAllTeachers();
        return ResponseEntity.ok(total);
    }
}
