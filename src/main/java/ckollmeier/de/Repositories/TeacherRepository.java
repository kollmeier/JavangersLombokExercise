package ckollmeier.de.Repositories;

import ckollmeier.de.Entities.Teacher;

public class TeacherRepository {
    /**
     * Datenbank zur Speicherung der Lehrer.
     */
    private final java.util.Map<String, Teacher> teacherDatabase = new java.util.HashMap<>();

    /**
     * Speichert einen Lehrer in der Datenbank.
     *
     * @param teacher Der zu speichernde Lehrer.
     */
    public void save(final Teacher teacher) {
        if (teacher != null && teacher.id() != null) {
            teacherDatabase.put(teacher.id(), teacher);
        }
    }

    /**
     * Findet einen Lehrer in der Datenbank anhand seiner ID.
     *
     * @param id Die ID des zu findenden Lehrers.
     * @return Der Lehrer mit der gegebenen ID oder null, falls kein solcher Lehrer existiert.
     */
    public Teacher findById(final String id) {
        return teacherDatabase.get(id);
    }

    /**
     * Löscht einen Lehrer aus der Datenbank anhand seiner ID.
     *
     * @param id Die ID des zu löschenden Lehrers.
     */
    public void deleteById(final String id) {
        teacherDatabase.remove(id);
    }

    /**
     * Gibt alle Lehrer in der Datenbank zurück.
     *
     * @return Eine Liste aller Lehrer.
     */
    public java.util.List<Teacher> findAll() {
        return new java.util.ArrayList<>(teacherDatabase.values());
    }
}

