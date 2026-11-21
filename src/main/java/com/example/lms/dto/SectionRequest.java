package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;

public record SectionRequest(
        @NotBlank(message = "Title is required")
        String title
) {
}