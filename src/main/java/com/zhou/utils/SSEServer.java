package com.zhou.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class SSEServer {
    private static Map<String, SseEmitter> sseClents = new ConcurrentHashMap<>();

    private static AtomicInteger onlineCounter = new AtomicInteger(0);

    public static SseEmitter connect(String userId ) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(completeCallback(userId));
        sseEmitter.onError(errorCallback(userId));
        sseEmitter.onTimeout(timeoutCallback(userId));

        sseClents.put(userId, sseEmitter);
        log.info("当前创建新的SSE连接，用户ID为：{}",userId);

        onlineCounter.incrementAndGet();
        return sseEmitter;
    }

    public static void sendMessage(String userId,String message,SSEMsgType type) {
        if(!sseClents.containsKey(userId)) {
            return;
        }
        SseEmitter sseEmitter = sseClents.get(userId);
        sendEmitterMessage(sseEmitter,userId,message,type);
    }

    public static void sendMessageToAllUsers(String message) {
        if(CollectionUtils.isEmpty(sseClents)) {
            return;
        }
        sseClents.forEach((userId,sseEmitter) -> {
            sendMessage(userId,message,SSEMsgType.MESSAGE);
        });
    }

    public static void sendEmitterMessage(SseEmitter sseEmitter,
                                   String userId,
                                   String message,
                                   SSEMsgType msgType) {
        SseEmitter.SseEventBuilder builder = SseEmitter.event().id(userId).name(msgType.type).data(message);

        try {
            sseEmitter.send(builder);
        } catch (IOException e) {
            log.error("用户【{}】的消息推送发生错误",userId);
            removeConnection(userId);
        }

    }

    public static void stopServer(String userId) {
        if(CollectionUtils.isEmpty(sseClents)) {
            return;
        }
        SseEmitter sseEmitter = sseClents.get(userId);
        if(sseEmitter != null) {
            sseEmitter.complete();
            log.info("连接关闭成功，关闭的用户为{}",userId);
        }else {
            log.warn("当前连接已关闭，请不要重复操作");
        }
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
        onlineCounter.decrementAndGet();
    }

    public static int getOnlineCounter() {
        return onlineCounter.intValue();
    }
}
