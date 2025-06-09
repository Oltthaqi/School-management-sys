package com.example.school.mapper;

import com.example.school.dto.EnrollmentDTO;
import com.example.school.model.Course;
import com.example.school.model.Enrollment;
import com.example.school.model.Student;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:41:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public EnrollmentDTO.Response toResponse(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }

        EnrollmentDTO.Response response = new EnrollmentDTO.Response();

        response.setId( enrollment.getId() );
        response.setStudent( studentMapper.toSummary( enrollment.getStudent() ) );
        response.setCourse( courseMapper.toSummary( enrollment.getCourse() ) );
        response.setEnrolledAt( enrollment.getEnrolledAt() );
        if ( enrollment.getStatus() != null ) {
            response.setStatus( enrollment.getStatus().name() );
        }
        response.setGrade( gradeMapper.toResponse( enrollment.getGrade() ) );
        response.setCreatedAt( enrollment.getCreatedAt() );
        response.setUpdatedAt( enrollment.getUpdatedAt() );

        return response;
    }

    @Override
    public Enrollment toEntity(EnrollmentDTO.Request request, Student student, Course course) {
        if ( request == null && student == null && course == null ) {
            return null;
        }

        Enrollment.EnrollmentBuilder enrollment = Enrollment.builder();

        enrollment.student( student );
        enrollment.course( course );
        enrollment.enrolledAt( java.time.LocalDateTime.now() );
        enrollment.status( com.example.school.model.Enrollment.Status.ACTIVE );

        return enrollment.build();
    }
}
