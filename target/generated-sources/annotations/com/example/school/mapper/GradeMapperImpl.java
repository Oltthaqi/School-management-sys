package com.example.school.mapper;

import com.example.school.dto.GradeDTO;
import com.example.school.model.Enrollment;
import com.example.school.model.Grade;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T15:50:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class GradeMapperImpl implements GradeMapper {

    @Override
    public GradeDTO.Response toResponse(Grade grade) {
        if ( grade == null ) {
            return null;
        }

        GradeDTO.Response response = new GradeDTO.Response();

        response.setEnrollmentId( gradeEnrollmentId( grade ) );
        response.setId( grade.getId() );
        response.setGradeValue( grade.getGradeValue() );
        response.setLetterGrade( grade.getLetterGrade() );
        response.setGradedAt( grade.getGradedAt() );
        response.setComments( grade.getComments() );
        response.setCreatedAt( grade.getCreatedAt() );
        response.setUpdatedAt( grade.getUpdatedAt() );

        return response;
    }

    @Override
    public Grade toEntity(GradeDTO.Request request, Enrollment enrollment) {
        if ( request == null && enrollment == null ) {
            return null;
        }

        Grade.GradeBuilder grade = Grade.builder();

        if ( request != null ) {
            grade.gradeValue( request.getGradeValue() );
            grade.letterGrade( request.getLetterGrade() );
            grade.comments( request.getComments() );
        }
        grade.enrollment( enrollment );
        grade.gradedAt( java.time.LocalDateTime.now() );

        return grade.build();
    }

    private Long gradeEnrollmentId(Grade grade) {
        if ( grade == null ) {
            return null;
        }
        Enrollment enrollment = grade.getEnrollment();
        if ( enrollment == null ) {
            return null;
        }
        Long id = enrollment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
