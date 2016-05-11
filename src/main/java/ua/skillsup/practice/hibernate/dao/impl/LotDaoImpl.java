package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.LotDao;
import ua.skillsup.practice.hibernate.dao.entity.Lot;
import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;

import java.util.ArrayList;
import java.util.List;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

@Repository
@Transactional(readOnly = true)
public class LotDaoImpl implements LotDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<LotDto> findAll() {
		List<Lot> lots = this.sessionFactory.getCurrentSession().
			createQuery("FROM Lot order by dateEnd asc").
			list();
		List<LotDto> lotsDto = new ArrayList<>(lots.size());
		for (Lot lot : lots) {
			lotsDto.add( convert(lot) );
		}
		return lotsDto;
	}

	public LotDto findById(long id) {
		Lot lot = (Lot) this.sessionFactory.getCurrentSession().
			createQuery("from Lot where id = :id").
			setParameter("id", id).
			uniqueResult();
		if (lot == null) {
			return null;
		}
		return convert(lot);
	}

	public List<LotDto> findByFilter(LotFilter filter) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Lot.class);
		if (filter.getDateCreatedFrom() != null) {
			criteria.add(Restrictions.ge("datePlaced", filter.getDateCreatedFrom()));
			criteria.addOrder(Order.asc("datePlaced"));
		}
		if (filter.getDateCreatedTo() != null) {
			criteria.add(Restrictions.le("datePlaced", filter.getDateCreatedTo()));
			criteria.addOrder(Order.asc("datePlaced"));
		}
		if (filter.getDateEndFrom() != null) {
			criteria.add(Restrictions.ge("dateEnd", filter.getDateEndFrom()));
			criteria.addOrder(Order.asc("dateEnd"));
		}
		if (filter.getDateEndTo() != null) {
			criteria.add(Restrictions.le("dateEnd", filter.getDateEndTo()));
			criteria.addOrder(Order.asc("dateEnd"));
		}
		if (filter.getOwner() != null) {
			criteria.add(Restrictions.eq("owner", convert(filter.getOwner())));
			//criteria.addOrder(Order.asc("owner")); //нужен компаратор User?
		}
		if (filter.getCurrentBuyer() != null) {
			criteria.add(Restrictions.eq("buyer", convert(filter.getCurrentBuyer())));
			//criteria.addOrder(Order.asc("buyer")); //нужен компаратор User?
		}
		if (filter.getCurrentPriceFrom() != null) {
			criteria.add(Restrictions.ge("currentPrice", filter.getCurrentPriceFrom()));
			criteria.addOrder(Order.asc("currentPrice"));
		}
		if (filter.getCurrentPriceTo() != null) {
			criteria.add(Restrictions.le("currentPrice", filter.getCurrentPriceTo()));
			criteria.addOrder(Order.asc("currentPrice"));
		}
		if (filter.getItem() != null) {
			criteria.add(Restrictions.eq("item", convert(filter.getItem())));
			//criteria.addOrder(Order.asc("item")); //нужен компаратор Item?
		}

		List<Lot> lots = criteria.list();
		List<LotDto> lotsDto = new ArrayList<>(lots.size());
		for (Lot lot : lots) {
			lotsDto.add(convert(lot));
		}
		return lotsDto;
	}

	@Transactional(readOnly = false)
	public long create(LotDto lotDto) {
		Lot lot = convert(lotDto);
		this.sessionFactory.getCurrentSession().save(lot);
		return lot.getId();
	}

	public void update(LotDto lotDto) {

	}
}