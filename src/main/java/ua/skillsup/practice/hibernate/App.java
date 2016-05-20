package ua.skillsup.practice.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.service.AuctionService;
import ua.skillsup.practice.hibernate.service.impl.AuctionServiceImpl;

/**
 * Created by oleksii on 10/10/15.
 */
public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        ItemDao itemDao = context.getBean(ItemDao.class);

        AuctionService service = context.getBean(AuctionService.class);

        AuctionServiceImpl s = new AuctionServiceImpl();
        System.out.println(s.getAllItems());

        context.stop();
    }
}
