package ua.skillsup.practice.hibernate.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.skillsup.practice.hibernate.dao.db.AuctionDao;
import ua.skillsup.practice.hibernate.dao.db.ItemDao;
import ua.skillsup.practice.hibernate.dao.db.LotDao;
import ua.skillsup.practice.hibernate.dao.db.LotHistoryDao;
import ua.skillsup.practice.hibernate.dao.db.UserDao;
import ua.skillsup.practice.hibernate.model.Data;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.dto.UserDto;
import ua.skillsup.practice.hibernate.service.AuctionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private LotHistoryDao lotHistoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private LotDao lotDao;

    @Autowired
    private AuctionDao auctionDao;


    public List<ItemDto> getAllItems() {
        return this.itemDao.findAll();
    }

    public UserDto getUser(String login) {
        return this.userDao.findByLogin(login);
    }

    public List<LotDto> getUserLots(String login, LocalDate from, LocalDate to) {
        throw new RuntimeException("AuctionServiceImpl#getUserLots(): не реализовано!");
        //return null;
    }

    public List<LotDto> getAllLots() {
        throw new RuntimeException("AuctionServiceImpl#getgetAllLots(): не реализовано!");
        //return null;
    }

    public LotDto createLot(String login, String item, BigDecimal startPrice, int period) {
        throw new RuntimeException("AuctionServiceImpl#createLot(): не реализовано!");
        //return null;
    }

    public void makeBid(String login, long lotId, BigDecimal newPrice) {
        LotDto lotDto = this.lotDao.findById(lotId);
        BigDecimal currentPrice = lotDto.getCurrentPrice();
        if ( !isValidBidPrice(currentPrice, newPrice) ) {
            System.out.println("Не годится, новая ставка должна превышать текущую минимум на " + Data.MIN_BID_STEP + "!");
            return;
        }
        UserDto userDto = this.userDao.findByLogin(login);
        LotHistoryDto lotHistoryDto = new LotHistoryDto(lotDto, userDto, currentPrice, LocalDateTime.now());
        this.auctionDao.makeBid(lotHistoryDto, newPrice);

    }

    private boolean isValidBidPrice(BigDecimal currentPrice, BigDecimal newPrice) {
        return newPrice.subtract(currentPrice).subtract(Data.MIN_BID_STEP).compareTo(BigDecimal.ZERO) >= 0;
    }

    public List<LotHistoryDto> getLotHistory(long lotId) {
        throw new RuntimeException("AuctionServiceImpl#getLotHistory(): не реализовано!");
        //return null;
    }
}