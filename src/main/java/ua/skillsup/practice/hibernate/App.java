package ua.skillsup.practice.hibernate;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.skillsup.practice.hibernate.dao.CategoryDao;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.LotDao;
import ua.skillsup.practice.hibernate.dao.LotHistoryDao;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.model.Data;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.dto.UserDto;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;
import ua.skillsup.practice.hibernate.service.AuctionService;

import java.math.BigDecimal;
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

        //Создание товара:
        String itemTitle = "Стол на трех ножках";
        System.out.println("Создание товара \"" + itemTitle +"\"");
        ItemDto createdItemDto = auctionService.createItem(
            itemTitle, "Оригинальное решение для...", Arrays.asList("Table"), 900, 900, 16
        );
        if (createdItemDto.getId() == Data.ID_FOR_ERROR_DTO) {
            return;
        }
        System.out.println("Товар создан:\n" + createdItemDto);
        System.out.println();

        //Создание лота:
        String owner = "Odin";
        String priceString = "9.99";
        int period = 25;
        System.out.println(
            "Пользователь " + owner + " создает лот \"" + itemTitle + "\", $" + priceString + ", дней " + period
        );
        LotDto createdLotDto = auctionService.createLot(owner, itemTitle, new BigDecimal(priceString), period);
        long lotId = createdLotDto.getId();
        if (lotId == Data.ID_FOR_ERROR_DTO) {
            return;
        }
        System.out.println("Id созданного лота: " + lotId);
        System.out.println();

        //Ставки на лот:
        LotDto lotDto = lotDao.findById(lotId);
        System.out.println(
            "Делаем ставки на этот лот: " +
            "lotId=" + lotDto.getId() + ", \"" + lotDto.getItem().getTitle() + "\", $" + lotDto.getCurrentPrice() +
            ", владелец " + lotDto.getOwner().getLogin()
        );
        List<String> logins = Arrays.asList("Loki", "Loki", "Loki", "Odin", "Spiderman", "Loki", "Loki");
        List<String> prices = Arrays.asList("10.08", "10.09", "11.25", "11.70", "12.01", "-15", "13");
        String currentLogin;
        String currentPriceString;
        for (int i = 0; i < logins.size(); i++) {
            currentLogin = logins.get(i);
            currentPriceString = prices.get(i);
            System.out.println(currentLogin + " делает ставку $" + currentPriceString);
            auctionService.makeBid(currentLogin, lotId, new BigDecimal(currentPriceString));
        }

        //Результаты ставок:
        System.out.println("Текущая цена лота составляет $" + lotDao.findById(lotId).getCurrentPrice());
        System.out.println("История ставок выглядит следующим образом:");
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
