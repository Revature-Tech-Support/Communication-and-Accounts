package com.github.RevatureTechSupport.comms;

import java.time.Duration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class WSHandler implements WebSocketHandler {

    Sinks.Many<String> sink = Sinks.many().replay().all();
    Flux<String> messages = sink.asFlux();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        // return webSocketSession.send(
        //         webSocketSession.receive()
        //             .map(WebSocketMessage::getPayloadAsText)
        //             .doOnNext(System.out::print)
        //             .map(webSocketSession::textMessage)
        //             .log()
        // );

        webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .subscribe(message -> sink.tryEmitNext(message));

        return webSocketSession.send(Mono.delay(Duration.ofMillis(100))
            .thenMany(messages.map(webSocketSession::textMessage)));
    }
}
