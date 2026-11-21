package com.example.lms.dto;

import com.example.lms.enums.Level;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CourseRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        BigDecimal price,

        @NotNull(message = "Level is required")
        Level level,

        @NotNull(message = "Category is required")
        Long categoryId,

        @NotNull(message = "Teacher is required")
        Long teacherId
) {
}
