package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.EnrollmentConverter;
import com.hoangnam25.hnam_courseware.exception.BadRequestException;
import com.hoangnam25.hnam_courseware.exception.NotFoundException;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentRequestDto;
import com.hoangnam25.hnam_courseware.model.dtos.EnrollmentResponseDto;
import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import com.hoangnam25.hnam_courseware.model.entity.Users;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import com.hoangnam25.hnam_courseware.model.enums.ErrorMessage;
import com.hoangnam25.hnam_courseware.repository.CourseRepository;
import com.hoangnam25.hnam_courseware.repository.EnrollmentRepository;
import com.hoangnam25.hnam_courseware.repository.UserRepository;
import com.hoangnam25.hnam_courseware.services.EnrollmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentConverter enrollmentConverter;

    public EnrollmentServiceImpl(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, EnrollmentConverter enrollmentConverter) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentConverter = enrollmentConverter;
    }

    @Override
    @Transactional
    public EnrollmentResponseDto enrollUserToCourse(EnrollmentRequestDto request, String username) {
        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));

        if (Objects.equals(course.getInstructor().getId(), currentUser.getId())) {
            throw new BadRequestException(ErrorMessage.INVALID_ENROLLMENT, "Instructor cannot enroll in own course");
        }

        Optional<Enrollment> existingEnrollmentOpt = enrollmentRepository.findByUserIdAndCourseId(currentUser.getId(), course.getId());

        Enrollment enrollmentToSave;

        if (existingEnrollmentOpt.isPresent()) {
            Enrollment existingEnrollment = existingEnrollmentOpt.get();
            EnrollmentStatus currentStatus = existingEnrollment.getStatus();

            if (currentStatus == EnrollmentStatus.ACTIVE || currentStatus == EnrollmentStatus.COMPLETED) {
                throw new BadRequestException(ErrorMessage.ALREADY_ENROLLMENT, "You have already enrolled in this course");
            }

            existingEnrollment.setStatus(EnrollmentStatus.ACTIVE);
            existingEnrollment.setEnrolledDate(LocalDateTime.now());
            existingEnrollment.setCompletedDate(null);
            enrollmentToSave = existingEnrollment;

        } else {
            Enrollment newEnrollment = new Enrollment();

            newEnrollment.setUser(currentUser);
            newEnrollment.setCourse(course);
            newEnrollment.setStatus(EnrollmentStatus.ACTIVE);
            newEnrollment.setProgressPercentage(BigDecimal.ZERO);
            newEnrollment.setEnrolledDate(LocalDateTime.now());

            enrollmentToSave = newEnrollment;
        }
        Enrollment savedEnrollment = enrollmentRepository.save(enrollmentToSave);

        return enrollmentConverter.convertToDTO(savedEnrollment);
    }
}
