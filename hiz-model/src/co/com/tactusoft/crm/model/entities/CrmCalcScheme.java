package co.com.tactusoft.crm.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CrmCalcScheme generated by hbm2java
 */
@Entity
@Table(name = "crm_calc_scheme", catalog = "crm_db")
public class CrmCalcScheme implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private byte status;
	private List<CrmCalcSchemeDetail> crmCalcSchemeDetails = new LinkedList<>();

	public CrmCalcScheme() {
	}

	public CrmCalcScheme(String name, byte status) {
		this.name = name;
		this.status = status;
	}

	public CrmCalcScheme(String name, byte status,
			List<CrmCalcSchemeDetail> crmCalcSchemeDetails) {
		this.name = name;
		this.status = status;
		this.crmCalcSchemeDetails = crmCalcSchemeDetails;
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmCalcScheme")
	public List<CrmCalcSchemeDetail> getCrmCalcSchemeDetails() {
		return this.crmCalcSchemeDetails;
	}

	public void setCrmCalcSchemeDetails(
			List<CrmCalcSchemeDetail> crmCalcSchemeDetails) {
		this.crmCalcSchemeDetails = crmCalcSchemeDetails;
	}

}
