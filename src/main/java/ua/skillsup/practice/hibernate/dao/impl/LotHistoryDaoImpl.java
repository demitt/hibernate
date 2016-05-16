package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.LotHistoryDao;
import ua.skillsup.practice.hibernate.dao.entity.LotHistory;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.filter.LotHistoryFilter;

import java.util.ArrayList;
import java.util.List;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LotHistoryDaoImpl implements LotHistoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<LotHistoryDto> findAll() {
		throw new RuntimeException("LotHistoryDaoImpl#findAll(): не реализовано.");
	}

	public LotHistoryDto findById(long id) {
		throw new RuntimeException("LotHistoryDaoImpl#findById(): не реализовано.");
	}

	public List<LotHistoryDto> findByFilter(LotHistoryFilter lotHistoryFilter) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(LotHistory.class);
		criteria.addOrder(Order.asc("changeTime"));

		if (lotHistoryFilter.getLot() != null) {
			criteria.add(Restrictions.eq("lot", convert(lotHistoryFilter.getLot())));
		}
		if (lotHistoryFilter.getBuyer() != null) {
			criteria.add(Restrictions.eq("buyer", convert(lotHistoryFilter.getBuyer())));
		}
		if (lotHistoryFilter.getDateFrom() != null) {
			criteria.add(Restrictions.ge("changeTime", lotHistoryFilter.getDateFrom()));
		}
		if (lotHistoryFilter.getDateTo() != null) {
			criteria.add(Restrictions.le("changeTime", lotHistoryFilter.getDateTo()));
		}
		if (lotHistoryFilter.getLotPriceFrom() != null) {
			criteria.add(Restrictions.ge("price", lotHistoryFilter.getLotPriceFrom()));
		}
		if (lotHistoryFilter.getLotPriceTo() != null) {
			criteria.add(Restrictions.le("price", lotHistoryFilter.getLotPriceTo()));
		}

		List<LotHistory> lotHistories = criteria.list();
		List<LotHistoryDto> lotHistoriesDto = new ArrayList<>(lotHistories.size());
		for (LotHistory lotHistory : lotHistories) {
			lotHistoriesDto.add(convert(lotHistory));
		}
		return lotHistoriesDto;
	}

	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	public long create(LotHistoryDto lotHistoryDto) {
		LotHistory lotHistory = convert(lotHistoryDto);
		this.sessionFactory.getCurrentSession().persist(lotHistory); //save?
		return lotHistory.getId();
	}
}