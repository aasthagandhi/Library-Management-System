package com.library.library_management.entity;
// It specifies that the Student class belongs to the entity package
// inside the com.library.library_management package.

import jakarta.persistence.Entity;
// It imports the Entity annotation from the Jakarta Persistence API.
// We use @Entity to map the Student class to a database table.

import jakarta.persistence.Id;
// It imports the Id annotation from the Jakarta Persistence API.
// We use @Id to mark the primary key of the Student table.

import jakarta.persistence.GeneratedValue;
// It imports the GeneratedValue annotation from the Jakarta Persistence API.
// We use it to automatically generate the value of the primary key.

import jakarta.validation.constraints.NotBlank;
 // It imports @NotBlank for checking that a String is not empty or blank.

import jakarta.validation.constraints.Email;
 // It imports @Email for checking a valid email format.
 import jakarta.validation.constraints.Pattern;
 // It imports @pattern for checking a valid phone number format.
@Entity
// It marks the Student class as a JPA entity.
// It tells Hibernate that this class should be mapped to a database table.

public class Student {
    // It defines the Student class.
    // This class represents a student in our Library Management System.

    @Id
    // It marks the id field as the primary key of the database table.

    @GeneratedValue
    // It tells JPA to automatically generate a value for the primary key.

    private Long id;
    // It stores the unique ID of each student and ID uniquely identify a student in the database.
   
    @NotBlank(message = "Name is required")
    // It ensures that the student name is not null, empty, or blank.
    private String name;
    // It stores the name of the student.

    @NotBlank(message = "Email is required") //not null,empty,black email
    @Pattern(
    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$",
    message = "Please enter a valid email address"
    ) // email in proper format
    private String email;
    // It stores the email address of the student.

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
    private String phone;
    // It stores the phone number of the student.
    @NotBlank(message = "Address is required")
    private String address;
    //It stores the address of the student.

    // It is a default constructor.
    // It is used to create a Student object without providing any values.
    // "Creates an object without initial values.
    // Required by JPA/Hibernate for creating entity objects.""


    public Student() {
    }


    // It is a parameterized constructor.
    // It is used to create a Student object with the given student details.
    // Creates an object with initial values.
    // Useful when we already have the student's details. 
    public Student(String name, String email, String phone,String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address; 
    }


    // It returns the unique ID of the student.

    public Long getId() {
        return id;
    }


    // It sets or updates the unique ID of the student.

    public void setId(Long id) {
        this.id = id;
    }


    // It returns the name of the student.

    public String getName() {
        return name;
    }


    // It sets or updates the name of the student.

    public void setName(String name) {
        this.name = name;
    }


    // It returns the email address of the student.

    public String getEmail() {
        return email;
    }


    // It sets or updates the email address of the student.

    public void setEmail(String email) {
        this.email = email;
    }


    // It returns the phone number of the student.

    public String getPhone() {
        return phone;
    }


    // It sets or updates the phone number of the student.

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //It returns the address of the student.
    public String getAddress() {
        return address;
    } 

    //it sets or updates the address of the student.
    public void setAddress(String address){
        this.address = address;
    }
}
