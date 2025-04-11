package ckollmeier.de.Repositories;

import ckollmeier.de.Entities.Course;

import java.util.List;
import java.util.Optional;

public class CourseRepository {
    /**
     * Datenbank zur Speicherung der Kurse.
     */
    private final java.util.Map<String, Course> courseDatabase = new java.util.HashMap<>();

    /**
     * Speichert einen Kurs in der Datenbank.
     *
     * @param course Der zu speichernde Kurs.
     */
    public void save(final Course course) {
        if (course != null && course.getId() != null) {
            courseDatabase.put(course.getId(), course);
        }
    }

    /**
     * Findet einen Kurs in der Datenbank anhand seiner ID.
     *
     * @param id Die ID des zu findenden Kurses.
     * @return Ein Optional mit dem Kurs der gegebenen ID oder ein leeres Optional, falls kein solcher Kurs existiert.
     */
    public Optional<Course> findById(final String id) {
        return Optional.ofNullable(courseDatabase.get(id));
    }

    /**
     * Löscht einen Kurs aus der Datenbank anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Kurses.
     */
    public void deleteById(final String id) {
        courseDatabase.remove(id);
    }

    /**
     * Gibt alle Kurse in der Datenbank zurück.
     *
     * @return Eine Liste aller Kurse.
     */
    public List<Course> findAll() {
        return new java.util.ArrayList<>(courseDatabase.values());
    }
}
