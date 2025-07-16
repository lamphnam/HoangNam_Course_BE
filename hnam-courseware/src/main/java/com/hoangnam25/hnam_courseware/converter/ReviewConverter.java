package com.hoangnam25.hnam_courseware.converter;


import com.hoangnam25.hnam_courseware.model.dtos.ReviewResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Review;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("reviewConverter")
public class ReviewConverter extends SuperConverter<ReviewResponseDto, Review> {
    private final ModelMapper modelMapper;

    public ReviewConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewResponseDto convertToDTO(Review entity) {
        ReviewResponseDto response = modelMapper.map(entity, ReviewResponseDto.class);
        response.setCourseId(entity.getCourse().getId());
        return response;
    }

    @Override
    public Review convertToEntity(ReviewResponseDto dto) {
        return modelMapper.map(dto, Review.class);
    }
}
