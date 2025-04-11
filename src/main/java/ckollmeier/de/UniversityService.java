package ckollmeier.de;

import ckollmeier.de.Entities.Course;
import ckollmeier.de.Entities.Student;
import ckollmeier.de.Entities.Teacher;
import ckollmeier.de.Repositories.CourseRepository;
import ckollmeier.de.Repositories.StudentRepository;
import ckollmeier.de.Repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UniversityService {

    /**
     * Adds a list of students to a specific course.
     *
     * @param courseId   The ID of the course to which students will be added.
     * @param studentIds A list of student IDs to be added to the course.
     * @throws IllegalArgumentException if the course or any of the students are not found.
     */
    public void addStudentsToCourse(final String courseId, final List<String> studentIds) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        for (String studentId : studentIds) {
            var student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
            student.addCourse(course);
        }
    }

    /**
     * Repository for managing teacher entities.
     */
    private final TeacherRepository teacherRepository;
    /**
     * Repository for managing course entities.
     */
    private final CourseRepository courseRepository;
    /**
     * Repository for managing student entities.
     */
    private final StudentRepository studentRepository;

    /**
     * Creates and saves a student in the repository.
     *
     * @param student The student to be created. Must not be null.
     */
    public void createStudent(final Student student) {
        studentRepository.save(student);
    }

    /**
     * Creates and saves a teacher in the repository.
     *
     * @param teacher The teacher to be created. Must not be null.
     */
    public void createTeacher(final Teacher teacher) {
        teacherRepository.save(teacher);
    }

    /**
     * Creates and saves a course in the repository.
     *
     * @param course The course to be created. Must not be null.
     */
    public void createCourse(final Course course) {
        courseRepository.save(course);
    }


    /**
     * Retrieves the average grade for all courses.
     *
     * @return A map where the key is the course ID and the value is the average grade for that course,
     * or an empty map if no courses or grades are found.
     */
    public Optional<BigDecimal> calculateAverageGrade() {
        var courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalSum = BigDecimal.ZERO;
        int totalGrades = 0;

        for (Course course : courses) {
            Optional<BigDecimal> grade = course.getAverageGrade();
            if (grade.isEmpty()) {
                continue;
            }
            totalSum = totalSum.add(grade.get());
            totalGrades++;
        }

        return totalGrades > 0
                ? Optional.of(totalSum.divide(BigDecimal.valueOf(totalGrades), RoundingMode.HALF_UP))
                : Optional.empty();
    }

    /**
     * Retrieves a list of students whose average grade is better than or equals to the given grade.
     *
     * @param grade The grade to compare the students' average grade against.
     * @return A list of students with an average grade better than or equals to the given grade.
     */
    public List<Student> getStudentsWithAverageGradeBetterOrEquals(final BigDecimal grade) {
        return studentRepository.findAll().stream()
                .filter(s -> s.getAverageGrade()
                        .orElse(grade.add(BigDecimal.ONE))
                        .compareTo(grade) <= 0)
                .toList();
    }

    /**
     * Prints details of all students, teachers, and courses managed by the service.
     */
    public void printDetails() {
        System.out.println("Students:");
        studentRepository.findAll().forEach(student ->
                System.out.printf("  - Name: %-20s ID: %s | ⌀%s%n", student.getName(), student.getId(), student.getAverageGrade().orElse(BigDecimal.ZERO).setScale(1, RoundingMode.HALF_UP))
        );

        System.out.println("Teachers:");
        teacherRepository.findAll().forEach(teacher ->
                System.out.printf("  - Name: %-20s ID: %s | %d courses%n", teacher.name(), teacher.id(), courseRepository.findAll().stream().filter(course -> course.getTeacher().id().equals(teacher.id())).count())
        );

        System.out.println("Courses:");
        courseRepository.findAll().forEach(course ->
                System.out.printf("  - Name: %-30s ID: %s | %d students with ⌀%s%n", course.getName(), course.getId(), course.getStudents().size(), course.getAverageGrade().orElse(BigDecimal.ZERO).setScale(1, RoundingMode.HALF_UP))
        );
    }
}
