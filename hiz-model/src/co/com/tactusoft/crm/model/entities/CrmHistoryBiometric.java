package co.com.tactusoft.crm.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrmHistoryBiometric generated by hbm2java
 */
@Entity
@Table(name = "crm_history_biometric", catalog = "crm_db")
public class CrmHistoryBiometric implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmAppointment crmAppointment;
	private String test;

	public CrmHistoryBiometric() {
	}

	public CrmHistoryBiometric(BigDecimal id, CrmAppointment crmAppointment) {
		this.id = id;
		this.crmAppointment = crmAppointment;
	}

	public CrmHistoryBiometric(BigDecimal id, String test) {
		this.id = id;
		this.test = test;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_appointment", nullable = false)
	public CrmAppointment getCrmAppointment() {
		return crmAppointment;
	}

	public void setCrmAppointment(CrmAppointment crmAppointment) {
		this.crmAppointment = crmAppointment;
	}

	@Column(name = "test", length = 65535)
	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}