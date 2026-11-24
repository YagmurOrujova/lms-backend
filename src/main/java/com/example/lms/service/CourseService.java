package com.example.lms.service;

import com.example.lms.dto.CourseRequest;
import com.example.lms.dto.CourseResponse;
import com.example.lms.entity.Category;
import com.example.lms.entity.Course;
import com.example.lms.entity.User;
import com.example.lms.enums.Level;
import com.example.lms.enums.Role;
import com.example.lms.repository.CategoryRepository;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.ReviewRepository;
import com.example.lms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public CourseService(CourseRepository courseRepository,
                         CategoryRepository categoryRepository,
                         UserRepository userRepository,
                         ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public CourseResponse create(CourseRequest request) {
        User teacher = userRepository.findById(request.teacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
        if (teacher.getRole() != Role.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a teacher");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Course course = Course.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .level(request.level())
                .teacher(teacher)
                .category(category)
                .build();

        return toResponse(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> search(String keyword,
                                       Long categoryId,
                                       Level level,
                                       BigDecimal minPrice,
                                       BigDecimal maxPrice,
                                       Double minRating) {
        String pattern = (keyword == null || keyword.isBlank())
                ? "%"
                : "%" + keyword.trim().toLowerCase() + "%";

        return courseRepository.search(pattern, categoryId, level, minPrice, maxPrice, minRating).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        return toResponse(course);
    }

    private CourseResponse toResponse(Course course) {
        Double avg = reviewRepository.findAverageRatingByCourseId(course.getId());
        double averageRating = avg == null ? 0.0 : Math.round(avg * 10.0) / 10.0;
        long reviewCount = reviewRepository.countByCourseId(course.getId());

        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getLevel(),
                course.getCategory().getId(),
                course.getCategory().getName(),
                course.getTeacher().getId(),
                course.getTeacher().getFullName(),
                course.getCreatedAt(),
                averageRating,
                reviewCount
        );
    }
}