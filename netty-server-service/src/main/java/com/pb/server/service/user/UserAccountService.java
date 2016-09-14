package com.pb.server.service.user;

import pb.server.dao.model.Friend;
import pb.server.dao.model.UserAccount;

import java.util.List;

/**
 * Created by piecebook on 2016/9/6.
 */
public interface UserAccountService {
    public void addUserAccount(UserAccount user);

    public UserAccount getUserAccount(String uid);

    public void update(UserAccount user);

    public String login(String uid, String pwd);

    public UserAccount getByid(Long id);

    public List<String> getUids(List<Long> ids);

    public List<Friend> search(final String key);
}
