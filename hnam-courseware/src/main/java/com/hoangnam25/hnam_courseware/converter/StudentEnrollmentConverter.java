package com.hoangnam25.hnam_courseware.converter;

import com.hoangnam25.hnam_courseware.model.dtos.StudentEnrollmentResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("studentEnrollmentConverter")
public class StudentEnrollmentConverter extends SuperConverter<StudentEnrollmentResponseDto, Enrollment> {

    private final UserConverter userConverter;
    private final ModelMapper modelMapper;

    public StudentEnrollmentConverter(UserConverter userConverter, ModelMapper modelMapper) {
        this.userConverter = userConverter;
        this.modelMapper = modelMapper;
    }

    @Override
    public StudentEnrollmentResponseDto convertToDTO(Enrollment entity) {

        if (entity == null) {
            return null;
        }

        StudentEnrollmentResponseDto dto = new StudentEnrollmentResponseDto();

        dto.setEnrollmentId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setProgressPercentage(entity.getProgressPercentage());
        dto.setEnrolledDate(LocalDate.from(entity.getEnrolledDate()));

        if (entity.getCompletedDate() != null) {
            dto.setCompletedDate(LocalDate.from(entity.getCompletedDate()));
        } else {
            dto.setCompletedDate(null);
        }

        if (entity.getUser() != null) {
            dto.setUser(userConverter.convertToDTO(entity.getUser()));
        }

        return dto;
    }

    @Override
    public Enrollment convertToEntity(StudentEnrollmentResponseDto dto) {
        return modelMapper.map(dto, Enrollment.class);
    }
}
