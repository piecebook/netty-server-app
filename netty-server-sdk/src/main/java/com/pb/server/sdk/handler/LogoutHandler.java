package com.pb.server.sdk.handler;

import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import pb.server.dao.model.Message;

/**
 * 用户注销处理器
 */
public class LogoutHandler implements PBRequestHandler {

    /**
     *
     * @param session 用户连接session
     * @param msg 注销消息包
     * @return 注销回复
     */
    @Override
    public Message process(PBSession session, Message msg) {
        SessionManage sessionManager = (SessionManage) ContexHolder.getBean("sessionManager");
        session.close();//关闭连接
        sessionManager.remove(session.getUid());//根据uid删除用户连接的session
		Message reply = new Message();
        reply.setType(PBCONSTANT.LOGOUT_REPLY_FLAG);
        reply.setParam("r_uid",session.getUid());
        reply.setParam("st",PBCONSTANT.SUCCESS);
        reply.setParam("s_uid",PBCONSTANT.SYSTEM);
        return reply;
    }

}
