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
    // https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Sinks.html
    // many() enables multicasting (broadcasting to multiple subscribers)
    // replay() retains (all) history
    private Sinks.Many<String> sink = Sinks.many().replay().all();
    private Flux<String> messages = sink.asFlux();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .log()
            .subscribe(this::onNext, this::onError, this::onComplete);

        return webSocketSession.send(messages.map(webSocketSession::textMessage));
    }

    private void onNext(String message) {
        sink.tryEmitNext(message);
    }

    private void onError(Throwable error) {
        error.printStackTrace();
    }

    private void onComplete() {
        // Upon all subscribers unsubscribing, create a new sink to erase previous sink history
        if (sink.currentSubscriberCount() == 1) {
            sink = Sinks.many().replay().all();
            messages = sink.asFlux();
        }
    }
}
