package ckollmeier.de.Entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Diese Klasse repräsentiert einen Kurs mit seinen Basisinformationen.
 */
@Value
@Builder
@With
public class Course {
    /**
     * Die ID des Kurses.
     */
    private @NotBlank String id;
    /**
     * Der Name des Kurses.
     */
    private @NotBlank String name;
    /**
     * Der Dozent, der den Kurs unterrichtet.
     */
    private @NotNull Teacher teacher;
    /**
     * Eine Map, die Studenten anhand ihrer ID speichert.
     */
    private @EqualsAndHashCode.Exclude @NotNull Map<String, StudentsGradesInCourses> studentsGrades = new HashMap<>();

    /**
     * Fügt einen Studenten zum Kurs hinzu.
     *
     * @param student Der hinzuzufügende Student. Der Student muss eine gültige ID besitzen.
     */
    public void addStudent(final Student student) {
        if (student != null && student.getId() != null) {
            if (this.studentsGrades.containsKey(student.getId())) {
                return;
            }
            this.studentsGrades.put(student.getId(), StudentsGradesInCourses
                    .builder()
                    .student(student)
                    .course(this)
                    .build());
            student.addCourse(this);
        }
    }

    /**
     * Setzt die Note eines Studenten in diesem Kurs.
     *
     * @param studentId Die ID des Studenten.
     * @param grade     Die Note des Studenten.
     */
    public void setGrade(final String studentId, final String grade) {
        StudentsGradesInCourses studentGrade = studentsGrades.getOrDefault(studentId, null);
        if (studentGrade == null) {
            throw new IllegalArgumentException("Student not found.");
        }

        studentsGrades.put(studentId, studentGrade.withGrade(new BigDecimal(grade)));
    }

    /**
     * Entfernt einen Studenten aus dem Kurs.
     *
     * @param student Der zu entfernende Student.
     */
    public void removeStudent(final @NonNull Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("Student has no id.");
        }
        if (this.studentsGrades.remove(student.getId()) != null) {
            student.removeCourse(this);
        }
    }

    /**
     * Retrieves the grade of a specific student in the course.
     *
     * @param studentId The ID of the student whose grade is to be retrieved.
     * @return An Optional containing the grade of the student as a BigDecimal, or an empty Optional if the student ID is not found.
     */
    public Optional<BigDecimal> getGradeForStudent(final @NonNull String studentId) {
        if (!studentsGrades.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found.");
        }
        return Optional.ofNullable(studentsGrades.get(studentId).getGrade());
    }

    /**
     * Calculates the average grade of all students in this course.
     *
     * @return An Optional containing the average grade as a BigDecimal, or Optional.empty() if there are no students or grades.
     */
    public Optional<BigDecimal> getAverageGrade() {
        long count = studentsGrades.values().stream()
                .map(StudentsGradesInCourses::getGrade)
                .filter(Objects::nonNull)
                .count();

        if (count == 0) {
            return Optional.empty();
        }

        BigDecimal sum = studentsGrades.values().stream()
                .map(StudentsGradesInCourses::getGrade)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Optional.of(sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP));
    }

    /**
     * Retrieves all the students enrolled in the course.
     *
     * @return A Set of students currently enrolled in the course.
     */
    public Set<Student> getStudents() {
        return this.studentsGrades.values().stream()
                .map(StudentsGradesInCourses::getStudent)
                .collect(Collectors.toSet());
    }
}
