package com.example.lms.controller;

import com.example.lms.dto.ProgressResponse;
import com.example.lms.service.LessonProgressService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments/{enrollmentId}")
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }

    @PostMapping("/lessons/{lessonId}/complete")
    public ProgressResponse completeLesson(@PathVariable Long enrollmentId,
                                           @PathVariable Long lessonId) {
        return lessonProgressService.completeLesson(enrollmentId, lessonId);
    }

    @GetMapping("/progress")
    public ProgressResponse getProgress(@PathVariable Long enrollmentId) {
        return lessonProgressService.getProgress(enrollmentId);
    }
}