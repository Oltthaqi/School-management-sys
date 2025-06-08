package com.example.school.repository;

import com.example.school.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Optional<Admission> findByApplicationNumber(String applicationNumber);
    Optional<Admission> findByStudentId(Long studentId);
    Boolean existsByApplicationNumber(String applicationNumber);
}