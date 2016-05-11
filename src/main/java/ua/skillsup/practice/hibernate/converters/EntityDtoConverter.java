package ua.skillsup.practice.hibernate.converters;

import ua.skillsup.practice.hibernate.dao.entity.Category;
import ua.skillsup.practice.hibernate.dao.entity.Item;
import ua.skillsup.practice.hibernate.dao.entity.Lot;
import ua.skillsup.practice.hibernate.dao.entity.LotHistory;
import ua.skillsup.practice.hibernate.dao.entity.User;
import ua.skillsup.practice.hibernate.model.dto.CategoryDto;
import ua.skillsup.practice.hibernate.model.dto.ItemDto;
import ua.skillsup.practice.hibernate.model.dto.LotDto;
import ua.skillsup.practice.hibernate.model.dto.LotHistoryDto;
import ua.skillsup.practice.hibernate.model.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public final class EntityDtoConverter {

	private EntityDtoConverter() {
		//private default constructor for utility class
	}

	public static Item convert(ItemDto itemDto) {
		if (itemDto == null) {
			return null;
		}
		Item item = new Item();
		item.setId(itemDto.getId());
		item.setTitle(itemDto.getTitle());
		item.setDescription(itemDto.getDescription());
		item.setHeight(itemDto.getHeight());
		item.setWeight(itemDto.getWeight());
		item.setWidth(itemDto.getWidth());
		List<CategoryDto> catsDto = itemDto.getCategories();
		List<Category> cats = new ArrayList<>(catsDto.size());
		for (CategoryDto catDto : catsDto) {
			cats.add(convert(catDto));
		}
		item.setCategories(cats);
		return item;
	}

	public static ItemDto convert(Item item) {
		if (item == null) {
			return null;
		}
		ItemDto itemDto = new ItemDto();
		itemDto.setId(item.getId());
		itemDto.setTitle(item.getTitle());
		itemDto.setDescription(item.getDescription());
		itemDto.setHeight(item.getHeight());
		itemDto.setWeight(item.getWeight());
		itemDto.setWidth(item.getWidth());
		List<Category> cats = item.getCategories();
		List<CategoryDto> catsDto = new ArrayList<>(cats.size());
		for (Category cat : cats) {
			catsDto.add(convert(cat));
		}
		itemDto.setCategories(catsDto);
		return itemDto;
	}

	public static User convert(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		User user = new User();
		user.setId(userDto.getId());
		user.setLogin(userDto.getLogin());
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setDeliveryAddress(userDto.getDeliveryAddress());
		user.setContactPhone(userDto.getContactPhone());
		return user;
	}

	public static UserDto convert(User user) {
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setLogin(user.getLogin());
		userDto.setName(user.getName());
		userDto.setLastName(user.getLastName());
		userDto.setDeliveryAddress(user.getDeliveryAddress());
		userDto.setContactPhone(user.getContactPhone());
		return userDto;
	}

	public static Lot convert(LotDto lotDto) {
		if (lotDto == null) {
			return null;
		}
		Lot lot = new Lot();
		lot.setId(lotDto.getId());
		lot.setItem(convert(lotDto.getItem()));
		lot.setOwner(convert(lotDto.getOwner()));
		lot.setStartPrice(lotDto.getStartPrice());
		lot.setDatePlaced(lotDto.getDatePlaced());
		lot.setBuyer(convert(lotDto.getBuyer()));
		lot.setCurrentPrice(lotDto.getCurrentPrice());
		lot.setDateEnd(lotDto.getDateEnd());
		return lot;
	}

	public static LotDto convert(Lot lot) {
		if (lot == null) {
			return null;
		}
		LotDto lotDto = new LotDto();
		lotDto.setId(lot.getId());
		lotDto.setItem(convert(lot.getItem()));
		lotDto.setOwner(convert(lot.getOwner()));
		lotDto.setStartPrice(lot.getStartPrice());
		lotDto.setDatePlaced(lot.getDatePlaced());
		lotDto.setBuyer(convert(lot.getBuyer()));
		lotDto.setCurrentPrice(lot.getCurrentPrice());
		lotDto.setDateEnd(lot.getDateEnd());
		return lotDto;
	}

	public static Category convert(CategoryDto categoryDto) {
		if (categoryDto == null) {
			return null;
		}
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		return category;
	}

	public static CategoryDto convert(Category category) {
		if (category == null) {
			return null;
		}
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setTitle(category.getTitle());
		categoryDto.setDescription(category.getDescription());
		return categoryDto;
	}

	public static LotHistory convert(LotHistoryDto lotHistoryDto) {
		if (lotHistoryDto == null) {
			return null;
		}
		LotHistory lotHistory = new LotHistory();
		lotHistory.setId(lotHistoryDto.getId());
		lotHistory.setLot(convert(lotHistoryDto.getLot()));
		lotHistory.setBuyer(convert(lotHistoryDto.getBuyer()));
		lotHistory.setChangeTime(lotHistoryDto.getChangeTime());
		lotHistory.setPrice(lotHistoryDto.getPrice());
		return lotHistory;
	}

	public static LotHistoryDto convert(LotHistory lotHistory) {
		if (lotHistory == null) {
			return null;
		}
		LotHistoryDto lotHistoryDto = new LotHistoryDto();
		lotHistoryDto.setId(lotHistory.getId());
		lotHistoryDto.setLot(convert(lotHistory.getLot()));
		lotHistoryDto.setBuyer(convert(lotHistory.getBuyer()));
		lotHistoryDto.setChangeTime(lotHistory.getChangeTime());
		lotHistoryDto.setPrice(lotHistory.getPrice());
		return lotHistoryDto;
	}
}