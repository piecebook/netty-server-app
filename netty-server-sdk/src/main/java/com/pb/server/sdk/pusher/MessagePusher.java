package com.pb.server.sdk.pusher;


import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/8.
 */

/**
 * 向客户端推送消息的类必须实现该接口
 */
public interface MessagePusher {
    public String push(Message msg);
}
