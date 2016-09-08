package com.pb.server.sdk.handler;


import com.pb.server.sdk.constant.PBCONSTANT;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import com.pb.server.service.user.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;

/**
 * 用户登录消息处理器
 */
public class LoginHandler implements PBRequestHandler {
    private static Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private UserAccountService accountService;

    /**
     *
     * @param session 用户连接session
     * @param msg 登录消息包
     * @return 是否登录成功回复
     */
    @Override
    public Message process(PBSession session, Message msg) {
        Message reply = new Message();
        reply.setType(PBCONSTANT.LOGIN_REPLY_FLAG);
        reply.setParam("r_uid", msg.get("s_uid"));

        SessionManage sessionManager = (SessionManage) ContexHolder
                .getBean("sessionManager");

        //需要操作数据库
        String result = accountService.login(msg.get("s_uid"), msg.get("pwd"));

        if ("success".equals(result)) {
            //用户名、密码 验证成功
            logger.info(msg.get("s_uid") + " login on " + session.getSession().remoteAddress());

            reply.setParam("st", PBCONSTANT.SUCCESS);//成功标志

            /**
             * 根据用户名 从sessionManager取出oldsession
             * 如果oldsession 为 null ，则 用户之前没有登录,不是重复登录
             * 如果oldsession 不为 null ,则 用户在其他设备在线 或者 之前登录过 ，那么 强迫oldsession 下线，建立新session
             */
            PBSession oldsession = sessionManager.get(msg.get("s_uid"));
            if (oldsession == null)
                sessionManager.add(msg.get("s_uid"), session);
            else if (oldsession == session) {
            } else {
                sendForceOffLine(oldsession);//强迫oldsession下线
                sessionManager.add(msg.get("s_uid"), session);
            }
        } else {
            //验证失败
            reply.setParam("st", result);//result包含失败原因，1、用户名不存在 2、密码错误
        }
        reply.setParam("s_uid", PBCONSTANT.SYSTEM);
        return reply;
    }

    /**
     * 功能：强迫用户下线
     * @param session 用户连接session
     */
    public void sendForceOffLine(PBSession session) {
        logger.info("Force offline:" + session.getUid() + " at "
                + session.getSession().remoteAddress() + " on "
                + session.getDeviceId());
        Message force = new Message();
        //TODO: 用户下线消息
        session.getSession().writeAndFlush(force);

    }

    public void setAccountService(UserAccountService accountService) {
        this.accountService = accountService;
    }
}
