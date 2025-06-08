package com.example.school.controller;

import com.example.school.dto.AdmissionDTO;
import com.example.school.service.AdmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<AdmissionDTO.Response>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or (hasRole('STUDENT') and @admissionService.isCurrentUser(#id))")
    public ResponseEntity<AdmissionDTO.Response> getAdmissionById(@PathVariable Long id) {
        return ResponseEntity.ok(admissionService.getAdmissionById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdmissionDTO.Response> createAdmission(
            @Valid @RequestBody AdmissionDTO.Request request) {
        return ResponseEntity.ok(admissionService.createAdmission(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdmissionDTO.Response> updateAdmission(
            @PathVariable Long id,
            @Valid @RequestBody AdmissionDTO.Request request) {
        return ResponseEntity.ok(admissionService.updateAdmission(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        admissionService.deleteAdmission(id);
        return ResponseEntity.ok().build();
    }
}
