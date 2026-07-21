package com.iot.green.house.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig
        implements WebSocketMessageBrokerConfigurer {

    public WebSocketConfig() {
        log.info("################ WS CONSTRUCTOR ################");
    }

    @Override
    public void configureMessageBroker(
            MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
        log.info("################ BROKER ################");
    }

    @Override
    public void registerStompEndpoints(
            StompEndpointRegistry registry) {
        log.info("################ REGISTER WS ################");
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
//                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();

    }
}