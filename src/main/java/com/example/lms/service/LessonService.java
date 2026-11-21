package com.example.lms.service;

import com.example.lms.dto.LessonRequest;
import com.example.lms.dto.LessonResponse;
import com.example.lms.entity.Lesson;
import com.example.lms.entity.Section;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SectionRepository sectionRepository;

    public LessonService(LessonRepository lessonRepository,
                         SectionRepository sectionRepository) {
        this.lessonRepository = lessonRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public LessonResponse create(Long sectionId, LessonRequest request) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found"));

        int nextOrder = (int) lessonRepository.countBySectionId(sectionId) + 1;

        Lesson lesson = Lesson.builder()
                .title(request.title())
                .content(request.content())
                .videoUrl(request.videoUrl())
                .orderIndex(nextOrder)
                .section(section)
                .build();

        return toResponse(lessonRepository.save(lesson));
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> getBySection(Long sectionId) {
        if (!sectionRepository.existsById(sectionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Section not found");
        }
        return lessonRepository.findBySectionIdOrderByOrderIndexAsc(sectionId).stream()
                .map(this::toResponse)
                .toList();
    }

    private LessonResponse toResponse(Lesson lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getContent(),
                lesson.getVideoUrl(),
                lesson.getOrderIndex(),
                lesson.getSection().getId()
        );
    }
}