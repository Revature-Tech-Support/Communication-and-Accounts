package com.example.chattingApp.chattingApp.Controller;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//public class TestHandler implements WebSocketHandler {

//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        return session.receive()
//                .doOnNext(message -> {
//                    // ...
//                })
//                .concatMap(message -> {
//                    // ...
//                })
//                .then();
//    }
//}
