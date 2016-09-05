package com.pb.server.sdk.service.serviceImpl;

import com.pb.server.sdk.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.Message;
import pb.server.dao.model.MessageModel;
import pb.server.dao.service.MessageDao;
import pb.server.dao.util.MessageUtil;

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
        messageDao.addMessage(MessageUtil.toMessageModel(message));
    }

    @Override
    public void addMessageList(List<Message> list) {
        logger.info("mult insert: "+list.toString());
        List<MessageModel> models = MessageUtil.toMultMessageModel(list);
        messageDao.addMessageList(models);
    }

    @Override
    public void addOfflineMessage(Message message) {
        logger.info("insert offline message: " + message.toString());
        messageDao.addOfflineMessage(MessageUtil.toMessageModel(message));
    }

    @Override
    public List<Message> getMessageBySessionId(long session_id, String time_begin, String time_end) {
        List<MessageModel> modellist = messageDao.getMessageBySessionId(session_id, time_begin, time_end);
        List<Message> messagelist = MessageUtil.toMultMessage(modellist);
        return messagelist;
    }

    @Override
    public void deleteMessage(String time_end) {
        logger.info("delete message before " + time_end);
        messageDao.deleteMessage(time_end);
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
