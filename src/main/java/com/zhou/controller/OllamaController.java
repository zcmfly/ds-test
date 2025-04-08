package com.zhou.controller;

import com.zhou.service.OllamaService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("ollama")
public class OllamaController {

    @Autowired
    private OllamaChatClient ollamaChatClient;

    @Resource
    private OllamaService ollamaService;

    @GetMapping("ai/chat")
    public Object aiOllamaChat (@RequestParam String msg) {
        return ollamaService.aiOllamaChat(msg);
    }

    @GetMapping("ai/stream1")
    public Flux<ChatResponse> aiOllamaStream1 (@RequestParam String msg) {
        return ollamaService.aiOllamaStream1(msg);
    }

    @GetMapping("ai/stream2")
    public List<String> aiOllamaStream2 (@RequestParam String msg) {
        return ollamaService.aiOllamaStream2(msg);
    }

}
