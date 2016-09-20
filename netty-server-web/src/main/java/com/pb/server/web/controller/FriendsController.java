package com.pb.server.web.controller;

import com.pb.server.service.user.FriendsService;
import com.pb.server.service.user.UserAccountService;
import com.pb.server.web.model.Response;
import com.pb.server.web.util.IDCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pb.server.dao.model.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piecebook on 2016/9/13.
 */


@RequestMapping(value = "/friends")
public class FriendsController {
    private static Logger log = LoggerFactory.getLogger(FriendsController.class);
    @Autowired
    private FriendsService friendsService;

    @Autowired
    private IDCheck idCheck;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Response getFriends(@RequestParam("user_id") String user_id, @RequestParam("uid") String uid) {
        Response response = idCheck.idCheck(uid, user_id, "/friends/get");

        if (response.getError_code() == Response.SUCCESS) {
            Long id = Long.parseLong(user_id);
            List<Friend> friends = friendsService.getFriends(id);
            response.setError_code(Response.SUCCESS);
            response.setReason("success");
            response.setData(friends);
        }

        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Response searchFriend(@RequestParam("search_key") String search_key) {

        List<Friend> friends = userAccountService.search(search_key);
        if (friends == null) friends = new ArrayList<>(0);
        Response response = new Response();
        return response.success(friends);
    }

    public void setFriendsService(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    public void setIdCheck(IDCheck idCheck) {
        this.idCheck = idCheck;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
