package com.example.lms.controller;

import com.example.lms.dto.SectionRequest;
import com.example.lms.dto.SectionResponse;
import com.example.lms.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<SectionResponse> create(@PathVariable Long courseId,
                                                  @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.create(courseId, request));
    }

    @GetMapping
    public List<SectionResponse> getByCourse(@PathVariable Long courseId) {
        return sectionService.getByCourse(courseId);
    }
}