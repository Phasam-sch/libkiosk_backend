package com.sch.libkiosk.Service;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ServerEndpoint(value="sock")
public class camService {
    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String msg, Session session) throws Exception {
        log.info("receive message : " + msg);
        for (Session s : clients) {
                System.out.println("send data : " + msg);
            s.getBasicRemote().sendText(msg);
        }
    }
    @OnOpen
    public void onOpen(Session s){
        log.info("[try]Open Session: " + s.toString());

        if(!clients.contains(s)){
            clients.add(s);
            log.info("[Success]Session Open : " + s );
        }else {
            log.info("[fail] Session already connected");
            }
    }

    @OnClose
    public void onClose(Session s){

    }
}
