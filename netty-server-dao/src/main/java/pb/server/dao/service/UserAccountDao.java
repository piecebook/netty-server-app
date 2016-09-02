package pb.server.dao.service;

import pb.server.dao.model.UserAccount;

/**
 * Created by DW on 2016/9/2.
 */
public interface UserAccountDao {

    public void addUserAccount(UserAccount user);

    public UserAccount getUserAccount(String uid);

    public void update(UserAccount user);

}
