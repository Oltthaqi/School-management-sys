package com.example.school.mapper;

import com.example.school.dto.StudentDTO;
import com.example.school.model.Student;
import com.example.school.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:41:41+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO.Response toResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO.Response response = new StudentDTO.Response();

        response.setFirstName( studentUserFirstName( student ) );
        response.setLastName( studentUserLastName( student ) );
        response.setEmail( studentUserEmail( student ) );
        response.setId( student.getId() );
        response.setStudentId( student.getStudentId() );
        response.setDateOfBirth( student.getDateOfBirth() );
        response.setAdmissionDate( student.getAdmissionDate() );
        response.setPhoneNumber( student.getPhoneNumber() );
        response.setAddress( student.getAddress() );
        response.setCreatedAt( student.getCreatedAt() );
        response.setUpdatedAt( student.getUpdatedAt() );

        return response;
    }

    @Override
    public StudentDTO.Summary toSummary(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO.Summary summary = new StudentDTO.Summary();

        summary.setFirstName( studentUserFirstName( student ) );
        summary.setLastName( studentUserLastName( student ) );
        summary.setEmail( studentUserEmail( student ) );
        summary.setId( student.getId() );
        summary.setStudentId( student.getStudentId() );

        return summary;
    }

    @Override
    public Student toEntity(StudentDTO.Request request, User user) {
        if ( request == null && user == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        if ( request != null ) {
            student.studentId( request.getStudentId() );
            student.dateOfBirth( request.getDateOfBirth() );
            student.admissionDate( request.getAdmissionDate() );
            student.phoneNumber( request.getPhoneNumber() );
            student.address( request.getAddress() );
        }
        student.user( user );

        return student.build();
    }

    @Override
    public User createUser(StudentDTO.Request request, String hashedPassword, Set<User.Role> roles) {
        if ( request == null && hashedPassword == null && roles == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        if ( request != null ) {
            user.email( request.getEmail() );
            user.firstName( request.getFirstName() );
            user.lastName( request.getLastName() );
        }
        user.password( hashedPassword );
        Set<User.Role> set = roles;
        if ( set != null ) {
            user.roles( new LinkedHashSet<User.Role>( set ) );
        }
        user.isActive( true );

        return user.build();
    }

    private String studentUserFirstName(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String studentUserLastName(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String studentUserEmail(Student student) {
        if ( student == null ) {
            return null;
        }
        User user = student.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
