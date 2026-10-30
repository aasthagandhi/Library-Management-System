package com.library.library_management.controller;
// It specifies that the IssueController class belongs to the controller package
// inside the com.library.library_management package.

import com.library.library_management.entity.Issue;
// It imports the Issue entity.
// We use Issue objects to receive and return issue data.

import com.library.library_management.service.IssueService;
// It imports IssueService.
// The controller sends issue-related requests to the service layer.

import org.springframework.web.bind.annotation.DeleteMapping;
// It imports DeleteMapping to handle HTTP DELETE requests.

import org.springframework.web.bind.annotation.GetMapping;
// It imports GetMapping to handle HTTP GET requests.

import org.springframework.web.bind.annotation.PathVariable;
// It imports PathVariable to get the issue ID from the URL.

import org.springframework.web.bind.annotation.PostMapping;
// It imports PostMapping to handle HTTP POST requests.

import org.springframework.web.bind.annotation.PutMapping;
// It imports PutMapping to handle HTTP PUT requests.

import org.springframework.web.bind.annotation.RequestBody;
// It imports RequestBody to receive Issue data from the request body.

import org.springframework.web.bind.annotation.RestController;
// It imports RestController.
// It tells Spring that this class handles REST API requests.

import jakarta.validation.Valid;
 // It enables validation for request data.

import java.util.List;
// It imports List to return multiple Issue objects.

import java.util.Optional;
// It imports Optional when an Issue may or may not exist.

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500"
})
// It marks IssueController as a REST controller.
// It allows this class to handle HTTP requests and return JSON responses.

@RestController
public class IssueController {

    // It creates a reference to IssueService.
    // The service performs the business operations related to issues.

    private final IssueService issueService;


    // It is a constructor used to inject IssueService into IssueController.
    // Spring automatically provides the IssueService object.

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }


    // It maps POST requests to /issues.
    // It is used to create a new Issue record.

    @PostMapping("/issues")
    public Issue addIssue(@Valid @RequestBody Issue issue) {
        return issueService.addIssue(issue);
    }


    // It maps GET requests to /issues.
    // It is used to retrieve all Issue records.

    @GetMapping("/issues")
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }


    // It maps GET requests to /issues/{id}.
    // It is used to retrieve a specific Issue using its ID.

    @GetMapping("/issues/{id}")
    public Optional<Issue> getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id);
    }


    @GetMapping("/issues/student/{studentId}") // It creates an API for a student's issue history.
    public List<Issue> getIssuesByStudent(@PathVariable Long studentId) {
        return issueService.getIssuesByStudent(studentId);
    }

    @GetMapping("/issues/book/{bookId}") // It creates an API for a book's issue history.
    public List<Issue> getIssuesByBook(@PathVariable Long bookId) {
        return issueService.getIssuesByBook(bookId);
    }

    @GetMapping("/issues/active") // It creates an API for currently issued books.
    public List<Issue> getActiveIssues() {
        return issueService.getActiveIssues();
    }

    @GetMapping("/issues/overdue") // It creates an API for overdue books.
    public List<Issue> getOverdueIssues() {
        return issueService.getOverdueIssues();
    }




    // It maps PUT requests to /issues/{id}.
    // It is used to update an existing Issue record.

    @PutMapping("/issues/{id}")
    public Issue updateIssue(@PathVariable Long id, @RequestBody Issue issue) {
        issue.setId(id);
        return issueService.updateIssue(issue);
    }


    // It maps DELETE requests to /issues/{id}.
    // It is used to delete an Issue record using its ID.

    @DeleteMapping("/issues/{id}")
    public void deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
    }

    // It maps PUT requests to /issues/{id}/return.
    // It is used to return a book and update its return details.

    @PutMapping("/issues/{id}/return")
    public Issue returnBook(@PathVariable Long id) {
        return issueService.returnBook(id);
    }
}
