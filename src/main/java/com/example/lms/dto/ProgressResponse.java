package com.example.lms.dto;

import com.example.lms.enums.EnrollmentStatus;

public record ProgressResponse(
        Long enrollmentId,
        Long courseId,
        long totalLessons,
        long completedLessons,
        int percentage,
        EnrollmentStatus status
) {
}
