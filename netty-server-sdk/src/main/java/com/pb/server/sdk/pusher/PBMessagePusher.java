package com.pb.server.sdk.pusher;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.session.PBSessionManage;
import com.pb.server.sdk.util.ContexHolder;
import pb.server.dao.model.Message;


/**
 * Created by piecebook on 2016/8/8.
 */
public class PBMessagePusher implements MessagePusher {

    @Override
    public void push(Message msg){
        PBSessionManage sessionManager =(PBSessionManage) ContexHolder.getBean("sessionManager");
        String msg_key = msg.get("s_uid")+msg.getMsg_id();
        msg.setTime(System.currentTimeMillis());
        msg.setParam("tm",msg.getTime().toString());
        //MessageHolder.send_messages.put(msg_key,msg);
        RedisUtil redisUtil = (RedisUtil) com.pb.server.sdk.util.ContexHolder.getBean("redisUtil");
        sessionManager.get(msg.get("r_uid")).write(msg);
    }
}
