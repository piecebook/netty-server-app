package com.pb.server.sdk.handler;


import com.pb.server.cache.util.ContexHolder;
import com.pb.server.sdk.MessageFactory.OfflineMessageManager;
import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.pusher.PBMessagePusher;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import pb.server.dao.model.Message;

public class MessageHandler implements PBRequestHandler {
	@Override
	public Message process(PBSession session, Message msg) {
		SessionManage sessionManager = (SessionManage) ContexHolder
				.getBean("sessionManager");
		PBSession receiver_session = sessionManager.get(msg.get("r_uid"));
		Message reply = new Message();
		reply.setType(PBCONSTANT.MESSAGE_REPLY_FLAG);
		if (receiver_session != null && receiver_session.getSession().isActive()) {
			((PBMessagePusher)ContexHolder.getBean("messagePusher")).push(msg);
			reply.setParam("r_uid",msg.get("s_uid"));
			reply.setParam("st",PBCONSTANT.SUCCESS);
		} else {
			reply.setParam("st","fl");
			((OfflineMessageManager)ContexHolder.getBean("offlineMessageManager")).add(msg);
		}
		reply.setParam("s_uid",PBCONSTANT.SYSTEM);
		return reply;
	}
}
