package co.com.tactusoft.crm.model.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class VwAppointmentBranch implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private VwAppointmentBranchId id;
	private String branchName;
	private Long count;

	public VwAppointmentBranch() {
	}

	public VwAppointmentBranch(VwAppointmentBranchId id, String branchName,
			long count) {
		this.id = id;
		this.branchName = branchName;
		this.count = count;
	}

	@EmbeddedId
	public VwAppointmentBranchId getId() {
		return id;
	}

	public void setId(VwAppointmentBranchId id) {
		this.id = id;
	}

	@Column(name = "branchName")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "count")
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
