package com.example.lms.dto;

public record SectionResponse(
        Long id,
        String title,
        Integer orderIndex,
        Long courseId
) {
}