package co.com.tactusoft.crm.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * CrmCalcSchemeResult generated by hbm2java
 */
@Entity
@Table(name = "crm_calc_scheme_result", catalog = "crm_db")
public class CrmCalcSchemeResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmSaleOrderDetail crmSaleOrderDetail;
	private CrmTerm crmTerm;
	private BigDecimal amount;
	private Boolean start;
	private Boolean end;
	private String description;

	public CrmCalcSchemeResult() {
	}

	public CrmCalcSchemeResult(Integer id,
			CrmSaleOrderDetail crmSaleOrderDetail, CrmTerm crmTerm,
			BigDecimal amount, Boolean start, Boolean end) {
		this.id = id;
		this.crmSaleOrderDetail = crmSaleOrderDetail;
		this.crmTerm = crmTerm;
		this.amount = amount;
		this.start = start;
		this.end = end;
	}

	public CrmCalcSchemeResult(CrmTerm crmTerm, String description, BigDecimal amount,
			Boolean start, Boolean end) {
		this.crmTerm = crmTerm;
		this.description = description;
		this.amount = amount;
		this.start = start;
		this.end = end;
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
	@JoinColumn(name = "id_order_detail", nullable = false)
	public CrmSaleOrderDetail getCrmSaleOrderDetail() {
		return this.crmSaleOrderDetail;
	}

	public void setCrmSaleOrderDetail(CrmSaleOrderDetail crmSaleOrderDetail) {
		this.crmSaleOrderDetail = crmSaleOrderDetail;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_term", nullable = true)
	public CrmTerm getCrmTerm() {
		return this.crmTerm;
	}

	public void setCrmTerm(CrmTerm crmTerm) {
		this.crmTerm = crmTerm;
	}

	@Column(name = "amount", nullable = false, scale = 3)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "start", nullable = false)
	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	@Column(name = "end", nullable = false)
	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
