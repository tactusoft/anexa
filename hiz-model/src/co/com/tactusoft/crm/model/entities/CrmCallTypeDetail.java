package co.com.tactusoft.crm.model.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CrmCallTypeDetail generated by hbm2java
 */
@Entity
@Table(name = "crm_call_type_detail", catalog = "crm_db")
public class CrmCallTypeDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmCallType crmCallType;
	private String code;
	private String description;
	private int status;
	private Set<CrmCall> crmCalls = new HashSet<CrmCall>(0);

	public CrmCallTypeDetail() {
	}

	public CrmCallTypeDetail(CrmCallType crmCallType, String code,
			String description, int status) {
		this.crmCallType = crmCallType;
		this.code = code;
		this.description = description;
		this.status = status;
	}

	public CrmCallTypeDetail(CrmCallType crmCallType, String code,
			String description, int status, Set<CrmCall> crmCalls) {
		this.crmCallType = crmCallType;
		this.code = code;
		this.description = description;
		this.status = status;
		this.crmCalls = crmCalls;
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
	@JoinColumn(name = "id_call_type", nullable = false)
	public CrmCallType getCrmCallType() {
		return this.crmCallType;
	}

	public void setCrmCallType(CrmCallType crmCallType) {
		this.crmCallType = crmCallType;
	}

	@Column(name = "code", nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmCallTypeDetail")
	public Set<CrmCall> getCrmCalls() {
		return this.crmCalls;
	}

	public void setCrmCalls(Set<CrmCall> crmCalls) {
		this.crmCalls = crmCalls;
	}

}
