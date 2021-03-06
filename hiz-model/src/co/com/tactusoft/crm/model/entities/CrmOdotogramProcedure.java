package co.com.tactusoft.crm.model.entities;

// Generated 18/01/2014 02:55:36 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CrmOdotogramProcedure generated by hbm2java
 */
@Entity
@Table(name = "crm_odotogram_procedure", catalog = "crm_db")
public class CrmOdotogramProcedure implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String cod;
	private boolean appliedTooth;
	private boolean appliedFace;
	private String color;
	private boolean status;

	public CrmOdotogramProcedure() {
	}

	public CrmOdotogramProcedure(String name, boolean appliedTooth,
			boolean appliedFace, boolean status) {
		this.name = name;
		this.appliedTooth = appliedTooth;
		this.appliedFace = appliedFace;
		this.status = status;
	}

	public CrmOdotogramProcedure(String name, String cod, boolean appliedTooth,
			boolean appliedFace, String color, boolean status) {
		this.name = name;
		this.cod = cod;
		this.appliedTooth = appliedTooth;
		this.appliedFace = appliedFace;
		this.color = color;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 1000)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "cod", length = 45)
	public String getCod() {
		return this.cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	@Column(name = "applied_tooth", nullable = false)
	public boolean isAppliedTooth() {
		return this.appliedTooth;
	}

	public void setAppliedTooth(boolean appliedTooth) {
		this.appliedTooth = appliedTooth;
	}

	@Column(name = "applied_face", nullable = false)
	public boolean isAppliedFace() {
		return this.appliedFace;
	}

	public void setAppliedFace(boolean appliedFace) {
		this.appliedFace = appliedFace;
	}
	
	@Column(name = "color", nullable = false)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
