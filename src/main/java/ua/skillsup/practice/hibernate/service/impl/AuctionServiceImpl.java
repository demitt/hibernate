package ua.skillsup.practice.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import ua.skillsup.practice.hibernate.model.filter.LotHistoryFilter;
import ua.skillsup.practice.hibernate.service.AuctionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {
    @Autowired
    private LotHistoryDao historyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private LotDao lotDao;
    @Autowired
    private CategoryDao categoryDao;

    public List<ItemDto> getAllItems() {
        return this.itemDao.findAll();
    }

    public UserDto getUser(String login) {
        return this.userDao.findByLogin(login);
    }

    public List<LotDto> getUserLots(String login, LocalDate dateFrom, LocalDate dateTo) {
        UserDto userDto = this.userDao.findByLogin(login);
        LotFilter lotFilter = new LotFilter();
        lotFilter.setOwner(userDto);
        if (dateFrom != null) {
            lotFilter.setDateCreatedFrom(dateFrom);
        }
        if (dateTo != null) {
            lotFilter.setDateCreatedTo(dateTo);
        }
        return this.lotDao.findByFilter(lotFilter);
    }

    public List<LotDto> getAllLots() {
        return this.lotDao.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public LotDto createLot(String login, String itemTitle, BigDecimal startPrice, int period) {
        //Проверка цены:
        if (startPrice.signum() < 0) { //допускаем цену 0
            System.out.println("Цена лота не может быть отрицательной");
            return createErrorLot();
        }
        //Проверка периода торгов:
        if (period < Data.DURATION_MIN || period > Data.DURATION_MAX) {
            System.out.println(
                "Период торгов не должен выходить на пределы " + Data.DURATION_MIN +"-" + Data.DURATION_MAX + " дней"
            );
            return createErrorLot();
        }

        //Поиск пользователя:
        UserDto userDto = this.userDao.findByLogin(login);
        if (userDto == null) {
            System.out.println("Пользователь с логином \"" + login + "\" не найден");
            return createErrorLot();
        }

        //Поиск item:
        ItemDto itemDto = this.itemDao.findByTitle(itemTitle);
        if (itemDto == null) {
            System.out.println("Товар \"" + login + "\" не найден");
            return createErrorLot();
        }

        //Создание лота:
        LotDto lotDto = new LotDto(itemDto,userDto, startPrice, period);
        long lotId = this.lotDao.create(lotDto);
        lotDto.setId(lotId);
        return lotDto;
    }

    private LotDto createErrorLot() {
        return new LotDto(-1);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void makeBid(String login, long lotId, BigDecimal newPrice) {
        LotDto lotDto = this.lotDao.findById(lotId);
        if (lotDto == null) {
            System.out.println("Лот с id=" + lotId + " не найден");
            return;
        }

        if (LocalDate.now().isAfter(lotDto.getDateEnd())) {
            String buyer = lotDto.getBuyer().getLogin();
            System.out.println(
                "Торги по этому лоту завершились, " +
                ( buyer == null ? "ставок не было" : "последнюю ставку сделал " + buyer )
            );
            return;
        }

        BigDecimal currentPrice = lotDto.getCurrentPrice();
        if ( !isValidBidAmount(currentPrice, newPrice) ) {
            System.out.println("Новая ставка должна превышать текущую минимум на " + Data.MIN_BID_STEP);
            return;
        }

        UserDto newBuyerDto = this.userDao.findByLogin(login);
        if (newBuyerDto == null) {
            System.out.println("Пользователь с логином \"" + login + "\" не найден");
            return;
        }
        if (newBuyerDto.equals(lotDto.getOwner())) {
            System.out.println("Продавец лота не может быть его покупателем");
            return;
        }

        UserDto currentBuyerDto = lotDto.getBuyer();
        if (currentBuyerDto != null && currentBuyerDto.equals(newBuyerDto)) {
            System.out.println("Новый покупатель лота не может быть предыдущем его покупателем");
            return;
        }

        //Insert into LotHistory:
        //long lotHistoryId = -1;
        if (currentBuyerDto != null) { //был хотя бы один покупатель
            LotHistoryDto lotHistoryDto = new LotHistoryDto(lotDto);
            /*lotHistoryId =*/ this.historyDao.create(lotHistoryDto);
        }

        //Update Lot:
        lotDto.setCurrentPrice(newPrice);
        lotDto.setBuyer(newBuyerDto);
        lotDto.setLastUpdate(LocalDateTime.now());
        lotDao.update(lotDto);

        System.out.println("Ставка сделана, текущий покупатель " + newBuyerDto.getLogin());
    }

    private boolean isValidBidAmount(BigDecimal currentPrice, BigDecimal newPrice) {
        return newPrice.subtract(currentPrice).subtract(Data.MIN_BID_STEP).signum() >= 0;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<LotHistoryDto> getLotHistory(long lotId) {
        LotDto lotDto = lotDao.findById(lotId);
        LotHistoryFilter lotHistoryFilter = new LotHistoryFilter();
        lotHistoryFilter.setLot(lotDto);
        List<LotHistoryDto> lotHistoryDto = historyDao.findByFilter(lotHistoryFilter);
        if (lotDto.getBuyer() != null) {
            LotHistoryDto currentLotHistoryElement = new LotHistoryDto(lotDto); //последняя ставка
            lotHistoryDto.add(currentLotHistoryElement);
        }
        return lotHistoryDto;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ItemDto createItem(String title, String description, List<String> categoryTitles, double width, double height, double weight) {
        if (this.itemDao.findByTitle(title) != null) {
            System.out.println("Товар с именем \"" + title +"\" уже существует");
            return createErrorItem();
        }
        if (width < 0 || height < 0 || weight < 0) {
            System.out.println("Габариты и вес товара не могут быть отрицательными");
            return createErrorItem();
        }

        //Поиск категорий:
        List<CategoryDto> allCategories = this.categoryDao.findAll(); //все имеющиеся в БД категории
        List<CategoryDto> categoriesDto = new ArrayList<>(categoryTitles.size()); //ДТОшки категорий item-а
        List<String> categoriesNotExist = new ArrayList<>(); //сюда сложим заголовки не имеющихся в БД категорий
        for (String categoryTitle : categoryTitles) {
            CategoryDto categoryDto = new CategoryDto(categoryTitle);
            int indexOfCategory = allCategories.indexOf(categoryDto);
            if (indexOfCategory == -1) {
                categoriesNotExist.add(categoryTitle);
                continue;
            }
            categoriesDto.add( allCategories.get(indexOfCategory) );
        }
        if (!categoriesNotExist.isEmpty()) {
            System.out.println("Следующие категории не существуют: " + String.join(", ", categoriesNotExist));
            return createErrorItem();
        }

        ItemDto itemDto = new ItemDto(title, description, width, height, weight, categoriesDto);
        long itemId = this.itemDao.create(itemDto);
        itemDto.setId(itemId);

        return itemDto;
    }

    private ItemDto createErrorItem() {
        return new ItemDto(Data.ID_FOR_ERROR_DTO);
    }

}