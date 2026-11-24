package com.example.lms.controller;

import com.example.lms.dto.ReviewRequest;
import com.example.lms.dto.ReviewResponse;
import com.example.lms.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@PathVariable Long courseId,
                                                 @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(courseId, request));
    }

    @GetMapping
    public List<ReviewResponse> getByCourse(@PathVariable Long courseId) {
        return reviewService.getByCourse(courseId);
    }
}
