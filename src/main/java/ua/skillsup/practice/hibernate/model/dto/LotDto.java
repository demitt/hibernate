package ua.skillsup.practice.hibernate.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LotDto {

	private Long id;
	private ItemDto item;
	private UserDto owner;
	private LocalDate datePlaced;
	private LocalDateTime lastUpdate;
	private BigDecimal startPrice;
	private UserDto buyer;
	private BigDecimal currentPrice;
	private LocalDate dateEnd;

	public LotDto(ItemDto item, UserDto owner, BigDecimal price, int period) {
		this.item = item;
		this.owner = owner;
		this.startPrice = price;
		this.currentPrice = price;
		this.datePlaced = LocalDate.now();
		this.dateEnd = this.datePlaced.plusDays(period);
	}

	public LotDto() {

	}

	public LotDto(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemDto getItem() {
		return item;
	}

	public void setItem(ItemDto item) {
		this.item = item;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public LocalDate getDatePlaced() {
		return datePlaced;
	}

	public void setDatePlaced(LocalDate datePlaced) {
		this.datePlaced = datePlaced;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public UserDto getBuyer() {
		return buyer;
	}

	public void setBuyer(UserDto buyer) {
		this.buyer = buyer;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public LocalDate getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(LocalDate dateEnd) {
		this.dateEnd = dateEnd;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LotDto lotDto = (LotDto) o;

		if (!item.equals(lotDto.item)) return false;
		if (!owner.equals(lotDto.owner)) return false;
		if (!datePlaced.equals(lotDto.datePlaced)) return false;
		return startPrice.equals(lotDto.startPrice);

	}

	@Override
	public int hashCode() {
		int result = item.hashCode();
		result = 31 * result + owner.hashCode();
		result = 31 * result + datePlaced.hashCode();
		result = 31 * result + startPrice.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "LotDto{" +
			"id=" + id +
			", item=" + item +
			", owner=" + owner +
			", datePlaced=" + datePlaced +
			", lastUpdate=" + lastUpdate +
			", startPrice=" + startPrice +
			", buyer=" + buyer +
			", currentPrice=" + currentPrice +
			", dateEnd=" + dateEnd +
			'}';
	}
}