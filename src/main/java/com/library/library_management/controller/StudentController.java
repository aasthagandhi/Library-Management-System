package com.library.library_management.controller;
// It specifies that the StudentController class belongs to the controller package
// inside the com.library.library_management package.

import com.library.library_management.entity.Student;
// It imports the Student entity.
// We use Student objects to receive and return student data.

import com.library.library_management.service.StudentService;
// It imports StudentService.
// The controller sends student-related requests to the service layer.

import org.springframework.web.bind.annotation.DeleteMapping;
// It imports DeleteMapping.
// We use it to handle HTTP DELETE requests.

import org.springframework.web.bind.annotation.GetMapping;
// It imports GetMapping.
// We use it to handle HTTP GET requests.

import org.springframework.web.bind.annotation.PathVariable;
// It imports PathVariable.
// We use it to get the student ID from the URL.

import org.springframework.web.bind.annotation.PostMapping;
// It imports PostMapping.
// We use it to handle HTTP POST requests.

import org.springframework.web.bind.annotation.PutMapping;
// It imports PutMapping.
// We use it to handle HTTP PUT requests.

import org.springframework.web.bind.annotation.RequestBody;
// It imports RequestBody.
// We use it to receive Student data from the request body.

import org.springframework.web.bind.annotation.RestController;
// It imports RestController.
// It tells Spring that this class handles REST API requests.

import jakarta.validation.Valid;
 // It imports @Valid to trigger validation on request data.

import java.util.List;
// It imports the List interface.
// We use List to return multiple Student objects.

import java.util.Optional;
// It imports Optional.
// We use Optional when a requested Student may or may not exist.

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500"
})
// It marks the StudentController class as a REST controller.
// It allows this class to handle HTTP requests and return data as JSON.
@RestController

// It defines the StudentController class.
// This class handles API requests related to students.
public class StudentController {

    // It creates a reference to StudentService.
    // The service is used to perform student-related business operations.
    private final StudentService studentService;


    // It is a constructor used to inject StudentService into StudentController.
    // Spring provides the StudentService object automatically.
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    // It maps POST requests to /students.
    // It is used to add a new student.
    @PostMapping("/students")
    public Student addStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }


    // It maps GET requests to /students.
    // It is used to retrieve all students.
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }


    // It maps GET requests to /students/{id}.
    // It is used to retrieve a specific student using the student's ID.
    @GetMapping("/students/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    // It maps PUT requests to /students/{id}.
    // It is used to update an existing student's details.
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        student.setId(id);
        return studentService.updateStudent(student);
    }


    // It maps DELETE requests to /students/{id}.
    // It is used to delete a student using the student's ID.
    @DeleteMapping("/students/{id}")
    public void deleteStudent( @PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
