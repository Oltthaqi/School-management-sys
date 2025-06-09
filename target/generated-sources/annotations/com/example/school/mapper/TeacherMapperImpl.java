package com.example.school.mapper;

import com.example.school.dto.TeacherDTO;
import com.example.school.model.Teacher;
import com.example.school.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T14:41:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public TeacherDTO.Response toResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDTO.Response response = new TeacherDTO.Response();

        response.setFirstName( teacherUserFirstName( teacher ) );
        response.setLastName( teacherUserLastName( teacher ) );
        response.setEmail( teacherUserEmail( teacher ) );
        response.setId( teacher.getId() );
        response.setEmployeeId( teacher.getEmployeeId() );
        response.setDepartment( teacher.getDepartment() );
        response.setPhoneNumber( teacher.getPhoneNumber() );
        response.setSpecialization( teacher.getSpecialization() );
        response.setCreatedAt( teacher.getCreatedAt() );
        response.setUpdatedAt( teacher.getUpdatedAt() );

        return response;
    }

    @Override
    public TeacherDTO.Summary toSummary(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDTO.Summary summary = new TeacherDTO.Summary();

        summary.setFirstName( teacherUserFirstName( teacher ) );
        summary.setLastName( teacherUserLastName( teacher ) );
        summary.setId( teacher.getId() );
        summary.setEmployeeId( teacher.getEmployeeId() );
        summary.setDepartment( teacher.getDepartment() );

        return summary;
    }

    @Override
    public Teacher toEntity(TeacherDTO.Request request, User user) {
        if ( request == null && user == null ) {
            return null;
        }

        Teacher.TeacherBuilder teacher = Teacher.builder();

        if ( request != null ) {
            teacher.employeeId( request.getEmployeeId() );
            teacher.department( request.getDepartment() );
            teacher.phoneNumber( request.getPhoneNumber() );
            teacher.specialization( request.getSpecialization() );
        }
        teacher.user( user );

        return teacher.build();
    }

    @Override
    public User createUser(TeacherDTO.Request request, String hashedPassword, Set<User.Role> roles) {
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

    private String teacherUserFirstName(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }
        User user = teacher.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String teacherUserLastName(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }
        User user = teacher.getUser();
        if ( user == null ) {
            return null;
        }
        String lastName = user.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String teacherUserEmail(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }
        User user = teacher.getUser();
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
