package ckollmeier.de.Entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.With;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents the association between a student, a course, and the student's grade in the course.
 */
@Builder
@With
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class StudentsGradesInCourses {
        /**
         * The student associated with the grade and course; cannot be null.
         */
        private final @NotNull Student student;

        /**
         * The course associated with the student and grade; cannot be null.
         */
        private final @NotNull Course course;

        /**
         * The grade the student received in the course. Must be a positive value and cannot exceed 6.
         */
        private final @EqualsAndHashCode.Exclude @Positive @Max(value = 6) BigDecimal grade;
}
