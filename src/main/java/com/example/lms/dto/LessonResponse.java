package com.example.lms.dto;

public record LessonResponse(
        Long id,
        String title,
        String content,
        String videoUrl,
        Integer orderIndex,
        Long sectionId
) {
}