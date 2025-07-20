package com.hoangnam25.hnam_courseware.controller;

import com.hoangnam25.hnam_courseware.model.dtos.ReviewRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewUpdateRequestDto;
import com.hoangnam25.hnam_courseware.response.Response;
import com.hoangnam25.hnam_courseware.services.ReviewService;
import com.hoangnam25.hnam_courseware.utils.SecurityUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping()
    public Response createReview(@RequestBody ReviewRequestDto request) {
        String username = SecurityUtil.getUsername();
        return new Response(reviewService.createReview(username, request));
    }

    @PatchMapping("/{id}")
    public Response updateReview(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto request) {
        String username =  SecurityUtil.getUsername();
        return new Response(reviewService.updateReview(id, username, request));
    }
    @DeleteMapping("/{id}")
    public Response deleteReview(@PathVariable Long id) {
        String username =  SecurityUtil.getUsername();
        return new Response(reviewService.deleteReview(id, username));
    }
}
