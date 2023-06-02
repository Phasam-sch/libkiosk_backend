package com.sch.libkiosk.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class MyWebSocketHandler  extends TextWebSocketHandler {

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception   {
        log.info("AF_Connect Established" + session);
        this.session = session;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // 수신된 메시지 처리 로직을 구현합니다.

        // 클라이언트로 응답을 보낼 때는 아래와 같이 전송합니다.
        session.sendMessage(new TextMessage("서버에서 보낸 응답 메시지"));
    }

    public void sendMessageToClient(String message) throws Exception{
        if(session != null && session.isOpen()){
            session.sendMessage(new TextMessage(message));
        }
    }
}
