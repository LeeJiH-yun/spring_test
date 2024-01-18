package com.example.spring_test;

import com.example.websocket.WebSocketConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTestApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/jihyun");
		SpringApplication.run(WebSocketConfiguration.class, args);
	}
}
