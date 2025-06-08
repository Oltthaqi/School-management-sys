package com.example.school.repository;

import com.example.school.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByEnrollmentId(Long enrollmentId);
    
    @Query("SELECT g FROM Grade g WHERE g.enrollment.student.id = :studentId")
    List<Grade> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT g FROM Grade g WHERE g.enrollment.course.id = :courseId")
    List<Grade> findByCourseId(@Param("courseId") Long courseId);
}