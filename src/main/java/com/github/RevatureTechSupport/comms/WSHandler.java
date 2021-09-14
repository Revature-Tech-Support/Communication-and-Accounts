package com.github.RevatureTechSupport.comms;

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

        webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .log()
            .subscribe(message -> sink.tryEmitNext(message),
                error -> error.printStackTrace(),
                () -> {
                    if (sink.currentSubscriberCount() == 1) {
                    sink = Sinks.many().replay().all();
                    messages = sink.asFlux();
                }});

        return webSocketSession.send(messages.map(webSocketSession::textMessage));
    }
}
