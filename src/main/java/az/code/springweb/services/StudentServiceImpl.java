package az.code.springweb.services;

import az.code.springweb.daos.StudentDAO;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    StudentDAO dao;

    @Value("${app.services.repo.name}")
    String name;

    @Autowired
    private void getStudentDao(ApplicationContext context) {
        this.dao = (StudentDAO) context.getBean(name);
    }

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

    @Override //TODO 1: update student does not work as intended!
    public Student save(Student student) {
        return dao.save(student);
    }

    @Override
    public void createStudents(List<Student> students) {
        dao.create(students);
    }

    @Override
    public Student remove(Long id) {
        return dao.remove(id);
    }

    @Override
    public Grade getGradeById(Long studentId, Long gradeId) {
        return dao.getGradeById(studentId, gradeId);
    }

    @Override
    public Grade saveGrade(Long studentId, Grade grade) {
        return dao.saveGrade(studentId, grade);
    }

    @Override
    public Grade removeGrade(Long studentId, Long gradeId) {
        return dao.removeGrade(studentId, gradeId);
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
                                .orElse(0.0)))
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
}
