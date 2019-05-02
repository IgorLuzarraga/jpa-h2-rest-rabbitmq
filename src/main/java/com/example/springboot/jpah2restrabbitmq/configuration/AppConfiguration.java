package com.example.springboot.jpah2restrabbitmq.configuration;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import com.example.springboot.jpah2restrabbitmq.services.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public CommandLineRunner loadData(BookService bookService){
        return (args) -> {
            bookService.deleteAll();

            // save some books
            bookService.save(new Book("BDD IN ACTION", "John Ferguson Smart", "john@mail.com"));
            bookService.save(new Book("Clean Code", "Robert C. Martin", "robert@mail.com"));
            bookService.save(new Book("Clean Architecture", "Robert C. Martin", "robert@mail.com"));
            bookService.save(new Book("Redis IN ACTION", "Josiah L. Carlson", "josiah@mail.com"));
            bookService.save(new Book("Spring Boot IN ACTION", "Craig Walls", "craig@mail.com"));
            bookService.save(new Book("Functional Programming in Java", "Venkat Subramaniam", "venkat@mail.com"));
        };
    }

}

