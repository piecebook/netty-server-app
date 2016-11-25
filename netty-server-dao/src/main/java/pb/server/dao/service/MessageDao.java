package pb.server.dao.service;

import org.apache.ibatis.annotations.Param;
import pb.server.dao.model.Message;

import java.util.List;

/**
 * Created by piecebook on 2016/9/1.
 */
public interface MessageDao {
    public void addMessage(Message message);

    public void addMessageList(List<Message> list);

    public void addOfflineMessage(Message message);

    public List<Message> getMessageBySessionId(@Param("session_id") long session_id, @Param("time_begin") String time_begin, @Param("time_end") String time_end);

    public void deleteMessage(String time_end);

    public List<Message> getOfflineMsg(@Param("uid") String uid);

    public void deleteOfflineMsg(List<Long> list);
}
