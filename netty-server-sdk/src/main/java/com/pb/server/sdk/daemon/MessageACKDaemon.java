package com.pb.server.sdk.daemon;


import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.MessageFactory.MessageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by piecebook on 2016/8/8.
 */

/**
 * 消息ACK守护线程
 *
 * 该类作为单独线程运行，取出 ACK包队列 的包，判断出哪个消息被客户端收到了
 * 再从 等待ACK消息的队列 中删除对应的消息
 */
public class MessageACKDaemon {
    private static Logger logger = LoggerFactory.getLogger(MessageACKDaemon.class);
    private RedisUtil redisUtil;// = (RedisUtil) ContexHolder.getBean("redisUtil");

    public void run() {

        while (true) {
            System.out.println("MessageACKDaemon Running!");
            String msg_key = null;
            try {
                msg_key = MessageHolder.rec_messages.take(); //从ack队列取出ACK包
            } catch (InterruptedException e) {
                logger.error("MessageACKDaemon:", e);
            }
            if (msg_key == null) {
                continue;
            } else {
                redisUtil.removeForAHash("message", msg_key);   //从 等待ack消息 的队列 删除对应的消息
            }
        }
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
