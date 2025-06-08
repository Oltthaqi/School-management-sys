package com.example.school.repository;

import com.example.school.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmployeeId(String employeeId);
    Optional<Teacher> findByUserEmail(String email);
    Optional<Teacher> findByUserId(Long userId);
    Boolean existsByEmployeeId(String employeeId);
    
    @Query("SELECT t FROM Teacher t WHERE t.user.email = :email")
    Optional<Teacher> findByEmail(@Param("email") String email);
}