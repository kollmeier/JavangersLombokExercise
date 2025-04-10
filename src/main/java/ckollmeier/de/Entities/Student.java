package ckollmeier.de.Entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Diese Klasse repräsentiert einen Studenten mit seinen Basisinformationen.
 */
@Value
@Builder
@With
public class Student {
    /**
     * Unique identifier for the student.
     */
    private @NotBlank String id;
    /**
     * Full name of the student.
     */
    private @NotBlank String name;
    /**
     * Residential address of the student.
     */
    private @NotBlank String address;

    /**
     * Courses of the student with their grades.
     */
    private @ToString.Exclude @EqualsAndHashCode.Exclude Map<String, Course> courses = new HashMap<>();

    /**
     * Fügt dem Studenten einen Kurs hinzu.
     * Diese Methode sollte nicht überschrieben werden, da sie die Konsistenz zwischen Studenten und Kursen gewährleisten soll.
     *
     * @param course Der hinzuzufügende Kurs.
     */
    public void addCourse(final Course course) {
        this.courses.put(course.getId(), course);
        course.addStudent(this);
    }

    /**
     * Entfernt einen Kurs vom Studenten.
     * Diese Methode sollte nicht überschrieben werden, da sie die Konsistenz zwischen Studenten und Kursen gewährleisten soll.
     *
     * @param course Der zu entfernende Kurs.
     */
    public void removeCourse(final Course course) {
        this.courses.remove(course.getId());
        course.removeStudent(this);
    }

    /**
     * Retrieves the grade of a specific course for the student.
     * If the course is not found, an empty Optional is returned.
     *
     * @param courseId The ID of the course to retrieve the grade for.
     * @return An Optional containing the grade as a BigDecimal, or an empty Optional if the course is not present.
     */
    public Optional<BigDecimal> getGradeInCourse(final String courseId) {
        return Optional.ofNullable(this.courses.get(courseId))
                .flatMap(course -> course.getGradeForStudent(this.id));
    }

    /**
     * Calculates and returns the average grade of all courses the student is enrolled in.
     * If there are no grades, an empty Optional is returned.
     *
     * @return An Optional containing the average grade as a BigDecimal, or an empty Optional if no grades are present.
     */
    public Optional<BigDecimal> getAverageGrade() {
        if (this.courses.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;

        for (Course course : courses.values()) {
            Optional<BigDecimal> grade = course.getGradeForStudent(this.id);
            if (grade.isPresent()) {
                sum = sum.add(grade.get());
                count++;
            }
        }

        return count > 0
                ? Optional.of(sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP))
                : Optional.empty();
    }
}
