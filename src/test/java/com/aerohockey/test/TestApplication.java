package com.aerohockey.test;

import com.aerohockey.WebSocketConfig;
import com.aerohockey.websocket.GameSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.aerohockey.Application;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

/**
 * Created by sergey on 21.03.17.
 */

@SpringBootApplication
@Import(Application.class)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{WebSocketConfig.class, Application.class}, args);
    }

    @Bean
    public WebSocketHandler gameWebSocketHandler() {
        return new PerConnectionWebSocketHandler(GameSocketHandler.class);
    }
}