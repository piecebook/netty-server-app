package pb.server.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pb.server.dao.model.MessageModel;
import pb.server.dao.service.MessageDao;

import java.util.List;

/**
 * Created by piecebook on 2016/9/1.
 */
public class DaoTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        MessageDao messageDao = (MessageDao) context.getBean("messageADao");
        List<MessageModel> list = messageDao.getMessageBySessionId(Long.valueOf(1), null, null);
        for(MessageModel msg : list)
        {
            System.out.println(msg.toString());
        }
    }
}
