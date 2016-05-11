package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.filter.ItemFilter;

import java.util.List;

/**
 * Created by oleksii on 10/10/15.
 */
public interface ItemDao {
	List<ItemDto> findAll();
	ItemDto findById(long id);
	ItemDto findByTitle(String title);
	List<ItemDto> findByFilter(ItemFilter filter);
	List<ItemDto> findByCategory(CategoryDto categoryDto);
	long create(ItemDto itemDto);
	void update(ItemDto itemDto);

}