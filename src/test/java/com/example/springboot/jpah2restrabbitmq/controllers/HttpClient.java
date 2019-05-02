package com.example.springboot.jpah2restrabbitmq.controllers;

import com.example.springboot.jpah2restrabbitmq.domain.Book;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;


public class HttpClient {
    private static final int SERVER_ERROR = 500;
    private final int serverPort;
    private CloseableHttpClient httpClient;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public HttpClient(int serverPort){
        this.serverPort = serverPort;
    }

    public void init(){
        httpClient = HttpClients.createDefault();
    }

    public void finish() throws IOException{
        httpClient.close();
    }

    // GET all books
    public int getAllBooks() {
        Option<HttpGet> httpRequest = getHttpGetAll();

        return  Try.of(() -> httpClient.execute(httpRequest.get()))
                        .onSuccess(suc -> log.info("Success Http request --> func. getAllBooks"))
                        .onFailure(exc -> log.error("Error Http request --> func. getAllBooks", exc))
                        .map(CloseableHttpResponse::getStatusLine)
                        .map(StatusLine::getStatusCode)
                        .getOrElse(SERVER_ERROR);
    }

    // Get one book
    public int getBook(int id){
        Option<HttpGet> httpRequest = getHttpGetOne(id);

        return  Try.of(() -> httpClient.execute(httpRequest.get()))
                .onSuccess(suc -> log.info("Success Http request --> func. getBook"))
                .onFailure(exc -> log.error("Error Http request --> func. getBook", exc))
                .map(CloseableHttpResponse::getStatusLine)
                .map(StatusLine::getStatusCode)
                .getOrElse(SERVER_ERROR);
    }

    // PUT
    public int updateBook(int id, Book book){
        Optional<HttpPut> httpRequest = getHttpUpdate(id, book);

        return  Try.of(() -> httpClient.execute(httpRequest.get()))
                .onSuccess(suc -> log.info("Success Http request --> func. updateBook"))
                .onFailure(exc -> log.error("Error Http request --> func. updateBook", exc))
                .map(CloseableHttpResponse::getStatusLine)
                .map(StatusLine::getStatusCode)
                .getOrElse(SERVER_ERROR);
    }

    // POST
    public int addBook(Book book){

        Optional<HttpPost> httpRequest = getHttpAdd(book);

        return  Try.of(() -> httpClient.execute(httpRequest.get()))
                .onSuccess(suc -> log.info("Success Http request --> func. addBook"))
                .onFailure(exc -> log.error("Error Http request --> func. addBook", exc))
                .map(CloseableHttpResponse::getStatusLine)
                .map(StatusLine::getStatusCode)
                .getOrElse(SERVER_ERROR);
    }

    // DELETE
    public int deleteBook(int id){
        Option<HttpDelete> httpRequest = getHttpDelete(id);

        return  Try.of(() -> httpClient.execute(httpRequest.get()))
                .onSuccess(suc -> log.info("Success Http request --> func. deleteBook"))
                .onFailure(exc -> log.error("Error Http request --> func. deleteBook", exc))
                .map(CloseableHttpResponse::getStatusLine)
                .map(StatusLine::getStatusCode)
                .getOrElse(SERVER_ERROR);
    }

    public List<Integer> getBooksListId() {
        Option<HttpGet> httpGet = getHttpGetAll();

        return  Try.of(() -> httpClient.execute(httpGet.get()))
                .onSuccess(suc -> log.info("Success Http request --> func. getBooksListId"))
                .onFailure(exc -> log.error("Error Http request --> func. getBooksListId", exc)).map(CloseableHttpResponse::getEntity)
            .mapTry(EntityUtils::toString)
            .mapTry(jsonMapper::getListIdFromJson)
            .get();
    }

    private String getBaseUrl(){
        return "http://localhost:" + serverPort + "/books";
    }

    private String getBaseUrlWithId(int id){
        return getBaseUrl() + "/" + id;
    }

    private Option<URI> getUri(String baseUrl) {
        return Option.of(URI.create(baseUrl));
    }

    private Option<HttpGet> getHttpGet(Option<URI> uri){
        return Option.of(new HttpGet(uri.get()));
    }

    private Option<HttpGet> getHttpGetAll() {
        return getHttpGet(getUri(getBaseUrl()));
    }

    private Option<HttpGet> getHttpGetOne(int id){
        return getHttpGet(getUri(getBaseUrlWithId(id)));
    }

    private Option<HttpDelete> getHttpDelete(int id){
        return Option.of(new HttpDelete(getUri(getBaseUrlWithId(id)).get()));
    }

    private Optional<HttpPost> getHttpAdd(Book book)  {
        Optional<HttpPost> httpPostOptional = Optional.ofNullable(new HttpPost(getUri(getBaseUrl()).get()));

        httpPostOptional.ifPresent(httpPost -> {
            httpPost.setEntity(getEntity(book));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
        });

        return httpPostOptional;
    }

    private Optional<HttpPut> getHttpUpdate(int id, Book book)  {
        Optional<HttpPut> httpPutOptional = Optional.ofNullable(new HttpPut(getUri(getBaseUrlWithId(id)).get()));

        httpPutOptional.ifPresent(httpPut -> {
            httpPut.setEntity(getEntity(book));
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
        });

        return httpPutOptional;
    }

    private StringEntity getEntity(Book book){
         return Try
                .of(() -> jsonMapper.getJsonFromBook(book))
                .mapTry(this::getStringEntity)
                .onSuccess(succ -> log.info("Success from Book to Json"))
                .onFailure(exc -> log.error("Error from Book to Json"))
                .get();
    }

    private StringEntity getStringEntity(String json) throws IOException{
        return new StringEntity(json);
    }
}
