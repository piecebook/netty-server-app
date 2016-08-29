package com.pb.server.sdk.handler;


import com.pb.server.sdk.MessageFactory.MessageHolder;
import com.pb.server.sdk.session.PBSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/8.
 */
public class ACKHandler implements PBRequestHandler{
    private static Logger logger = LoggerFactory.getLogger(ACKHandler.class);

    @Override
    public Message process(PBSession session, Message msg) {
        logger.info("ACK:" + msg.toString());
        MessageHolder.rec_messages.add(msg.get("msg_key"));
        return null;
    }
}
