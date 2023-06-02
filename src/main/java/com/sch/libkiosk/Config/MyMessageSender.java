package com.sch.libkiosk.Config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MyMessageSender {
    private MyWebSocketHandler webSocketHandler;

    @Autowired
    public MyMessageSender(MyWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendMessageToClient(String message) throws Exception{
        webSocketHandler.sendMessageToClient(message);
    }
}
