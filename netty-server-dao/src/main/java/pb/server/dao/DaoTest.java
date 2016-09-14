package pb.server.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pb.server.dao.model.Friend;
import pb.server.dao.service.UserAccountDao;

import java.util.List;

/**
 * Created by piecebook on 2016/9/1.
 */
public class DaoTest {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");

        UserAccountDao userAccountDao = (UserAccountDao) context.getBean("userAccountDao");
        List<Friend> friends = userAccountDao.search("%piece%");

        for (Friend friend : friends) {
            System.out.println(friend.toString());
        }

        /*UserAccountDao userAccountDao = (UserAccountDao)context.getBean("userAccountDao");
        List<Long> ids = new ArrayList<>();
        ids.add(Long.valueOf(1));
        ids.add(Long.valueOf(4));
        List<String> uids = userAccountDao.getUids(ids);
        for (String uid : uids) {
            System.out.println(uid);
        }*/

        /*MessageDao messageDao = (MessageDao) context.getBean("messageDao");
        //MessageModel msg = new MessageModel("tom","jack","hello jack");
        //messageDao.addMessage(msg);
        List<MessageModel> list = messageDao.getMessageBySessionId(1, "2016-08-18 15:57:13", null);

        for (MessageModel msg : list) {
            msg.setTime_long();
            System.out.println(msg.toString());
        }*//*


        //UserAccount user = new UserAccount("lisa", "123", "1234567890", true, "lisa@qq.com");
        UserAccount userm = new UserAccount();
        userm.setUid("xiaoming");
        userm.setPassword("12345");

        UserAccountDao userAccountDao = (UserAccountDao) context.getBean("userAccountDao");

        //userAccountDao.addUserAccount(userm);

        UserAccount user = userAccountDao.getUserAccount("xiaoming");
        System.out.println(user.toString());*/

    }
}
