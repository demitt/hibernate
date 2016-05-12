package ua.skillsup.practice.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.LotDao;
import ua.skillsup.practice.hibernate.dao.LotHistoryDao;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.model.Data;
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
import java.util.List;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

@Service
public class AuctionServiceImpl implements AuctionService {

    //@Autowired
    //private SessionFactory sessionFactory;

    @Autowired
    private LotHistoryDao historyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private LotDao lotDao;

    //@Autowired
    //private AuctionDao auctionDao;


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

        public LotDto createLot(String login, String item, BigDecimal startPrice, int period) {
        throw new RuntimeException("AuctionServiceImpl#createLot(): не реализовано!");
        //return null;
    }

    public void makeBid(String login, long lotId, BigDecimal newPrice) {
        LotDto lotDto = this.lotDao.findById(lotId);
        if (lotDto == null) {
            System.out.println("Лот с id=" + lotId + " не найден");
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

        BigDecimal currentPrice = lotDto.getCurrentPrice();
        if ( !isValidBidAmount(currentPrice, newPrice) ) {
            System.out.println("Новая ставка должна превышать текущую минимум на " + Data.MIN_BID_STEP);
            return;
        }

        //Insert into LotHistory:
        long lotHistoryId = -1;
        if (currentBuyerDto != null) { //был хотя бы один покупатель
            LotHistoryDto lotHistoryDto = new LotHistoryDto(lotDto);
            lotHistoryId = this.historyDao.create(lotHistoryDto);
        }

        //Update Lot:
        lotDto.setCurrentPrice(newPrice);
        lotDto.setBuyer(newBuyerDto);
        lotDto.setLastUpdate(LocalDateTime.now());
        lotDao.update(lotDto);

        System.out.println("Ставка сделана");
    }

    private boolean isValidBidAmount(BigDecimal currentPrice, BigDecimal newPrice) {
        return newPrice.subtract(currentPrice).subtract(Data.MIN_BID_STEP).signum() >= 0;
    }

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
}