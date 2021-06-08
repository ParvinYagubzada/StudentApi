package az.code.springweb.daos;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import az.code.springweb.services.Random;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StudentDAOMemoryImpl implements StudentDAO {
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
    public Student getById(int id) {
        Student response = null;
        for (Student student : students) {
            if (student.getId() == id)
                response = student;
        }
        return response;
    }

    @Override
    public Student save(Student student) {
        Student response = student;

        if (response.getId() == 0)
            response.setId(Random.getId());

        for (Student entry : students) {
            if (entry.getId() == student.getId()) {
                entry = student;
                response = entry;
                break;
            }
        }
        students.add(response);
        return response;
    }

    @Override
    public Student remove(int id) {
        Student response = null;

        for (Student entry : students) {
            if (entry.getId() == id) {
                response = entry;
                students.remove(entry);
                break;
            }
        }

        return response;
    }
}
