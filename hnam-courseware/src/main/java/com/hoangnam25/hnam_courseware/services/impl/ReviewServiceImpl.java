package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.ReviewConverter;
import com.hoangnam25.hnam_courseware.exception.BadRequestException;
import com.hoangnam25.hnam_courseware.exception.ForbiddenException;
import com.hoangnam25.hnam_courseware.exception.NotFoundException;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewSearchRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.ReviewUpdateRequestDto;
import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.entity.Review;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;
import com.hoangnam25.hnam_courseware.model.enums.Role;
import com.hoangnam25.hnam_courseware.repository.CourseRepository;
import com.hoangnam25.hnam_courseware.repository.EnrollmentRepository;
import com.hoangnam25.hnam_courseware.repository.ReviewRepository;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.services.ReviewService;
import com.hoangnam25.hnam_courseware.specification.ReviewSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    public ReviewServiceImpl(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    public ReviewResponseDto createReview(String username, ReviewRequestDto request) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));

        Boolean hasEnrolled = enrollmentRepository.existsByUserIdAndCourseIdAndStatusIn(user.getId(),
                course.getId(),
                List.of(EnrollmentStatus.ACTIVE, EnrollmentStatus.COMPLETED));

        if (!hasEnrolled) {
            throw new BadRequestException(ErrorMessage.NOT_ENROLLMENT, "You must enroll in the course before reviewing.");
        }

        if (reviewRepository.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new BadRequestException(ErrorMessage.ALREADY_REVIEWED, "You have already reviewed this course.");
        }

        Review review = new Review();
        review.setUser(user);
        review.setCourse(course);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedDate(LocalDateTime.now());
        review.setUpdatedDate(LocalDateTime.now());

        Review saved = reviewRepository.save(review);
        updateCourseRating(saved.getCourse().getId());

        return reviewConverter.convertToDTO(saved);

    }

    @Override
    public Page<ReviewResponseDto> getReviewsForCourse(Long courseId, ReviewSearchRequestDto request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));

        Specification<Review> spec = ReviewSpecification.belongsToCourse(courseId);

        if (request.getRating() != null) {
            spec = spec.and(ReviewSpecification.hasRating(request.getRating()));
        }

        Sort sortable = Sort.by(Sort.Direction.fromString(request.getDirection().name()), request.getAttribute());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortable);

        Page<Review> reviews = reviewRepository.findAll(spec, pageable);

        return reviews.map(reviewConverter::convertToDTO);
    }

    @Override
    public ReviewResponseDto updateReview(Long id, String username, ReviewUpdateRequestDto request) {
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.REVIEW_NOT_FOUND));
        if (!currentUser.getId().equals(review.getUser().getId())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_AUTHORITY);
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }
        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        review.setUpdatedDate(LocalDateTime.now());
        updateCourseRating(review.getCourse().getId());
        reviewRepository.save(review);
        return reviewConverter.convertToDTO(review);
    }

    @Override
    public Map<String, String> deleteReview(Long id, String username) {
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.REVIEW_NOT_FOUND));
        if (!currentUser.getId().equals(review.getUser().getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_AUTHORITY);
        }
        reviewRepository.delete(review);
        updateCourseRating(review.getCourse().getId());
        return Map.of("message", "Review deleted successfully");
    }

    private void updateCourseRating(Long courseId) {
        BigDecimal newAvgRating = reviewRepository.calculateAverageRating(courseId);
        Long newReviewCount = reviewRepository.countByCourseId(courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));
        course.setAverageRating(newAvgRating != null ? newAvgRating : course.getAverageRating());
        course.setReviewCount(newReviewCount);
        courseRepository.save(course);
    }
}
