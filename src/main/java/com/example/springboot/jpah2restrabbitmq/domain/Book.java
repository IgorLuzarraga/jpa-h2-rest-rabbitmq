package com.example.springboot.jpah2restrabbitmq.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Book {

    private @Id @GeneratedValue Long id;
    private String title;
    private String author;
    private String email;

    public Book(){}

    public Book(String title, String author, String email) {
        this.title = title;
        this.author = author;
        this.email = email;
    }
}
