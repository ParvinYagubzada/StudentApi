package az.code.springweb.daos;

import az.code.springweb.exceptions.StudentNotFound;
import az.code.springweb.models.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("postgre")
public class StudentDAOPostgre implements StudentDAO {

    @PersistenceContext
    EntityManager manager;

    public List<Student> getAll() {
        return manager.createQuery("SELECT student FROM Student student", Student.class).getResultList();
    }

    @Override
    public List<Student> find(String name, String surname) {
        return manager.createQuery(
                "SELECT student FROM Student student " +
                        "WHERE lower(student.name) LIKE lower(:name) AND lower(student.surname) LIKE lower(:surname)",
                Student.class)
                .setParameter("name", name + "%")
                .setParameter("surname", surname + "%")
                .getResultList();
    }

    @Override
    public Student getById(Long id) {
        return manager.find(Student.class, id);
    }

    @Override
    public Student save(Student student) {
        if (student.getId() != null) {
            Student temp = manager.find(Student.class, student.getId());
            if (temp != null)
                manager.detach(temp);
            else
                throw new StudentNotFound();
        }
        return manager.merge(student);
    }

    @Override
    public void createStudents(List<Student> students) {
        for (Student student : students) {
            manager.merge(student);
        }
    }

    @Override
    public Student remove(Long id) {
        Student response = manager.find(Student.class, id);
        manager.remove(response);
        return response;
    }
}