package com.example.school.mapper;

import com.example.school.dto.AdmissionDTO;
import com.example.school.model.Admission;
import com.example.school.model.Student;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-08T03:04:06+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AdmissionMapperImpl implements AdmissionMapper {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public AdmissionDTO.Response toResponse(Admission admission) {
        if ( admission == null ) {
            return null;
        }

        AdmissionDTO.Response response = new AdmissionDTO.Response();

        response.setId( admission.getId() );
        response.setStudent( studentMapper.toSummary( admission.getStudent() ) );
        response.setApplicationNumber( admission.getApplicationNumber() );
        if ( admission.getStatus() != null ) {
            response.setStatus( admission.getStatus().name() );
        }
        response.setAppliedAt( admission.getAppliedAt() );
        response.setDecisionAt( admission.getDecisionAt() );
        response.setNotes( admission.getNotes() );
        response.setCreatedAt( admission.getCreatedAt() );
        response.setUpdatedAt( admission.getUpdatedAt() );

        return response;
    }

    @Override
    public Admission toEntity(AdmissionDTO.Request request, Student student) {
        if ( request == null && student == null ) {
            return null;
        }

        Admission.AdmissionBuilder admission = Admission.builder();

        if ( request != null ) {
            admission.applicationNumber( request.getApplicationNumber() );
            admission.notes( request.getNotes() );
        }
        admission.student( student );
        admission.appliedAt( java.time.LocalDateTime.now() );
        admission.status( com.example.school.model.Admission.Status.PENDING );

        return admission.build();
    }
}
