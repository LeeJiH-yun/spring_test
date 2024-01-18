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
                session.sendMessage(new TextMessage("Woooof"));
            }
            case ("2") -> {
                System.out.println("Cat button was pressed");
                session.sendMessage(new TextMessage("Meooow"));
            }
            case ("3") -> {
                System.out.println("Pig button was pressed");
                session.sendMessage(new TextMessage("Bork Bork"));
            }
            case ("4") -> {
                System.out.println("Fox button was pressed");
                session.sendMessage(new TextMessage("Fraka-kaka-kaka"));
            }
            default -> System.out.println("Connected to Client");
        }
    }
}
