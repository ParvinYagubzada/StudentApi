package az.code.springweb.services;

import az.code.springweb.daos.StudentDAO;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDAO dao;

    @Override
    public List<Student> getStudents() {
        return dao.getAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Student> find(String name, String surname) {
        return dao.find(name, surname);
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })//TODO 1: update student not working!
    public Student save(Student student) {
        return dao.save(student);
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public void createStudents(List<Student> students) {
        dao.createStudents(students);
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public Student remove(Long id) {
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
    @Transactional(rollbackFor = { SQLException.class })
    public Grade saveGrade(Long studentId, Grade grade) {//TODO 2: Fix update
        Student student = getStudentById(studentId);
        Grade response = null;
        if (student != null) {
            if (grade.getId() == null) {
                student.getGrades().add(grade);
                response = student.getGrades().get(student.getGrades().size() - 1);
            } else {
                response = grade;
                student.getGrades().set(student.getGrades().indexOf(grade), grade);
                student.getGrades().forEach(grade1 -> System.out.println(grade1.getLessonName()));
            }
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public Grade removeGrade(Long studentId, Long gradeId) {
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
    public Grade getGradeById(Long studentId, Long gradeId) {
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
