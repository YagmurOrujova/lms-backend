package com.example.lms.controller;

import com.example.lms.dto.LessonRequest;
import com.example.lms.dto.LessonResponse;
import com.example.lms.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections/{sectionId}/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public ResponseEntity<LessonResponse> create(@PathVariable Long sectionId,
                                                 @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.create(sectionId, request));
    }

    @GetMapping
    public List<LessonResponse> getBySection(@PathVariable Long sectionId) {
        return lessonService.getBySection(sectionId);
    }
}