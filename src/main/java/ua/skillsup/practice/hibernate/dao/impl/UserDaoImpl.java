package ua.skillsup.practice.hibernate.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.skillsup.practice.hibernate.dao.UserDao;
import ua.skillsup.practice.hibernate.dao.entity.User;
import ua.skillsup.practice.hibernate.model.UserDto;

import static ua.skillsup.practice.hibernate.converters.EntityDtoConverter.convert;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = sessionFactory.getCurrentSession().createQuery("from User").list();
        List<UserDto> userDto = new ArrayList<>(users.size());
        for (User user : users) {
            userDto.add( convert(user) );
        }
        return userDto;
    }

    public UserDto findById(long id) {
        return null;
    }

    public UserDto findByLogin(String login) {
        return null;
    }

    public long create(UserDto userDto) {
        return 0;
    }

    public void update(UserDto userDto) {

    }
}