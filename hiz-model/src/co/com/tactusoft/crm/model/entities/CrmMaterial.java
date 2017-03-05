package co.com.tactusoft.crm.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CrmMedication generated by hbm2java
 */
@Entity
@Table(name = "crm_material", catalog = "crm_db")
public class CrmMaterial implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private String code;
	private String name;
	private String type;
	private Integer status;

	public CrmMaterial() {

	}

	public CrmMaterial(BigDecimal id, String code, String name, String type,
			Integer status) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
		this.status = status;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "code", nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}