package ua.skillsup.practice.hibernate.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.skillsup.practice.hibernate.dao.AuctionDao;
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
        UserDto buyerDto = this.userDao.findByLogin(login);

        if (lotDto.getOwner().equals(buyerDto)) {
            System.out.println("Продавец лота не може быть покупателем этого лота.");
            return;
        }

        BigDecimal currentPrice = lotDto.getCurrentPrice();
        if ( !isValidBidAmount(currentPrice, newPrice) ) {
            System.out.println("Новая ставка должна превышать текущую минимум на " + Data.MIN_BID_STEP + ".");
            return;
        }


        LotHistoryDto lotHistoryDto = new LotHistoryDto(lotDto, buyerDto, currentPrice, LocalDateTime.now());
        this.auctionDao.makeBid(lotHistoryDto, newPrice);

        System.out.println("Ставка сделана.");

    }

    private boolean isValidBidAmount(BigDecimal currentPrice, BigDecimal newPrice) {
        return newPrice.subtract(currentPrice).subtract(Data.MIN_BID_STEP).signum() >= 0;
    }

    public List<LotHistoryDto> getLotHistory(long lotId) {

        throw new RuntimeException("AuctionServiceImpl#getLotHistory(): не реализовано!");
        //return null;
    }
}