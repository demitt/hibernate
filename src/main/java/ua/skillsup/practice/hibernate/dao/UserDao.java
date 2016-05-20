package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.UserDto;

import java.util.List;

public interface UserDao {
	List<UserDto> findAll();
	UserDto findById(long id);
	UserDto findByLogin(String login);
	long create(UserDto userDto);
	void update(UserDto userDto);
}