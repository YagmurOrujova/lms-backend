package com.example.lms.repository;

import com.example.lms.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    boolean existsByEnrollmentIdAndLessonId(Long enrollmentId, Long lessonId);

    long countByEnrollmentId(Long enrollmentId);

    List<LessonProgress> findByEnrollmentId(Long enrollmentId);
}