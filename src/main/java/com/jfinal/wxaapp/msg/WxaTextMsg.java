/**
 * Copyright (c) 2011-2014, L.cm 卢春梦 (qq596392912@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.wxaapp.msg;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 文本消息
 * @author L.cm
 *
 */
public class WxaTextMsg extends WxaMsg {
    private static final long serialVersionUID = -5656735737008641147L;

    // 文本消息内容
    @JsonProperty("Content")
    @JSONField(name = "Content")
    private String content;
    
    // 消息id，64位整型
    @JsonProperty("MsgId")
    @JSONField(name = "MsgId")
    private Long msgId;
    
    public WxaTextMsg(String toUserName, String fromUserName, Integer createTime) {
        super(toUserName, fromUserName, createTime, "text");
    }

    public String getContent() {
        return content;
    }

    public Long getMsgId() {
        return msgId;
    }
}
