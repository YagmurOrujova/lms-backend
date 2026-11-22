package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotNull(message = "Student is required")
        Long studentId
) {
}