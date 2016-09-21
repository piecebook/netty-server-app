package com.pb.server.web.controller;

import com.pb.server.service.user.UserAccountService;
import com.pb.server.service.util.PWD_Util;
import com.pb.server.web.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pb.server.dao.model.UserAccount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by piecebook on 2016/9/6.
 */
@Controller
@RequestMapping(value = "/account")
public class UserController {
    @Autowired
    private UserAccountService userService;

    @RequestMapping(value = "/register")
    public ModelAndView register(Account user) {
        Map<String, Object> data = new HashMap<String, Object>();
        String result = null;
        if (!StringUtils.hasText(user.getUid()) || !StringUtils.hasText(user.getPwd()) || !StringUtils.hasText(user.getEmail()) || null == user.getSex()) {
            result = "Wrong Info";
            data.put("result", result);
            return new ModelAndView("register_result", data);
        }
        UserAccount isExist = userService.getUserAccount(user.getUid());
        if (isExist != null) {
            result = "uid exist";
            data.put("result", result);
            return new ModelAndView("register_result", data);
        }

        isExist = new UserAccount();
        isExist.setEmail(user.getEmail());
        isExist.setSex(user.getSex());
        isExist.setUid(user.getUid());

        String salt = PWD_Util.getSalt(32);
        isExist.setSalt(salt);

        String pwd = PWD_Util.getHash(user.getPwd() + salt);
        isExist.setPassword(pwd);

        userService.addUserAccount(isExist);

        result = "success";
        data.put("result", result);
        return new ModelAndView("register_result", data);
    }

    public void setUserService(UserAccountService userService) {
        this.userService = userService;
    }
}
