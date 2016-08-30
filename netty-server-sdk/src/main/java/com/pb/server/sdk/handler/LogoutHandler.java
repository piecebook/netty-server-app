package com.pb.server.sdk.handler;

import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import pb.server.dao.model.Message;

public class LogoutHandler implements PBRequestHandler {

    @Override
    public Message process(PBSession session, Message msg) {
        SessionManage sessionManager = (SessionManage) ContexHolder.getBean("sessionManager");
        session.close();
        sessionManager.remove(session.getUid());
		Message reply = new Message();
        reply.setType(PBCONSTANT.LOGOUT_REPLY_FLAG);
        reply.setParam("r_uid",session.getUid());
        reply.setParam("st",PBCONSTANT.SUCCESS);
        reply.setParam("s_uid",PBCONSTANT.SYSTEM);
        return reply;
    }

}
