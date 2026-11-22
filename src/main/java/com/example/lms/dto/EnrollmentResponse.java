package com.example.lms.dto;

import com.example.lms.enums.EnrollmentStatus;

import java.time.LocalDateTime;

public record EnrollmentResponse(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        String courseTitle,
        EnrollmentStatus status,
        LocalDateTime enrolledAt
) {
}
