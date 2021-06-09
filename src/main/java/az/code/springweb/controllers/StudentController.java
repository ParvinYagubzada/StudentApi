package az.code.springweb.controllers;

import az.code.springweb.exceptions.StudentNotFound;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import az.code.springweb.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @ExceptionHandler(StudentNotFound.class)
    public ResponseEntity<String> handleNotFound(StudentNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> response = service.getStudents();
        if (response.size() == 0)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student response = service.getStudentById(id);
        if (response == null)
           throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Student>> findStudentById(@RequestParam String name, @RequestParam String surname) {
        List<Student> response = service.find(name, surname);
        if (response.size() == 0)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(service.save(student), HttpStatus.CREATED);
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createStudents(@RequestBody List<Student> students) {
        service.createStudents(students);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return new ResponseEntity<>(service.save(student.toBuilder().id(id).build()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student response = service.remove(id);
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/topTen")
    public ResponseEntity<List<Student>> getTopTen() {
        List<Student> response = service.getTopTen();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/higherThan")
    public ResponseEntity<List<Student>> getHigherThan(@RequestParam int value) {
        List<Student> response = service.getHigherThan(value);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/aboveAverage")
    public ResponseEntity<List<Student>> getAboveAverage() {
        List<Student> response = service.getAboveAverage();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/grades")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long id) {
        Student response = service.getStudentById(id);
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response.getGrades(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> getStudentGrades(@PathVariable Long id, @PathVariable Long gradeId) {
        Grade response = service.getGradeById(id, gradeId);
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/grades")
    public ResponseEntity<Grade> createGrade(@PathVariable Long id, @RequestBody Grade grade) {
        Grade response = service.saveGrade(id, grade);
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @PathVariable Long gradeId, @RequestBody Grade grade) {
        Grade response = service.saveGrade(id, grade.toBuilder().id(gradeId).build());
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable Long id, @PathVariable Long gradeId) {
        Grade response = service.removeGrade(id, gradeId);
        if (response == null)
            throw new StudentNotFound();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}