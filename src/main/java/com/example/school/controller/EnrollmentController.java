// src/main/java/com/example/school/controller/EnrollmentController.java
package com.example.school.controller;

import com.example.school.dto.EnrollmentDTO;
import com.example.school.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // ADMIN or TEACHER can list
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<EnrollmentDTO.Response>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    // ADMIN/TEACHER or student themself
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or (hasRole('STUDENT') and @enrollmentService.isCurrentUser(#id))")
    public ResponseEntity<EnrollmentDTO.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    // ADMIN only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO.Response> create(
            @Valid @RequestBody EnrollmentDTO.Request request) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(request));
    }

    // ADMIN only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO.Response> update(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentDTO.Request request) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, request));
    }

    // ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok().build();
    }
}
