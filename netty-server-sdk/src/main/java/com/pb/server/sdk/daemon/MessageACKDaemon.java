package com.pb.server.sdk.daemon;


import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.MessageFactory.MessageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by piecebook on 2016/8/8.
 */

public class MessageACKDaemon {
    private static Logger logger = LoggerFactory.getLogger(MessageACKDaemon.class);
    private RedisUtil redisUtil;// = (RedisUtil) ContexHolder.getBean("redisUtil");

    public void run() {

        while (true) {
            System.out.println("MessageACKDaemon Running!");
            String msg_key = null;
            try {
                msg_key = MessageHolder.rec_messages.take();
            } catch (InterruptedException e) {
                logger.error("MessageACKDaemon:", e);
            }
            if (msg_key == null) {
                continue;
            } else {
                redisUtil.removeForAHash("message", msg_key);
            }
        }
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
