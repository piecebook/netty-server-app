package com.pb.server.service.user;

import pb.server.dao.model.UserAccount;

/**
 * Created by piecebook on 2016/9/6.
 */
public interface UserAccountService {
    public void addUserAccount(UserAccount user);

    public UserAccount getUserAccount(String uid);

    public void update(UserAccount user);

    public String login(String uid, String pwd);
}
