package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VwMedicationDoctorId generated by hbm2java
 */
@Entity
@Table(name = "vw_medication_doctor", catalog = "crm_db")
public class VwMedicationDoctor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private BigDecimal branchId;
	private String branchCode;
	private String branchName;
	private BigDecimal doctorId;
	private String doctorCode;
	private String doctorName;
	private String descMaterial;
	private Long codMaterial;
	private Long unit;
	private Date appointmentDate;

	public VwMedicationDoctor() {
	}

	public VwMedicationDoctor(BigDecimal id, BigDecimal branchId,
			BigDecimal doctorId, String doctorCode, String doctorName,
			String descMaterial, Long codMaterial, Long unit) {
		this.id = id;
		this.branchId = branchId;
		this.doctorId = doctorId;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.descMaterial = descMaterial;
		this.codMaterial = codMaterial;
		this.unit = unit;
	}

	public VwMedicationDoctor(BigDecimal id, BigDecimal branchId,
			String branchCode, String branchName, BigDecimal doctorId,
			String doctorCode, String doctorName, String descMaterial,
			Long codMaterial, Long unit, Date appointmentDate) {
		this.id = id;
		this.branchId = branchId;
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.doctorId = doctorId;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.descMaterial = descMaterial;
		this.codMaterial = codMaterial;
		this.unit = unit;
		this.appointmentDate = appointmentDate;
	}

	@Id
	@Column(name = "id", nullable = false, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "branch_id", nullable = false, scale = 0)
	public BigDecimal getBranchId() {
		return this.branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	@Column(name = "branch_code", length = 45)
	public String getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "branch_name", length = 1000)
	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "doctor_id", nullable = false, length = 45)
	public BigDecimal getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(BigDecimal doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "doctor_code", nullable = false, length = 45)
	public String getDoctorCode() {
		return this.doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	@Column(name = "doctor_name", nullable = false, length = 45)
	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "desc_material", nullable = false, length = 1000)
	public String getDescMaterial() {
		return this.descMaterial;
	}

	public void setDescMaterial(String descMaterial) {
		this.descMaterial = descMaterial;
	}

	@Column(name = "cod_material", nullable = false)
	public Long getCodMaterial() {
		return this.codMaterial;
	}

	public void setCodMaterial(Long codMaterial) {
		this.codMaterial = codMaterial;
	}

	@Column(name = "unit", nullable = false)
	public Long getUnit() {
		return this.unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	@Column(name = "appointment_date", length = 10)
	public Date getAppointmentDate() {
		return this.appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

}
