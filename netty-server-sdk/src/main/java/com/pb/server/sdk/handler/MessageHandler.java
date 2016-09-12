package com.pb.server.sdk.handler;


import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.pusher.PBMessagePusher;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import pb.server.dao.model.Message;

/**
 * 聊天消息转发处理器
 */
public class MessageHandler implements PBRequestHandler {

    /**
     * @param session 用户连接session
     * @param msg     聊天消息体
     * @return 聊天消息ACK
     * <p>
     * 用户之间发送消息通过服务端转发，
     * 服务端在这里接收到用户的消息，服务端先给发送用户发送ACK包，代表服务端已经接收到了消息
     * 然后服务端再把消息转发给接收用户并持久化消息，接收用户客户端收到消息后需要发送ACK包给服务器
     * 服务端如果没有接收到客户端的ACK包，则会将消息持久化到离线消息表
     * <p>
     * 消息发送时间已服务端接收到的时间为准
     */
    @Override
    public Message process(PBSession session, Message msg) {
        SessionManage sessionManager = (SessionManage) ContexHolder
                .getBean("sessionManager");
        PBSession receiver_session = sessionManager.get(msg.get("r_uid"));//得到接收用户连接的session
        Message reply = new Message();//ACK包
        reply.setMsg_id(msg.getMsg_id());
        reply.setType(PBCONSTANT.MESSAGE_REPLY_FLAG);
        reply.setParam("r_uid", msg.get("s_uid"));

        String result = ((PBMessagePusher) ContexHolder.getBean("messagePusher")).push(msg);//用户在线：result=sc； 用户不在线：result=fl

        reply.setParam("st", result);
        reply.setParam("s_uid", PBCONSTANT.SYSTEM);
        return reply;
    }
}
