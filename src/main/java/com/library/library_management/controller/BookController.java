package com.library.library_management.controller;

// It imports the BookService class from the service package.
// We use BookService in the controller to handle book-related business logic.
import com.library.library_management.service.BookService;

// It imports the RestController annotation from Spring.
// We use @RestController to mark a class as a REST API controller.
import org.springframework.web.bind.annotation.RestController;

// It imports the PostMapping annotation from Spring.
// We use @PostMapping to handle HTTP POST requests.
import org.springframework.web.bind.annotation.PostMapping;


// It marks the BookController class as a REST controller.
// It allows the class to handle HTTP requests and return data as a response.

// It imports the Book entity.
// We use Book to receive and return book data.
import com.library.library_management.entity.Book;

// It imports the RequestBody annotation.
// It converts the JSON request body into a Book object.
import org.springframework.web.bind.annotation.RequestBody;

// It imports the GetMapping annotation from Spring.
// We use @GetMapping to handle HTTP GET requests.
import org.springframework.web.bind.annotation.GetMapping;


// It imports the PathVariable annotation.
// We use it to get a value from the URL path.
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;
 // It reads the search value from the URL.

import jakarta.validation.Valid; 
// It imports @Valid to trigger validation on request data.


// It imports the PutMapping annotation from Spring.
// We use @PutMapping to handle HTTP PUT requests.
import org.springframework.web.bind.annotation.PutMapping;
// It imports the DeleteMapping annotation from Spring.
// We use @DeleteMapping to handle HTTP DELETE requests.
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500"
})

@RestController  //Ye Spring ko batata hai:"BookController ek controller hai jo HTTP requests handle karega."

public class BookController {

// It creates a reference to the BookService.
// The BookService is used to handle the business logic related to books.
    private final BookService bookService;

// It is a constructor used to inject the BookService dependency into BookController.
// Spring provides the BookService object when creating the BookController object.
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
// It maps HTTP POST requests to the addBook method.
// It is used when we want to add a new book.
    @PostMapping("/books")
// It handles the request to add a new book.
// It receives the Book data and passes it to the service layer.
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

// It maps HTTP GET requests to the getAllBooks method.
// It is used to retrieve all books.
    @GetMapping("/books")

// It retrieves all books through the service layer.
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

// It maps GET requests to retrieve a specific book using its ID.
    @GetMapping("/books/{id}")

// It retrieves a specific book using its unique ID.
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

// It maps PUT requests to update an existing book using its ID.
    @PutMapping("/books/{id}")

// It updates an existing book using its ID.
// It receives the updated Book data and passes it to the service layer.
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return bookService.updateBook(book);
    }

// It maps DELETE requests to delete a book using its ID.
    @DeleteMapping("/books/{id}")

// It deletes a book using its unique ID.
// It passes the ID to the service layer for deletion.
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }   

    @GetMapping("/books/search") // It creates an API endpoint for book search.
    public List<Book> searchBooks(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

     @GetMapping("/books/search/author") // It creates an API endpoint for author search.
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("/books/search/category") // It creates an API endpoint for category search.
    public List<Book> searchBooksByCategory(@RequestParam String category) {
        return bookService.searchByCategory(category);
    }

   @GetMapping("/books/available") // It creates an API endpoint for available books.
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }


}

