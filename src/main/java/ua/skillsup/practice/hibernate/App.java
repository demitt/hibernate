package ua.skillsup.practice.hibernate;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.skillsup.practice.hibernate.dao.CategoryDao;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.LotDao;
import ua.skillsup.practice.hibernate.dao.LotHistoryDao;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.dao.entity.LotHistory;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.dto.UserDto;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;
import ua.skillsup.practice.hibernate.model.filter.LotHistoryFilter;
import ua.skillsup.practice.hibernate.service.AuctionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        System.out.println("Поиск всех лотов пользователя:");
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

        long categoryId = 4;
        System.out.println("Поиск категории с id=" + categoryId + ":");
        CategoryDto cat = categoryDao.findById(categoryId);
        System.out.println(cat);
        System.out.println();

        System.out.println("Поиск items, имеющих конкретную категорию:");
        System.out.println(itemDao.findByCategory(cat));
        System.out.println();


        LotHistoryDao historyDao = context.getBean(LotHistoryDao.class);


        System.out.println("\n**********************************************\n");


        AuctionService auctionService = context.getBean(AuctionService.class);


        long lotId = 1;
        LotDto lotDto = lotDao.findById(lotId);
        System.out.println("*** " +
            "Делаем ставки на лот: " +
            "lotId=" + lotDto.getId() + ", '" + lotDto.getItem().getTitle() + "', $" + lotDto.getCurrentPrice() +
            ", владелец " + lotDto.getOwner().getLogin()
        );
        List<String> logins = Arrays.asList("Loki", "Loki", "Loki", "Odin", "Лютый_Бандеровец", "Loki");
        List<String> prices = Arrays.asList("10.08", "10.09", "11.25", "11.70", "12.01", "15");
        String login;
        String priceString;
        for (int i = 0; i < logins.size(); i++) {
            login = logins.get(i);
            priceString = prices.get(i);
            System.out.println(login + " делает ставку $" + priceString);
            auctionService.makeBid(login, lotId, new BigDecimal(priceString));
        }

        System.out.println("Итого текущая цена лота составляет $" + lotDao.findById(lotId).getCurrentPrice());
        System.out.println("А история ставок выглядит следующим образом:");
        List<LotHistoryDto> lotHistoryList = auctionService.getLotHistory(lotId);
        for (LotHistoryDto historyDto : lotHistoryList) {
            System.out.println(
                (historyDto.getId() == null ? "текущая" : historyDto.getId() ) +
                ") $" + historyDto.getPrice() + ", " + historyDto.getBuyer().getLogin() +
                ", " + historyDto.getChangeTime()
            );
        }




        context.stop();
    }
}
