package az.code.springweb.daos;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> getAll();

    List<Student> find(String name, String surname);

    Student getById(Long id);

    Student save(Student student);

    void create(List<Student> students);

    Student remove(Long id);

    Grade getGradeById(Long studentId, Long gradeId);

    Grade saveGrade(Long studentId, Grade grade);

    Grade removeGrade(Long studentId, Long gradeId);
}
