package com.pb.server.sdk.daemon;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.pusher.PBMessagePusher;
import com.pb.server.sdk.session.PBSession;
import com.pb.server.sdk.session.SessionManage;
import com.pb.server.sdk.util.ContexHolder;
import com.pb.server.service.message.MessageService;
import pb.server.dao.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DW on 2016/8/29.
 */

/**
 * 消息重发线程
 *
 * 该类作为单独线程运行，判断 等待ACK消息 的队列中的消息是否等待超时，
 * 超时(15s)则重发； 重发两次后，判断 接收用户离线，把消息持久化到mysql
 */
public class MessageResendDaemon {
    private RedisUtil redisUtil;// = (RedisUtil) ContexHolder.getBean("redisUtil");

    private MessageService messageService;

    public void run() {
        //System.out.println("Resend running");
        Long current_time_15s_before = System.currentTimeMillis() - 15000;
        Long current_time_30s_before = current_time_15s_before - 15000;
        Long current_time_45s_before = current_time_30s_before - 15000;
        List<Message> list = redisUtil.getValuesForAHash("message");
        List<Message> resend_list = new ArrayList<>();
        List<Message> offline_messages = new ArrayList<>();
        for (Message msg : list) {
            if (msg.getTime() < current_time_45s_before)//45s 之前的消息 视为 接收者离线，持久化到mysql
                offline_messages.add(msg);
            else if (msg.getTime() < current_time_15s_before)//15s 之前的消息，等待ack超时，重发
                resend_list.add(msg);
        }

        if (!resend_list.isEmpty()) {
            PBMessagePusher messagePusher = ((PBMessagePusher) ContexHolder.getBean("messagePusher"));
            for (Message msg : resend_list) {
                messagePusher.push(msg);
            }
        }
        if (!offline_messages.isEmpty()) {
            for (Message msg : offline_messages) {
                redisUtil.removeForAHash("message", msg.get("s_uid") + msg.getMsg_id());
                System.out.println("offline message:" + msg.toString());
                messageService.addMessage(msg);     //msg持久化




                //接收者下线
                SessionManage sessionManager = (SessionManage) ContexHolder.getBean("sessionManager");
                PBSession session = sessionManager.get(msg.get("r_uid"));
                if (session!=null){
                    session.close();
                }
                sessionManager.remove(msg.get("r_uid"));
                //TODO: 这里需要重构，接收者下线
            }
        }
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
