package ua.skillsup.practice.hibernate.dao.db.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.db.LotDao;
import ua.skillsup.practice.hibernate.dao.db.entity.Lot;
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
		}
		if (filter.getDateCreatedTo() != null) {
			criteria.add(Restrictions.le("datePlaced", filter.getDateCreatedTo()));
		}
		if (filter.getDateEndFrom() != null) {
			criteria.add(Restrictions.ge("dateEnd", filter.getDateEndFrom()));
		}
		if (filter.getDateEndTo() != null) {
			criteria.add(Restrictions.le("dateEnd", filter.getDateEndTo()));
		}
		if (filter.getOwner() != null) {
			criteria.add(Restrictions.eq("owner", convert(filter.getOwner())));
		}
		if (filter.getCurrentBuyer() != null) {
			criteria.add(Restrictions.eq("buyer", convert(filter.getCurrentBuyer())));
		}
		if (filter.getCurrentPriceFrom() != null) {
			criteria.add(Restrictions.ge("currentPrice", filter.getCurrentPriceFrom()));
		}
		if (filter.getCurrentPriceTo() != null) {
			criteria.add(Restrictions.le("currentPrice", filter.getCurrentPriceTo()));
		}
		if (filter.getItem() != null) {
			criteria.add(Restrictions.eq("item", convert(filter.getItem())));
		}
		List<Lot> lots = criteria.list();
		List<LotDto> lotsDto = new ArrayList<>(lots.size());
		for (Lot lot : lots) {
			lotsDto.add( convert(lot) );
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