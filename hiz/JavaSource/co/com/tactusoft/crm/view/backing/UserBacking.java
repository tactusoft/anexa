package co.com.tactusoft.crm.view.backing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmBusinessGroup;
import co.com.tactusoft.crm.model.entities.CrmDepartment;
import co.com.tactusoft.crm.model.entities.CrmDoctor;
import co.com.tactusoft.crm.model.entities.CrmNurse;
import co.com.tactusoft.crm.model.entities.CrmProfile;
import co.com.tactusoft.crm.model.entities.CrmRole;
import co.com.tactusoft.crm.model.entities.CrmUser;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.security.ldap.LDAPDao;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.view.datamodel.UserDataModel;

@Named
@Scope("view")
public class UserBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private List<CrmUser> list;
	private UserDataModel model;
	private CrmUser selected;

	private List<SelectItem> listDepartment;
	private Map<BigDecimal, CrmDepartment> mapDepartment;

	private DualListModel<CrmRole> listRole;
	private DualListModel<CrmBranch> listBranchUser;
	private DualListModel<CrmProfile> listUserProfile;
	private DualListModel<CrmBranch> listBranchPostsale;

	private String password;
	private CrmDoctor crmDoctor;
	private CrmNurse crmNurse;
	private String rolePrincipal;
	private List<SelectItem> listUsersItems;

	public UserBacking() {
		newAction();
	}

	public List<CrmUser> getList() {
		return list;
	}

	public void setList(List<CrmUser> list) {
		this.list = list;
	}

	public UserDataModel getModel() {
		if (model == null) {

			list = tablesService.getListUser();
			model = new UserDataModel(list);

			if (list.size() > 0) {
				this.setSelected(list.get(0));
			}
		}
		return model;
	}

	public void setModel(UserDataModel model) {
		this.model = model;
	}

	public CrmUser getSelected() {
		return selected;
	}

	public void setSelected(CrmUser selected) {
		this.selected = selected;
	}

	public List<SelectItem> getListDepartment() {
		if (listDepartment == null) {
			mapDepartment = new HashMap<BigDecimal, CrmDepartment>();
			listDepartment = new LinkedList<SelectItem>();
			for (CrmDepartment row : tablesService.getListDepartmentActive()) {
				listDepartment.add(new SelectItem(row.getId(), row.getName()));
				mapDepartment.put(row.getId(), row);
			}
		}
		return listDepartment;
	}

	public void setListDepartment(List<SelectItem> listDepartment) {
		this.listDepartment = listDepartment;
	}

	public DualListModel<CrmRole> getListRole() {
		return listRole;
	}

	public void setListRole(DualListModel<CrmRole> listRole) {
		this.listRole = listRole;
	}

	public DualListModel<CrmBranch> getListBranchUser() {
		return listBranchUser;
	}

	public void setListBranchUser(DualListModel<CrmBranch> listBranchUser) {
		this.listBranchUser = listBranchUser;
	}

	public DualListModel<CrmProfile> getListUserProfile() {
		return listUserProfile;
	}

	public void setListUserProfile(DualListModel<CrmProfile> listUserProfile) {
		this.listUserProfile = listUserProfile;
	}

	public DualListModel<CrmBranch> getListBranchPostsale() {
		return listBranchPostsale;
	}

	public void setListBranchPostsale(
			DualListModel<CrmBranch> listBranchPostsale) {
		this.listBranchPostsale = listBranchPostsale;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRolePrincipal() {
		return rolePrincipal;
	}

	public void setRolePrincipal(String rolePrincipal) {
		this.rolePrincipal = rolePrincipal;
	}

	public List<SelectItem> getListUsersItems() {
		if (listUsersItems == null) {
			listUsersItems = new LinkedList<SelectItem>();
			LDAPDao lDAPDao = FacesUtil.findBean("LDAPDao");
			List<String> listUsersLDAP = lDAPDao.getListUsers();
			for (String rowLDAP : listUsersLDAP) {
				boolean isExists = false;
				for (CrmUser row : list) {
					if (row.getUsername().equalsIgnoreCase(rowLDAP)) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					listUsersItems.add(new SelectItem(rowLDAP, rowLDAP));
				}
			}
		}
		return listUsersItems;
	}

	public void setListUsersItems(List<SelectItem> listUsersItems) {
		this.listUsersItems = listUsersItems;
	}

	public void newAction() {
		selected = new CrmUser();
		selected.setState(Constant.STATE_ACTIVE);
		selected.setCrmDepartment(new CrmDepartment());

		listBranchUser = new DualListModel<CrmBranch>();
		listRole = new DualListModel<CrmRole>();
		listUserProfile = new DualListModel<CrmProfile>();
		listBranchPostsale = new DualListModel<CrmBranch>();
		generateListAction(null);

		rolePrincipal = Constant.ROLE_USER;
		idSpeciality = Constant.DEFAULT_VALUE;

		crmDoctor = null;
		crmNurse = null;

		listUsersItems = null;
		if (list != null) {
			this.getListUsersItems();
		}
	}

	public void copyUserAction(ActionEvent event) {
		generateListAction(null);
		listUsersItems = null;
		this.getListUsersItems();
		CrmUser old = selected;
		selected = new CrmUser();
		selected.setCrmDepartment(old.getCrmDepartment());
		selected.setState(Constant.STATE_ACTIVE);
		crmDoctor = null;
		crmNurse = null;
	}

	public void saveAction() {
		String message = null;

		if (listBranchUser.getTarget().size() == 0) {
			message = FacesUtil.getMessage("usr_msg_error_branch");
			FacesUtil.addError(message);
		}

		if (listRole.getTarget().size() == 0) {
			message = FacesUtil.getMessage("usr_msg_error_role");
			FacesUtil.addError(message);
		}

		if (listUserProfile.getTarget().size() == 0) {
			message = FacesUtil.getMessage("usr_msg_error_profile");
			FacesUtil.addError(message);
		}

		if ((rolePrincipal.equals(Constant.ROLE_DOCTOR) || rolePrincipal
				.equals(Constant.ROLE_CONSULTANT))
				&& idSpeciality.intValue() == -1) {
			message = FacesUtil.getMessage("usr_msg_error_speciality");
			FacesUtil.addError(message);
		}

		if (message == null) {
			selected.setUsername(selected.getUsername().toLowerCase());
			selected.setCrmDepartment(mapDepartment.get(selected
					.getCrmDepartment().getId()));
			selected.setCrmBusinessGroup(new CrmBusinessGroup(idBusinessGroup));

			if (crmDoctor == null
					&& (rolePrincipal.equals(Constant.ROLE_DOCTOR) || rolePrincipal
							.equals(Constant.ROLE_CONSULTANT))) {
				crmDoctor = new CrmDoctor();
				crmDoctor.setCode(selected.getDoc());
				crmDoctor.setNames(selected.getSurnames().toUpperCase() + " "
						+ selected.getNames().toUpperCase());
				crmDoctor.setCrmSpeciality(mapCrmSpeciality.get(idSpeciality));
				if (rolePrincipal.equals(Constant.ROLE_DOCTOR)) {
					crmDoctor.setMedicalType(Constant.ROLE_DOCTOR);
				} else {
					crmDoctor.setMedicalType(Constant.ROLE_CONSULTANT);
				}
				crmDoctor.setState(Constant.STATE_ACTIVE);
			} else if (crmDoctor != null) {
				crmDoctor.setCode(selected.getDoc());
				crmDoctor.setNames(selected.getSurnames().toUpperCase() + " "
						+ selected.getNames().toUpperCase());
				crmDoctor.setCrmSpeciality(mapCrmSpeciality.get(idSpeciality));
				crmDoctor
						.setState((rolePrincipal.equals(Constant.ROLE_DOCTOR) || rolePrincipal
								.equals(Constant.ROLE_CONSULTANT)) ? Constant.STATE_ACTIVE
								: Constant.STATE_INACTIVE);
			}

			if (crmNurse == null
					&& (rolePrincipal.equals(Constant.ROLE_NURSE) || rolePrincipal
							.equals(Constant.ROLE_NURSE_AUX))) {
				crmNurse = new CrmNurse();
				crmNurse.setCode(selected.getDoc());
				crmNurse.setNames(selected.getSurnames().toUpperCase() + " "
						+ selected.getNames().toUpperCase());
				crmNurse.setAux(rolePrincipal.equals(Constant.ROLE_NURSE_AUX) ? Constant.STATE_ACTIVE
						: Constant.STATE_INACTIVE);
				crmNurse.setState(Constant.STATE_ACTIVE);
			} else if (crmNurse != null) {
				crmNurse.setCode(selected.getDoc());
				crmNurse.setNames(selected.getSurnames().toUpperCase() + " "
						+ selected.getNames().toUpperCase());
				crmNurse.setAux(rolePrincipal.equals(Constant.ROLE_NURSE_AUX) ? Constant.STATE_ACTIVE
						: Constant.STATE_INACTIVE);
				crmNurse.setState(rolePrincipal.equals(Constant.ROLE_NURSE)
						|| rolePrincipal.equals(Constant.ROLE_NURSE_AUX) ? Constant.STATE_ACTIVE
						: Constant.STATE_INACTIVE);
			}

			int result = tablesService.saveUser(selected, crmDoctor, crmNurse);

			if (result == 0) {
				tablesService.saveUserBranch(selected,
						listBranchUser.getTarget());
				tablesService.saveUserRole(selected, listRole.getTarget());
				tablesService.saveUserProfile(selected,
						listUserProfile.getTarget());
				tablesService.saveUserBranchPostsale(selected,
						listBranchPostsale.getTarget());
				list = tablesService.getListUser();
				model = new UserDataModel(list);

				message = FacesUtil.getMessage("msg_record_ok");

				if (result == -2) {
					message = message
							+ FacesUtil.getMessage("usr_msg_error_doctor");
				} else if (result == -3) {
					message = message
							+ FacesUtil.getMessage("usr_msg_error_nurse");
				}
				FacesUtil.addInfo(message);
			} else if (result == -1) {
				String paramValue = FacesUtil.getMessage("usr_username");
				message = FacesUtil.getMessage("msg_record_unique_exception",
						paramValue);
				FacesUtil.addError(message);
			} else {
				message = FacesUtil.getMessage("msg_record_unique_exception");
				FacesUtil.addError(message);
			}
		}
	}

	public void generateListAction(ActionEvent event) {
		List<CrmBranch> listTargetBranch = new LinkedList<CrmBranch>();
		List<CrmBranch> listSourceBranch = new LinkedList<CrmBranch>();
		List<CrmRole> listTargetRole = new LinkedList<CrmRole>();
		List<CrmRole> listSourceRole = new LinkedList<CrmRole>();
		List<CrmProfile> listTargetProfile = new LinkedList<CrmProfile>();
		List<CrmProfile> listSourceProfile = new LinkedList<CrmProfile>();
		List<CrmBranch> listTargetBranchPostsale = new LinkedList<CrmBranch>();
		List<CrmBranch> listSourceBranchPostsale = new LinkedList<CrmBranch>();

		if (selected != null && selected.getId() != null) {
			listUsersItems = new LinkedList<SelectItem>();
			for (CrmUser row : list) {
				listUsersItems.add(new SelectItem(row.getUsername(), row
						.getUsername()));
			}

			List<CrmDoctor> listDoctor = tablesService.getDoctorByUser(selected
					.getId());
			if (listDoctor.size() > 0) {
				crmDoctor = listDoctor.get(0);
				idSpeciality = crmDoctor.getCrmSpeciality().getId();
				if (crmDoctor.getState() == Constant.STATE_ACTIVE) {
					rolePrincipal = crmDoctor.getMedicalType();
				} else {
					rolePrincipal = Constant.ROLE_USER;
				}
			} else {
				List<CrmNurse> listNurse = tablesService
						.getNurseByUser(selected.getId());
				if (listNurse.size() > 0) {
					crmNurse = listNurse.get(0);
					if (crmNurse.getState() == Constant.STATE_ACTIVE) {
						if (crmNurse.getAux() == Constant.STATE_ACTIVE) {
							rolePrincipal = Constant.ROLE_NURSE_AUX;
						} else {
							rolePrincipal = Constant.ROLE_NURSE;
						}
					} else {
						rolePrincipal = Constant.ROLE_USER;
					}
				} else {
					crmDoctor = null;
					crmNurse = null;
					rolePrincipal = Constant.ROLE_USER;
				}
			}

			idBusinessGroup = selected.getCrmBusinessGroup() != null ? selected
					.getCrmBusinessGroup().getId() : 0;
			listTargetBranch = tablesService.getListBranchByUser(selected
					.getId());

			List<CrmBranch> source;
			if (idBusinessGroup == 0) {
				source = tablesService.getListBranchActive1000();
			} else {
				source = tablesService
						.getListBranchbyBusinessGroup(idBusinessGroup);
			}

			for (CrmBranch row : source) {
				boolean exits = false;
				for (CrmBranch avb : listTargetBranch) {
					if (avb.getId().intValue() == row.getId().intValue()) {
						exits = true;
						break;
					}
				}

				if (!exits) {
					listSourceBranch.add(row);
				}
			}

			listTargetRole = securityService.getListCrmRoleByUser(selected
					.getId());
			for (CrmRole row : tablesService.getListRoleActive()) {
				boolean exits = false;
				for (CrmRole avb : listTargetRole) {
					if (avb.getId().intValue() == row.getId().intValue()) {
						exits = true;
						break;
					}
				}

				if (!exits) {
					listSourceRole.add(row);
				}
			}

			listTargetProfile = tablesService.getListProfileByUser(selected
					.getId());
			for (CrmProfile row : tablesService.getListProfileActive()) {
				boolean exits = false;
				for (CrmProfile avb : listTargetProfile) {
					if (avb.getId().intValue() == row.getId().intValue()) {
						exits = true;
						break;
					}
				}

				if (!exits) {
					listSourceProfile.add(row);
				}
			}

			listTargetBranchPostsale = tablesService
					.getListBranchPostsaleByUser(selected.getId());
			for (CrmBranch row : FacesUtil.getCurrentUserData()
					.getListBranchAll()) {
				boolean exits = false;
				for (CrmBranch avb : listTargetBranchPostsale) {
					if (avb.getId().intValue() == row.getId().intValue()) {
						exits = true;
						break;
					}
				}

				if (!exits) {
					listSourceBranchPostsale.add(row);
				}
			}
		} else {
			if (tablesService != null) {
				listSourceBranch = tablesService.getListBranchActive1000();
				listSourceRole = tablesService.getListRoleActive();
				listSourceProfile = tablesService.getListProfileActive();
				listSourceBranchPostsale = tablesService
						.getListBranchActive1000();
			}
		}

		listBranchUser = new DualListModel<CrmBranch>(listSourceBranch,
				listTargetBranch);
		listRole = new DualListModel<CrmRole>(listSourceRole, listTargetRole);
		listUserProfile = new DualListModel<CrmProfile>(listSourceProfile,
				listTargetProfile);
		listBranchPostsale = new DualListModel<CrmBranch>(
				listSourceBranchPostsale, listTargetBranchPostsale);
	}

	public void refreshPasswordAction(ActionEvent event) {
		String message = null;
		this.password = Constant.PASSWORD_DEFAULT;
		selected.setPassword(FacesUtil.getMD5(this.password));
		int result = tablesService.saveUser(selected, null, null);

		if (result == 0) {
			model = new UserDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
		}
	}

	public void updatePasswordAction() {
		String message = null;
		selected = FacesUtil.getCurrentUser();
		selected.setPassword(FacesUtil.getMD5(this.password));
		int result = tablesService.saveUser(selected, null, null);

		if (result == 0) {
			model = new UserDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
		}
	}

	public void handleBGChange() {
		List<CrmBranch> source;
		if (idBusinessGroup == 0) {
			source = tablesService.getListBranchActive1000();
		} else {
			source = tablesService
					.getListBranchbyBusinessGroup(idBusinessGroup);
		}

		List<CrmBranch> target = new LinkedList<CrmBranch>();
		listBranchUser = new DualListModel<>(source, target);
	}

}
