package com.hoangnam25.hnam_courseware.repository;

import com.hoangnam25.hnam_courseware.model.entity.Course;
import com.hoangnam25.hnam_courseware.model.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @EntityGraph(attributePaths = {"instructor"})
    Page<Course> findAllByStatusIn(Collection<CourseStatus> status, Pageable pageable);

    @EntityGraph(attributePaths = {"instructor"})
    Page<Course> findAllByStatusInAndTitleLike(Collection<CourseStatus> status, String title, Pageable pageable);

    Course findAllById(Long id);

    Page<Course> findAllByInstructorUsername(String username, Pageable pageable);

}
