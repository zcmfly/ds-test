package com.zhou.service.impl;

import com.zhou.service.OllamaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OllamaServiceImpl implements OllamaService {

    @Autowired
    private OllamaChatClient ollamaChatClient;

    @Override
    public Object aiOllamaChat(String msg) {
        return ollamaChatClient.call(msg);
    }

    @Override
    public Flux<ChatResponse> aiOllamaStream1(String msg) {
        Prompt prompt = new Prompt(new UserMessage(msg));
        Flux<ChatResponse> streamResponse = ollamaChatClient.stream(prompt);
        return streamResponse;
    }

    @Override
    public List<String> aiOllamaStream2(String msg) {
        Prompt prompt = new Prompt(new UserMessage(msg));
        Flux<ChatResponse> streamResponse = ollamaChatClient.stream(prompt);
        List<String> list = streamResponse.toStream().map(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            log.info(content);
            return content;
        }).collect(Collectors.toList());
        return list;
    }
}
