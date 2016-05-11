package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.entity.Category;
import ua.skillsup.practice.hibernate.dao.entity.Item;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.filter.ItemFilter;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<ItemDto> findAll() {
        List<Item> items = sessionFactory.getCurrentSession().
            createQuery("from Item order by title asc").
            list();
        List<ItemDto> itemsDto = new ArrayList<>(items.size());
        for (Item item : items) {
            itemsDto.add(convert(item));
        }
        return itemsDto;
    }

    public ItemDto findById(long id) {
        Item item = (Item) sessionFactory.getCurrentSession().
            createQuery("from Item where id = :id").
            setParameter("id", id).
            uniqueResult();
        if (item == null) {
            return null;
        }
        return convert(item);
    }

    public ItemDto findByTitle(String title) {
        Item item = (Item) sessionFactory.getCurrentSession().
            createQuery("from Item where title = :title").
            setParameter("title", title).
            uniqueResult();
        if (item == null ) {
            return null;
        }
        return convert(item);
    }

    public List<ItemDto> findByFilter(ItemFilter filter) {
        Criteria criteria = sessionFactory.getCurrentSession().
            createCriteria(Item.class);
        if (filter.getHeightFrom() != null) {
            criteria.add(Restrictions.ge("height", filter.getHeightFrom()));
        }
        if (filter.getHeightTo() != null) {
            criteria.add(Restrictions.le("height", filter.getHeightTo()));
        }
        if (filter.getWidthFrom() != null) {
            criteria.add(Restrictions.ge("width", filter.getWidthFrom()));
        }
        if (filter.getWidthTo() != null) {
            criteria.add(Restrictions.le("width", filter.getWidthTo()));
        }
        if (filter.getWeightFrom() != null) {
            criteria.add(Restrictions.ge("weight", filter.getWeightFrom()));
        }
        if (filter.getWeightTo() != null) {
            criteria.add(Restrictions.le("weight", filter.getWeightTo()));
        }
        List<Item> items = criteria.list();
        List<ItemDto> itemsDto = new ArrayList<>(items.size());
        for (Item item : items) {
            itemsDto.add(convert(item));
        }
        return itemsDto;
    }

    @Override
    public List<ItemDto> findByCategory(CategoryDto categoryDto) {
        Category category = convert(categoryDto);
        List<Item> items = this.sessionFactory.getCurrentSession().
            createQuery("select c.items from Category c where c = :category").
            setParameter("category", category).
            list();
        List<ItemDto> itemsDto = new ArrayList<>(items.size());
        for (Item item : items) {
            itemsDto.add(convert(item));
        }
        return itemsDto;
    }

    public long create(ItemDto itemDto) {
        return 0;
    }

    public void update(ItemDto itemDto) {

    }
}