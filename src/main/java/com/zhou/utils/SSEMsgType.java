package com.zhou.utils;

public enum SSEMsgType {

    MESSAGE("message","单次发送的普通消息"),
    ADD("add","消息追加，用于流式stream推送"),
    FINISH("finish","消息完成"),
    DONE("done","消息完成");

    public final String type;
    public final String value;

    SSEMsgType(String type,String value) {
        this.type = type;
        this.value = value;
    }

}
