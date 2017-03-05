package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CrmDoctorException generated by hbm2java
 */
@Entity
@Table(name = "crm_doctor_exception", catalog = "crm_db")
public class CrmDoctorException implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmDoctor crmDoctor;
	private Date startHour;
	private Date endHour;
	private CrmBranch crmBranch;

	public CrmDoctorException() {
	}

	public CrmDoctorException(BigDecimal id, CrmDoctor crmDoctor,
			Date startHour, Date endHour, CrmBranch crmBranch) {
		this.id = id;
		this.crmDoctor = crmDoctor;
		this.startHour = startHour;
		this.endHour = endHour;
		this.crmBranch = crmBranch;
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
	@JoinColumn(name = "id_doctor", nullable = false)
	public CrmDoctor getCrmDoctor() {
		return this.crmDoctor;
	}

	public void setCrmDoctor(CrmDoctor crmDoctor) {
		this.crmDoctor = crmDoctor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_hour", nullable = false, length = 19)
	public Date getStartHour() {
		return this.startHour;
	}

	public void setStartHour(Date startHour) {
		this.startHour = startHour;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_hour", nullable = false, length = 19)
	public Date getEndHour() {
		return this.endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_branch")
	public CrmBranch getCrmBranch() {
		return crmBranch;
	}

	public void setCrmBranch(CrmBranch crmBranch) {
		this.crmBranch = crmBranch;
	}

}
