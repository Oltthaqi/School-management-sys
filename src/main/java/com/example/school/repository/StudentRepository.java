package com.example.school.repository;

import com.example.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByUserEmail(String email);
    Optional<Student> findByUserId(Long userId);
    Boolean existsByStudentId(String studentId);
    
    @Query("SELECT s FROM Student s WHERE s.user.email = :email")
    Optional<Student> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();
}