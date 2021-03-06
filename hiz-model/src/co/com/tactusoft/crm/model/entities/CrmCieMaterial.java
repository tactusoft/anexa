package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrmCieMaterial generated by hbm2java
 */
@Entity
@Table(name = "crm_cie_material", catalog = "crm_db")
public class CrmCieMaterial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmCie crmCie;
	private String description;
	private String material;
	private Integer state;

	public CrmCieMaterial() {
	}

	public CrmCieMaterial(BigDecimal id, CrmCie crmCie, String material) {
		this.id = id;
		this.crmCie = crmCie;
		this.material = material;
	}

	public CrmCieMaterial(BigDecimal id, CrmCie crmCie, String description,
			String material, Integer state) {
		this.id = id;
		this.crmCie = crmCie;
		this.description = description;
		this.material = material;
		this.state = state;
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
	@JoinColumn(name = "id_cie", nullable = false)
	public CrmCie getCrmCie() {
		return this.crmCie;
	}

	public void setCrmCie(CrmCie crmCie) {
		this.crmCie = crmCie;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "material", nullable = false, length = 45)
	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
