package com.example.springboot.jpah2restrabbitmq.services;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import com.example.springboot.jpah2restrabbitmq.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SenderServiceImpl implements SenderService{
    private final Sender sender;

    @Autowired
    public SenderServiceImpl(Sender sender){
        this.sender = sender;
    }

    public void sendDeleted(Book book){
        String log = buildLogMessage(book, "DELETED");

        sender.send(log);
    }

    public void sendAdded(Book book){
        String log = buildLogMessage(book, "ADDED");

        sender.send(log);
    }

    public void sendUpdated(Book book){
        String log = buildLogMessage(book, "UPDATED");

        sender.send(log);
    }

    private String buildLogMessage(Book book, String action){
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append(getDateTime());
        strBuilder.append(" --> ");
        strBuilder.append(book.toString());
        strBuilder.append(" ");
        strBuilder.append("Action: ");
        strBuilder.append(action);

        return strBuilder.toString();
    }

    private String getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return localDateTime.format(formatter);
    }
}
