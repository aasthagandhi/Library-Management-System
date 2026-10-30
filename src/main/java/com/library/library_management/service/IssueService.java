package com.library.library_management.service;
// It specifies that the IssueService class belongs to the service package
// inside the com.library.library_management package.

import com.library.library_management.entity.Book;
import com.library.library_management.entity.Issue;
// It imports the Issue entity.
// We use Issue objects to work with issue records.

import com.library.library_management.repository.IssueRepository;
// It imports the IssueRepository.
// We use it to perform database operations related to Issue records.

import com.library.library_management.repository.BookRepository;
// It imports BookRepository.
// We will use it to check and update Book data.

import com.library.library_management.repository.StudentRepository;
// It imports StudentRepository.
// We will use it to check whether a Student exists.

import com.library.library_management.entity.Student;

import org.springframework.transaction.annotation.Transactional;
// It imports @Transactional to keep related database operations in one transaction.

import org.springframework.stereotype.Service;
// It imports the Service annotation.
// @Service tells Spring to manage this class as a service component.

import java.time.LocalDate;
// It imports LocalDate to work with dates.

import java.util.List;
// It imports List.
// We use List to store multiple Issue objects.

import java.util.Optional;
// It imports Optional.
// We use Optional when an Issue may or may not exist.



@Service
// It marks IssueService as a Spring service component.
// Spring will create and manage an object of this class.

public class IssueService {
    // It defines the IssueService class.
    // This class contains the business logic related to book issue records.

    private final IssueRepository issueRepository;
    // It creates a reference to IssueRepository.
    // The repository will be used to access Issue data in the database.
    
    private final BookRepository bookRepository;
    // It creates a reference to BookRepository.
    // We will use it for book availability and book updates.

    private final StudentRepository studentRepository;
    // It creates a reference to StudentRepository.
    // We will use it to check whether the student exists.

    // It is a constructor used to inject IssueRepository into IssueService.
    // Spring automatically provides the IssueRepository object here.

    public IssueService(
        IssueRepository issueRepository,
        BookRepository bookRepository,
        StudentRepository studentRepository) {

    this.issueRepository = issueRepository;
    this.bookRepository = bookRepository;
    this.studentRepository = studentRepository;
}

    @Transactional
    // It makes the issue operation execute as one database transaction.
    public Issue addIssue(Issue issue) {

        // It gets the Student ID from the Issue object.
        Long studentId = issue.getStudent().getId();

        // It finds the student by ID and throws an error if the student does not exist.
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        // It sets the existing student in the Issue object.
        issue.setStudent(student);


        // It gets the Book ID from the Issue object.
        Long bookId = issue.getBook().getId();

        // It finds the book by ID and throws an error if the book does not exist.
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        // It sets the existing book in the Issue object.
        issue.setBook(book);

        // It checks whether the book is currently available for issuing.
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available");
        }

        // It sets the issue date to today's date.
        issue.setIssueDate(LocalDate.now());

        // It sets the due date to 7 days after the issue date.
        issue.setDueDate(issue.getIssueDate().plusDays(7));

        // It marks the book as unavailable after it is issued.
        book.setAvailable(false);

        // It saves the updated book with unavailable status in the database.
        bookRepository.save(book);


        // It saves the Issue record in the database.
        return issueRepository.save(issue);
    }
    

    

    // It retrieves all Issue records from the database.
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // It gets all issues of a student.
    public List<Issue> getIssuesByStudent(Long studentId) {
        return issueRepository.findByStudentId(studentId);
    }
    // It gets all issues of a book.
    public List<Issue> getIssuesByBook(Long bookId) {
        return issueRepository.findByBookId(bookId);
    }

    // It gets all currently active issues.
    public List<Issue> getActiveIssues() {
        return issueRepository.findByReturnDateIsNull();
    }

    public List<Issue> getOverdueIssues() {
        return issueRepository.findByReturnDateIsNullAndDueDateBefore(LocalDate.now());
    }



    // It retrieves one Issue record using its unique ID.
    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }


    // It updates an existing Issue record.
    public Issue updateIssue(Issue issue) {
        return issueRepository.save(issue);
    }


    // It deletes an Issue record using its unique ID.
    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }

// It returns an issued book and calculates fine for late return.
@Transactional
public Issue returnBook(Long issueId) {

    // It finds the issue record using the issue ID.
    Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));

    // It sets today's date as the return date.
    issue.setReturnDate(LocalDate.now());

    // It gets the book associated with this issue.
    Book book = issue.getBook();

    // It marks the book as available after it is returned.
    book.setAvailable(true);

    // It saves the updated book in the database.
    bookRepository.save(book);

    // It calculates the number of days the book is returned late.
    long lateDays = java.time.temporal.ChronoUnit.DAYS.between(
            issue.getDueDate(),
            issue.getReturnDate()
    );

    // It checks whether the book was returned late.
    if (lateDays > 0) {

        // It calculates fine at ₹10 per late day.
        double fine = lateDays * 10;

        // It stores the calculated fine in the issue record.
        issue.setFine(fine);

    } else {

        // No fine is charged if the book is returned on or before the due date.
        issue.setFine(0.0);
    }

    // It saves the updated issue with return date and fine.
    return issueRepository.save(issue);
}
}