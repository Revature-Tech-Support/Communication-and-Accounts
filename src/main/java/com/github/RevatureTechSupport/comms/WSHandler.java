package com.github.RevatureTechSupport.comms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Sinks.Many<Message> sink = Sinks.many().replay().all();
    private Flux<Message> messages = sink.asFlux();

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        webSocketSession.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .map(this::deserializeMessage)
            .log()
            .subscribe(this::onNext, this::onError, this::onComplete);

        return webSocketSession.send(messages.map(this::serializeMessage).map(webSocketSession::textMessage));
    }

    private void onNext(Message message) {
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

    private Message deserializeMessage(String string) {
        Message message;
        try {
            message = OBJECT_MAPPER.readValue(string, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            message = null;
        }
        return message;
    }

    private String serializeMessage(Message message) {
        String string;
        try {
            string = OBJECT_MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            string = null;
        }
        return string;
    }
}
