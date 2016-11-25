package com.pb.server.service.message.serviceImpl;


import com.pb.server.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;
import pb.server.dao.service.MessageDao;

import java.util.List;

/**
 * Created by piecebook on 2016/9/2.
 */
public class MessageServiceImpl implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private MessageDao messageDao;

    @Override
    public void addMessage(Message message) {
        logger.info("insert message: " + message.toString());
        messageDao.addMessage(message);
    }

    @Override
    public void addMessageList(List<Message> list) {
        logger.info("mult insert: " + list.toString());
        messageDao.addMessageList(list);
    }

    @Override
    public void addOfflineMessage(Message message) {
        logger.info("insert offline message: " + message.toString());
        messageDao.addOfflineMessage(message);
    }

    @Override
    public List<Message> getMessageBySessionId(long session_id, String time_begin, String time_end) {
        List<Message> list = messageDao.getMessageBySessionId(session_id, time_begin, time_end);
        return list;
    }

    @Override
    public void deleteMessage(String time_end) {
        logger.info("delete message before " + time_end);
        messageDao.deleteMessage(time_end);
    }

    @Override
    public List<Message> getOfflineMsg(String uid) {
        List<Message> messages = messageDao.getOfflineMsg(uid);
        return messages;
    }

    @Override
    public void deleteOfflineMsg(List<Long> list) {
        if (list == null) return;
        if (list.size() == 0) return;
        messageDao.deleteOfflineMsg(list);
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
