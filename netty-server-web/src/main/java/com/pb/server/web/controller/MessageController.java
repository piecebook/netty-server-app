package com.pb.server.web.controller;

import com.pb.server.service.message.MessageService;
import com.pb.server.web.model.Response;
import com.pb.server.web.util.IDCheck;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pb.server.dao.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/19.
 */

@RequestMapping(value = "/msg")
public class MessageController {
    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private IDCheck idCheck;

    @RequestMapping(value = "/get_offline_msg", method = RequestMethod.POST)
    @ResponseBody
    public Response getOfflineMsg(@Param("user_id") String user_id, @Param("uid") String uid) {

        Response response = idCheck.idCheck(uid, user_id, "/msg/get_offline_msg");

        if (response.getError_code() == Response.SUCCESS) {
            List<Message> messages = messageService.getOfflineMsg(uid);
            response.setData(messages);
        }

        return response;
    }

    @RequestMapping(value = "/ack_offline_msg", method = RequestMethod.POST)
    @ResponseBody
    public Response ackOfflineMsg(@Param("user_id") String user_id, @Param("uid") String uid, @Param("ids_str") String ids_str) {
        Response response = idCheck.idCheck(uid, user_id, "/msg/ack_offline_msg");


        if (response.getError_code() == Response.SUCCESS) {
            String[] ids = ids_str.split(",");
            List<Long> id_list = new ArrayList<>(ids.length);
            if (ids.length > 0) {
                for (String msg : ids) {
                    id_list.add(Long.parseLong(msg));
                }
                messageService.deleteOfflineMsg(id_list);
            }
        }

        return response;
    }


    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void setIdCheck(IDCheck idCheck) {
        this.idCheck = idCheck;
    }
}
