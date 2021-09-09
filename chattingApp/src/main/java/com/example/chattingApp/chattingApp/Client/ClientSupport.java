package com.example.chattingApp.chattingApp.Client;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientSupport {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        System.out.println("running");
        WebSocketClient client = new ReactorNettyWebSocketClient();
        // Support listens to client
        URI url = new URI("ws://localhost:8080/user");
        client.execute(url, session ->
                session.receive()
                        .doOnNext(message -> System.out.println("client says: " + message))
                        .then())
        ;

    }
}
