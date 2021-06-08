package az.code.springweb.services;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Student getStudentById(int id);

    List<Student> find(String name, String surname);

    Student save(Student student);

    void createStudents(List<Student> students);

    Student remove(int id);

    List<Student> getTopTen();

    List<Student> getHigherThan(int grade);

    List<Student> getAboveAverage();

    Grade saveGrade(int studentId, Grade grade);

    Grade removeGrade(int studentId, int gradeId);

    Grade getGradeById(int id, int gradeId);
}
