package com.example.springboot.jpah2restrabbitmq.sender;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {
    private final String QUEUE_NAME = "LOG_MESSAGES_QUEUE";

    @Autowired
    RabbitMessagingTemplate template;

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }

    public void send(String message){
        template.convertAndSend(QUEUE_NAME, message);
    }
}


