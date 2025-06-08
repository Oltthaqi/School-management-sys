package com.example.school.mapper;

import com.example.school.dto.CourseDTO;
import com.example.school.model.Course;
import com.example.school.model.Teacher;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T03:04:06+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public CourseDTO.Response toResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.Response response = new CourseDTO.Response();

        response.setId( course.getId() );
        response.setCourseCode( course.getCourseCode() );
        response.setName( course.getName() );
        response.setDescription( course.getDescription() );
        response.setCreditHours( course.getCreditHours() );
        response.setTeacher( teacherMapper.toSummary( course.getTeacher() ) );
        response.setCreatedAt( course.getCreatedAt() );
        response.setUpdatedAt( course.getUpdatedAt() );

        return response;
    }

    @Override
    public CourseDTO.Summary toSummary(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.Summary summary = new CourseDTO.Summary();

        summary.setId( course.getId() );
        summary.setCourseCode( course.getCourseCode() );
        summary.setName( course.getName() );
        summary.setCreditHours( course.getCreditHours() );

        return summary;
    }

    @Override
    public Course toEntity(CourseDTO.Request request, Teacher teacher) {
        if ( request == null && teacher == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        if ( request != null ) {
            course.courseCode( request.getCourseCode() );
            course.name( request.getName() );
            course.description( request.getDescription() );
            course.creditHours( request.getCreditHours() );
        }
        course.teacher( teacher );

        return course.build();
    }
}
