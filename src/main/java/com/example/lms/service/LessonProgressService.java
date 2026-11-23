package com.example.lms.service;

import com.example.lms.dto.ProgressResponse;
import com.example.lms.entity.Enrollment;
import com.example.lms.entity.Lesson;
import com.example.lms.entity.LessonProgress;
import com.example.lms.enums.EnrollmentStatus;
import com.example.lms.repository.EnrollmentRepository;
import com.example.lms.repository.LessonProgressRepository;
import com.example.lms.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    public LessonProgressService(LessonProgressRepository lessonProgressRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 LessonRepository lessonRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    public ProgressResponse completeLesson(Long enrollmentId, Long lessonId) {
        Enrollment enrollment = findEnrollment(enrollmentId);

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        Long lessonCourseId = lesson.getSection().getCourse().getId();
        if (!lessonCourseId.equals(enrollment.getCourse().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lesson does not belong to this course");
        }

        if (lessonProgressRepository.existsByEnrollmentIdAndLessonId(enrollmentId, lessonId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Lesson is already completed");
        }

        LessonProgress progress = LessonProgress.builder()
                .enrollment(enrollment)
                .lesson(lesson)
                .build();
        lessonProgressRepository.save(progress);

        ProgressResponse response = buildProgress(enrollment);
        if (response.percentage() == 100 && enrollment.getStatus() != EnrollmentStatus.COMPLETED) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            return buildProgress(enrollment);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public ProgressResponse getProgress(Long enrollmentId) {
        return buildProgress(findEnrollment(enrollmentId));
    }

    private Enrollment findEnrollment(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
    }

    private ProgressResponse buildProgress(Enrollment enrollment) {
        Long courseId = enrollment.getCourse().getId();
        long total = lessonRepository.countBySectionCourseId(courseId);
        long completed = lessonProgressRepository.countByEnrollmentId(enrollment.getId());
        int percentage = total == 0 ? 0 : (int) (completed * 100 / total);

        return new ProgressResponse(
                enrollment.getId(),
                courseId,
                total,
                completed,
                percentage,
                enrollment.getStatus()
        );
    }
}