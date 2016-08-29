package com.pb.server.cache;

import com.pb.server.cache.redisUtil.RedisUtil;
import com.pb.server.cache.util.ContexHolder;
import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/26.
 */
public class MainTest {

    public static void main(String[] args){
        RedisUtil redisUtil = (RedisUtil) ContexHolder.getBean("redisUtil");
        Message message = new Message();
        message.setType((byte)1);
        message.setMsg_id(222);
        message.setLength(10);
        message.setParam("s_uid","lily");
        message.setParam("r_uid","lisa");
        redisUtil.setForAHashMap("message",message.get("s_uid")+message.getMsg_id(),message);

        Message msg = (Message)redisUtil.getForAHashMap("message",message.get("s_uid")+message.getMsg_id());
        System.out.println(message.toString());
        System.out.println(msg.toString());

        /*redisUtil.removeForAHash("message","lily111");
        List<Message> list = redisUtil.getValuesForAHash("message");
        for(Message msg : list){
            System.out.println(msg.toString());
        }*/

    }
}
