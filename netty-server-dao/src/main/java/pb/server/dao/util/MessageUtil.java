package pb.server.dao.util;

import pb.server.dao.model.Message;
import pb.server.dao.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/2.
 */
public class MessageUtil {
    public static Message toMessage(MessageModel model) {
        model.setTime_long();
        Message msg = new Message();
        msg.setType((byte) model.getType());
        msg.setTime(model.getTime_long());
        msg.setParam("s_uid", model.getSender());
        msg.setParam("r_uid", model.getReceiver());
        msg.setParam("cot", model.getContent());
        msg.setParam("sid", model.getSession_id().toString());
        msg.setMsg_id(Integer.valueOf(model.getId().toString()));
        return msg;
    }

    public static MessageModel toMessageModel(Message message) {
        MessageModel model = new MessageModel();
        model.setReceiver(message.get("r_uid"));
        model.setSender(message.get("s_uid"));
        if (message.getTime() != null)
            model.setTime_long(message.getTime());
        else model.setTime_long(System.currentTimeMillis());
        model.setContent(message.get("cot"));
        model.setSession_id(Long.valueOf(message.get("sid")));
        model.setType(message.getType());
        return model;
    }

    public static List<Message> toMultMessage(List<MessageModel> models) {
        List<Message> list = new ArrayList<>();
        for (MessageModel model : models) {
            model.setTime_long();
            Message msg = new Message();
            msg.setType((byte) model.getType());
            msg.setTime(model.getTime_long());
            msg.setParam("s_uid", model.getSender());
            msg.setParam("r_uid", model.getReceiver());
            msg.setParam("cot", model.getContent());
            msg.setParam("sid", model.getSession_id().toString());
            msg.setMsg_id(Integer.valueOf(model.getId().toString()));
            list.add(msg);
        }
        return list;
    }

    public static List<MessageModel> toMultMessageModel(List<Message> messages) {
        List<MessageModel> list = new ArrayList<>();
        for (Message message : messages) {
            MessageModel model = new MessageModel();
            model.setReceiver(message.get("r_uid"));
            model.setSender(message.get("s_uid"));
            if (message.getTime() != null)
                model.setTime_long(message.getTime());
            else model.setTime_long(System.currentTimeMillis());
            model.setContent(message.get("cot"));
            model.setSession_id(Long.valueOf(message.get("sid")));
            model.setType(message.getType());
            list.add(model);
        }
        return list;
    }
}
