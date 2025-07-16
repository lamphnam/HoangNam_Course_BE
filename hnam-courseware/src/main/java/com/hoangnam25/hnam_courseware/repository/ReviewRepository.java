package com.hoangnam25.hnam_courseware.repository;

import com.hoangnam25.hnam_courseware.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Override
    @EntityGraph(value = "Review.withUserAndCourse")
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);

}
