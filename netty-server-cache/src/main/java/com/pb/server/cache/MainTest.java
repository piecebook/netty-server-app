package com.pb.server.cache;

import com.pb.server.cache.redisUtil.RedisUtil;
import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/26.
 */
public class MainTest {

    public static void main(String[] args) {
        RedisUtil redisUtil = null;//(RedisUtil) ContexHolder.getBean("redisUtil");
        Message message = new Message();
        message.setType((byte) 1);
        message.setMsg_id(222L);
        message.setSender("lily");
        message.setReceiver("lisa");
        redisUtil.setForAHashMap("message", message.getSender() + message.getMsg_id(), message);

        Message msg = (Message) redisUtil.getForAHashMap("message", message.getSender() + message.getMsg_id());
        System.out.println(message.toString());
        System.out.println(msg.toString());

        /*redisUtil.removeForAHash("message","lily111");
        List<Message> list = redisUtil.getValuesForAHash("message");
        for(Message msg : list){
            System.out.println(msg.toString());
        }*/

    }
}
