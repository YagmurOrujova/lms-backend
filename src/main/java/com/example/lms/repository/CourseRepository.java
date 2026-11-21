package com.example.lms.repository;

import com.example.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByCategoryId(Long categoryId);
}
