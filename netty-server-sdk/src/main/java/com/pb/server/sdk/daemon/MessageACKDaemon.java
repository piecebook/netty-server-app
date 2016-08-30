package com.pb.server.sdk.daemon;


import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.util.ContexHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by piecebook on 2016/8/8.
 */
@Service
public class MessageACKDaemon {
    private static Logger logger = LoggerFactory.getLogger(MessageACKDaemon.class);
    private RedisUtil redisUtil = (RedisUtil) ContexHolder.getBean("redisUtil");

    public void run() {
        System.out.println("ACK running");
        /*
        while (true) {
            System.out.println("MessageACKDaemon Running!");
            String msg_key = null;
            try {
                msg_key = MessageHolder.rec_messages.take();
            } catch (InterruptedException e) {
                //TODO: deal with Exception
            }
            if (msg_key == null) {
                continue;
            } else {
                redisUtil.removeForAHash("message", msg_key);
                //Message msg = MessageHolder.send_messages.remove(msg_key);
                *//*if(msg != null){
                    logger.info("Insert messge :" + msg.toString());
                    //TODO:从redis插入Mysql
                }*//*
            }
        }*/
    }
}
