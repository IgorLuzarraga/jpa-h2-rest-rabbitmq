package com.example.springboot.jpah2restrabbitmq.services;

import com.example.springboot.jpah2restrabbitmq.repositories.BookRepository;
import com.example.springboot.jpah2restrabbitmq.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book>  findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Book save(Book book)
    {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book){
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAll(){
        bookRepository.deleteAll();
    }

    @Override
    public Long count(){
        return bookRepository.count();
    }
}
