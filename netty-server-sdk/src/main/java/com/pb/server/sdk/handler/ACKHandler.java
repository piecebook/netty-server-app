package com.pb.server.sdk.handler;


import com.pb.server.sdk.MessageFactory.MessageHolder;
import com.pb.server.sdk.session.PBSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/8.
 */

/**
 * 聊天消息确认处理器
 */
public class ACKHandler implements PBRequestHandler{
    private static Logger logger = LoggerFactory.getLogger(ACKHandler.class);

    /**
     *
     * @param session 用户连接session
     * @param msg ACK 包
     * @return null
     *
     * 通过ACK包知道客户端已经收到服务端推送的消息，无须像客户端发送回复
     */
    @Override
    public Message process(PBSession session, Message msg) {
        logger.info("ACK:" + msg.toString());
        //向等待ACK的消息队列 加入 该ACK包，由MessageACKDaemon处理
        MessageHolder.rec_messages.add(msg.get("msg_key"));
        return null;
    }
}
