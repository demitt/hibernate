package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;

import java.math.BigDecimal;

public interface AuctionDao {
    boolean makeBid(LotHistoryDto lotHistoryDto, BigDecimal newPrice);
}
