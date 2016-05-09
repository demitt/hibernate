package ua.skillsup.practice.hibernate.dao.db.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.db.AuctionDao;
import ua.skillsup.practice.hibernate.dao.db.entity.Lot;
import ua.skillsup.practice.hibernate.dao.db.entity.LotHistory;
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
        //Insert into LotHistory:
        LotHistory lotHistory = convert(lotHistoryDto);
        this.sessionFactory.getCurrentSession().persist(lotHistory);

        //Update Lot:
        long lotId = lotHistoryDto.getLot().getId();
        Lot lot = (Lot) this.sessionFactory.getCurrentSession().load(Lot.class, lotId);
        lot.setCurrentPrice(newPrice);

        //Проверки:
        List<LotHistory> lh = this.sessionFactory.getCurrentSession().
            createQuery("from LotHistory where lot = :lot").
            setParameter("lot", lot).
            list();
        System.out.println("Проверки.\nСписок lotHistory для тек. лота:");
        for (LotHistory history : lh) {
            System.out.println("Цена " + history.getPrice() + ", время " + history.getChangeTime());
        }
        System.out.println("Список lotHistory завершен.");

        BigDecimal newPriceFromDB =
            (BigDecimal) this.sessionFactory.getCurrentSession().
            createQuery("select currentPrice from Lot where id = :id").
            setParameter("id", lotId).
            uniqueResult();
        System.out.println("Новая цена лота " + newPriceFromDB);

        System.out.println("*************************************************");

        return false;
    }
}
