package com.pb.server.service.user.serviceImpl;

import com.pb.server.service.user.UserAccountService;
import com.pb.server.service.util.PWD_Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pb.server.dao.model.UserAccount;
import pb.server.dao.service.UserAccountDao;

/**
 * Created by piecebook on 2016/9/6.
 */
public class UserAccountServiceImpl implements UserAccountService {

    private static Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private UserAccountDao userAccountDao;

    @Override
    public void addUserAccount(UserAccount user) {
        logger.info("User registered: " + user.toString());
        userAccountDao.addUserAccount(user);
    }

    @Override
    public UserAccount getUserAccount(String uid) {
        return userAccountDao.getUserAccount(uid);
    }

    @Override
    public void update(UserAccount user) {
        logger.info("User update: " + user.toString());
        userAccountDao.update(user);
    }

    @Override
    public String login(String uid, String pwd) {
        UserAccount user = userAccountDao.getUserAccount(uid);
        if (user == null) return "user not found";
        String salt = user.getSalt();
        String hash = PWD_Util.getHash(pwd + salt);
        if (hash.equals(user.getPassword()))
            return "success";
        else return "fail";
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
}
