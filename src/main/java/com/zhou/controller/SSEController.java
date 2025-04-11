package com.zhou.controller;

import com.zhou.service.OllamaService;
import com.zhou.utils.SSEMsgType;
import com.zhou.utils.SSEServer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("sse")
public class SSEController {

    @Resource
    private OllamaService ollamaService;

    @GetMapping(path = "connect",produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter aiOllamaChat (@RequestParam String userId) {
        return SSEServer.connect(userId);
    }

    @GetMapping("sendMessage")
    public Object sendMessage (@RequestParam String userId,@RequestParam String message) {
        SSEServer.sendMessage(userId,message, SSEMsgType.MESSAGE);
        return "ok";
    }

}
