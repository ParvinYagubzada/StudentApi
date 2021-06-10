package az.code.springweb.repositories;

import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findStudentByNameStartingWithAndSurnameStartingWith(String name, String surname);
}
