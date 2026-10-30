package com.library.library_management.entity;
// It specifies that the Book class belongs to the entity package
// inside the com.library.library_management package.


import jakarta.persistence.Entity;
// It marks the Book class as a JPA entity.
// It tells Hibernate that this class should be mapped to a database table.

import jakarta.persistence.Id;
// It imports the "Id annotation" from the Jakarta Persistence API.

import jakarta.persistence.GeneratedValue;
// It imports the "GeneratedValue annotation" from the Jakarta Persistence API.
// We use it to automatically generate the value of the primary key.

import jakarta.validation.constraints.NotBlank;
 // It imports @NotBlank for checking that a String is not empty or blank.

@Entity //annotation

// It defines the Book class.
// This class represents a book in our Library Management System.
public class Book {
    @Id
// It marks the "id field" as the "primary key" of the database table.
    
    @GeneratedValue
// It tells "JPA to automatically generate a value" for the "primary key".
   
    private Long id;// It stores the unique ID of each book.
// The ID is used to uniquely identify a book in the database.

    @NotBlank(message = "Title is required")
// It ensures that the book title is not null, empty, or blank.
    private String title;
// It stores the title/name of the book.

    @NotBlank(message = "Author is required")
    private String author;
// It stores the name of the author who wrote the book.
    @NotBlank(message = "ISBN is required")
    private String isbn;
// It stores the ISBN (International Standard Book Number) of the book.
    @NotBlank(message = "Category is required")    
    private String category;
// It stores the category or genre of the book.
    private boolean available;
// It stores whether the book is currently available for issuing.
// true means the book is available, and false means it is issued.

// It is a default constructor."public Book()"
// It is used to create a Book object without providing any values.
    public Book(){

    }

// It is a parameterized constructor.
// It is used to create a Book object with the given book details.
    public Book(String title, String author, String isbn, String category, boolean available) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.available = available;
    }

// It returns the unique ID of the book.
    public Long getId() {
        return id;
    }

// It sets the unique ID of the book.
    public void setId(Long id) {
        this.id = id;
    }

//It returns the title of the book. 
    public String getTitle(){
        return title;
    }
//It set or update the title of book.
    public void setTitle(String title){
        this.title = title;
    }

//It returns the author of the book. 
    public String getAuthor(){
        return author;
    }
//It set or update the author of book.
    public void setAuthor(String author){
        this.author = author;
    }

//It returns the isbn of the book. 
    public String getIsbn(){
        return isbn;
    }
// It sets or updates the ISBN of the book.
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

//It returns the category of the book. 
    public String getCategory(){    
        return category;
    }
// It sets or updates the category of the book.
    public void setCategory(String category) {
        this.category = category;
    }

// It returns whether the book is currently available.
    public boolean isAvailable(){
        return available;
    }
// It sets or updates the availability status of the book.
    public void setAvailable(boolean available) {
        this.available = available;
    }



}
