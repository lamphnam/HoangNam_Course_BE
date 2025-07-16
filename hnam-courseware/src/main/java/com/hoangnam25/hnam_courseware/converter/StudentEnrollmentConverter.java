package com.hoangnam25.hnam_courseware.converter;

import com.hoangnam25.hnam_courseware.model.dtos.StudentEnrollmentResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("studentEnrollmentConverter")
public class StudentEnrollmentConverter extends SuperConverter<StudentEnrollmentResponseDto, Enrollment> {

    private final ModelMapper modelMapper;

    public StudentEnrollmentConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentEnrollmentResponseDto convertToDTO(Enrollment entity) {

        StudentEnrollmentResponseDto response = modelMapper.map(entity, StudentEnrollmentResponseDto.class);
        response.setEnrollmentId(entity.getId());
        response.setEnrolledDate(entity.getEnrolledDate());
        if (entity.getCompletedDate() != null) {
            response.setCompletedDate(entity.getCompletedDate());
        }
        return response;
    }

    @Override
    public Enrollment convertToEntity(StudentEnrollmentResponseDto dto) {
        return modelMapper.map(dto, Enrollment.class);
    }
}
