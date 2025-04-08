package com.zhou.service;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OllamaService {

    public Object aiOllamaChat (String msg);

    public Flux<ChatResponse> aiOllamaStream1 (String msg) ;

    public List<String> aiOllamaStream2 (String msg);

}
