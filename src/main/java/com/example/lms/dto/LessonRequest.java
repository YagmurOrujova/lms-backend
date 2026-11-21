package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;

public record LessonRequest(
        @NotBlank(message = "Title is required")
        String title,

        String content,

        String videoUrl
) {
}
