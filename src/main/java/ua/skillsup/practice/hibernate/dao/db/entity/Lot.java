package ua.skillsup.practice.hibernate.dao.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "LOT")
public class Lot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "ITEM_ID", nullable = false)
	private Item item;
	@ManyToOne
	@JoinColumn(name ="OWNER_ID", nullable = false)
	private User owner;
	@Column(name = "DATE_PLACED")
	private LocalDate datePlaced;
	@Column(name = "START_PRICE")
	private BigDecimal startPrice;
	@ManyToOne
	@JoinColumn(name = "BUYER_ID")
	private User buyer;
	@Column(name = "CURRENT_PRICE")
	private BigDecimal currentPrice;
	@Column(name = "DATE_END")
	private LocalDate dateEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
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

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Lot lot = (Lot) o;
		return Objects.equals(id, lot.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/*@Override
	public String toString() {
		return "Lot{" +
				"id=" + id +
				", item=" + item +
				", owner=" + owner +
				", datePlaced=" + datePlaced +
				", startPrice=" + startPrice +
				", buyer=" + buyer +
				", currentPrice=" + currentPrice +
				", dateEnd=" + dateEnd +
				'}';
	}*/
}