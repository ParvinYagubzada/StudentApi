package az.code.springweb.daos;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import az.code.springweb.services.Random;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository("memory")
public class StudentDAOMemory implements StudentDAO {
    List<Student> students = new ArrayList<>();

    {
        Faker faker = new Faker();
        IntStream.rangeClosed(1, 100).forEach(
                ignored -> students.add(
                        Student.builder()
                                .id(Random.getId())
                                .name(faker.name().firstName())
                                .surname(faker.name().lastName())
                                .grades(IntStream
                                        .rangeClosed(1, 15)
                                        .mapToObj(value -> Grade.builder()
                                                .id(Random.getGradeId())
                                                .lessonName(faker.university().name())
                                                .grade(faker.number().numberBetween(1, 101))
                                                .creationTime(LocalDateTime.now())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build()
                )
        );
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public List<Student> find(String name, String surname) {
        List<Student> response = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name) && student.getSurname().equals(surname))
                response.add(student);
        }
        return response;
    }

    @Override
    public Student getById(Long id) {
        Student response = null;
        for (Student student : students) {
            if (student.getId().equals(id))
                response = student;
        }
        return response;
    }

    @Override
    public Student save(Student student) {
        Student response = student;

        if (response.getId() == null)
            response.setId(Random.getId());

        for (Student entry : students) {
            if (entry.getId().equals(student.getId())) {
                entry = student;
                response = entry;
                break;
            }
        }
        students.add(response);
        return response;
    }

    @Override
    public void create(List<Student> students) {
        for (Student student : students) {
            this.students.add(student.toBuilder()
                    .id(Random.getId())
                    .grades(student.getGrades().stream()
                            .map(grade -> grade.toBuilder()
                                    .id(Random.getGradeId())
                                    .build())
                            .collect(Collectors.toList()))
                    .build());
        }
    }

    @Override
    public Student remove(Long id) {
        Student response = null;
        for (Student entry : students) {
            if (entry.getId().equals(id)) {
                response = entry;
                students.remove(entry);
                break;
            }
        }
        return response;
    }

    @Override
    public Grade getGradeById(Long studentId, Long gradeId) {
        Student student = getById(studentId);
        Grade response = null;
        if (student != null) {
            List<Grade> grades = student.getGrades();
            Grade search = Grade.builder().id(gradeId).build();
            if (grades.contains(search)) {
                response = grades.get(grades.indexOf(search));
            }
        }
        return response;
    }

    @Override
    public Grade saveGrade(Long studentId, Grade grade) {
        Student student = getById(studentId);
        Grade response = null;
        if (student != null) {
            if (grade.getId() == null) {
                student.getGrades().add(grade);
                response = student.getGrades().get(student.getGrades().size() - 1);
            } else {
                response = grade;
                student.getGrades().set(student.getGrades().indexOf(grade), grade);
            }
        }
        return response;
    }

    @Override
    public Grade removeGrade(Long studentId, Long gradeId) {
        Student student = getById(studentId);
        Grade response = null;
        if (student != null) {
            List<Grade> grades = student.getGrades();
            Grade search = Grade.builder().id(gradeId).build();
            if (grades.contains(search)) {
                response = grades.get(grades.indexOf(search));
                grades.remove(response);
            }
        }
        return response;
    }
}
