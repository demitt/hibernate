package ua.skillsup.practice.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.LotDao;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.model.ItemDto;
import ua.skillsup.practice.hibernate.model.UserDto;
import ua.skillsup.practice.hibernate.model.filter.ItemFilter;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;
import ua.skillsup.practice.hibernate.service.AuctionService;
import ua.skillsup.practice.hibernate.service.impl.AuctionServiceImpl;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");


        ItemDao itemDao = context.getBean(ItemDao.class);

        /*
        System.out.println("Список всех items:");
        System.out.println( itemDao.findAll() );
        System.out.println();

        long itemId = 1L;
        System.out.println("Поиск item с id=" + itemId +":");
        System.out.println( itemDao.findById(itemId) );
        System.out.println();

        String title = "Dinning table";
        System.out.println("Поиск item с title=" + title + ":");
        System.out.println( itemDao.findByTitle(title) );
        System.out.println();

        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setWeightFrom(0.3);
        System.out.println("Поиск items по критериям.\nКритерии: " + itemFilter);
        System.out.println( itemDao.findByFilter(itemFilter) );
        System.out.println();
        */


        UserDao userDao = context.getBean(UserDao.class);

        /*
        System.out.println("Список всех пользователей:");
        System.out.println( userDao.findAll() );
        System.out.println();
        */

        /*
        long userId = 1L;
        System.out.println("Поиск user с id=" + userId +":");
        System.out.println( userDao.findById(userId) );
        System.out.println();
        */

        /*String login = "Odin";
        System.out.println("Поиск пользователей с login=" + login + ":");
        System.out.println( userDao.findByLogin(login) );
        System.out.println();*/


        LotDao lotDao = context.getBean(LotDao.class);

        //System.out.println("Список всех лотов:");
        //System.out.println(lotDao.findAll());

        //long lotId = 1;
        //System.out.println("Поиск лота с id=" + lotId + ":");
        //System.out.println( lotDao.findById(lotId) );

        System.out.println("Поиск всех лотов юзера:");
        UserDto user = userDao.findById(1);
        //System.out.println("Юзер:");
        //System.out.println(user);
        LotFilter filter = new LotFilter();
        filter.setOwner(user);
        //System.out.println("Лоты:");
        System.out.println( lotDao.findByFilter(filter) );


        //AuctionService service = context.getBean(AuctionService.class);

        context.stop();
    }
}
