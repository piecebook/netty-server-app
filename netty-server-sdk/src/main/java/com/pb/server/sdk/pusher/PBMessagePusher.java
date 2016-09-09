package com.pb.server.sdk.pusher;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.session.PBSessionManage;
import com.pb.server.sdk.util.ContexHolder;
import pb.server.dao.model.Message;


/**
 * Created by piecebook on 2016/8/8.
 */

/**
 * 该类处理 所有 服务端推送给客户端的消息
 */
public class PBMessagePusher implements MessagePusher {

    /**
     *
     * @param msg 需要发送的消息体
     *
     * 功能：将消息推送到接收用户的
     */
    @Override
    public void push(Message msg) {
        PBSessionManage sessionManager = (PBSessionManage) ContexHolder.getBean("sessionManager");
        String msg_key = msg.get("s_uid") + msg.getMsg_id();
        //msg.setTime(System.currentTimeMillis());
        //把时间放进消息体传输
        msg.setParam("tm", msg.getTime().toString());
        //MessageHolder.send_messages.put(msg_key,msg);
        RedisUtil redisUtil = (RedisUtil) ContexHolder.getBean("redisUtil");
        redisUtil.list_right_push("message_mysql_list", msg);
        redisUtil.setForAHashMap("message", msg_key, msg);
        sessionManager.get(msg.get("r_uid")).write(msg);
    }
}
