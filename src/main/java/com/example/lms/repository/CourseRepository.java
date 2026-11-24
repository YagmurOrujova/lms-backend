package com.example.lms.repository;

import com.example.lms.entity.Course;
import com.example.lms.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByCategoryId(Long categoryId);

    @Query("""
            select c from Course c
            where lower(c.title) like :pattern
              and (:categoryId is null or c.category.id = :categoryId)
              and (:level is null or c.level = :level)
              and (:minPrice is null or c.price >= :minPrice)
              and (:maxPrice is null or c.price <= :maxPrice)
              and (:minRating is null or
                   (select coalesce(avg(r.rating), 0) from Review r where r.course = c) >= :minRating)
            order by c.createdAt desc
            """)
    List<Course> search(@Param("pattern") String pattern,
                        @Param("categoryId") Long categoryId,
                        @Param("level") Level level,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        @Param("minRating") Double minRating);
}