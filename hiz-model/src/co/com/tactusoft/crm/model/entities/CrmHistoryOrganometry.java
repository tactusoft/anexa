package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * CrmHistoryOrganometry generated by hbm2java
 */
@Entity
@Table(name = "crm_history_organometry", catalog = "crm_db", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id_patient", "id_appointment" }))
public class CrmHistoryOrganometry implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmPatient crmPatient;
	private CrmAppointment crmAppointment;
	private Boolean organometryCheck;
	private String organometryAnalysis;

	public CrmHistoryOrganometry() {
	}

	public CrmHistoryOrganometry(BigDecimal id, CrmPatient crmPatient,
			CrmAppointment crmAppointment) {
		this.id = id;
		this.crmPatient = crmPatient;
		this.crmAppointment = crmAppointment;
	}

	public CrmHistoryOrganometry(BigDecimal id, CrmPatient crmPatient,
			Boolean organometryCheck, String organometryAnalysis) {
		this.id = id;
		this.crmPatient = crmPatient;
		this.organometryCheck = organometryCheck;
		this.organometryAnalysis = organometryAnalysis;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_patient", nullable = false)
	public CrmPatient getCrmPatient() {
		return this.crmPatient;
	}

	public void setCrmPatient(CrmPatient crmPatient) {
		this.crmPatient = crmPatient;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_appointment", nullable = false)
	public CrmAppointment getCrmAppointment() {
		return crmAppointment;
	}

	public void setCrmAppointment(CrmAppointment crmAppointment) {
		this.crmAppointment = crmAppointment;
	}

	@Column(name = "organometry_check")
	public Boolean getOrganometryCheck() {
		return this.organometryCheck;
	}

	public void setOrganometryCheck(Boolean organometryCheck) {
		this.organometryCheck = organometryCheck;
	}

	@Column(name = "organometry_analysis", length = 65535)
	public String getOrganometryAnalysis() {
		return this.organometryAnalysis;
	}

	public void setOrganometryAnalysis(String organometryAnalysis) {
		this.organometryAnalysis = organometryAnalysis;
	}

}
