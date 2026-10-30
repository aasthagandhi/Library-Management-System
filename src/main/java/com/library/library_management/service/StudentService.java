package com.library.library_management.service;
// It specifies that the StudentService class belongs to the service package
// inside the com.library.library_management package.

import com.library.library_management.entity.Student;
// It imports the Student entity.
// We use Student objects to work with student data.

import com.library.library_management.repository.StudentRepository;
// It imports the StudentRepository.
// We use StudentRepository to access and manage Student data in the database.

import org.springframework.stereotype.Service;
// It imports the Service annotation from the Spring Framework.
// We use @Service to mark StudentService as a service component.

import java.util.List;
// It imports the List interface from Java.
// We use List to store multiple Student objects.

import java.util.Optional;
// It imports the Optional class from Java.
// We use Optional when a requested Student may or may not exist.


// It marks the StudentService class as a Spring service component.
// It tells Spring to create and manage an object of this class.
@Service

// It defines the StudentService class.
// This class contains the business logic related to students.
public class StudentService {

    // It creates a reference to the StudentRepository.
    // The StudentRepository is used by the service to access Student data from the database.
    private final StudentRepository studentRepository;


    // It is a constructor used to inject the StudentRepository dependency into StudentService.
    // Spring provides the StudentRepository object when creating the StudentService object.
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // It saves a new student in the database.
    // It uses the StudentRepository to perform the save operation.
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }


    // It retrieves all students from the database.
    // It uses the StudentRepository to find all Student records.
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    // It retrieves a student from the database using the unique ID.
    // It uses the StudentRepository to find the student by ID.
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }


    // It updates the details of an existing student in the database.
    // It uses the StudentRepository to save the updated Student object.
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }


    // It deletes a student from the database using the unique ID.
    // It uses the StudentRepository to delete the student by ID.
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}