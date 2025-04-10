package ckollmeier.de;

import ckollmeier.de.Entities.Course;
import ckollmeier.de.Entities.Student;
import ckollmeier.de.Entities.Teacher;
import ckollmeier.de.Repositories.CourseRepository;
import ckollmeier.de.Repositories.StudentRepository;
import ckollmeier.de.Repositories.TeacherRepository;

import java.math.BigDecimal;

public class Main {

    public static void main(final String[] args) {
        UniversityService universityService = createSampleUniversityService();

        universityService.printDetails();

        System.out.println("\n\nAverage grade of our Students: ");
        universityService.calculateAverageGrade().ifPresent(System.out::println);

        System.out.println("\nOur students with grades better than average:");
        universityService.getStudentsWithAverageGradeBetterOrEquals(universityService.calculateAverageGrade().orElse(BigDecimal.TEN))
                .forEach(
                        student -> System.out.format("%s: %s\n",
                                student.getName(),
                                student.getAverageGrade().orElse(BigDecimal.ZERO)
                                        .setScale(1).toPlainString()

                                )
                );
    }

    private static UniversityService createSampleUniversityService() {
        UniversityService universityService = new UniversityService(
                new TeacherRepository(),
                new CourseRepository(),
                new StudentRepository()
        );
        // Create Teacher
        Teacher teacher1 = new Teacher("1", "Dewey Finn", "Unknown Address");
        universityService.createTeacher(teacher1);

        // Create Teacher
        Teacher teacher2 = new Teacher("2", "Ned Schneebly", "Unknown Address");
        universityService.createTeacher(teacher2);

        // Create Students
        Student student1 = Student.builder()
                .id("1")
                .name("Zack Mooneyham")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student1);

        Student student2 = Student.builder()
                .id("2")
                .name("Freddy Jones")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student2);

        Student student3 = Student.builder()
                .id("3")
                .name("Katie")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student3);

        Student student4 = Student.builder()
                .id("4")
                .name("Lawrence")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student4);

        Student student5 = Student.builder()
                .id("5")
                .name("Summer Hathaway")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student5);

        Student student6 = Student.builder()
                .id("6")
                .name("Tomika")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student6);

        Student student7 = Student.builder()
                .id("7")
                .name("Billy")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student7);

        Student student8 = Student.builder()
                .id("8")
                .name("Marta")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student8);

        Student student9 = Student.builder()
                .id("9")
                .name("Alicia")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student9);

        Student student10 = Student.builder()
                .id("10")
                .name("Gordon")
                .address("Unknown Address")
                .build();
        universityService.createStudent(student10);

        // Create first Course
        Course course1 = Course.builder()
                .id("1")
                .name("Rock Band 101")
                .teacher(teacher2)
                .build();
        universityService.createCourse(course1);

        course1.addStudent(student1);
        course1.setGrade(student1.getId(), "2");
        course1.addStudent(student2);
        course1.setGrade(student2.getId(), "3");
        course1.addStudent(student3);
        course1.setGrade(student3.getId(), "2.3");
        course1.addStudent(student4);
        course1.setGrade(student4.getId(), "5");
        course1.addStudent(student5);
        course1.setGrade(student5.getId(), "3.7");
        course1.addStudent(student10);
        course1.setGrade(student10.getId(), "1.3");


        // Create second Course
        Course course2 = Course.builder()
                .id("2")
                .name("Advanced Rock Theory")
                .teacher(teacher2)
                .build();
        universityService.createCourse(course2);

        course2.addStudent(student3);
        course2.setGrade(student3.getId(), "3.3");
        course2.addStudent(student4);
        course2.setGrade(student4.getId(), "2");
        course2.addStudent(student5);
        course2.setGrade(student5.getId(), "2.7");
        course2.addStudent(student6);
        course2.setGrade(student6.getId(), "1");
        course2.addStudent(student7);
        course2.setGrade(student7.getId(), "1.3");
        course2.addStudent(student8);
        course2.setGrade(student8.getId(), "2.3");
        course2.addStudent(student9);
        course2.setGrade(student9.getId(), "2");
        course2.addStudent(student10);
        course2.addStudent(student10);
        course2.setGrade(student10.getId(), "1.7");

        Course course3 = Course.builder()
                .id("3")
                .name("Mathematics for musicians")
                .teacher(teacher1)
                .build();

        course3.addStudent(student1);
        course3.setGrade(student1.getId(), "4");
        course3.addStudent(student2);
        course3.setGrade(student2.getId(), "5");
        course3.addStudent(student3);
        course3.setGrade(student3.getId(), "3.3");
        course3.addStudent(student8);
        course3.setGrade(student8.getId(), "4");
        course3.addStudent(student9);
        course3.setGrade(student9.getId(), "3");
        course3.addStudent(student10);
        course3.addStudent(student10);
        course3.setGrade(student10.getId(), "1.0");

        universityService.createCourse(course3);

        return universityService;
    }
}