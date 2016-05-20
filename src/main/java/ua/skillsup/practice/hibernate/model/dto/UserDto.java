package ua.skillsup.practice.hibernate.model.dto;

public class UserDto {

	private Long id;
	private String login;
	private String name;
	private String lastName;
	private String deliveryAddress;
	private String contactPhone;

	public UserDto() {   }

	public UserDto(String login, String name, String lastName, String deliveryAddress, String contactPhone) {
		this.login = login;
		this.name = name;
		this.lastName = lastName;
		this.deliveryAddress = deliveryAddress;
		this.contactPhone = contactPhone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDto userDto = (UserDto) o;

		if (!login.equals(userDto.login)) return false;
		if (!name.equals(userDto.name)) return false;
		if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
		if (!deliveryAddress.equals(userDto.deliveryAddress)) return false;
		return contactPhone.equals(userDto.contactPhone);

	}

	@Override
	public int hashCode() {
		int result = login.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + deliveryAddress.hashCode();
		result = 31 * result + contactPhone.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserDto{" +
				"id=" + id +
				", login='" + login + '\'' +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", deliveryAddress='" + deliveryAddress + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				"}";
	}
}