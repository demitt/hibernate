package ua.skillsup.practice.hibernate;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.skillsup.practice.hibernate.dao.db.CategoryDao;
import ua.skillsup.practice.hibernate.dao.db.ItemDao;
import ua.skillsup.practice.hibernate.dao.db.LotDao;
import ua.skillsup.practice.hibernate.dao.db.UserDao;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.UserDto;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;
import ua.skillsup.practice.hibernate.service.AuctionService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
        */

        String title = "Dinning table";
        System.out.println("Поиск items с title=" + title + ":");
        System.out.println( itemDao.findByTitle(title) );
        System.out.println();

        /*
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

        /*
        String login = "Odin";
        System.out.println("Поиск пользователей с login=" + login + ":");
        System.out.println( userDao.findByLogin(login) );
        System.out.println();
        */


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
        System.out.println();


        CategoryDao categoryDao = context.getBean(CategoryDao.class);

        System.out.println("Список всех категорий:");
        System.out.println(categoryDao.findAll());
        System.out.println();

        long catId = 4;
        System.out.println("Поиск категории с id=" + catId + ":");
        CategoryDto cat = categoryDao.findById(catId);
        System.out.println(cat);
        System.out.println();

        System.out.println("Поиск items, имеющих конкретную категорию:");
        System.out.println(itemDao.findByCategory(cat));
        System.out.println();



        System.out.println("\n\n********************************************************************");

        AuctionService service = context.getBean(AuctionService.class);

        String login;

        login = "Odin";
        System.out.println(login + " делает ставку.");
        service.makeBid(login, 1, new BigDecimal(10.09));

        login = "Loki";
        System.out.println(login + " делает ставку.");
        service.makeBid(login, 1, new BigDecimal(10.50));

        login = "Odin";
        System.out.println(login + " делает ставку.");
        service.makeBid(login, 1, new BigDecimal(12.01));

        login = "Loki";
        System.out.println(login + " делает ставку.");
        service.makeBid(login, 1, new BigDecimal(15.00));


        context.stop();
    }
}
