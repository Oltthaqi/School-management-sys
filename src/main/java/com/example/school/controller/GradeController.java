// src/main/java/com/example/school/controller/GradeController.java
package com.example.school.controller;

import com.example.school.dto.GradeDTO;
import com.example.school.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<GradeDTO.Response>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    // ADMIN/TEACHER or the owning STUDENT can fetch a specific grade
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or (hasRole('STUDENT') and @gradeService.isCurrentUser(#id))")
    public ResponseEntity<GradeDTO.Response> getGradeById(@PathVariable Long id) {
        return ResponseEntity.ok(gradeService.getGradeById(id));
    }

    // TEACHER (and optionally ADMIN) can assign a new grade
    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<GradeDTO.Response> createGrade(
            @Valid @RequestBody GradeDTO.Request request) {
        return ResponseEntity.ok(gradeService.createGrade(request));
    }

    // TEACHER can update an existing grade
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<GradeDTO.Response> updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody GradeDTO.Request request) {
        return ResponseEntity.ok(gradeService.updateGrade(id, request));
    }

    // ADMIN only: remove a grade record
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok().build();
    }
}
