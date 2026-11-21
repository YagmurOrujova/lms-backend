package com.example.lms.service;

import com.example.lms.dto.SectionRequest;
import com.example.lms.dto.SectionResponse;
import com.example.lms.entity.Course;
import com.example.lms.entity.Section;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    public SectionService(SectionRepository sectionRepository,
                          CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public SectionResponse create(Long courseId, SectionRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        int nextOrder = (int) sectionRepository.countByCourseId(courseId) + 1;

        Section section = Section.builder()
                .title(request.title())
                .orderIndex(nextOrder)
                .course(course)
                .build();

        return toResponse(sectionRepository.save(section));
    }

    @Transactional(readOnly = true)
    public List<SectionResponse> getByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        return sectionRepository.findByCourseIdOrderByOrderIndexAsc(courseId).stream()
                .map(this::toResponse)
                .toList();
    }

    private SectionResponse toResponse(Section section) {
        return new SectionResponse(
                section.getId(),
                section.getTitle(),
                section.getOrderIndex(),
                section.getCourse().getId()
        );
    }
}
