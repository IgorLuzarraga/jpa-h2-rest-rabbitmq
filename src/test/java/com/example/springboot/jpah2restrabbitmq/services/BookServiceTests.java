package com.example.springboot.jpah2restrabbitmq.services;

import com.example.springboot.jpah2restrabbitmq.Application;
import com.example.springboot.jpah2restrabbitmq.domain.Book;
import com.example.springboot.jpah2restrabbitmq.services.BookService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceTests {
    private static final String TITLE = "Test-Driven Java Development";
    private static final String AUTHOR = "V. Farcic / A. Garcia";
    private static final String EMAIL = "name@mail.com";

    @Autowired
    BookService bookService;

    @Before
    public void init() {
        // delete the DB to start fresh.
        bookService.deleteAll();
    }

    @Test
    public void givenBook_WhenSaveBook_ThenStoreBook() {

        // Number of books before save a new book
        long bookCountStart = bookService.count();

        // given a new book
        Book book = new Book(TITLE, AUTHOR, EMAIL);

        // when
        bookService.save(book);

        // then
        long bookCountEnd = bookService.count();
        assertEquals(bookCountStart+1, bookCountEnd);
    }

    @Test
    public void givenBook_WhenDeleteBook_ThenDeleteBook() {
        // Number of books before save a new book
        long bookCountStart = bookService.count();

        // given a book
        Book book = new Book(TITLE, AUTHOR, EMAIL);
        bookService.save(book);

        // The number of books increments in one.
        long bookCountSave = bookService.count();
        assertEquals(bookCountStart+1, bookCountSave);

        // when
        bookService.delete(book);

        // then the book is deleted
        long bookCountEnd = bookService.count();
        assertEquals(bookCountStart, bookCountEnd);
    }

    @Test
    public void givenBook_WhenFindByAuthor_ThenFindBook() {

        // given a book
        Book book = new Book(TITLE, AUTHOR, EMAIL);
        bookService.save(book);

        // when
        List<Book> bookList = bookService.findByAuthor(AUTHOR);

        // then the book finded is the one saved
        Book bookFind = bookList.get(0);
        assertEquals(book, bookFind);
    }

    @Test
    public void givenBook_WhenFindByTitle_ThenFindBook() {

        // given a book
        Book book = new Book(TITLE, AUTHOR, EMAIL);
        bookService.save(book);

        // when
        List<Book> bookList = bookService.findByTitle(TITLE);

        // then the book finded is the one saved
        Book bookFind = bookList.get(0);
        assertEquals(book, bookFind);
    }
}

