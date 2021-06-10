package az.code.springweb.daos;

import az.code.springweb.exceptions.GradeNotFound;
import az.code.springweb.exceptions.StudentNotFound;
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
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name) && student.getSurname().equals(surname))
                result.add(student);
        }
        return result;
    }

    @Override
    public Student getById(Long id) {
        for (Student student : students) {
            if (student.getId().equals(id))
                return student;
        }
        throw new StudentNotFound();
    }

    @Override
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(Random.getId());
            for (Grade grade : student.getGrades()) {
                grade.setId(Random.getGradeId());
            }
            students.add(student);
            return student;
        } else {
            for (Student entry : students) {
                if (entry.getId().equals(student.getId())) {
                    entry = student;
                    return entry;
                }
            }
            throw new StudentNotFound();
        }
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
        for (Student entry : students) {
            if (entry.getId().equals(id)) {
                students.remove(entry);
                return entry;
            }
        }
        throw new StudentNotFound();
    }

    @Override
    public Grade getGradeById(Long studentId, Long gradeId) {
        Student student = getById(studentId);
        List<Grade> grades = student.getGrades();
        Grade search = Grade.builder().id(gradeId).build();
        if (grades.contains(search)) {
            return grades.get(grades.indexOf(search));
        } else {
            throw new GradeNotFound();
        }
    }

    @Override
    public Grade saveGrade(Long studentId, Grade grade) {
        Student student = getById(studentId);
        if (grade.getId() == null) {
            Grade gradeWithId = grade.toBuilder().id(Random.getGradeId()).build();
            student.getGrades().add(gradeWithId);
            return gradeWithId;
        } else if (student.getGrades().contains(grade)) {
            student.getGrades().set(student.getGrades().indexOf(grade), grade);
            return grade;
        }
        throw new GradeNotFound();
    }

    @Override
    public Grade removeGrade(Long studentId, Long gradeId) {
        Student student = getById(studentId);
        List<Grade> grades = student.getGrades();
        Grade search = Grade.builder().id(gradeId).build();
        if (grades.contains(search)) {
            Grade result = grades.get(grades.indexOf(search));
            grades.remove(result);
            return result;
        } else {
            throw new GradeNotFound();
        }
    }
}
