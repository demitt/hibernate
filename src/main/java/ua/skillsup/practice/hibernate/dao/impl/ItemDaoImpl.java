package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.ItemDao;
import ua.skillsup.practice.hibernate.dao.entity.Item;
import ua.skillsup.practice.hibernate.model.ItemDto;
import ua.skillsup.practice.hibernate.model.filter.ItemFilter;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    //TODO вставить аннотацию @Transactional
    public List<ItemDto> findAll() {
        List<Item> items = sessionFactory.getCurrentSession().createQuery("from Item").list();
        List<ItemDto> itemsDto = new ArrayList<>(items.size());
        for (Item item : items) {
            itemsDto.add(convert(item));
        }
        return itemsDto;
    }

    public ItemDto findById(long id) {
        return null;
    }

    public ItemDto findByTitle(String title) {

        return null;
    }

    public List<ItemDto> findByFilter(ItemFilter filter) {
        return null;
    }

    public long create(ItemDto itemDto) {
        return 0;
    }

    public void update(ItemDto itemDto) {

    }
}