package com.hoangnam25.hnam_courseware.services;

import com.hoangnam25.hnam_courseware.converter.CourseConverter;
import com.hoangnam25.hnam_courseware.model.dtos.CourseRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.CourseResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.CourseStatus;
import com.hoangnam25.hnam_courseware.repository.CourseRepository;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseConverter courseConverter;

    public CourseService(UserRepository userRepository, CourseRepository courseRepository,  CourseConverter courseConverter) {
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
}
