package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Config.MyMessageSender;
import com.sch.libkiosk.Service.CamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cam")
public class CamController {

    private MyMessageSender myMessageSender;

    @Autowired
    public void MyController(MyMessageSender myMessageSender){
        this.myMessageSender = myMessageSender;
    }

    @GetMapping("/sendMessage/{msg}")
    public void sendMessage(@PathVariable String msg) throws Exception{
        if(msg.equals("login") || msg.equals("signup") || msg.equals("rfidscan")){
            log.info("전송 메시지: " + msg);
            myMessageSender.sendMessageToClient(msg);
        }
    }

}
