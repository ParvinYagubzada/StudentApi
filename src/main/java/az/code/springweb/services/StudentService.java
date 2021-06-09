package az.code.springweb.services;

import az.code.springweb.dtos.StudentDTO;
import az.code.springweb.models.Grade;
import az.code.springweb.dtos.GradeDTO;
import az.code.springweb.models.Student;
import az.code.springweb.util.Paging;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    Paging<Student> getStudents(int pageIndex, int limit, String url);

    Student getStudentById(Long id);

    List<Student> find(String name, String surname);

    Paging<Student> find(String name, String surname, int pageIndex, int limit, String url);

    Student save(Student student);

    void createStudents(List<Student> students);

    Student remove(Long id);

    Grade getGradeById(Long id, Long gradeId);

    Grade saveGrade(Long studentId, Grade grade);

    Grade removeGrade(Long studentId, Long gradeId);

    List<Student> getTop(int value);

    Paging<Student> getTop(int value, int pageIndex, int limit, String url);

    List<Student> getHigherThan(int grade);

    Paging<Student> getHigherThan(int value, int pageIndex, int limit, String url);

    List<Student> getAboveAverage();

    Paging<Student> getAboveAverage(int pageIndex, int limit, String url);

    List<StudentDTO> getStudentNamedHigherThan(String name, int grade);

    Paging<StudentDTO> getStudentNamedHigherThan(String name, int grade, int pageIndex, int limit, String url);

    List<GradeDTO> getGradeNamedHigherThan(String name, int grade);

    Paging<GradeDTO> getGradeNamedHigherThan(String name, int grade, int pageIndex, int limit, String url);
}
