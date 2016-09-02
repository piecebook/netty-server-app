package pb.server.dao.model;

/**
 * Created by piecebook on 2016/9/2.
 */
public class UserAccount {
    private long id;
    private String uid;
    private String password;
    private String salt;
    private Boolean sex;
    private String email;

    public UserAccount() {
    }

    public UserAccount(String uid, String password, String salt, boolean sex, String email) {
        this.uid = uid;
        this.password = password;
        this.salt = salt;
        this.sex = sex;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                '}';
    }
}
