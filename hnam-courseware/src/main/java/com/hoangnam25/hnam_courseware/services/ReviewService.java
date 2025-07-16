package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.model.dtos.ReviewRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(String username, ReviewRequestDto request);
}
