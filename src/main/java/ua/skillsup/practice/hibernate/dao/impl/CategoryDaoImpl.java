package ua.skillsup.practice.hibernate.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.CategoryDao;
import ua.skillsup.practice.hibernate.dao.entity.Category;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

@Repository
@Transactional(readOnly = true)
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CategoryDto> findAll() {
        List<Category> cats = this.sessionFactory.getCurrentSession().
            createQuery("from Category").
            list();
        List<CategoryDto> catsDto = new ArrayList<>(cats.size());
        for (Category cat : cats) {
            catsDto.add(convert(cat));
        }
        return catsDto;
    }

    @Override
    public CategoryDto findById(long id) {
        Category cat = (Category) this.sessionFactory.getCurrentSession().
            createQuery("from Category where id = :id").
            setParameter("id", id).
            uniqueResult();
        if (cat == null) {
            return null;
        }
        return convert(cat);
    }
}
