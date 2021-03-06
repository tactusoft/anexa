package co.com.tactusoft.crm.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrmTermMaterial generated by hbm2java
 */
@Entity
@Table(name = "crm_term_material", catalog = "crm_db")
public class CrmTermMaterial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmTerm crmTerm;
	private String codMaterial;

	public CrmTermMaterial() {
	}

	public CrmTermMaterial(CrmTerm crmTerm, String codMaterial) {
		this.crmTerm = crmTerm;
		this.codMaterial = codMaterial;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_term", nullable = false)
	public CrmTerm getCrmTerm() {
		return this.crmTerm;
	}

	public void setCrmTerm(CrmTerm crmTerm) {
		this.crmTerm = crmTerm;
	}

	@Column(name = "cod_material", nullable = false, length = 13)
	public String getCodMaterial() {
		return this.codMaterial;
	}

	public void setCodMaterial(String codMaterial) {
		this.codMaterial = codMaterial;
	}

}
