package com.example.school.controller;

import com.example.school.dto.CourseDTO;
import com.example.school.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // List all courses (ADMIN or TEACHER)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<CourseDTO.Response>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Get one course (ADMIN or owning TEACHER)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @courseService.isCurrentTeacher(#id))")
    public ResponseEntity<CourseDTO.Response> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // Create course (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO.Response> createCourse(
            @Valid @RequestBody CourseDTO.Request request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    // Update course (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDTO.Response> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO.Request request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    // Delete course (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
