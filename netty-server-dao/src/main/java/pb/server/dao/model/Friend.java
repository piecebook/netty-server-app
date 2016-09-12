package pb.server.dao.model;

/**
 * Created by piecebook on 2016/9/12.
 */
public class Friend {
    private long sid;
    private long uid1;
    private long uid2;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getUid1() {
        return uid1;
    }

    public void setUid1(long uid1) {
        this.uid1 = uid1;
    }

    public long getUid2() {
        return uid2;
    }

    public void setUid2(long uid2) {
        this.uid2 = uid2;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "sid=" + sid +
                ", uid1=" + uid1 +
                ", uid2=" + uid2 +
                '}';
    }
}
