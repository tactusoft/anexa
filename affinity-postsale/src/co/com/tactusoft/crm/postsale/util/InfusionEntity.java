package co.com.tactusoft.crm.postsale.util;

import java.io.Serializable;
import java.util.Date;

public class InfusionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String phone;
	private String names;
	private Date eventDate;
	private String disease;

	public InfusionEntity() {

	}

	public InfusionEntity(String email, Date eventDate) {
		super();
		this.email = email;
		this.eventDate = eventDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

}
