package ua.skillsup.practice.hibernate.dao.db;

import ua.skillsup.practice.hibernate.model.dto.CategoryDto;

import java.util.List;

public interface CategoryDao {
    List<CategoryDto> findAll();
    CategoryDto findById(long id);
}
