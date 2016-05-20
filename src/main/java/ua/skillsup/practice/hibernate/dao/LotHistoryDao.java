package ua.skillsup.practice.hibernate.dao;

import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.filter.LotHistoryFilter;

import java.util.List;

public interface LotHistoryDao {
	List<LotHistoryDto> findAll();
	LotHistoryDto findById(long id);
	List<LotHistoryDto> findByFilter(LotHistoryFilter lotHistoryFilter);
	long create(LotHistoryDto lotHistoryDto);
}