package pb.server.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pb.server.dao.model.UserAccount;
import pb.server.dao.service.UserAccountDao;

/**
 * Created by piecebook on 2016/9/1.
 */
public class DaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");


        /*MessageDao messageDao = (MessageDao) context.getBean("messageDao");
        //MessageModel msg = new MessageModel("tom","jack","hello jack");
        //messageDao.addMessage(msg);
        List<MessageModel> list = messageDao.getMessageBySessionId(1, "2016-08-18 15:57:13", null);

        for (MessageModel msg : list) {
            msg.setTime_long();
            System.out.println(msg.toString());
        }*/


        //UserAccount user = new UserAccount("lisa", "123", "1234567890", true, "lisa@qq.com");
        UserAccount userm = new UserAccount();
        userm.setUid("xiaoming");
        userm.setPassword("12345");

        UserAccountDao userAccountDao = (UserAccountDao) context.getBean("userAccountDao");

        //userAccountDao.addUserAccount(userm);

        UserAccount user = userAccountDao.getUserAccount("xiaoming");
        System.out.println(user.toString());

    }
}
