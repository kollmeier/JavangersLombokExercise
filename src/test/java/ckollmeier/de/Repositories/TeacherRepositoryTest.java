package ckollmeier.de.Repositories;

import ckollmeier.de.Entities.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherRepositoryTest {

    private TeacherRepository teacherRepository;
    private Teacher teacher1;
    private Teacher teacher2;

    @BeforeEach
    void setUp() {
        teacherRepository = new TeacherRepository();
        // Assuming a constructor or builder exists for Teacher
        teacher1 = Teacher.builder().id("teacher-1").name("Snape").address("Hogwarts").build(); // Example Teacher
        teacher2 = Teacher.builder().id("teacher-2").name("McGonagall").address("Hogwarts").build(); // Example Teacher
    }

    @Test
    void save_shouldStoreTeacher_whenTeacherIsValid() {
        teacherRepository.save(teacher1);
        Optional<Teacher> found = teacherRepository.findById(teacher1.id());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(teacher1);
    }

    @Test
    void save_shouldOverwriteTeacher_whenIdExists() {
        teacherRepository.save(teacher1);
        Teacher updatedTeacher1 = teacher1.withAddress("Askaban"); // Changed address
        teacherRepository.save(updatedTeacher1);

        Optional<Teacher> found = teacherRepository.findById(teacher1.id());
        assertThat(found).isPresent();
        assertThat(found.get().address()).isEqualTo("Askaban");
        assertThat(teacherRepository.findAll()).hasSize(1); // Ensure no duplicate entry
    }

    @Test
    void save_shouldNotStoreTeacher_whenTeacherIsNull() {
        teacherRepository.save(null);
        assertThat(teacherRepository.findAll()).isEmpty();
    }

    @Test
    void save_shouldNotStoreTeacher_whenTeacherIdIsNull() {
        Teacher teacherWithNullId = Teacher.builder().id(null).name("Invalid").address("Teacher").build();
        teacherRepository.save(teacherWithNullId);
        assertThat(teacherRepository.findAll()).isEmpty();
    }

    @Test
    void findById_shouldReturnTeacher_whenIdExists() {
        teacherRepository.save(teacher1);
        Optional<Teacher> found = teacherRepository.findById(teacher1.id());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(teacher1);
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdDoesNotExist() {
        teacherRepository.save(teacher1);
        Optional<Teacher> found = teacherRepository.findById("non-existent-id");

        assertThat(found).isNotPresent();
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdIsNull() {
        Optional<Teacher> found = teacherRepository.findById(null);
        assertThat(found).isNotPresent();
    }

    @Test
    void deleteById_shouldRemoveTeacher_whenIdExists() {
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        teacherRepository.deleteById(teacher1.id());

        assertThat(teacherRepository.findById(teacher1.id())).isNotPresent();
        assertThat(teacherRepository.findAll()).hasSize(1);
        assertThat(teacherRepository.findAll()).containsExactly(teacher2);
    }

    @Test
    void deleteById_shouldDoNothing_whenIdDoesNotExist() {
        teacherRepository.save(teacher1);
        teacherRepository.deleteById("non-existent-id");

        assertThat(teacherRepository.findAll()).hasSize(1);
        assertThat(teacherRepository.findById(teacher1.id())).isPresent();
    }

    @Test
    void deleteById_shouldDoNothing_whenIdIsNull() {
        teacherRepository.save(teacher1);
        teacherRepository.deleteById(null);

        assertThat(teacherRepository.findAll()).hasSize(1);
        assertThat(teacherRepository.findById(teacher1.id())).isPresent();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        List<Teacher> teachers = teacherRepository.findAll();
        assertThat(teachers).isNotNull();
        assertThat(teachers).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllTeachers_whenRepositoryIsNotEmpty() {
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        List<Teacher> teachers = teacherRepository.findAll();

        assertThat(teachers).hasSize(2);
        assertThat(teachers).containsExactlyInAnyOrder(teacher1, teacher2);
    }
}