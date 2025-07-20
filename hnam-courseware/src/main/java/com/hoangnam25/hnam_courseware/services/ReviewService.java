package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.ReviewRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewSearchRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewUpdateRequestDto;
import org.springframework.data.domain.Page;

import java.util.Map;


public interface ReviewService {
    ReviewResponseDto createReview(String username, ReviewRequestDto request);

    Page<ReviewResponseDto> getReviewsForCourse(Long courseId, ReviewSearchRequestDto build);

    ReviewResponseDto updateReview(Long id, String username, ReviewUpdateRequestDto request);

    Map<String, String> deleteReview(Long id, String username);
}
