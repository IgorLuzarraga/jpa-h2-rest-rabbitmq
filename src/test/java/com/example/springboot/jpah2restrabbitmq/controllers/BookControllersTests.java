package com.example.springboot.jpah2restrabbitmq.controllers;

import com.example.springboot.jpah2restrabbitmq.Application;
import com.example.springboot.jpah2restrabbitmq.domain.Book;
import com.example.springboot.jpah2restrabbitmq.services.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllersTests {

    private static final int SUCCESSFUL_HTTP_REQUEST = 200;
    private static final int SUCCESSFUL_RESOURCE_CREATE_HTTP_REQUEST = 201;
    private static final int SUCCESSFUL_RESOURCE_DELETE_HTTP_REQUEST = 204;
    private static final int BAD_HTTP_REQUEST = 404;
    private static final String TITLE = "Test-Driven Java Development";
    private static final String AUTHOR = "V. Farcic / A. Garcia";
    private static final String EMAIL = "name@mail.com";

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private BookService bookService;

    private HttpClient httpClient;

    @Before
    public void init(){
        // save some books
        saveBooks();

        httpClient = new HttpClient(randomServerPort);
        httpClient.init();
    }

    @After
    public void finish() throws IOException {
        httpClient.finish();
    }

   private void saveBooks(){

       // delete the DB to start fresh.
       bookService.deleteAll();

       bookService.save(new Book("BDD IN ACTION", "John Ferguson Smart", "name@mail.com"));
       bookService.save(new Book("Clean Code", "Robert C. Martin", "name@mail.com"));
       bookService.save(new Book("Clean Architecture", "Robert C. Martin", "name@mail.com"));
       bookService.save(new Book("Redis IN ACTION", "Josiah L. Carlson", "name@mail.com"));
       bookService.save(new Book("Spring Boot IN ACTION", "Craig Walls", "name@mail.com"));
       bookService.save(new Book("Functional Programming in Java", "Venkat Subramaniam", "name@mail.com"));
   }

    @Test
    public void whenGetAllBooks_thenCorrect() {

        // given
        int responseCode = httpClient.getAllBooks();

        // then correct
        assertThat(responseCode, equalTo(SUCCESSFUL_HTTP_REQUEST));
    }

    @Test
    public void givenOneBookId_whenGetThisBook_thenCorrect() {
        // given the books id list, select one book
        List<Integer> booksListId = httpClient.getBooksListId();
        int id = booksListId.get(0);

        // when Get this book from the data base
        int responseCode = httpClient.getBook(id);

        // then correct
        assertThat(responseCode, equalTo(SUCCESSFUL_HTTP_REQUEST));
    }

    @Test
    public void givenOneBookIdThatDosentExist_whenGetThisBook_thenInCorrect(){

        // given one book that dosen´t exist in the data base
        int id = -1;

        // when Get this book from the data base
        int responseCode = httpClient.getBook(id);

        // then incorrect
        assertThat(responseCode, equalTo(BAD_HTTP_REQUEST));
    }

    @Test
    public void givenNewBook_whenAddTheBook_thenCorrect(){

        // given new book
        Book book = getNewBook();

        // when add the book to the data base
        int responseCode = httpClient.addBook(book);

        // then correct
        assertThat(responseCode, equalTo(SUCCESSFUL_RESOURCE_CREATE_HTTP_REQUEST));
    }

    @Test
    public void givenTheBookToUpdate_whenUpdateBook_thenCorrect(){

        // given the books id list, select one book to update
        List<Integer> booksListId = httpClient.getBooksListId();
        int id = booksListId.get(0);
        // and given the book to update
        Book book = getNewBook();

        // when update the book to the data base
        int responseCode = httpClient.updateBook(id, book);

        // then correct
        assertThat(responseCode, equalTo(SUCCESSFUL_RESOURCE_CREATE_HTTP_REQUEST));
    }

    @Test
    public void givenTheBookIdToDelete_whenDeleteBook_thenCorrect() {

        // given the books id list, select one book to delete
        List<Integer> booksListId = httpClient.getBooksListId();
        int id = booksListId.get(0);

        // when delete this book from the data base
        int responseCode = httpClient.deleteBook(id);

        // then correct
        assertThat(responseCode, equalTo(SUCCESSFUL_RESOURCE_DELETE_HTTP_REQUEST));
    }

    private Book getNewBook(){
        return new Book(TITLE, AUTHOR, EMAIL);
    }
}
