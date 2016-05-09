package ua.skillsup.practice.hibernate.dao.db.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "ITEM")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "TITLE", nullable = false, unique = true)
	private String title;
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	@Column(name = "WIDTH")
	private Double width;
	@Column(name = "HEIGHT")
	private Double height;
	@Column(name = "WEIGHT")
	private Double weight;

	@ManyToMany
	@JoinTable(
		name = "ITEM_CATEGORY",
		joinColumns = @JoinColumn(name = "ITEM_ID"),
		inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
	)
	private List<Category> categories;

	//Не требуется:
	//@OneToMany(mappedBy = "item")
	//private List<Lot> lots;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item item = (Item) o;
		return Objects.equals(id, item.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
