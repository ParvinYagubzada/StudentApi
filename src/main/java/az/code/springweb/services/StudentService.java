package az.code.springweb.services;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Student getStudentById(Long id);

    List<Student> find(String name, String surname);

    Student save(Student student);

    void createStudents(List<Student> students);

    Student remove(Long id);

    List<Student> getTopTen();

    List<Student> getHigherThan(int grade);

    List<Student> getAboveAverage();

    Grade saveGrade(Long studentId, Grade grade);

    Grade removeGrade(Long studentId, Long gradeId);

    Grade getGradeById(Long id, Long gradeId);
}
