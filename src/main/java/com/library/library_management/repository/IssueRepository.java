package com.library.library_management.repository;

import com.library.library_management.entity.Issue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// It defines the IssueRepository interface.
// This repository is responsible for accessing Issue data in the database.
public interface IssueRepository extends JpaRepository<Issue, Long> {

        List<Issue> findByStudentId(Long studentId); // It finds all issues of a student.
        List<Issue> findByBookId(Long bookId); // It finds all issues of a book.
        List<Issue> findByReturnDateIsNull(); // It finds books that are currently issued.
        List<Issue> findByReturnDateIsNullAndDueDateBefore(LocalDate date); // It finds unreturned issues past their due date.

// IssueRepository, Issue entity ke database operations handle karega aur Issue ka primary key Long type ka hai.
    // It extends JpaRepository so IssueRepository gets
    // built-in CRUD operations automatically.

    // Issue specifies the entity that this repository manages.
    // Long specifies the data type of the Issue entity's primary key.
}
