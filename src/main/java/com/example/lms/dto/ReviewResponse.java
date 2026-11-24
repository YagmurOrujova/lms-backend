package com.example.lms.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
}