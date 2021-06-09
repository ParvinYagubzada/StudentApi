package az.code.springweb.controllers;

import az.code.springweb.dtos.GradeDTO;
import az.code.springweb.dtos.StudentDTO;
import az.code.springweb.exceptions.GradeNotFound;
import az.code.springweb.exceptions.StudentNotFound;
import az.code.springweb.models.Grade;
import az.code.springweb.models.Student;
import az.code.springweb.services.StudentService;
import az.code.springweb.util.Paging;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(GradeNotFound.class)
    public ResponseEntity<String> handleNotFound(GradeNotFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(service.getStudents(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/paging")
    public ResponseEntity<Paging<Student>> getStudents(HttpServletRequest request,
                                                       @RequestParam(required = false, defaultValue = "10") int limit,
                                                       @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getStudents(pageIndex, limit, request.getRequestURL().toString()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getStudentById(id), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Student>> findStudentByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return new ResponseEntity<>(service.find(name, surname), HttpStatus.OK);
    }

    @GetMapping("/find/paging")
    public ResponseEntity<Paging<Student>> findStudentByNameAndSurname(HttpServletRequest request,
                                                                       @RequestParam String name,
                                                                       @RequestParam String surname,
                                                                       @RequestParam(required = false, defaultValue = "10") int limit,
                                                                       @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.find(name, surname, pageIndex, limit, request.getRequestURL().toString()), HttpStatus.ACCEPTED);
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
        return new ResponseEntity<>(service.remove(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/grades")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long id) {
        return new ResponseEntity<>(service.getStudentById(id).getGrades(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> getStudentGrade(@PathVariable Long id, @PathVariable Long gradeId) {
        return new ResponseEntity<>(service.getGradeById(id, gradeId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/grades")
    public ResponseEntity<Grade> createGrade(@PathVariable Long id, @RequestBody Grade grade) {
        return new ResponseEntity<>(service.saveGrade(id, grade), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @PathVariable Long gradeId, @RequestBody Grade grade) {
        Grade response = service.saveGrade(id, grade.toBuilder().id(gradeId).build());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/grades/{gradeId}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable Long id, @PathVariable Long gradeId) {
        return new ResponseEntity<>(service.removeGrade(id, gradeId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/topTen")
    public ResponseEntity<List<Student>> getTop(@RequestParam int value) {
        return new ResponseEntity<>(service.getTop(value), HttpStatus.ACCEPTED);
    }

    @GetMapping("/topTen/paging")
    public ResponseEntity<Paging<Student>> getTop(HttpServletRequest request,
                                                  @RequestParam int value,
                                                  @RequestParam(required = false, defaultValue = "10") int limit,
                                                  @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getTop(value, pageIndex, limit, request.getRequestURL().toString()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/higherThan")
    public ResponseEntity<List<Student>> getHigherThan(@RequestParam int value) {
        return new ResponseEntity<>(service.getHigherThan(value), HttpStatus.ACCEPTED);
    }

    @GetMapping("/higherThan/paging")
    public ResponseEntity<Paging<Student>> getHigherThan(
            HttpServletRequest request,
            @RequestParam int value,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getHigherThan(value, pageIndex, limit, request.getRequestURL().toString()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/aboveAverage")
    public ResponseEntity<List<Student>> getAboveAverage() {
        return new ResponseEntity<>(service.getAboveAverage(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/aboveAverage/paging")
    public ResponseEntity<Paging<Student>> getAboveAverage(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getAboveAverage(pageIndex, limit, request.getRequestURL().toString()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/studentNamedHigherThan")
    public ResponseEntity<List<StudentDTO>> getStudentNamedHigherThan(@RequestParam String name, @RequestParam int value) {
        return new ResponseEntity<>(service.getStudentNamedHigherThan(name, value), HttpStatus.ACCEPTED);
    }

    @GetMapping("/studentNamedHigherThan/paging")
    public ResponseEntity<Paging<StudentDTO>> getStudentNamedHigherThan(
            HttpServletRequest request,
            @RequestParam String name,
            @RequestParam int value,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getStudentNamedHigherThan(name, value, pageIndex, limit, request.getRequestURL().toString()),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/gradeNamedHigherThan")
    public ResponseEntity<List<GradeDTO>> getGradeNamedHigherThan(@RequestParam String name, @RequestParam int value) {
        return new ResponseEntity<>(service.getGradeNamedHigherThan(name, value), HttpStatus.ACCEPTED);
    }

    @GetMapping("/gradeNamedHigherThan/paging")
    public ResponseEntity<Paging<GradeDTO>> getGradeNamedHigherThan(
            HttpServletRequest request,
            @RequestParam String name,
            @RequestParam int value,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int pageIndex) {
        return new ResponseEntity<>(service.getGradeNamedHigherThan(name, value, pageIndex, limit, request.getRequestURL().toString()),
                HttpStatus.ACCEPTED);
    }
}