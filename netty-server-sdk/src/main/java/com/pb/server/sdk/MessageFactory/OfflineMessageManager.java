package com.pb.server.sdk.MessageFactory;


import com.pb.server.service.message.MessageService;
import pb.server.dao.model.Message;

/**
 * Created by piecebook on 2016/8/17.
 */
public class OfflineMessageManager {
    private MessageService messageService;

    public void add(Message msg) {
        messageService.addOfflineMessage(msg);
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
