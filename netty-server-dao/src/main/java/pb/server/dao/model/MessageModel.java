package pb.server.dao.model;

import java.sql.Timestamp;

/**
 * Created by piecebook on 2016/8/18.
 */
public class MessageModel {
    private Long id;
    private String sender;
    private String receiver;
    private String content;
    private int type;
    private String create_time;
    private Long time_long;
    private Long session_id;

    public MessageModel() {
    }

    public MessageModel(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.type = 1;
        this.time_long = System.currentTimeMillis();
        create_time = new Timestamp(time_long).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Long getTime_long() {
        return time_long;
    }

    public void setTime_long(Long time_long) {
        this.time_long = time_long;
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public void setTime_long() {
        this.time_long = Timestamp.valueOf(create_time).getTime();
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", create_time='" + create_time + '\'' +
                ", time_long=" + time_long +
                ", session_id=" + session_id +
                '}';
    }
}
