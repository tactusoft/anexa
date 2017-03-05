package co.com.tactusoft.crm.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmMaterial;
import co.com.tactusoft.crm.model.entities.CrmPage;
import co.com.tactusoft.crm.model.entities.CrmParameter;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmProfile;
import co.com.tactusoft.crm.model.entities.CrmRole;
import co.com.tactusoft.crm.model.entities.CrmUser;

import com.tactusoft.webservice.client.beans.WSBean;

public class UserData implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<SimpleGrantedAuthority> roles;
	private List<CrmRole> listRoleAll;
	private CrmUser user;
	private List<CrmProfile> listProfile;
	private List<CrmPage> listPage;
	private List<CrmPage> listPageAll;
	private String pageDefault;
	private List<CrmBranch> listBranch;
	private List<CrmBranch> listBranchAll;
	private List<CrmParameter> listParameter;
	private List<WSBean> listWSGroupSellers;
	private List<CrmMaterial> listWSMaterials;
	private List<WSBean> listWSDocType;
	private List<WSBean> listMethodPayment;
	private List<WSBean> listPaymentTerm;
	private List<WSBean> listSalesGrp;
	private CrmPatient selectedPatient;
	private String rolePrincipal;
	private int numCalls;
	private boolean openAppointment;
	private boolean printFormula;
	private boolean printHistorial;
	private boolean printHistorialOdo;
	private boolean viewHistorial;
	private boolean viewHistorialOdo;

	private boolean accountNonExpired = true;
	private boolean credentialsNonExpired = true;
	private boolean accountNonLocked = false;
	private boolean enabled;

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public List<SimpleGrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<SimpleGrantedAuthority> roles) {
		this.roles = roles;
	}

	public List<CrmRole> getListRoleAll() {
		return listRoleAll;
	}

	public void setListRoleAll(List<CrmRole> listRoleAll) {
		this.listRoleAll = listRoleAll;
	}

	public CrmUser getUser() {
		return user;
	}

	public void setUser(CrmUser user) {
		this.user = user;
	}

	public List<CrmProfile> getListProfile() {
		return listProfile;
	}

	public void setListProfile(List<CrmProfile> listProfile) {
		this.listProfile = listProfile;
	}

	public List<CrmPage> getListPage() {
		return listPage;
	}

	public void setListPage(List<CrmPage> listPage) {
		this.listPage = listPage;
	}

	public List<CrmPage> getListPageAll() {
		return listPageAll;
	}

	public void setListPageAll(List<CrmPage> listPageAll) {
		this.listPageAll = listPageAll;
	}

	public String getPageDefault() {
		return pageDefault;
	}

	public void setPageDefault(String pageDefault) {
		this.pageDefault = pageDefault;
	}

	public List<CrmBranch> getListBranch() {
		return listBranch;
	}

	public void setListBranch(List<CrmBranch> listBranch) {
		this.listBranch = listBranch;
	}

	public List<CrmBranch> getListBranchAll() {
		return listBranchAll;
	}

	public void setListBranchAll(List<CrmBranch> listBranchAll) {
		this.listBranchAll = listBranchAll;
	}

	public List<CrmParameter> getListParameter() {
		return listParameter;
	}

	public void setListParameter(List<CrmParameter> listParameter) {
		this.listParameter = listParameter;
	}

	public List<WSBean> getListWSGroupSellers() {
		return listWSGroupSellers;
	}

	public void setListWSGroupSellers(List<WSBean> listWSGroupSellers) {
		this.listWSGroupSellers = listWSGroupSellers;
	}

	public List<CrmMaterial> getListWSMaterials() {
		return listWSMaterials;
	}

	public void setListWSMaterials(List<CrmMaterial> listWSMaterials) {
		this.listWSMaterials = listWSMaterials;
	}

	public List<WSBean> getListWSDocType() {
		return listWSDocType;
	}

	public void setListWSDocType(List<WSBean> listWSDocType) {
		this.listWSDocType = listWSDocType;
	}

	public List<WSBean> getListMethodPayment() {
		return listMethodPayment;
	}

	public void setListMethodPayment(List<WSBean> listMethodPayment) {
		this.listMethodPayment = listMethodPayment;
	}

	public List<WSBean> getListPaymentTerm() {
		return listPaymentTerm;
	}

	public void setListPaymentTerm(List<WSBean> listPaymentTerm) {
		this.listPaymentTerm = listPaymentTerm;
	}

	public List<WSBean> getListSalesGrp() {
		return listSalesGrp;
	}

	public void setListSalesGrp(List<WSBean> listSalesGrp) {
		this.listSalesGrp = listSalesGrp;
	}

	public CrmPatient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(CrmPatient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	public String getRolePrincipal() {
		return rolePrincipal;
	}

	public void setRolePrincipal(String rolePrincipal) {
		this.rolePrincipal = rolePrincipal;
	}

	public int getNumCalls() {
		return numCalls;
	}

	public void setNumCalls(int numCalls) {
		this.numCalls = numCalls;
	}

	public boolean isOpenAppointment() {
		return openAppointment;
	}

	public void setOpenAppointment(boolean openAppointment) {
		this.openAppointment = openAppointment;
	}

	public boolean isPrintFormula() {
		return printFormula;
	}

	public void setPrintFormula(boolean printFormula) {
		this.printFormula = printFormula;
	}

	public boolean isPrintHistorial() {
		return printHistorial;
	}

	public void setPrintHistorial(boolean printHistorial) {
		this.printHistorial = printHistorial;
	}

	public boolean isPrintHistorialOdo() {
		return printHistorialOdo;
	}

	public void setPrintHistorialOdo(boolean printHistorialOdo) {
		this.printHistorialOdo = printHistorialOdo;
	}

	public boolean isViewHistorial() {
		return viewHistorial;
	}

	public void setViewHistorial(boolean viewHistorial) {
		this.viewHistorial = viewHistorial;
	}

	public boolean isViewHistorialOdo() {
		return viewHistorialOdo;
	}

	public void setViewHistorialOdo(boolean viewHistorialOdo) {
		this.viewHistorialOdo = viewHistorialOdo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof UserData) {
			return username.equals(((UserData) rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired)
				.append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
				.append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked)
				.append("; ");
		if (!roles.isEmpty()) {
			sb.append("Granted Authorities: ");
			boolean first = true;
			for (GrantedAuthority auth : roles) {
				if (!first) {
					sb.append(",");
				}
				first = false;
				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}
		return sb.toString();
	}
}
