package com.zhou.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class SSEServer {
    private static Map<String, SseEmitter> sseClents = new ConcurrentHashMap<>();
    public static SseEmitter connect(String userId ) {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.onCompletion(completeCallback(userId));
        sseEmitter.onError(errorCallback(userId));
        sseEmitter.onTimeout(timeoutCallback(userId));

        sseClents.put(userId, sseEmitter);
        log.info("当前创建新的SSE连接，用户ID为：{}",userId);
        return sseEmitter;
    }

    private static Runnable completeCallback(String userId){
        return () ->{
            log.info("SSE连接完成并结束，用户ID为：{}",userId);
            removeConnection(userId);
        };
    }

    private static Runnable timeoutCallback(String userId){
        return () ->{
            log.info("SSE连接超时，用户ID为：{}",userId);
            removeConnection(userId);
        };
    }

    private static Consumer<Throwable> errorCallback(String userId){
        return (Throwable throwable) ->{
            log.info("SSE连接发生错误，用户ID为：{}",userId);
            removeConnection(userId);
        };
    }

    public static void removeConnection(String userId){
        sseClents.remove(userId);
        log.info("SSE连接被移除，移除用户ID为：{}",userId);
    }
}
