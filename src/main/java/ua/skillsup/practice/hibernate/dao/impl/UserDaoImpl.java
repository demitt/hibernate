package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.dao.entity.User;
import ua.skillsup.practice.hibernate.model.dto.UserDto;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

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

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY)
    public long create(UserDto userDto) {
        throw new RuntimeException("UserDaoImpl#create(): не реализовано.");
    }

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY)
    public void update(UserDto userDto) {
        throw new RuntimeException("UserDaoImpl#update(): не реализовано.");
    }
}