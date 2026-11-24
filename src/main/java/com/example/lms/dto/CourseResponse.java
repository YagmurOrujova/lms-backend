package com.example.lms.dto;

import com.example.lms.enums.Level;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        Level level,
        Long categoryId,
        String categoryName,
        Long teacherId,
        String teacherName,
        LocalDateTime createdAt,
        Double averageRating,
        long reviewCount
) {
}