//it specifies that the LibraryManagementApplication class is belongs to the com.library.library_management pacakge.

// if we want to use a class or other type from another package, we use import statement to make it available in our code. 

package com.library.library_management; 


// Imports SpringApplication from Spring Boot.
// SpringApplication is used to start/run our Spring Boot application.
import org.springframework.boot.SpringApplication;


// Imports the @SpringBootApplication annotation.
// This annotation tells Spring Boot that LibraryManagementApplication is the main Spring Boot application class.
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marks LibraryManagementApplication as the main Spring Boot application.
// It also enables Spring Boot's auto-configuration and component scanning.
@SpringBootApplication
public class LibraryManagementApplication {

	// main() is the entry point of a Java application.
    // The JVM starts executing the program from this method.

	public static void main(String[] args) {

		// Starts the Spring Boot application.
        // LibraryManagementApplication.class tells Spring Boot
        // which class is the main application class.
        // args contains command-line arguments passed to the application.
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
