package com.example.springboot.jpah2restrabbitmq.controllers;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class jsonMapper {

     public static List<Integer> getListIdFromJson(String jsonBookList) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> booksIdList = new ArrayList<>();

        JsonNode rootNode = mapper.readTree(jsonBookList);
        JsonNode _embeddedNode = rootNode.path("_embedded");
        JsonNode bookListNode = _embeddedNode.path("bookList");

        Iterator<JsonNode> elements = bookListNode.elements();
        while(elements.hasNext()) {
            JsonNode element = elements.next();
            JsonNode idNode = element.path("id");
            booksIdList.add(idNode.asInt());
        }

        return booksIdList;
    }

    public static String getJsonFromBook(Book book) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(book);
    }
}
