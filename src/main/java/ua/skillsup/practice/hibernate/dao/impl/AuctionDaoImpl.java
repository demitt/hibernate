package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.AuctionDao;
import ua.skillsup.practice.hibernate.dao.entity.Lot;
import ua.skillsup.practice.hibernate.dao.entity.LotHistory;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;

import java.math.BigDecimal;
import java.util.List;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

@Repository
@Transactional
public class AuctionDaoImpl implements AuctionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean makeBid(LotHistoryDto lotHistoryDto, BigDecimal newPrice) {
        Session session = this.sessionFactory.getCurrentSession();

        //Insert into LotHistory:
        LotHistory lotHistory = convert(lotHistoryDto);
        session.persist(lotHistory);

        //Update Lot:
        long lotId = lotHistoryDto.getLot().getId();
        Lot lot = (Lot) session.load(Lot.class, lotId);
        lot.setCurrentPrice(newPrice);

        //Проверки:
        /*List<LotHistory> lotHistoryList = session.
            createQuery("from LotHistory where lot = :lot").
            setParameter("lot", lot).
            list();
        System.out.println("Проверки.\nСписок lotHistory для тек. лота:");
        for (LotHistory historyElement : lotHistoryList) {
            LotHistoryDto historyElementDto = convert(historyElement);
            System.out.println("Цена " + historyElementDto.getPrice() + ", время " + historyElementDto.getChangeTime());
        }
        System.out.println("Список lotHistory завершен.");

        BigDecimal newPriceFromDB =
            (BigDecimal) session.
            createQuery("select currentPrice from Lot where id = :id").
            setParameter("id", lotId).
            uniqueResult();
        System.out.println("Новая цена лота " + newPriceFromDB);*/

        return true; //TODO при ошибках возвращать false
    }
}
