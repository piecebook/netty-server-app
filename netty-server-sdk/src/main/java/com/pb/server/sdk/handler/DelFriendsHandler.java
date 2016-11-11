package com.pb.server.sdk.handler;

import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.pusher.PBMessagePusher;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.util.ContexHolder;
import com.pb.server.service.user.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import pb.server.dao.model.Message;

/**
 * Created by PieceBook on 2016/9/24.
 */
public class DelFriendsHandler implements PBRequestHandler {
    @Autowired
    private FriendsService friendsService;

    @Override
    public Message process(PBSession session, Message msg) {
        Message reply = new Message();
        reply.setMsg_id(System.currentTimeMillis());
        reply.setParam("s_uid", PBCONSTANT.SYSTEM);
        reply.setParam("r_uid", msg.get("s_uid"));
        reply.setType(PBCONSTANT.DEL_FRIEND_FLAG);
        friendsService.remove(msg.get("s_uid"), msg.get("r_uid"));
        ((PBMessagePusher) ContexHolder.getBean("messagePusher")).push(msg);
        return reply;
    }

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }
}
