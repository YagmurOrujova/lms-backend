package com.example.lms.repository;

import com.example.lms.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Review> findByCourseId(Long courseId);

    long countByCourseId(Long courseId);

    @Query("select avg(r.rating) from Review r where r.course.id = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") Long courseId);
}