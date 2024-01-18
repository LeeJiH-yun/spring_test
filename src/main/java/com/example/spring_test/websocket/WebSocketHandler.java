package com.example.spring_test.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = message.getPayload();
        System.out.println("msg : "+msg);

        switch (msg) {
            case ("1") -> {
                System.out.println("Dog button was pressed");
                session.sendMessage(new TextMessage("이지현"));
            }
            case ("2") -> {
                System.out.println("Cat button was pressed");
                session.sendMessage(new TextMessage("유원정"));
            }
            case ("3") -> {
                System.out.println("Pig button was pressed");
                session.sendMessage(new TextMessage("이상환"));
            }
            case ("4") -> {
                System.out.println("Fox button was pressed");
                session.sendMessage(new TextMessage("멍청이"));
            }
            default -> System.out.println("나는 멍청이다.");
        }
    }
}
