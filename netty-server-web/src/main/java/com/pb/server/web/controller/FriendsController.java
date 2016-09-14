package com.pb.server.web.controller;

import com.alibaba.fastjson.JSON;
import com.pb.server.service.user.FriendsService;
import com.pb.server.service.user.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pb.server.dao.model.Friend;
import pb.server.dao.model.UserAccount;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/13.
 */


@RequestMapping(value = "/friends")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void getFriends(@RequestParam("user_id") String user_id, @RequestParam("uid") String uid, PrintWriter writer) {
        Long id = Long.parseLong(user_id);
        UserAccount user = userAccountService.getByid(id);
        if (user == null) return;
        if (uid.equals(user.getUid())) {

            List<Friend> friends = friendsService.getFriends(id);
            writer.print(JSON.toJSONString(friends));
            writer.flush();
            writer.close();
        } else return;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public void searchFriend(@RequestParam("search_key") String search_key, PrintWriter writer) {
        List<Friend> friends = userAccountService.search(search_key);
        if (friends == null) friends = new ArrayList<>(0);
        writer.print(JSON.toJSONString(friends));
        writer.flush();
        writer.close();
    }

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
