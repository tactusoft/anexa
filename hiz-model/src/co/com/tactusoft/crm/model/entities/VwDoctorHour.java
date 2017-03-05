package co.com.tactusoft.crm.model.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VwDoctorHour generated by hbm2java
 */
@Entity
@Table(name = "vw_doctor_hour", catalog = "crm_db")
public class VwDoctorHour implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private VwDoctorHourId id;
	private Double hours;

	public VwDoctorHour() {
	}

	public VwDoctorHour(VwDoctorHourId id, Double hours) {
		this.id = id;
		this.hours = hours;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idBranch", column = @Column(name = "id_branch", nullable = false, scale = 0)),
			@AttributeOverride(name = "idDoctor", column = @Column(name = "id_doctor", nullable = false, scale = 0)),
			@AttributeOverride(name = "onlyDate", column = @Column(name = "only_date", length = 10)) })
	public VwDoctorHourId getId() {
		return this.id;
	}

	public void setId(VwDoctorHourId id) {
		this.id = id;
	}

	@Column(name = "hours", precision = 23, scale = 6)
	public Double getHours() {
		return this.hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

}
