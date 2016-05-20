package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.filter.ItemFilter;

import java.util.List;

public interface ItemDao {
	List<ItemDto> findAll();
	ItemDto findById(long id);
	ItemDto findByTitle(String title);
	List<ItemDto> findByFilter(ItemFilter filter);
	List<ItemDto> findByCategory(CategoryDto categoryDto);
	long create(ItemDto itemDto);
	void update(ItemDto itemDto);
}