
// It specifies that the BookRepository interface belongs
// to the repository package inside the com.library.library_management package.
package com.library.library_management.repository;

// It imports the JpaRepository interface from Spring Data JPA.
// We use it to perform database operations for the Book entity.
// JpaRepository Spring Data JPA ka ready-made interface hai jo hume common database operations provide karta hai.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.library.library_management.entity.Book;


// It defines the BookRepository interface.
// This repository is used to access and manage Book data in the database.
// extends = inherits the features/operations of another interface or class.   It allows the BookRepository interface to inherit the built-in database operations provided by the JpaRepository interface.
// Book specifies the entity that this repository will manage.
// Long specifies the data type of the Book entity's primary key.
public interface BookRepository extends JpaRepository<Book, Long> {
      List<Book> findByTitleContainingIgnoreCase(String title); // It searches books by title.
      List<Book> findByAuthorContainingIgnoreCase(String author); // It searches books by author.
      List<Book> findByCategoryContainingIgnoreCase(String category); // It searches books by author.
      List<Book> findByAvailableTrue(); // It finds only available books.

}
