package com.github.RevatureTechSupport.comms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@SpringBootTest
class CommsApplicationTests {

	@Test
	void sendingMessageReturnsMessage() {
		WebSocketClient client = new ReactorNettyWebSocketClient();
		URI uri = URI.create("ws://localhost:8080/ws");	
		
		client.execute(uri,
			session -> session.send(Mono.just(session.textMessage("test")))
				.thenMany(session.receive())
				.map(WebSocketMessage::getPayloadAsText)
				.then())
			.block();
	}
}
