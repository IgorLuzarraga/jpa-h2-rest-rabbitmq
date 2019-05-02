package com.example.springboot.jpah2restrabbitmq.services;

import com.example.springboot.jpah2restrabbitmq.domain.Book;

public interface SenderService {
    public void sendDeleted(Book book);
    public void sendAdded(Book book);
    public void sendUpdated(Book book);
}
