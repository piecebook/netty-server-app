package com.pb.server.sdk.daemon;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.util.ContexHolder;
import org.springframework.stereotype.Service;

/**
 * Created by DW on 2016/8/29.
 */
@Service
public class MessageResendDaemon {
    private RedisUtil redisUtil = (RedisUtil) ContexHolder.getBean("redisUtil");

    public void run() {
        System.out.println("Resend running");
        /*
        Long current_time_15s_before = System.currentTimeMillis() - 15000;
        Long current_time_30s_before = current_time_15s_before - 15000;
        Long current_time_45s_before = current_time_30s_before - 15000;
        List<Message> list = redisUtil.getValuesForAHash("message");
        List<Message> resend_list = new ArrayList<>();
        List<Message> offline_messages = new ArrayList<>();
        for (Message msg : list) {
            if (msg.getTime() < current_time_45s_before)
                offline_messages.add(msg);
            else if (msg.getTime() < current_time_15s_before)
                resend_list.add(msg);
        }

        if (!resend_list.isEmpty()) {
            for (Message msg : resend_list) {
                ((PBMessagePusher) com.pb.server.cache.util.ContexHolder.getBean("messagePusher")).push(msg);
            }
        }
        if (!offline_messages.isEmpty()) {
            for (Message msg : offline_messages) {
                redisUtil.removeForAHash("message", msg.get("s_uid") + msg.getMsg_id());
                System.out.println("offline message:" + msg.toString());
                //TODO: 1.msg持久化，   2.接收者下线
            }
        }*/
    }
}
