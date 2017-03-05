package co.com.tactusoft.crm.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import co.com.tactusoft.crm.controller.bo.ParameterBo;
import co.com.tactusoft.crm.controller.bo.SecurityBo;
import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmDoctor;
import co.com.tactusoft.crm.model.entities.CrmDomain;
import co.com.tactusoft.crm.model.entities.CrmMaterial;
import co.com.tactusoft.crm.model.entities.CrmNurse;
import co.com.tactusoft.crm.model.entities.CrmPage;
import co.com.tactusoft.crm.model.entities.CrmParameter;
import co.com.tactusoft.crm.model.entities.CrmProfile;
import co.com.tactusoft.crm.model.entities.CrmRole;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.SAPEnvironment;
import co.com.tactusoft.crm.util.SugarEnvironment;
import co.com.tactusoft.crm.view.backing.ApplicationBacking;

import com.tactusoft.webservice.client.beans.WSBean;
import com.tactusoft.webservice.client.execute.CustomListsExecute;

public class DaoAuthenticationProviderCustom extends
		AbstractUserDetailsAuthenticationProvider {

	@Resource
	private SecurityBo securityService;

	@Resource
	private TablesBo tableService;

	@Resource
	private ParameterBo parameterService;

	private UserDetailsServiceCustom userDetailsService;
	private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	private SaltSource saltSource;

	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Object salt = null;

		if (saltSource != null) {
			salt = saltSource.getSalt(userDetails);
		}

		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
				authentication.getCredentials().toString(), salt)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		} else {

			UserData user = (UserData) userDetails;

			// get profile
			List<CrmProfile> listProfile = tableService
					.getListProfileByUser(user.getUser().getId());
			user.setListProfile(listProfile);

			// get Roles
			List<CrmRole> listRole = securityService.getListCrmRoleByUser(user
					.getUser().getId());
			List<SimpleGrantedAuthority> listGrantedAuthority = new ArrayList<SimpleGrantedAuthority>();
			for (CrmRole role : listRole) {
				listGrantedAuthority.add(new SimpleGrantedAuthority(role
						.getName()));
			}
			user.setRoles(listGrantedAuthority);
			user.setOpenAppointment(false);
			user.setPrintFormula(false);
			user.setPrintHistorial(false);
			user.setPrintHistorialOdo(false);
			user.setViewHistorial(false);
			user.setViewHistorialOdo(false);

			String idRoles = "";
			for (CrmRole row : listRole) {
				idRoles = idRoles + row.getId() + ",";
				if (row.getOpenAppointment() && !user.isOpenAppointment()) {
					user.setOpenAppointment(true);
				}
				if (row.getPrintFormula()) {
					user.setPrintFormula(true);
				}

				if (row.getPrintHistorial()) {
					user.setPrintHistorial(true);
				}

				if (row.getPrintHistorialOdo()) {
					user.setPrintHistorialOdo(true);
				}

				if (row.getViewHistorial()) {
					user.setViewHistorial(true);
				}

				if (row.getViewHistorialOdo()) {
					user.setViewHistorialOdo(true);
				}
			}

			idRoles = idRoles.substring(0, idRoles.length() - 1);

			List<CrmRole> listRoleAll = tableService.getListRoleActive();
			user.setListRoleAll(listRoleAll);

			// get Pages
			List<CrmPage> listPage = securityService
					.getListCrmPageByRole(idRoles);
			user.setListPage(listPage);

			listPage = tableService.getListPages();
			user.setListPageAll(listPage);

			// get Default Page
			String pageDefault = listRole.get(0).getCrmPage().getPage();
			user.setPageDefault(pageDefault);

			// get Branch
			List<CrmBranch> listBranch = securityService
					.getListBranchByUser(user.getUser().getId());
			user.setListBranch(listBranch);

			// get Parameter
			List<CrmParameter> listParameter;
			listParameter = parameterService.getListParameter();
			user.setListParameter(listParameter);
			
			List<CrmDomain> listDocType = tableService.getListDomain("TIPO_DOCUMENTO");
			List<CrmDomain> listGroupSeller = tableService.getListDomain("PAUTA_PUBLICITARIA");

			// get listWSGroupSellers
			SAPEnvironment sap = FacesUtil.findBean("SAPEnvironment");
			sap.getLisParameter();

			//SugarEnvironment sugar = FacesUtil.findBean("sugarEnvironment");
			//sugar.getLisParameter();

			listBranch = tableService.getListBranchActive1000();
			user.setListBranchAll(listBranch);

			ApplicationBacking applicationBacking = FacesUtil
					.findBean("applicationBacking");

			Date newCurrentDate = FacesUtil.getDateWithoutTime(new Date());
			if (applicationBacking.getCurrentDate() == null
					|| applicationBacking.getCurrentDate().compareTo(
							newCurrentDate) < 0) {
				applicationBacking.setCurrentDate(newCurrentDate);
				applicationBacking.setConsecutive(1);
				
				user.setListBranchAll(listBranch);
				
				applicationBacking.setListDocTypes(new ArrayList<WSBean>());
				for(CrmDomain dom:listDocType){
					WSBean bean = new WSBean();
					bean.setCode(dom.getCode());
					bean.setNames(dom.getItemValue());
					applicationBacking.getListDocTypes().add(bean);
				}
				
				applicationBacking.setListGroupSellers(new ArrayList<WSBean>());
				for(CrmDomain dom:listGroupSeller){
					WSBean bean = new WSBean();
					bean.setCode(dom.getCode());
					bean.setNames(dom.getItemValue());
					applicationBacking.getListGroupSellers().add(bean);
				}

				/*try {
					for (WSBean row : applicationBacking.getListBranchs()) {
						boolean notExists = true;
						for (CrmBranch rowDB : listBranch) {
							if (row.getCode().equals(rowDB.getCode())) {
								notExists = false;
							}
						}

						if (notExists) {
							CrmBranch newBranch = new CrmBranch();
							newBranch.setCode(row.getCode());
							newBranch.setName(row.getNames());
							newBranch.setSociety(row.getSociety());
							newBranch.setState(Constant.STATE_ACTIVE);
							tableService.saveBranch(newBranch);
							user.getListBranchAll().add(newBranch);
						}
					}
				} catch (Exception ex) {
					user.setListBranchAll(listBranch);
				}

				try {
					applicationBacking.setListBranchs(CustomListsExecute
							.getBranchs(sap.getUrlWebList(), sap.getUsername(),
									sap.getPassword()));
				} catch (Exception ex) {
					applicationBacking.setListBranchs(new ArrayList<WSBean>());
				}

				try {
					applicationBacking.setListGroupSellers(CustomListsExecute
							.getGroupSellers(sap.getUrlWebList(),
									sap.getUsername(), sap.getPassword()));
				} catch (Exception ex) {
					applicationBacking
							.setListGroupSellers(new ArrayList<WSBean>());
				}*/

				try {
					applicationBacking.setListMaterials(tableService.getListMaterial());
				} catch (Exception ex) {
					applicationBacking
							.setListMaterials(new ArrayList<CrmMaterial>());
				}

				/*try {
					applicationBacking.setListDocTypes(CustomListsExecute
							.getDocTypes(sap.getUrlWebList(),
									sap.getUsername(), sap.getPassword()));
				} catch (Exception ex) {
					applicationBacking.setListDocTypes(new ArrayList<WSBean>());
				}*/

			}

			user.setListWSGroupSellers(applicationBacking.getListGroupSellers());
			user.setListWSMaterials(applicationBacking.getListMaterials());
			user.setListWSDocType(applicationBacking.getListDocTypes());

			CrmDoctor doctor = tableService
					.getCrmDoctor(user.getUser().getId());
			if (doctor != null) {
				user.setRolePrincipal(doctor.getMedicalType());
			} else {
				CrmNurse nurse = tableService.getCrmNurse(user.getUser()
						.getId());
				if (nurse != null) {
					if (nurse.getAux() == Constant.STATE_ACTIVE) {
						user.setRolePrincipal(Constant.ROLE_NURSE_AUX);
					} else {
						user.setRolePrincipal(Constant.ROLE_NURSE);
					}
				} else {
					user.setRolePrincipal(Constant.ROLE_USER);
				}
			}
		}
	}

	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			loadedUser = userDetailsService.loadUserByUsername(username);
		} catch (DataAccessException repositoryProblem) {
			throw new AuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"AuthenticationDao returned null, which is an interface contract violation");
		}

		return loadedUser;
	}

	public UserDetailsServiceCustom getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			UserDetailsServiceCustom userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
