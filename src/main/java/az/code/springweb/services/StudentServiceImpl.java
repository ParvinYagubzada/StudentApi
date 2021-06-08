package az.code.springweb.services;

import az.code.springweb.daos.StudentDAO;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDAO dao;

    public List<Student> getStudents() {
        return dao.getAll();
    }

    public Student getStudentById(int id) {
        return dao.getById(id);
    }

    public List<Student> find(String name, String surname) {
        return dao.find(name, surname);
    }

    public Student save(Student student) {
        return dao.save(student);
    }

    @Override
    public void createStudents(List<Student> students) {

    }

    @Override
    public Student remove(int id) {
        return dao.remove(id);
    }

    @Override
    public List<Student> getTopTen() {
        return dao.getAll().stream()
                .sorted(Comparator.comparing(
                        student -> -student
                                .getGrades()
                                .stream()
                                .mapToInt(Grade::getGrade)
                                .average()
                                .getAsDouble()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> getHigherThan(int grade) {
        return dao.getAll().stream()
                .filter(student -> student
                        .getGrades()
                        .stream()
                        .anyMatch(studentGrade -> studentGrade.getGrade() > grade)
                ).collect(Collectors.toList());
    }

    @Override
    public List<Student> getAboveAverage() {
        return dao.getAll().stream()
                .filter(student -> {
                    List<Integer> studentGrades = student.getGrades()
                            .stream()
                            .mapToInt(Grade::getGrade)
                            .filter(value -> value > 50)
                            .boxed()
                            .collect(Collectors.toList());
                    return (studentGrades.size() * 100) / student.getGrades().size() >= 70;
                }).collect(Collectors.toList());
    }

    @Override
    public Grade saveGrade(int studentId, Grade grade) {
        Student student = getStudentById(studentId);
        Grade response = null;
        if (student != null) {
            if (grade.getId() == 0) {
                response = grade.toBuilder().id(Random.getGradeId()).build();
                student.getGrades().add(response);
            } else {
                response = grade;
                student.getGrades().set(student.getGrades().indexOf(grade), grade);
            }
        }
        return response;
    }

    @Override
    public Grade removeGrade(int studentId, int gradeId) {
        Student student = getStudentById(studentId);
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

    @Override
    public Grade getGradeById(int studentId, int gradeId) {
        Student student = getStudentById(studentId);
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
}
