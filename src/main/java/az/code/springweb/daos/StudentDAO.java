package az.code.springweb.daos;

import az.code.springweb.models.Student;

import java.util.List;

public interface StudentDAO {

    List<Student> getAll();

    List<Student> find(String name, String surname);

    Student getById(Long id);

    Student save(Student student);

    void createStudents(List<Student> students);

    Student remove(Long id);
}
