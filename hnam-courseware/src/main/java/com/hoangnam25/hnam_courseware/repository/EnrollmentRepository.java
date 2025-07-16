package com.hoangnam25.hnam_courseware.repository;

import com.hoangnam25.hnam_courseware.model.entity.Enrollment;
import com.hoangnam25.hnam_courseware.model.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment> {
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    @Override
    @EntityGraph(value = "Enrollment.withDetails")
    Page<Enrollment> findAll(Specification<Enrollment> spec, Pageable pageable);

    Boolean existsByUserIdAndCourseIdAndStatusIn(Long userId, Long courseId, List<EnrollmentStatus> active);
}
