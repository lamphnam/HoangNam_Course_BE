package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.CourseConverter;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

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
                new RuntimeException("User not found with username: " + username));

        Course course = courseConverter.convertRequestToEntity(request);

        course.setCreatedDate(LocalDateTime.now());
        course.setStatus(CourseStatus.DRAFT);
        course.setInstructor(user);
        courseRepository.save(course);

        return courseConverter.convertToResponseDTO(course);
    }

    @Override
    public Page<CourseResponseDto> searchCourses(Integer page, Integer size, DirectionEnum direction, String attribute, String title) {
        Sort sortable = Sort.by(Sort.Direction.fromString(direction.name()), attribute);
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<Course> courses;
        if (ObjectUtils.isEmpty(title)) {
            courses = courseRepository.findAllByStatusIn(List.of(CourseStatus.PUBLISHED), pageable);
        } else {
            courses = courseRepository.findAllByStatusInAndTitleLike(List.of(CourseStatus.PUBLISHED), title, pageable);
        }
        return courses.map(courseConverter::convertToResponseDTO);
    }

    @Override
    public CourseResponseDto findCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return courseConverter.convertToResponseDTO(course);
    }

    @Override
    public CourseResponseDto updateCourseById(Long id, CourseUpdateRequestDto request, String username) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id: " + id));

        if (!username.equals(course.getInstructor().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this course");
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id: " + id));

        if (!username.equals(course.getInstructor().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this course");
        }
        courseRepository.deleteById(id);
        return  Map.of("message", "Course deleted successfully");
    }
}
