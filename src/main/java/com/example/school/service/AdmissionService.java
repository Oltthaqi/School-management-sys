package com.example.school.service;

import com.example.school.dto.AdmissionDTO;
import com.example.school.mapper.AdmissionMapper;
import com.example.school.model.Admission;
import com.example.school.model.Student;
import com.example.school.repository.AdmissionRepository;
import com.example.school.repository.StudentRepository;
import com.example.school.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdmissionMapper admissionMapper;

    public List<AdmissionDTO.Response> getAllAdmissions() {
        return admissionRepository.findAll().stream()
                .map(admissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AdmissionDTO.Response getAdmissionById(Long id) {
        Admission adm = admissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admission not found with id: " + id));
        return admissionMapper.toResponse(adm);
    }

    public AdmissionDTO.Response createAdmission(AdmissionDTO.Request request) {
        // 1) Unique applicationNumber
        if (admissionRepository.existsByApplicationNumber(request.getApplicationNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Application number already exists: " + request.getApplicationNumber());
        }

        // 2) Load the Student entity
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id: " + request.getStudentId()));

        // 3) Map & persist
        Admission adm = admissionMapper.toEntity(request, student);
        adm = admissionRepository.save(adm);

        return admissionMapper.toResponse(adm);
    }

    public AdmissionDTO.Response updateAdmission(Long id, AdmissionDTO.Request request) {
        Admission adm = admissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admission not found with id: " + id));

        // 1) Optionally ensure applicationNumber stays unique
        if (!adm.getApplicationNumber().equals(request.getApplicationNumber())
                && admissionRepository.existsByApplicationNumber(request.getApplicationNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Application number already exists: " + request.getApplicationNumber());
        }

        // 2) Apply updates
        adm.setApplicationNumber(request.getApplicationNumber());
        if (request.getStatus() != null) {
            adm.setStatus(Admission.Status.valueOf(request.getStatus().toUpperCase()));
            adm.setDecisionAt(LocalDateTime.now());
        }
        adm.setNotes(request.getNotes());

        adm = admissionRepository.save(adm);
        return admissionMapper.toResponse(adm);
    }

    public void deleteAdmission(Long id) {
        Admission adm = admissionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admission not found with id: " + id));
        admissionRepository.delete(adm);
    }

    public boolean isCurrentUser(Long admissionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) return false;
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();

        Admission adm = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Admission not found with id: " + admissionId));

        return adm.getStudent().getUser().getId().equals(userId);
    }
}
