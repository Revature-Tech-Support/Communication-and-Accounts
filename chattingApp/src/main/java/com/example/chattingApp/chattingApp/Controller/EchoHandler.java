package com.example.chattingApp.chattingApp.Controller;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .send(
                        session.receive()
                                .map(msg -> "Received On Server User= " + msg.getPayloadAsText())
                                .map(session::textMessage)
                );
    }
}
