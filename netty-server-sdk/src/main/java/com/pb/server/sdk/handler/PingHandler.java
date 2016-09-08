package com.pb.server.sdk.handler;


import com.pb.server.sdk.constant.PBCONSTANT;
import pb.server.dao.model.Message;
import com.pb.server.sdk.session.PBSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳包处理器
 */
public class PingHandler implements PBRequestHandler {

    private static Logger logger = LoggerFactory.getLogger(PingHandler.class);

    /**
     * @param session 用户session
     * @param msg     ping 包
     * @return ping_ack 包
     */
    @Override
    public Message process(PBSession session, Message msg) {
        logger.info("Ping from:" + session.getSession().remoteAddress());
        Message reply = new Message();
        reply.setType(PBCONSTANT.PING_ACK_FLAG);
        reply.setTime(System.currentTimeMillis());
        reply.setParam("r_uid", msg.get("s_uid"));
        return reply;
    }

}
