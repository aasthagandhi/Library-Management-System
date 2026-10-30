// It specifies that the BookService class belongs to the service package inside the com.library.library_management package.
package com.library.library_management.service;

// It imports the Service annotation from the Spring Framework.
// We use @Service to mark a class as a service component.
import org.springframework.stereotype.Service;

import com.library.library_management.entity.Book;
// It imports the BookRepository interface from the repository package.
// We use BookRepository in the service layer to access Book data from the database.
import com.library.library_management.repository.BookRepository;

import java.util.List;
import java.util.Optional;

// It marks the BookService class as a Spring service component.
// It tells Spring to create and manage an object of this class
//@service Spring ko batata hai:"Ye class(bookService class) service layer ka part hai. Is class ko aur object ko Spring manage kare."
@Service
public class BookService {

// It creates a reference to the BookRepository.
// The BookRepository is used by the service to access Book data from the database.
    private final BookRepository bookRepository;
    // It is a constructor used to inject the BookRepository dependency into BookService.
// Spring provides the BookRepository object when creating the BookService object.
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

// It saves a new book in the database.
// It uses the BookRepository to perform the save operation.
    public Book addBook(Book book) {    
        return bookRepository.save(book);
    }

// It retrieves all books from the database.
// It uses the BookRepository to find all Book records.
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

// It retrieves a book from the database using its unique ID.
// It uses the BookRepository to find the book by its ID.
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

// It updates the details of an existing book in the database.
// It uses the BookRepository to save the updated Book object.
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

// It deletes a book from the database using its unique ID.
// It uses the BookRepository to delete the book by its ID.
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }    

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchByCategory(String category) {
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }

    // It finds all currently available books.
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
}
