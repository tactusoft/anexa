package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * CrmDoctor generated by hbm2java
 */
@Entity
@Table(name = "crm_doctor", catalog = "crm_db", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class CrmDoctor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmUser crmUser;
	private CrmSpeciality crmSpeciality;
	private CrmUser crmUserByIdUserCreate;
	private CrmUser crmUserByIdUserModified;
	private String code;
	private String names;
	private String medicalLicense;
	private String university;
	private String medicalType;
	private int state;
	private Date dateCreate;
	private Date dateModified;
	private Set<CrmAppointment> crmAppointments = new HashSet<CrmAppointment>(0);
	private Set<CrmDoctorSchedule> crmDoctorSchedules = new HashSet<CrmDoctorSchedule>(
			0);
	private Set<CrmDoctorException> crmDoctorExceptions = new HashSet<CrmDoctorException>(
			0);

	public CrmDoctor() {
	}

	public CrmDoctor(BigDecimal id, CrmUser crmUser,
			CrmSpeciality crmSpeciality, String code, String names, int state) {
		this.id = id;
		this.crmUser = crmUser;
		this.crmSpeciality = crmSpeciality;
		this.code = code;
		this.names = names;
		this.state = state;
	}

	public CrmDoctor(BigDecimal id, CrmUser crmUser,
			CrmSpeciality crmSpeciality, String code, String names, int state,
			Set<CrmAppointment> crmAppointments,
			Set<CrmDoctorSchedule> crmDoctorSchedules,
			Set<CrmDoctorException> crmDoctorExceptions) {
		this.id = id;
		this.crmUser = crmUser;
		this.crmSpeciality = crmSpeciality;
		this.code = code;
		this.names = names;
		this.state = state;
		this.crmAppointments = crmAppointments;
		this.crmDoctorSchedules = crmDoctorSchedules;
		this.crmDoctorExceptions = crmDoctorExceptions;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user", nullable = false)
	public CrmUser getCrmUser() {
		return this.crmUser;
	}

	public void setCrmUser(CrmUser crmUser) {
		this.crmUser = crmUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_speciality")
	public CrmSpeciality getCrmSpeciality() {
		return this.crmSpeciality;
	}

	public void setCrmSpeciality(CrmSpeciality crmSpeciality) {
		this.crmSpeciality = crmSpeciality;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_modified")
	public CrmUser getCrmUserByIdUserModified() {
		return this.crmUserByIdUserModified;
	}

	public void setCrmUserByIdUserModified(CrmUser crmUserByIdUserModified) {
		this.crmUserByIdUserModified = crmUserByIdUserModified;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_create")
	public CrmUser getCrmUserByIdUserCreate() {
		return this.crmUserByIdUserCreate;
	}

	public void setCrmUserByIdUserCreate(CrmUser crmUserByIdUserCreate) {
		this.crmUserByIdUserCreate = crmUserByIdUserCreate;
	}

	@Column(name = "code", unique = true, nullable = false, length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "names", nullable = false, length = 45)
	public String getNames() {
		return this.names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	@Column(name = "medical_license", length = 100)
	public String getMedicalLicense() {
		return medicalLicense;
	}

	public void setMedicalLicense(String medicalLicense) {
		this.medicalLicense = medicalLicense;
	}

	@Column(name = "university", length = 1000)
	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	@Column(name = "medical_type")
	public String getMedicalType() {
		return medicalType;
	}

	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}

	@Column(name = "state", nullable = false)
	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_create", length = 19)
	public Date getDateCreate() {
		return this.dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", length = 19)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmDoctor")
	public Set<CrmAppointment> getCrmAppointments() {
		return this.crmAppointments;
	}

	public void setCrmAppointments(Set<CrmAppointment> crmAppointments) {
		this.crmAppointments = crmAppointments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmDoctor")
	public Set<CrmDoctorSchedule> getCrmDoctorSchedules() {
		return this.crmDoctorSchedules;
	}

	public void setCrmDoctorSchedules(Set<CrmDoctorSchedule> crmDoctorSchedules) {
		this.crmDoctorSchedules = crmDoctorSchedules;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmDoctor")
	public Set<CrmDoctorException> getCrmDoctorExceptions() {
		return this.crmDoctorExceptions;
	}

	public void setCrmDoctorExceptions(
			Set<CrmDoctorException> crmDoctorExceptions) {
		this.crmDoctorExceptions = crmDoctorExceptions;
	}

}