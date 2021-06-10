package az.code.springweb.daos;

import az.code.springweb.exceptions.GradeNotFound;
import az.code.springweb.exceptions.StudentNotFound;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import az.code.springweb.repositories.GradeRepository;
import az.code.springweb.repositories.StudentRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("postgreRepo")
public class StudentDAOPostgreRepo implements StudentDAO {

    StudentRepository studentRepository;
    GradeRepository gradeRepository;

    public StudentDAOPostgreRepo(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Student> getAll() {
        return Streamable.of(studentRepository.findAll()).toList();
    }

    @Override
    public List<Student> find(String name, String surname) {
        return studentRepository.findStudentByNameStartingWithAndSurnameStartingWith(name, surname);
    }

    @Override
    public Student getById(Long id) {
        Optional<Student> result = studentRepository.findById(id);
        if (result.isEmpty())
            throw new StudentNotFound();
        return result.get();
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void create(List<Student> students) {
        studentRepository.saveAll(students);
    }

    @Override
    public Student remove(Long id) {
        Student result = getById(id);
        studentRepository.deleteById(id);
        return result;
    }

    @Override
    public Grade getGradeById(Long studentId, Long gradeId) {
        getById(studentId);
        Optional<Grade> result = gradeRepository.getGradeByIdAndStudentId(gradeId, studentId);
        if (result.isEmpty()) {
            throw new GradeNotFound();
        }
        return result.get();
    }

    @Override
    public Grade saveGrade(Long studentId, Grade grade) {
        getById(studentId);
        grade.setStudentId(studentId);
        return gradeRepository.save(grade);
    }

    @Override
    public Grade removeGrade(Long studentId, Long gradeId) {
        Grade grade = getGradeById(studentId, gradeId);
        gradeRepository.delete(grade);
        return grade;
    }
}
