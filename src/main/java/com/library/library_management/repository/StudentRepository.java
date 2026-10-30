package com.library.library_management.repository;
// It specifies that the StudentRepository interface belongs to the repository package
// inside the com.library.library_management package.

import com.library.library_management.entity.Student;
// It imports the Student entity from the entity package.
// We use Student because this repository will work with Student data.

import org.springframework.data.jpa.repository.JpaRepository;
// It imports JpaRepository from Spring Data JPA.
// JpaRepository provides built-in database operations such as save(), findAll(),
// findById(), and deleteById().


// It defines the StudentRepository interface.
// This repository is used to access and manage Student data in the database.
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // It extends JpaRepository so StudentRepository inherits
    // the built-in database operations provided by JpaRepository.
    
    // Student specifies the entity that this repository will manage.
    // Long specifies the data type of the Student entity's primary key.
}
