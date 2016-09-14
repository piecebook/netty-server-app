package com.pb.server.service.user.serviceImpl;

import com.pb.server.service.user.FriendsService;
import com.sun.istack.internal.logging.Logger;
import pb.server.dao.model.Friend;
import pb.server.dao.model.FriendShip;
import pb.server.dao.model.UserAccount;
import pb.server.dao.service.FriendsDao;
import pb.server.dao.service.UserAccountDao;

import java.util.List;

/**
 * Created by piecebook on 2016/9/12.
 */
public class FriendsServiceImpl implements FriendsService {
    private static Logger log = Logger.getLogger(FriendsServiceImpl.class);
    private FriendsDao friendsDao;
    private UserAccountDao userAccountDao;

    @Override
    public long add(final String uid1, final String uid2) {
        UserAccount user1 = userAccountDao.getUserAccount(uid1);
        UserAccount user2 = userAccountDao.getUserAccount(uid2);
        if (user1 == null) return -1;
        if (user2 == null) return -1;
        log.info("add friends: " + user1.getId() + ", " + user2.getId());
        return user1.getId() < user2.getId() ? friendsDao.add(user1.getId(), user2.getId()) : friendsDao.add(user2.getId(), user1.getId());
    }

    @Override
    public void remove(final String uid1, final String uid2) {
        log.info("remove friends: " + uid1 + uid2);
        UserAccount user1 = userAccountDao.getUserAccount(uid1);
        UserAccount user2 = userAccountDao.getUserAccount(uid2);
        if (user1.getId() < user2.getId()) {
            friendsDao.remove(user1.getId(), user2.getId());
        } else friendsDao.remove(user2.getId(), user1.getId());
    }

    @Override
    public List<FriendShip> getFriendShip(String uid) {
        UserAccount user = userAccountDao.getUserAccount(uid);
        return this.getFriendShip(user.getId());
    }

    @Override
    public List<FriendShip> getFriendShip(long uid) {
        log.info("get friend:" + uid);
        return friendsDao.getFriendShip(uid);
    }

    @Override
    public List<Friend> getFriends(long uid) {
        return friendsDao.getFriends(uid);
    }

    public void setFriendsDao(FriendsDao friendsDao) {
        this.friendsDao = friendsDao;
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
}
