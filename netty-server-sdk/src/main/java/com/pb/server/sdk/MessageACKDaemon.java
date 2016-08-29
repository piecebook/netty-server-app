package com.pb.server.sdk;


import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.sdk.MessageFactory.MessageHolder;
import com.pb.server.sdk.util.ContexHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by piecebook on 2016/8/8.
 */
public class MessageACKDaemon implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MessageACKDaemon.class);
    @Override
    public void run() {
        while(true){
            System.out.println("MessageACKDaemon Running!");
            String msg_key = null;
            try {
                msg_key = MessageHolder.rec_messages.take();
            } catch (InterruptedException e) {
                //TODO: deal with Exception
            }
            if(msg_key == null) {
                continue;
            }else{
                RedisUtil redisUtil = (RedisUtil)ContexHolder.getBean("redisUtil");
                redisUtil.removeForAHash("message",msg_key);
                //Message msg = MessageHolder.send_messages.remove(msg_key);
                /*if(msg != null){
                    logger.info("Insert messge :" + msg.toString());
                    //TODO:从redis插入Mysql
                }*/
            }
        }
    }
}
