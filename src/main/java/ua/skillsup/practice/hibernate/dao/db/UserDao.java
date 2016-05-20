package ua.skillsup.practice.hibernate.dao.db;

import ua.skillsup.practice.hibernate.model.dto.UserDto;

import java.util.List;

/**
 * Created by oleksii on 10/10/15.
 */
public interface UserDao {

	List<UserDto> findAll();
	UserDto findById(long id);
	UserDto findByLogin(String login);
	long create(UserDto userDto);
	void update(UserDto userDto);

}