package com.pb.server.service.message;

import pb.server.dao.model.Message;
import pb.server.dao.model.MessageModel;

import java.util.List;

/**
 * Created by piecebook on 2016/9/2.
 */
public interface MessageService {

    public void addMessage(Message message);

    public void addMessageList(List<Message> list);

    public void addOfflineMessage(Message message);

    public List<Message> getMessageBySessionId(long session_id, String time_begin, String time_end);

    public void deleteMessage(String time_end);

    public List<MessageModel> getOfflineMsg(String uid);

    public void deleteOfflineMsg(List<Long> list);
}
