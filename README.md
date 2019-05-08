# jpa-h2-rest-rabbitmq

Spring Boot Book´s Repository With REST and RabbitMQ
==============

Spring Boot application that implements a book's repository using
CRUD (Create, Read, Update, Delete) operations to create and recover
objects (books) stored in a H2 data base using Spring Data JPA.
The CRUD operations are made via RESTful.
When a CRUD operation is made, the Service sends a message
to the Log Server via RabbitMQ, to log the operation.
Note: the Server Log is located in my repository "Log Server"

Modules:
========
- Spring Boot
- Spring Data JPA
- Spring Boot - HATEOAS for RESTful Services
- Spring Boot Web 
- H2 In-Memory Database
- Spring Boot amqp
- RabbitMQ (https://www.rabbitmq.com/)

Build the jar:
-------------------------
./gradlew build

Run the jar:
-------------------------
java -jar build/libs/jpa-h2-rest-rabbitmq-0.0.1-SNAPSHOT.jar

Test the application:
-------------------------
In order to receive the sended log messages when a CRUD operation 
is made, you need:
1. download RabbitMQ, install and start the service
2. download the Server Log (receives the log messages) and 
start the service
Note: the Server Log is located in my repository "Log Server"
3. Start this application.

The project consists of two Http Serves:
1. http://localhost:8080/books - Restful web Service
2. http://localhost:8090 Log Server
RabbitMQ send the messages between these two servers.

Connect to the server via http://localhost:8080/books using
for instance a Restful API tester, such as Postman, to check the 
CRUD operations and check the logs sended to the other server
http://localhost:8090

Note: the json input for PUT or POST should be like:

{
	"author" : "book´s author",
	"title" : "book´s title"
}
