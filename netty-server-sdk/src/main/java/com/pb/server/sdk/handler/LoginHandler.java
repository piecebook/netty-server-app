package com.pb.server.sdk.handler;


import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import com.pb.server.service.user.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;

public class LoginHandler implements PBRequestHandler {
    private static Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private UserAccountService accountService;

    @Override
    public Message process(PBSession session, Message msg) {
        Message reply = new Message();
        reply.setType(PBCONSTANT.LOGIN_REPLY_FLAG);
        reply.setParam("r_uid", msg.get("s_uid"));

        SessionManage sessionManager = (SessionManage) ContexHolder
                .getBean("sessionManager");

        String result = accountService.login(msg.get("s_uid"), msg.get("pwd"));
        if ("success".equals(result)) {
            reply.setParam("st", PBCONSTANT.SUCCESS);
            PBSession oldsession = sessionManager.get(msg.get("s_uid"));
            logger.info(msg.get("s_uid") + " login on " + session.getSession().remoteAddress());
            if (oldsession == null)
                sessionManager.add(msg.get("s_uid"), session);
            else if (oldsession == session) {
            } else {
                sendForceOffLine(oldsession);
                sessionManager.add(msg.get("s_uid"), session);
            }
        } else {
            reply.setParam("st", result);
        }
        reply.setParam("s_uid", PBCONSTANT.SYSTEM);
        return reply;
    }

    public void sendForceOffLine(PBSession session) {
        logger.info("Force offline:" + session.getUid() + " at "
                + session.getSession().remoteAddress() + " on "
                + session.getDeviceId());
        Message force = new Message();
        session.getSession().writeAndFlush(force);

    }

    public void setAccountService(UserAccountService accountService) {
        this.accountService = accountService;
    }
}
