package ckollmeier.de.Repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ckollmeier.de.Entities.Student;

public class StudentRepository {
    /**
     * Datenbank zur Speicherung der Studenten.
     */
    private final Map<String, Student> studentDatabase = new HashMap<>();

    /**
     * Saves a student to the repository.
     *
     * @param student the student to save; must not be null and must contain a valid ID
     */
    public void save(final Student student) {
        if (student != null && student.getId() != null) {
            studentDatabase.put(student.getId(), student);
        }
    }

    /**
     * Finds a student in the repository by their ID.
     *
     * @param id the ID of the student to find
     * @return an Optional containing the student associated with the given ID, or an empty Optional if no such student exists
     */
    public Optional<Student> findById(final String id) {
        return Optional.ofNullable(studentDatabase.get(id));
    }

    /**
     * Deletes a student from the repository by their ID.
     *
     * @param id the ID of the student to be deleted
     */
    public void deleteById(final String id) {
        studentDatabase.remove(id);
    }
    /**
     * Finds all students in the repository.
     *
     * @return a collection of all students
     */
    public Collection<Student> findAll() {
        return studentDatabase.values();
    }
}
