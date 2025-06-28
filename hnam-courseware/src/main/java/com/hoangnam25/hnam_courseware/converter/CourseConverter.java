package com.hoangnam25.hnam_courseware.converter;

import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("courseConverter")
public class CourseConverter extends Super2Converter<CourseRequestDto, CourseResponseDto, Course> {

    private final ModelMapper modelMapper;

    public CourseConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CourseResponseDto convertToResponseDTO(Course entity) {
        return modelMapper.map(entity, CourseResponseDto.class);
    }

    @Override
    public Course convertRequestToEntity(CourseRequestDto dto) {
        return modelMapper.map(dto, Course.class);
    }

    @Override
    public CourseRequestDto convertEntityToRequest(Course request) {
        return modelMapper.map(request, CourseRequestDto.class);
    }
}
