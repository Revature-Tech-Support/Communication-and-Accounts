# Communications Microservice

## Application users are: 
*	IT Support Professional
*	User/Client

## IT Support can:
* Start a chat with the next available User
* Send and receive messages
* Close the chat with the issue resolved

## User can:
* Enter a chat with the next IT professional
* Send and receive messages, and leave chat

## Technology Stack
* Java 8+
* Karate Test Framework
* Spring Boot
* Reactive Microservices
  *	Spring WebFlux (Reactive WebSockets)

## How to run
```
mvn spring-boot:run
```

## Endpoints
Users can connect to the WebSocket at `ws://localhost:8080/ws`

## Team Members
* [Jonathan Gomez](https://github.com/JonathanAGomez)
* [Tam Nguyen](https://github.com/tamhpn)
* [Olenka Quispe](https://github.com/Olenkaqh)
* [Jesus Esquer](https://github.com/jm27)
