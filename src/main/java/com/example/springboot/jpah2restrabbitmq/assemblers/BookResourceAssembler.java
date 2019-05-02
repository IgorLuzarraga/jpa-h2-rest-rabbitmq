package com.example.springboot.jpah2restrabbitmq.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.example.springboot.jpah2restrabbitmq.controllers.BookController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler implements ResourceAssembler<Book, Resource<Book>> {

    @Override
    public Resource<Book> toResource(Book book) {
        return new Resource<>(
                book,
                ControllerLinkBuilder
                        .linkTo(methodOn(BookController.class)
                        .one(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).all())
                        .withRel("books")
        );
    }
}
