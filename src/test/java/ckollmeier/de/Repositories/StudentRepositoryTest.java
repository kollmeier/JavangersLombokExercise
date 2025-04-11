package ckollmeier.de.Repositories;

import ckollmeier.de.Entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudentRepositoryTest {

    private StudentRepository studentRepository;
    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepository();
        // Assuming a constructor or builder exists for Student
        student1 = Student.builder().id("student-1").name("Harry").address("Potter").build(); // Example Student
        student2 = Student.builder().id("student-2").name("Hermione").address("Granger").build(); // Example Student
    }

    @Test
    void save_shouldStoreStudent_whenStudentIsValid() {
        studentRepository.save(student1);
        Optional<Student> found = studentRepository.findById(student1.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(student1);
    }

    @Test
    void save_shouldOverwriteStudent_whenIdExists() {
        studentRepository.save(student1);
        Student updatedStudent1 = student1.withAddress("Potter").withName("Lilly"); // Changed name
        studentRepository.save(updatedStudent1);

        Optional<Student> found = studentRepository.findById(student1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Lilly");
        assertThat(studentRepository.findAll()).hasSize(1); // Ensure no duplicate entry
    }

    @Test
    void save_shouldNotStoreStudent_whenStudentIsNull() {
        studentRepository.save(null);
        assertThat(studentRepository.findAll()).isEmpty();
    }

    @Test
    void save_shouldNotStoreStudent_whenStudentIdIsNull() {
        Student studentWithNullId = Student.builder().id(null).name("Invalid").address("Student").build();
        studentRepository.save(studentWithNullId);
        assertThat(studentRepository.findAll()).isEmpty();
    }

    @Test
    void findById_shouldReturnStudent_whenIdExists() {
        studentRepository.save(student1);
        Optional<Student> found = studentRepository.findById(student1.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(student1);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdDoesNotExist() {
        studentRepository.save(student1);
        Optional<Student> found = studentRepository.findById("non-existent-id");

        assertThat(found).isNotPresent();
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdIsNull() {
        Optional<Student> found = studentRepository.findById(null);
        assertThat(found).isNotPresent();
    }

    @Test
    void deleteById_shouldRemoveStudent_whenIdExists() {
        studentRepository.save(student1);
        studentRepository.save(student2);

        studentRepository.deleteById(student1.getId());

        assertThat(studentRepository.findById(student1.getId())).isNotPresent();
        assertThat(studentRepository.findAll()).hasSize(1);
        assertThat(studentRepository.findAll()).containsExactly(student2);
    }

    @Test
    void deleteById_shouldDoNothing_whenIdDoesNotExist() {
        studentRepository.save(student1);
        studentRepository.deleteById("non-existent-id");

        assertThat(studentRepository.findAll()).hasSize(1);
        assertThat(studentRepository.findById(student1.getId())).isPresent();
    }

    @Test
    void deleteById_shouldDoNothing_whenIdIsNull() {
        studentRepository.save(student1);
        studentRepository.deleteById(null);

        assertThat(studentRepository.findAll()).hasSize(1);
        assertThat(studentRepository.findById(student1.getId())).isPresent();
    }

    @Test
    void findAll_shouldReturnEmptyCollection_whenRepositoryIsEmpty() {
        Collection<Student> students = studentRepository.findAll();
        assertThat(students).isNotNull();
        assertThat(students).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllStudents_whenRepositoryIsNotEmpty() {
        studentRepository.save(student1);
        studentRepository.save(student2);

        Collection<Student> students = studentRepository.findAll();

        assertThat(students).hasSize(2);
        assertThat(students).containsExactlyInAnyOrder(student1, student2);
    }
}