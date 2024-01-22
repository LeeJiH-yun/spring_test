package com.example.spring_test.websocket;

import com.example.spring_test.domain.ChatMessage;
import com.example.spring_test.domain.ChatRoom;
import com.example.spring_test.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final String payload = message.getPayload();

        final ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        final ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());

        final Set<WebSocketSession> sessions = room.getSessions();

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            return;
        }

        if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            sessions.remove(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            return;
        }

        sendToEachSocket(sessions, message);
    }

    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream()
                .forEach(roomSession -> sendMessage(message, roomSession));
    }

    private static void sendMessage(TextMessage message, WebSocketSession roomSession) {
        try {
            roomSession.sendMessage(message);
        } catch (IOException e) {
            log.error("오류 핸들링 해");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}
