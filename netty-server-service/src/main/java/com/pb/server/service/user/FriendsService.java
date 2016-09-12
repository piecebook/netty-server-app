package com.pb.server.service.user;

import pb.server.dao.model.Friend;

import java.util.List;

/**
 * Created by piecebook on 2016/9/12.
 */


public interface FriendsService {
    public long add(final String uid1, final String uid2);

    public void remove(final String uid1, final String uid2);

    public List<Friend> getFriends(final String uid);

    public List<Friend> getFriends(final long uid);
}
