package com.pb.server.web.controller;

import com.alibaba.fastjson.JSON;
import com.pb.server.service.message.MessageService;
import com.pb.server.service.user.UserAccountService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pb.server.dao.model.MessageModel;
import pb.server.dao.model.UserAccount;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/19.
 */

@RequestMapping(value = "/msg")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/get_offline_msg", method = RequestMethod.POST)
    public void getOfflineMsg(@Param("user_id") String user_id, @Param("uid") String uid, PrintWriter writer) {
        Long id = Long.parseLong(user_id);
        UserAccount user = userAccountService.getByid(id);
        if (null == user) return;
        if (user.getUid().equals(uid)) {
            List<MessageModel> messages = messageService.getOfflineMsg(uid);
            writer.print(JSON.toJSONString(messages));
            writer.flush();
            writer.close();
        } else return;
    }

    @RequestMapping(value = "/ack_offline_msg", method = RequestMethod.POST)
    public void ackOfflineMsg(@Param("user_id") String user_id, @Param("uid") String uid, @Param("ids_str") String ids_str, PrintWriter writer) {
        Long id = Long.parseLong(user_id);
        UserAccount user = userAccountService.getByid(id);
        if (null == user) return;
        if (user.getUid().equals(uid)) {
            String[] ids = ids_str.split(",");
            List<Long> id_list = new ArrayList<>(ids.length);
            if (ids.length > 0) {
                for (String msg : ids) {
                    id_list.add(Long.parseLong(msg));
                }
                messageService.deleteOfflineMsg(id_list);
                writer.print(JSON.toJSONString(1));
                writer.flush();
                writer.close();
            }
        } else return;
    }


    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
