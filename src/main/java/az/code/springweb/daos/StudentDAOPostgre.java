package az.code.springweb.daos;

import az.code.springweb.exceptions.GradeNotFound;
import az.code.springweb.exceptions.StudentNotFound;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
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
                        "WHERE lower(student.name) LIKE lower(:name) " +
                        "AND lower(student.surname) LIKE lower(:surname)", Student.class)
                .setParameter("name", name + "%")
                .setParameter("surname", surname + "%")
                .getResultList();
    }

    @Override
    public Student getById(Long id) {
        return manager.find(Student.class, id);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class}) //TODO 4: Make better!
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
    @Transactional(rollbackFor = {SQLException.class})
    public void create(List<Student> students) {
        for (Student student : students) {
            manager.merge(student);
        }
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public Student remove(Long id) {
        Student response = manager.find(Student.class, id);
        manager.remove(response);
        return response;
    }

    @Override
    public Grade getGradeById(Long studentId, Long gradeId) {
        List<Grade> grades = manager
                .createQuery("SELECT grade FROM Grade grade " +
                        "WHERE grade.studentId = :studentId " +
                        "AND grade.id = :id", Grade.class)
                .setParameter("studentId", studentId)
                .setParameter("id", gradeId)
                .getResultList();
        if (grades.size() == 0)
            throw new GradeNotFound();
        return grades.get(0);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public Grade saveGrade(Long studentId, Grade grade) {
        if (grade.getId() != null) {
            if (getIfExists(studentId).getGrades().contains(grade)) {
                return manager.merge(grade.toBuilder().studentId(studentId).build());
            } else {
                throw new GradeNotFound();
            }
        } else {
            return manager.merge(grade.toBuilder().studentId(studentId).build());
        }
    }

    @Override
    @Transactional
    public Grade removeGrade(Long studentId, Long gradeId) {
        Grade response;
        List<Grade> grades = getIfExists(studentId).getGrades();
        Grade search = Grade.builder().id(gradeId).build();
        if (grades.contains(search)) {
            response = grades.get(grades.indexOf(search));
            grades.remove(response);
            manager.remove(response);
        } else {
            throw new GradeNotFound();
        }
        return response;
    }

    private Student getIfExists(Long studentId) {
        Student student = getById(studentId);
        if (student != null) {
            return student;
        } else {
            throw new StudentNotFound();
        }
    }
}