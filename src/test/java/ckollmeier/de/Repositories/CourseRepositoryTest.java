package ckollmeier.de.Repositories;

import ckollmeier.de.Entities.Course;
import ckollmeier.de.Entities.Teacher; // Assuming Course might need a Teacher
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryTest {

    /**
     * Das CourseRepository für die Tests.
     */
    private CourseRepository courseRepository;
    /**
     * Der erste Testkurs.
     */
    private Course course1;
    /**
     * Der zweite Testkurs.
     */
    private Course course2;


    @BeforeEach
    void setUp() {
        courseRepository = new CourseRepository();
        // Assuming a constructor or builder exists for Course
        // You might need to mock or create a Teacher instance if required by Course
        Teacher dummyTeacher = Teacher.builder().id("teacher-1").name("Prof. Snape").address("Hogwarts").build(); // Example Teacher
        course1 = Course.builder().id("course-101").name("Potions").teacher(dummyTeacher).build(); // Example Course
        course2 = Course.builder().id("course-102").name("Defense Against the Dark Arts").teacher(dummyTeacher).build(); // Example Course
    }

    @Test
    void save_shouldStoreCourse_whenCourseIsValid() {
        courseRepository.save(course1);
        Optional<Course> found = courseRepository.findById(course1.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(course1);
    }

    @Test
    void save_shouldOverwriteCourse_whenIdExists() {
        courseRepository.save(course1);
        Course updatedCourse1 = course1.withName("Advanced Potions and some nice drinks");
        courseRepository.save(updatedCourse1);

        Optional<Course> found = courseRepository.findById(course1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Advanced Potions and some nice drinks");
        assertThat(courseRepository.findAll()).hasSize(1); // Ensure no duplicate entry
    }

    @Test
    void save_shouldNotStoreCourse_whenCourseIsNull() {
        courseRepository.save(null);
        assertThat(courseRepository.findAll()).isEmpty();
    }

    @Test
    void save_shouldNotStoreCourse_whenCourseIdIsNull() {
        Course courseWithNullId = Course.builder().id(null).name("Invalid Course").teacher(null).build();
        courseRepository.save(courseWithNullId);
        assertThat(courseRepository.findAll()).isEmpty();
    }

    @Test
    void findById_shouldReturnCourse_whenIdExists() {
        courseRepository.save(course1);
        Optional<Course> found = courseRepository.findById(course1.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(course1);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdDoesNotExist() {
        courseRepository.save(course1);
        Optional<Course> found = courseRepository.findById("non-existent-id");

        assertThat(found).isNotPresent();
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdIsNull() {
        Optional<Course> found = courseRepository.findById(null);
        assertThat(found).isNotPresent();
    }

    @Test
    void deleteById_shouldRemoveCourse_whenIdExists() {
        courseRepository.save(course1);
        courseRepository.save(course2);

        courseRepository.deleteById(course1.getId());

        assertThat(courseRepository.findById(course1.getId())).isNotPresent();
        assertThat(courseRepository.findAll()).hasSize(1);
        assertThat(courseRepository.findAll()).containsExactly(course2);
    }

    @Test
    void deleteById_shouldDoNothing_whenIdDoesNotExist() {
        courseRepository.save(course1);
        courseRepository.deleteById("non-existent-id");

        assertThat(courseRepository.findAll()).hasSize(1);
        assertThat(courseRepository.findById(course1.getId())).isPresent();
    }

    @Test
    void deleteById_shouldDoNothing_whenIdIsNull() {
        courseRepository.save(course1);
        courseRepository.deleteById(null);

        assertThat(courseRepository.findAll()).hasSize(1);
        assertThat(courseRepository.findById(course1.getId())).isPresent();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        List<Course> courses = courseRepository.findAll();
        assertThat(courses).isNotNull();
        assertThat(courses).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllCourses_whenRepositoryIsNotEmpty() {
        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findAll();

        assertThat(courses).hasSize(2);
        assertThat(courses).containsExactlyInAnyOrder(course1, course2);
    }
}