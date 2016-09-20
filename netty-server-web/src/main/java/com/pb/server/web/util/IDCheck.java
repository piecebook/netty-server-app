package com.pb.server.web.util;

import com.pb.server.service.user.UserAccountService;
import com.pb.server.web.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pb.server.dao.model.UserAccount;

/**
 * Created by piecebook on 2016/9/20.
 */
public class IDCheck {
    private static Logger log = LoggerFactory.getLogger(IDCheck.class);

    @Autowired
    private UserAccountService userAccountService;

    public Response idCheck(String uid, String user_id, String path) {
        Response response = new Response();

        Long id = null;
        try {
            id = Long.parseLong(user_id);
        } catch (Exception e) {
            log.error(path + ": unsupport user_id " + user_id, e);
            return response.error(Response.ARGS_ERROR, "unsupport user_id: " + user_id);
        }

        UserAccount user = userAccountService.getByid(id);
        if (user == null) {
            log.error("/msg/get_offline_msg: unsupport user_id " + user_id);
            return response.error(Response.ARGS_ERROR, "no user has such user_id: " + user_id);
        }

        if (user.getUid().equals(uid)) return response.success(null);
        else
            return response.error(Response.ARGS_ERROR, "user_id[" + user_id + "] and uid[" + uid + "] doesn't mapping");
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
