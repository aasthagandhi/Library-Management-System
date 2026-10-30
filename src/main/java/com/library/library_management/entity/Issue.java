package com.library.library_management.entity;
// It specifies that the Issue class belongs to the entity package
// inside the com.library.library_management package.

import jakarta.persistence.Entity;
// It imports the Entity annotation.
// It tells JPA/Hibernate that Issue is a database entity.

import jakarta.persistence.Id;
// It imports the Id annotation.
// It marks the id field as the primary key.

import jakarta.persistence.GeneratedValue;
// It imports the GeneratedValue annotation.
// It allows JPA to automatically generate the issue ID.

import jakarta.persistence.ManyToOne;
// It imports the ManyToOne annotation.
// It is used to create a relationship between Issue and Student/Book.

import jakarta.validation.constraints.NotNull;
// It imports @NotNull to ensure required objects are not null.

import java.time.LocalDate;
// It imports LocalDate from Java.
// It is used to store dates such as issue date, due date and return date.


@Entity
// It marks the Issue class as a JPA entity.
// Hibernate will map this class to an Issue table in the database.

public class Issue {
    // It defines the Issue class.
    // This class represents a book issue record in our Library Management System.

    @Id
    // It marks the id field as the primary key of the Issue table.

    @GeneratedValue
    // It tells JPA to automatically generate a unique ID for each issue record.

    private Long id;
    // It stores the unique ID of the issue record.


    @ManyToOne // It creates a many-to-one relationship between student and Book.
    @NotNull(message = "Student is required")//student must be selected for an issue.
    private Student student;
    // It stores the student who issued the book.


    @ManyToOne // It creates a many-to-one relationship between Issue and Book.
    @NotNull(message = "Book is required")//book must be selected for an issue. 
    private Book book;
    // It stores the book that was issued.


    private LocalDate issueDate;
    // It stores the date on which the book was issued.


    private LocalDate dueDate;
    // It stores the date by which the book should be returned.


    private LocalDate returnDate;
    // It stores the actual date on which the book was returned.
    // It can remain null while the book is still issued.
     
    // Fine amount for late return.
    private Double fine;

    // It is a default constructor.
    // JPA/Hibernate requires a no-argument constructor to create entity objects.

    public Issue() {
    }


    // It is a parameterized constructor.
    // It is used to create an Issue object with the given issue details.

    public Issue(Student student, Book book, LocalDate issueDate,
                 LocalDate dueDate, LocalDate returnDate, Double fine) {
        this.student = student;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }


    // It returns the unique ID of the issue record.

    public Long getId() {
        return id;
    }


    // It sets or updates the unique ID of the issue record.

    public void setId(Long id) {
        this.id = id;
    }


    // It returns the student associated with this issue.

    public Student getStudent() {
        return student;
    }


    // It sets or updates the student associated with this issue.

    public void setStudent(Student student) {
        this.student = student;
    }


    // It returns the book associated with this issue.

    public Book getBook() {
        return book;
    }


    // It sets or updates the book associated with this issue.

    public void setBook(Book book) {
        this.book = book;
    }


    // It returns the issue date.

    public LocalDate getIssueDate() {
        return issueDate;
    }


    // It sets or updates the issue date.

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }


    // It returns the due date.

    public LocalDate getDueDate() {
        return dueDate;
    }


    // It sets or updates the due date.

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    // It returns the return date.

    public LocalDate getReturnDate() {
        return returnDate;
    }


    // It sets or updates the return date.

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // It returns fine .
    public Double getFine() {
        return fine;
    }

    // It sets or updates fine.
    public void setFine(Double fine) {
        this.fine = fine;
    }
}
