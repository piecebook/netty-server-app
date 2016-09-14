package pb.server.dao.model;

/**
 * Created by piecebook on 2016/9/14.
 */
public class Friend {
    private long sid;
    private long id;
    private String uid;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "sid=" + sid +
                ", id=" + id +
                ", uid='" + uid + '\'' +
                '}';
    }
}
