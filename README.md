# Library Management System

A full-stack Library Management System built with **Java, Spring Boot,
MySQL, HTML, CSS, and JavaScript**. The application helps manage books,
student registrations, book issuing and returning, issue history,
overdue records, and late-return fines.

## Features

### Book Management

-   Add, view, update, and delete books.
-   Search books by title, author, and category.
-   View currently available books.
-   Track book availability when a book is issued or returned.

### Student Management

-   Register, view, update, and delete student records.
-   Validate required student details.
-   Validate email format.
-   Require a 10-digit phone number.

### Issue and Return Management

-   Issue available books to registered students.
-   Prevent issuing a book that is already unavailable.
-   Record issue date and due date.
-   Return issued books and update their availability.
-   Calculate a late-return fine at **INR 10 per overdue day**.
-   View active, overdue, student-wise, and book-wise issue records.

### Dashboard

-   View summary information for books, students, active issues, and
    overdue issues.
-   Use the frontend dashboard to access the main library operations.

## Technologies Used

  Layer         Technology
  ------------- ----------------------------
  Backend       Java, Spring Boot
  API           Spring Boot REST APIs
  Persistence   Spring Data JPA, Hibernate
  Database      MySQL
  Frontend      HTML, CSS, JavaScript
  API testing   Postman
  Build tool    Maven

## Project Structure

``` text
library-management/
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
├── src/
│   └── main/
│       ├── java/
│       │   └── com/library/library_management/
│       │       ├── controller/
│       │       ├── entity/
│       │       ├── repository/
│       │       ├── service/
│       │       └── exception/
│       └── resources/
│           └── application.properties.example
├── pom.xml
└── README.md
```

## Prerequisites

Install the following before running the application:

-   Java JDK compatible with the project's Spring Boot version
-   MySQL Server and MySQL Workbench (optional, for database management)
-   Apache Maven
-   Visual Studio Code or another Java IDE
-   A browser
-   VS Code Live Server extension (to run the frontend locally)

## Database Setup

1.  Start MySQL.
2.  Create the database:

``` sql
CREATE DATABASE library_db;
```

3.  Configure the database connection in
    `src/main/resources/application.properties.example`.

Example configuration (replace the placeholders with your own local
MySQL credentials):

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**Security:** Do not commit real database passwords or other secrets to
a public GitHub repository. Use environment variables or a local,
untracked configuration file for credentials.

## Run the Backend

1.  Open the project root folder (the folder containing `pom.xml`) in VS
    Code.
2.  Make sure MySQL is running and the database configuration is
    correct.
3.  Open the terminal in the project root.
4.  Run the application with Maven:

``` bash
mvn spring-boot:run
```

The backend runs at:

``` text
http://localhost:8081
```

The books endpoint can be used to check whether the backend is
responding:

``` text
http://localhost:8081/books
```

## Run the Frontend

1.  Keep the Spring Boot backend running.
2.  Open the `frontend` folder in VS Code.
3.  Right-click `index.html`.
4.  Select **Open with Live Server**.

The frontend is commonly served at:

``` text
http://127.0.0.1:5500
```

If the frontend cannot call the backend, check that: - Spring Boot is
running on port `8081`. - The frontend API base URL points to
`http://localhost:8081`. - CORS allows the exact Live Server origin
being used (for example, `http://127.0.0.1:5500` or
`http://localhost:5500`).

## API Endpoints

The backend exposes the following endpoints.

### Books

  -----------------------------------------------------------------------------------------------
  Method                  Endpoint                                        Purpose
  ----------------------- ----------------------------------------------- -----------------------
  GET                     `/books`                                        Get all books

  POST                    `/books`                                        Add a book

  GET                     `/books/{id}`                                   Get a book by ID

  PUT                     `/books/{id}`                                   Update a book

  DELETE                  `/books/{id}`                                   Delete a book

  GET                     `/books/search?title=Java`                      Search books by title

  GET                     `/books/search/author?author=James`             Search books by author

  GET                     `/books/search/category?category=Programming`   Search books by
                                                                          category

  GET                     `/books/available`                              Get available books
  -----------------------------------------------------------------------------------------------

### Students

  Method   Endpoint           Purpose
  -------- ------------------ ---------------------
  POST     `/students`        Register a student
  GET      `/students`        Get all students
  GET      `/students/{id}`   Get a student by ID
  PUT      `/students/{id}`   Update a student
  DELETE   `/students/{id}`   Delete a student

### Issues and Returns

  -------------------------------------------------------------------------------
  Method                  Endpoint                        Purpose
  ----------------------- ------------------------------- -----------------------
  POST                    `/issues`                       Issue a book

  GET                     `/issues`                       Get all issue records

  GET                     `/issues/{id}`                  Get an issue by ID

  GET                     `/issues/student/{studentId}`   Get issue records for a
                                                          student

  GET                     `/issues/book/{bookId}`         Get issue records for a
                                                          book

  GET                     `/issues/active`                Get active issues

  GET                     `/issues/overdue`               Get overdue issues

  PUT                     `/issues/{id}`                  Update an issue

  DELETE                  `/issues/{id}`                  Delete an issue

  PUT                     `/issues/{id}/return`           Return a book
  -------------------------------------------------------------------------------

All endpoints are relative to `http://localhost:8081`.

## Validation and Business Rules

-   Required student fields must not be blank.
-   Student email is validated against the configured email format rule.
-   Student phone number must contain exactly 10 digits.
-   Only available books can be issued.
-   Issuing a book marks it unavailable.
-   Returning a book marks it available.
-   The due date is set to 7 days after the issue date.
-   A fine of INR 10 is calculated for each day returned after the due
    date.

## Testing

The REST APIs can be tested using Postman. The frontend can also be used
to test the main book, student, issue, and return workflows.

Suggested checks: - Add a book and verify it appears in the book list. -
Register a student with valid and invalid phone/email values. - Try
issuing an unavailable book. - Return an issued book and verify its
availability changes. - Check active issues, overdue issues, and fine
calculation.

## Future Improvements

-   Add authentication and role-based access control.
-   Improve validation feedback in the frontend.
-   Add automated unit and integration tests.
-   Add pagination and sorting for larger datasets.
-   Deploy the application to a hosting platform.

## Author

Aastha Gandhi
