package com.example.school.repository;

import com.example.school.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByTeacherId(Long teacherId);
    Boolean existsByCourseCode(String courseCode);

    @Query("SELECT COUNT(c) FROM Course c")
    long countAllCourses();
}