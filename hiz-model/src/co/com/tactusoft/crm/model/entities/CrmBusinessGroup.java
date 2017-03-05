package co.com.tactusoft.crm.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "crm_business_group", catalog = "crm_db")
public class CrmBusinessGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String description;
	private Integer status;

	private Set<CrmBranch> crmBranches = new HashSet<CrmBranch>(0);
	private Set<CrmUser> crmUsers = new HashSet<CrmUser>(0);

	public CrmBusinessGroup() {

	}

	public CrmBusinessGroup(Integer id) {
		this.id = id;
	}

	public CrmBusinessGroup(Integer id, String name, String description,
			Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false, length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmBusinessGroup")
	public Set<CrmBranch> getCrmBranches() {
		return crmBranches;
	}

	public void setCrmBranches(Set<CrmBranch> crmBranches) {
		this.crmBranches = crmBranches;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmBusinessGroup")
	public Set<CrmUser> getCrmUsers() {
		return crmUsers;
	}

	public void setCrmUsers(Set<CrmUser> crmUsers) {
		this.crmUsers = crmUsers;
	}

}
