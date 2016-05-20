package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.filter.LotFilter;

import java.util.List;

public interface LotDao {
	List<LotDto> findAll();
	LotDto findById(long id);
	List<LotDto> findByFilter(LotFilter filter);
	List<LotDto> findByOwnerId(long id);
	long create(LotDto lotDto);
	void update(LotDto lotDto);
}
