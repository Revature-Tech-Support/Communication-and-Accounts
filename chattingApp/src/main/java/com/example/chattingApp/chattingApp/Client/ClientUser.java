package com.example.chattingApp.chattingApp.Client;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientUser {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {

        WebSocketClient client = new ReactorNettyWebSocketClient();

        // User Listens to support
        URI url = new URI("ws://localhost:8080/support");
        client.execute(url, session ->
                session.receive()
                        .doOnNext(System.out::println)
                        .then()).block();
    }
}
