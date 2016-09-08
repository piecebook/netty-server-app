package com.pb.server.sdk.daemon;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/5.
 */
public class MessageMysqlDaemon {
    private static Logger logger = LoggerFactory.getLogger(MessageMysqlDaemon.class);
    private RedisUtil redisUtil;
    private MessageService messageService;

    public void run() {
        Long current_time = System.currentTimeMillis();
        List<Message> list = new ArrayList<>();
        while (redisUtil.list_size("message_mysql_list") > 0) {
            Message message = (Message) redisUtil.list_left_pop("message_mysql_list");
            list.add(message);
            if (message.getTime() > current_time) break;
        }
        //logger.info("mult insert: " + list.toString());
        if (list.size() > 0) messageService.addMessageList(list);
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
