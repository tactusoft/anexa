package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
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
 * CrmCalcSchemeDetail generated by hbm2java
 */
@Entity
@Table(name = "crm_calc_scheme_detail", catalog = "crm_db")
public class CrmCalcSchemeDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmCalcScheme crmCalcScheme;
	private CrmTerm crmTerm;
	private String position;
	private BigDecimal value;

	public CrmCalcSchemeDetail() {
	}

	public CrmCalcSchemeDetail(CrmCalcScheme crmCalcScheme, CrmTerm crmTerm,
			String position, BigDecimal value) {
		this.crmCalcScheme = crmCalcScheme;
		this.crmTerm = crmTerm;
		this.position = position;
		this.value = value;
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
	@JoinColumn(name = "id_calc_scheme", nullable = false)
	public CrmCalcScheme getCrmCalcScheme() {
		return this.crmCalcScheme;
	}

	public void setCrmCalcScheme(CrmCalcScheme crmCalcScheme) {
		this.crmCalcScheme = crmCalcScheme;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_term", nullable = false)
	public CrmTerm getCrmTerm() {
		return this.crmTerm;
	}

	public void setCrmTerm(CrmTerm crmTerm) {
		this.crmTerm = crmTerm;
	}

	@Column(name = "position", nullable = false, length = 4)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "value", nullable = false, scale = 3)
	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
