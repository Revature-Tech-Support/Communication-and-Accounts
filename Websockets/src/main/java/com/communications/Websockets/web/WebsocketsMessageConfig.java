package com.communications.Websockets.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketsMessageConfig implements WebSocketMessageBrokerConfigurer
    {
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry)
        {
            registry.addEndpoint("/chat-example").withSockJS();
        }

     @Override
    public void configureMessageBroker (final MessageBrokerRegistry registry1)
        {
          registry1.setApplicationDestinationPrefixes("/app");
          registry1.enableSimpleBroker("/topic");
        }
    }
