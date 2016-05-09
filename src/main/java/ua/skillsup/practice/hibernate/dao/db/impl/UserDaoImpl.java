package ua.skillsup.practice.hibernate.dao.db.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.db.UserDao;
import ua.skillsup.practice.hibernate.dao.db.entity.User;
import ua.skillsup.practice.hibernate.model.dto.UserDto;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = sessionFactory.getCurrentSession().
            createQuery("from User").
            list();
        List<UserDto> userDto = new ArrayList<>(users.size());
        for (User user : users) {
            userDto.add( convert(user) );
        }
        return userDto;
    }

    @Transactional(readOnly = true)
    public UserDto findById(long id) {
        User user = (User) sessionFactory.getCurrentSession().
            createQuery("from User where id = :id").
            setParameter("id", id).
            uniqueResult();
        if (user == null) {
            return null;
        }
        return convert(user);
    }

    @Transactional(readOnly = true)
    public UserDto findByLogin(String login) {
        User user = (User) sessionFactory.getCurrentSession().
            createQuery("from User where login = :login").
            setParameter("login", login).
            uniqueResult();
        if (user == null) {
            return null;
        }
        return convert(user);
    }

    public long create(UserDto userDto) {
        return 0;
    }

    public void update(UserDto userDto) {

    }
}