package com.example.school.service;

import com.example.school.dto.CourseDTO;
import com.example.school.mapper.CourseMapper;
import com.example.school.model.Course;
import com.example.school.model.Teacher;
import com.example.school.repository.CourseRepository;
import com.example.school.repository.TeacherRepository;
import com.example.school.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseMapper courseMapper;

    public List<CourseDTO.Response> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CourseDTO.Response getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found with id: " + id));
        return courseMapper.toResponse(course);
    }

    public CourseDTO.Response createCourse(CourseDTO.Request request) {
        // ensure unique course code
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Course code already exists: " + request.getCourseCode());
        }
        // load teacher
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Teacher not found with id: " + request.getTeacherId()));
        // map & save
        Course course = courseMapper.toEntity(request, teacher);
        course = courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    public CourseDTO.Response updateCourse(Long id, CourseDTO.Request request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found with id: " + id));

        // if code changed, check uniqueness
        if (!course.getCourseCode().equals(request.getCourseCode())
                && courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Course code already exists: " + request.getCourseCode());
        }

        // apply updates
        course.setCourseCode(request.getCourseCode());
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCreditHours(request.getCreditHours());

        // maybe reassign teacher
        if (!course.getTeacher().getId().equals(request.getTeacherId())) {
            Teacher newTeacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Teacher not found with id: " + request.getTeacherId()));
            course.setTeacher(newTeacher);
        }

        course = courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found with id: " + id));
        courseRepository.delete(course);
    }

    public boolean isCurrentTeacher(Long courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) {
            return false;
        }
        Long userId = ((UserPrincipal) auth.getPrincipal()).getId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found with id: " + courseId));

        return course.getTeacher()
                .getUser()
                .getId()
                .equals(userId);
    }

    public  long countAllCourses() {
        return courseRepository.countAllCourses();
    }
}
