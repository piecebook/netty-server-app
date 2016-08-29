package com.pb.server.sdk.pusher;


import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/8.
 */
public interface MessagePusher {
    public void push(Message msg);
}
