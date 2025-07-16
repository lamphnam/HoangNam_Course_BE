package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.ReviewRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewSearchRequestDto;
import org.springframework.data.domain.Page;


public interface ReviewService {
    ReviewResponseDto createReview(String username, ReviewRequestDto request);

    Page<ReviewResponseDto> getReviewsForCourse(Long courseId, ReviewSearchRequestDto build);
}
