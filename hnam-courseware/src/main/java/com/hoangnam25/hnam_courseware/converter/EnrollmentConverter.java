package com.hoangnam25.hnam_courseware.converter;

import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("enrollmentConverter")
public class EnrollmentConverter extends SuperConverter<EnrollmentResponseDto, Enrollment> {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public EnrollmentResponseDto convertToDTO(Enrollment entity) {
        return modelMapper.map(entity, EnrollmentResponseDto.class);
    }

    @Override
    public Enrollment convertToEntity(EnrollmentResponseDto dto) {
        return modelMapper.map(dto, Enrollment.class);
    }
}
