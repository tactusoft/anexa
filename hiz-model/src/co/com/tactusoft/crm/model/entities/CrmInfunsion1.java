package co.com.tactusoft.crm.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;

@Entity
@Table(name = "crm_infunsion_1", catalog = "crm_db")
public class CrmInfunsion1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String email;
	private String phone;
	private String names;
	private Date eventDate;
	private String eventType;
	private Integer status;
	private String disease;
	private Integer idLog;
	private CrmAppointment crmAppointment;

	public CrmInfunsion1() {

	}

	public CrmInfunsion1(Integer id, String email, Date eventDate,
			String eventType, Integer status, CrmAppointment crmAppointment) {
		super();
		this.id = id;
		this.email = email;
		this.eventDate = eventDate;
		this.eventType = eventType;
		this.status = status;
		this.crmAppointment = crmAppointment;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email", nullable = false, length = 1000)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = true, length = 45)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "names", nullable = true, length = 500)
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "event_date", nullable = false, length = 19)
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "event_type", nullable = false, length = 45)
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "disease", length = 500)
	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	@Column(name = "id_log", nullable = false)
	public Integer getIdLog() {
		return idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_appointment", nullable = true)
	public CrmAppointment getCrmAppointment() {
		return crmAppointment;
	}

	public void setCrmAppointment(CrmAppointment crmAppointment) {
		this.crmAppointment = crmAppointment;
	}

}
