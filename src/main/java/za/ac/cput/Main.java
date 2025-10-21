package za.ac.cput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // Start Spring Boot application
        System.out.println("Hello, World!");    // Optional, runs after Spring Boot starts
    }
}
