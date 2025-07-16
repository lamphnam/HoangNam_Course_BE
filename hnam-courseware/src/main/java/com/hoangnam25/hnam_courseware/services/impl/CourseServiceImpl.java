package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.CourseConverter;
import com.hoangnam25.hnam_courseware.model.dtos.CourseSearchRequestDto;
import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;
import com.hoangnam25.hnam_courseware.exception.ForbiddenException;
import com.hoangnam25.hnam_courseware.exception.NotFoundException;
import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseResponseDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseUpdateRequestDto;
import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.CourseStatus;
import com.hoangnam25.hnam_courseware.model.enums.DirectionEnum;
import com.hoangnam25.hnam_courseware.repository.CourseRepository;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.services.CourseService;
import com.hoangnam25.hnam_courseware.specification.CourseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseConverter courseConverter;

    public CourseServiceImpl(UserRepository userRepository, CourseRepository courseRepository, CourseConverter courseConverter) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseConverter = courseConverter;
    }

    public CourseResponseDto createCourse(CourseRequestDto request, String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);

        Users user = userOptional.orElseThrow(() ->
                new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));

        Course course = courseConverter.convertRequestToEntity(request);

        course.setCreatedDate(LocalDateTime.now());
        course.setStatus(request.getStatus() != null ? request.getStatus() : CourseStatus.DRAFT);
        course.setInstructor(user);
        courseRepository.save(course);

        return courseConverter.convertToResponseDTO(course);
    }

    @Override
    public Page<CourseResponseDto> searchCourses(CourseSearchRequestDto request) {
        Specification<Course> spec = CourseSpecification.hasStatus(CourseStatus.PUBLISHED)
                .and(CourseSpecification.hasTitleLike(request.getTitle()))
                .and(CourseSpecification.hasDifficulty(request.getDifficulty()))
                .and(CourseSpecification.hasPriceBetween(request.getMinPrice(), request.getMaxPrice()))
                .and(CourseSpecification.hasMinRating(request.getMinRating()));

        Sort sortable = Sort.by(Sort.Direction.fromString(request.getDirection().name()), request.getAttribute());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortable);

        Page<Course> courses = courseRepository.findAll(spec, pageable);

        return courses.map(courseConverter::convertToResponseDTO);
    }

    @Override
    public CourseResponseDto findCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found with id: " + id));
        return courseConverter.convertToResponseDTO(course);
    }

    @Override
    public CourseResponseDto updateCourseById(Long id, CourseUpdateRequestDto request, String username) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found with id: " + id));

        if (!username.equals(course.getInstructor().getUsername())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_AUTHORITY, "You are not authorized to update this course");
        }
        if (request.getTitle() != null) {
            course.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            course.setPrice(request.getPrice());
        }
        if (request.getCurrency() != null) {
            course.setCurrency(request.getCurrency());
        }
        if (request.getDifficulty() != null) {
            course.setDifficulty(request.getDifficulty());
        }
        if (request.getImageUrl() != null) {
            course.setImageUrl(request.getImageUrl());
        }
        courseRepository.save(course);
        return courseConverter.convertToResponseDTO(course);

    }

    @Override
    public Map<String, String> deleteCourseById(Long id, String username) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found with id: " + id));

        if (!username.equals(course.getInstructor().getUsername())) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_AUTHORITY, "You are not authorized to update this course");
        }
        courseRepository.deleteById(id);
        return Map.of("message", "Course deleted successfully");
    }

    @Override
    public Page<CourseResponseDto> getInstructorCourses(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Course> courses = courseRepository.findAllByInstructorUsername(username, pageable);
        return courses.map(courseConverter::convertToResponseDTO);
    }
}
