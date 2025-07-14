package com.hoangnam25.hnam_courseware.services.impl;

import com.hoangnam25.hnam_courseware.converter.EnrollmentConverter;
import com.hoangnam25.hnam_courseware.converter.StudentEnrollmentConverter;
import com.hoangnam25.hnam_courseware.exception.BadRequestException;
import com.hoangnam25.hnam_courseware.exception.ForbiddenException;
import com.hoangnam25.hnam_courseware.exception.NotFoundException;
import com.hoangnam25.hnam_courseware.model.dtos.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.hoangnam25.hnam_courseware.specification.EnrollmentSpecification.*;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentConverter enrollmentConverter;
    private final StudentEnrollmentConverter studentEnrollmentConverter;

    public EnrollmentServiceImpl(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, EnrollmentConverter enrollmentConverter, StudentEnrollmentConverter studentEnrollmentConverter) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentConverter = enrollmentConverter;
        this.studentEnrollmentConverter = studentEnrollmentConverter;
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

    @Override
    @Transactional
    public EnrollmentResponseDto cancelEnrollment(String username, Long courseId) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));

        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), course.getId())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.ALREADY_ENROLLMENT, "You are not enrolled in this course"));

        if (enrollment.getStatus().equals(EnrollmentStatus.CANCELLED)) {
            throw new BadRequestException(ErrorMessage.ALREADY_UNENROLLMENT, "You already unenrolled this course");
        }
        if (enrollment.getStatus().equals(EnrollmentStatus.COMPLETED)) {
            throw new BadRequestException(ErrorMessage.CANNOT_CANCEL_COMPLETED_COURSE, "Cannot cancel a completed course");
        }
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        Enrollment saved = enrollmentRepository.save(enrollment);

        return enrollmentConverter.convertToDTO(saved);
    }

    @Override
    public Page<EnrollmentResponseDto> getEnrollmentsByUserId(String username, EnrollmentSearchRequestDto request) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));
        Specification<Enrollment> spec = belongsToUser(user.getId());

        if (request.getStatus() != null) {
            spec = spec.and(hasStatus(request.getStatus()));
        }
        if (StringUtils.hasText(request.getTitle())) {
            spec = spec.and(courseTitleContains(request.getTitle()));
        }

        if (request.getEnrolledDate() != null) {
            spec = spec.and(enrolledAfter(request.getEnrolledDate()));
        }
        if (request.getProgressPercentage() != null) {
            spec = spec.and(hasProgressGreaterThan(request.getProgressPercentage()));
        }

        Sort sortable = Sort.by(Sort.Direction.fromString(request.getDirection().name()), request.getAttribute());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortable);
        Page<Enrollment> enrollments = enrollmentRepository.findAll(spec, pageable);
        return enrollments.map(enrollmentConverter::convertToDTO);
    }

    @Override
    public EnrollmentResponseDto getEnrollmentDetailForUser(String username, Long courseId) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "User not found with username: " + username));
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ENROLLMENT_NOT_FOUND, "You have not enrolled in this course"));
        return enrollmentConverter.convertToDTO(enrollment);
    }

    @Override
    public Page<StudentEnrollmentResponseDto> getEnrollmentsForCourse(String username, Long courseId, StudentSearchRequestDto request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COURSE_NOT_FOUND, "Course not found"));
        if (!course.getInstructor().getUsername().equals(username)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN_AUTHORITY, "You are not the instructor of this course.");
        }
        Specification<Enrollment> spec = hasCourseId(courseId);
        if (request.getStudentName() != null) {
            spec = spec.and(studentNameContains(request.getStudentName()));
        }

        if (request.getStatus() != null) {
            spec = spec.and(hasStatus(request.getStatus()));
        }
        Sort sortable = Sort.by(Sort.Direction.fromString(request.getDirection().name()), request.getAttribute());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortable);
        Page<Enrollment> enrollments = enrollmentRepository.findAll(spec, pageable);
        return enrollments.map(studentEnrollmentConverter::convertToDTO);
    }
}
