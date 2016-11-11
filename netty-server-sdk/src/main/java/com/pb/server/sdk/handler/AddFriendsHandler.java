package com.pb.server.sdk.handler;

/**
 * Created by piecebook on 2016/9/12.
 */

import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.pusher.PBMessagePusher;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.util.ContexHolder;
import com.pb.server.service.user.FriendsService;
import pb.server.dao.model.Message;

/**
 * 好友关系处理器
 */
public class AddFriendsHandler implements PBRequestHandler {
    private FriendsService friendsService;

    @Override
    public Message process(PBSession session, Message msg) {
        Message reply = new Message();
        reply.setMsg_id(msg.getMsg_id());
        reply.setParam("st", "sc");
        reply.setParam("s_uid", PBCONSTANT.SYSTEM);
        if (msg.getType() == PBCONSTANT.ADD_FRIENDS_FLAG) {
            //用户发起添加好友请求
            ((PBMessagePusher) ContexHolder.getBean("messagePusher")).push(msg);
            reply.setType(PBCONSTANT.ADD_FRIENDS_MSG_ACK_FLAG);
        } else {
            //添加好友请求应答
            reply.setType(PBCONSTANT.ADD_FRIENDS_ACK_FLAG);
            if (msg.get("msg").equals("sc")) {
                //同意添加好友
                reply.setTime(System.currentTimeMillis());
                String sender = msg.get("s_uid");
                String receiver = msg.get("r_uid");
                long sid = friendsService.add(sender, receiver);
                if (sid == -1) {
                    reply.setParam("msg", "fl");
                    msg.setParam("msg", "fl");
                } else {
                    msg.setParam("msg", "" + sid);
                    reply.setParam("msg", "" + sid);
                }
                reply.setParam("s_uid", "Sys" + msg.get("r_uid"));
                reply.setParam("r_uid", msg.get("s_uid"));
                reply.setParam("sid", 0 + "");
                ((PBMessagePusher) ContexHolder.getBean("messagePusher")).push(reply);
                reply = null;
            } else {
                //拒绝添加好友
                msg.setParam("msg", "fl");
                reply.setMsg_id(msg.getMsg_id());
                reply.setType(PBCONSTANT.MESSAGE_REPLY_FLAG);
            }
            ((PBMessagePusher) ContexHolder.getBean("messagePusher")).push(msg);
            return reply;
        }
        return reply;
    }

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }
}
