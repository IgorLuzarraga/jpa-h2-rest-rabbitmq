package com.example.springboot.jpah2restrabbitmq.repositories;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
   List<Book> findByTitle(String title);
   List<Book> findByAuthor(String author);
}