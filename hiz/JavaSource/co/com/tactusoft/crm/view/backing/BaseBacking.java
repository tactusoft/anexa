package co.com.tactusoft.crm.view.backing;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleSelectEvent;

import co.com.tactusoft.crm.controller.bo.CapaignBo;
import co.com.tactusoft.crm.controller.bo.GenerateFormulaPDF;
import co.com.tactusoft.crm.controller.bo.ParameterBo;
import co.com.tactusoft.crm.controller.bo.ProcessBo;
import co.com.tactusoft.crm.controller.bo.SecurityBo;
import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmBusinessGroup;
import co.com.tactusoft.crm.model.entities.CrmCity;
import co.com.tactusoft.crm.model.entities.CrmCountry;
import co.com.tactusoft.crm.model.entities.CrmDoctor;
import co.com.tactusoft.crm.model.entities.CrmDomain;
import co.com.tactusoft.crm.model.entities.CrmEps;
import co.com.tactusoft.crm.model.entities.CrmMarketingActivity;
import co.com.tactusoft.crm.model.entities.CrmOccupation;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmProcedureDetail;
import co.com.tactusoft.crm.model.entities.CrmProfile;
import co.com.tactusoft.crm.model.entities.CrmRegion;
import co.com.tactusoft.crm.model.entities.CrmSpeciality;
import co.com.tactusoft.crm.model.entities.VwProcedure;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.SAPEnvironment;
import co.com.tactusoft.crm.view.datamodel.BranchDataModel;
import co.com.tactusoft.crm.view.datamodel.PatientDataModel;

import com.tactusoft.webservice.client.beans.WSBean;
import com.tactusoft.webservice.client.execute.CustomListsExecute;
import com.tactusoft.webservice.client.execute.CustomerExecute;

public class BaseBacking implements Serializable {

	@Inject
	protected TablesBo tablesService;

	@Inject
	protected ProcessBo processService;

	@Inject
	protected SecurityBo securityService;

	@Inject
	protected ParameterBo parameterService;

	@Inject
	protected CapaignBo campaignService;

	private static final long serialVersionUID = 1L;

	protected List<CrmPatient> listPatient;
	protected PatientDataModel patientModel;
	protected CrmPatient selectedPatient;
	protected CrmPatient selectedPatientTemp;
	protected String docPatient;
	protected String namePatient;
	protected String telPatient;
	protected String emailPatient;
	protected int optionSearchPatient;
	protected boolean disabledAddPatient;

	protected List<SelectItem> listAllProfile;
	protected Map<BigDecimal, CrmProfile> mapAllProfile;

	protected List<SelectItem> listProfile;
	protected BigDecimal idProfile;
	protected Map<BigDecimal, CrmProfile> mapProfile;

	protected List<SelectItem> listWSDoctor;
	protected Map<String, String> mapWSDoctor;
	protected String selectedWSDoctor;

	protected List<CrmBranch> listBranchObject;
	protected CrmBranch[] selectedsBranchObject;
	protected BranchDataModel branchDataModel;

	protected List<SelectItem> listCrmBranch;
	protected Map<BigDecimal, CrmBranch> mapCrmBranch;
	protected BigDecimal idBranch;
	protected List<String> listBranchSelected;

	protected List<SelectItem> listCrmBranchByUser;
	protected Map<BigDecimal, CrmBranch> mapCrmBranchByUser;

	protected List<SelectItem> listCrmSpeciality;
	protected Map<BigDecimal, CrmSpeciality> mapCrmSpeciality;
	protected BigDecimal idSpeciality;

	protected List<SelectItem> listCrmBusinessGroup;
	protected Integer idBusinessGroup;

	protected List<SelectItem> listWSGroupSellers;
	protected Map<String, String> mapWSGroupSellers;
	protected String selectedWSGroupSellers;

	protected List<SelectItem> listCountry;
	protected BigDecimal idCountry;
	protected Map<BigDecimal, CrmCountry> mapCountry;

	protected List<CrmRegion> listAllRegion;
	protected List<SelectItem> listRegion;
	protected BigDecimal idRegion;
	protected Map<BigDecimal, CrmRegion> mapRegion;

	protected List<CrmCity> listAllCity;
	protected List<SelectItem> listCity;
	protected BigDecimal idCity;
	protected Map<BigDecimal, CrmCity> mapCity;

	protected List<SelectItem> listBranch;
	protected String salesOff;

	protected List<SelectItem> listMaritalStatus;
	protected BigDecimal idMaritalStatus;
	protected Map<BigDecimal, CrmCountry> mapMaritalStatus;

	protected List<SelectItem> listMembershipType;
	protected BigDecimal idMembershipType;
	protected Map<BigDecimal, CrmCountry> mapMembershipType;

	protected List<SelectItem> listEps;
	protected BigDecimal idEps;

	protected List<SelectItem> listKin;
	protected BigDecimal idKin;
	protected Map<BigDecimal, CrmCountry> mapKin;

	protected List<SelectItem> listOccupation;
	protected Map<BigDecimal, CrmOccupation> mapOccupation;
	protected BigDecimal idOccupation;

	protected List<SelectItem> listRollbackCheck;
	protected BigDecimal idRollbackCheck;

	protected List<SelectItem> listGroup;
	protected BigDecimal idGroup;

	protected List<SelectItem> listMarketingActivity;
	protected Integer idMarketingActivity;

	protected List<SelectItem> listJob;
	protected List<String> listStatus;

	protected Integer numPhone;
	protected Integer numCell;

	protected String typeHousing;
	protected String neighborhood;

	protected Date today;
	protected Date todayMax;

	protected List<SelectItem> listDoctor;
	protected List<SelectItem> listProcedure;
	protected List<SelectItem> listStates;
	protected List<String> listDoctorSelected;

	protected BigDecimal idDoctor;
	protected BigDecimal idProcedure;

	protected Date startDate;
	protected Date endDate;

	protected List<SelectItem> listDoctorByUser;
	protected List<SelectItem> listDoctorByBranch;

	protected String DEFAULT_LABEL_ALL = FacesUtil
			.getMessage(Constant.DEFAULT_LABEL_ALL);

	protected String nameReport;

	public BaseBacking() {
		today = new Date();
	}

	public List<CrmPatient> getListPatient() {
		return listPatient;
	}

	public void setListPatient(List<CrmPatient> listPatient) {
		this.listPatient = listPatient;
	}

	public PatientDataModel getPatientModel() {
		return patientModel;
	}

	public void setPatientModel(PatientDataModel patientModel) {
		this.patientModel = patientModel;
	}

	public CrmPatient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(CrmPatient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	public CrmPatient getSelectedPatientTemp() {
		return selectedPatientTemp;
	}

	public void setSelectedPatientTemp(CrmPatient selectedPatientTemp) {
		this.selectedPatientTemp = selectedPatientTemp;
	}

	public String getDocPatient() {
		return docPatient;
	}

	public void setDocPatient(String docPatient) {
		this.docPatient = docPatient;
	}

	public String getNamePatient() {
		return namePatient;
	}

	public void setNamePatient(String namePatient) {
		this.namePatient = namePatient;
	}

	public String getTelPatient() {
		return telPatient;
	}

	public void setTelPatient(String telPatient) {
		this.telPatient = telPatient;
	}

	public String getEmailPatient() {
		return emailPatient;
	}

	public void setEmailPatient(String emailPatient) {
		this.emailPatient = emailPatient;
	}

	public int getOptionSearchPatient() {
		return optionSearchPatient;
	}

	public void setOptionSearchPatient(int optionSearchPatient) {
		this.optionSearchPatient = optionSearchPatient;
	}

	public void setDisabledAddPatient(boolean disabledAddPatient) {
		this.disabledAddPatient = disabledAddPatient;
	}

	public List<SelectItem> getListDoctor() {
		return listDoctor;
	}

	public void setListDoctor(List<SelectItem> listDoctor) {
		this.listDoctor = listDoctor;
	}

	public List<SelectItem> getListProcedure() {
		return listProcedure;
	}

	public void setListProcedure(List<SelectItem> listProcedure) {
		this.listProcedure = listProcedure;
	}

	public List<SelectItem> getListStates() {
		return listStates;
	}

	public void setListStates(List<SelectItem> listStates) {
		this.listStates = listStates;
	}

	public List<String> getListDoctorSelected() {
		return listDoctorSelected;
	}

	public void setListDoctorSelected(List<String> listDoctorSelected) {
		this.listDoctorSelected = listDoctorSelected;
	}

	public BigDecimal getIdDoctor() {
		return idDoctor;
	}

	public void setIdDoctor(BigDecimal idDoctor) {
		this.idDoctor = idDoctor;
	}

	public BigDecimal getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(BigDecimal idProcedure) {
		this.idProcedure = idProcedure;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNameReport() {
		return nameReport;
	}

	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}

	public List<SelectItem> getListDoctorByUser() {
		if (listDoctorByUser == null) {
			listDoctorByUser = new LinkedList<SelectItem>();
			for (CrmDoctor row : tablesService.getListDoctorByBranch()) {
				listDoctorByUser
						.add(new SelectItem(row.getId(), row.getNames()));
			}
		}
		return listDoctorByUser;
	}

	public void setListDoctorByUser(List<SelectItem> listDoctorByUser) {
		this.listDoctorByUser = listDoctorByUser;
	}

	public List<SelectItem> getListDoctorByBranch() {
		return listDoctorByBranch;
	}

	public void setListDoctorByBranch(List<SelectItem> listDoctorByBranch) {
		this.listDoctorByBranch = listDoctorByBranch;
	}

	public void searchPatientAction() {
		if ((optionSearchPatient == 1 && FacesUtil
				.isEmptyOrBlank(this.docPatient))
				|| (optionSearchPatient == 2 && FacesUtil
						.isEmptyOrBlank(this.namePatient))
				|| (optionSearchPatient == 3 && FacesUtil
						.isEmptyOrBlank(this.telPatient))
				|| (optionSearchPatient == 4 && FacesUtil
						.isEmptyOrBlank(this.emailPatient))) {

			this.listPatient = new ArrayList<CrmPatient>();
			this.patientModel = new PatientDataModel(listPatient);

		} else {
			listPatient = new ArrayList<CrmPatient>();
			List<WSBean> result = null;

			if (optionSearchPatient == 1) {
				listPatient = processService.getListPatientByField("DOC",
						this.docPatient);

				if (listPatient.size() == 0) {
					try {
						SAPEnvironment sap = FacesUtil
								.findBean("SAPEnvironment");
						CrmProfile profile = mapProfile.get(idProfile);
						result = CustomerExecute.findByDoc(
								sap.getUrlCustomer2(), sap.getUsername(),
								sap.getPassword(), profile.getSociety(),
								this.docPatient);

						if (result.size() > 0) {
							String message = FacesUtil
									.getMessage("pat_msg_exists_sap_2");
							FacesUtil.addWarn(message);
						}

						// for (WSBean row : result) {
						// CrmPatient patient = new CrmPatient();
						// patient.setCodeSap(row.getCode());
						// patient.setNames(row.getNames());
						// listPatient.add(patient);
						// }
					} catch (Exception ex) {

					}
				}
			} else if (optionSearchPatient == 2) {
				listPatient = processService.getListPatientByField("NAMES",
						this.namePatient.toUpperCase());
			} else if (optionSearchPatient == 3) {
				listPatient = processService.getListPatientByField("PHONE",
						this.telPatient);
			} else {
				listPatient = processService.getListPatientByField("EMAIL",
						this.emailPatient);
			}

			patientModel = new PatientDataModel(listPatient);

			if (listPatient.size() > 0) {
				selectedPatient = listPatient.get(0);
				selectedPatientTemp = listPatient.get(0);
			}
		}
	}

	public void searchPatientNewAction() {
		if (FacesUtil.isEmptyOrBlank(this.docPatient)) {
			this.listPatient = new ArrayList<CrmPatient>();
			this.patientModel = new PatientDataModel(listPatient);
		} else {
			listPatient = new ArrayList<CrmPatient>();
			List<WSBean> result = null;
			listPatient = processService.getListPatientByAll(this.docPatient);
			if (listPatient.size() == 0) {
				try {
					SAPEnvironment sap = FacesUtil.findBean("SAPEnvironment");
					CrmProfile profile = mapProfile.get(idProfile);
					result = CustomerExecute.findByDoc(sap.getUrlCustomer2(),
							sap.getUsername(), sap.getPassword(),
							profile.getSociety(), this.docPatient);

					if (result.size() > 0) {
						String message = FacesUtil
								.getMessage("pat_msg_exists_sap_2");
						FacesUtil.addWarn(message);
					}
				} catch (Exception ex) {

				}
			}

			patientModel = new PatientDataModel(listPatient);

			if (listPatient.size() > 0) {
				selectedPatient = listPatient.get(0);
				selectedPatientTemp = listPatient.get(0);
			}
		}
	}

	public void patientHandleClose(CloseEvent event) {
		// selectedPatient = null;
	}

	public List<SelectItem> getListAllProfile() {
		if (listAllProfile == null) {
			List<CrmProfile> list = tablesService.getListProfile();
			listAllProfile = new LinkedList<SelectItem>();
			mapAllProfile = new HashMap<BigDecimal, CrmProfile>();
			for (CrmProfile row : list) {
				listAllProfile.add(new SelectItem(row.getId(), row.getCode()));
				mapAllProfile.put(row.getId(), row);
			}

			if (listAllProfile.size() > 0) {
				idProfile = (BigDecimal) listAllProfile.get(0).getValue();
			}
		}
		return listAllProfile;
	}

	public void setListAllProfile(List<SelectItem> listAllProfile) {
		this.listAllProfile = listAllProfile;
	}

	public Map<BigDecimal, CrmProfile> getMapAllProfile() {
		return mapAllProfile;
	}

	public void setMapAllProfile(Map<BigDecimal, CrmProfile> mapAllProfile) {
		this.mapAllProfile = mapAllProfile;
	}

	public List<SelectItem> getListProfile() {
		if (listProfile == null) {
			listProfile = new LinkedList<SelectItem>();
			mapProfile = new HashMap<BigDecimal, CrmProfile>();
			for (CrmProfile row : FacesUtil.getCurrentUserData()
					.getListProfile()) {
				listProfile.add(new SelectItem(row.getId(), row.getCode()));
				mapProfile.put(row.getId(), row);
			}

			if (listProfile.size() > 0) {
				idProfile = (BigDecimal) listProfile.get(0).getValue();
			}
		}
		return listProfile;
	}

	public void setListProfile(List<SelectItem> listProfile) {
		this.listProfile = listProfile;
	}

	public BigDecimal getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(BigDecimal idProfile) {
		this.idProfile = idProfile;
	}

	public Map<BigDecimal, CrmProfile> getMapProfile() {
		return mapProfile;
	}

	public void setMapProfile(Map<BigDecimal, CrmProfile> mapProfile) {
		this.mapProfile = mapProfile;
	}

	public List<SelectItem> getListWSDoctor() {
		if (listWSDoctor == null) {
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			try {
				SAPEnvironment sap = FacesUtil.findBean("SAPEnvironment");

				List<WSBean> result = CustomListsExecute.getDoctors(
						sap.getUrlWebList(), sap.getUsername(),
						sap.getPassword());

				listWSDoctor = new ArrayList<SelectItem>();
				mapWSDoctor = new LinkedHashMap<String, String>();
				listWSDoctor.add(new SelectItem(Constant.DEFAULT_VALUE_STRING,
						label));
				for (WSBean row : result) {
					mapWSDoctor.put(row.getCode(), row.getNames());
					listWSDoctor.add(new SelectItem(row.getCode(), row
							.getNames()));
				}
			} catch (Exception ex) {
				listWSDoctor = new ArrayList<SelectItem>();
				listWSDoctor.add(new SelectItem(Constant.DEFAULT_VALUE_STRING,
						label));
			}
		}
		return listWSDoctor;
	}

	public void setListWSDoctor(List<SelectItem> listWSDoctor) {
		this.listWSDoctor = listWSDoctor;
	}

	public Map<String, String> getMapWSDoctor() {
		return mapWSDoctor;
	}

	public void setMapWSDoctor(Map<String, String> mapWSDoctor) {
		this.mapWSDoctor = mapWSDoctor;
	}

	public String getSelectedWSDoctor() {
		return selectedWSDoctor;
	}

	public void setSelectedWSDoctor(String selectedWSDoctor) {
		this.selectedWSDoctor = selectedWSDoctor;
	}

	public TablesBo getTablesService() {
		return tablesService;
	}

	public void setTablesService(TablesBo tablesService) {
		this.tablesService = tablesService;
	}

	public ProcessBo getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessBo processService) {
		this.processService = processService;
	}

	public boolean isDisabledAddPatient() {
		if ((listPatient == null) || (listPatient.size() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDisabledSelectedPatient() {
		if ((selectedPatient == null)
				|| (FacesUtil.isEmptyOrBlank(selectedPatient.getFirstnames()))) {
			return true;
		}
		return false;
	}

	public List<SelectItem> getListCrmBranch() {
		if (listCrmBranch == null) {
			listCrmBranch = new LinkedList<SelectItem>();
			mapCrmBranch = new LinkedHashMap<BigDecimal, CrmBranch>();
			// String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			// listCrmBranch.add(new SelectItem(Constant.DEFAULT_VALUE, label));
			for (CrmBranch row : tablesService.getListBranchActive1000()) {
				mapCrmBranch.put(row.getId(), row);
				listCrmBranch.add(new SelectItem(row.getId(), row.getName()
						+ " (" + row.getSociety() + ")"));
			}
		}
		return listCrmBranch;
	}

	public void setListCrmBranch(List<SelectItem> listCrmBranch) {
		this.listCrmBranch = listCrmBranch;
	}

	public List<SelectItem> getListCrmBranchByUser() {
		if (listCrmBranchByUser == null) {
			listCrmBranchByUser = new LinkedList<SelectItem>();
			mapCrmBranchByUser = new LinkedHashMap<BigDecimal, CrmBranch>();
			List<CrmBranch> list = null;
			if (FacesUtil.getCurrentUser().getCrmBusinessGroup().getId() == 0) {
				list = tablesService.getListBranchActive1000();
			} else {
				list = tablesService.getListBranchSelected();
			}
			for (CrmBranch row : list) {
				mapCrmBranchByUser.put(row.getId(), row);
				listCrmBranchByUser.add(new SelectItem(row.getId(), row
						.getName() + " (" + row.getSociety() + ")"));
			}
		}
		return listCrmBranchByUser;
	}

	public void setListCrmBranchByUser(List<SelectItem> listCrmBranchByUser) {
		this.listCrmBranchByUser = listCrmBranchByUser;
	}

	public Map<BigDecimal, CrmBranch> getMapCrmBranchByUser() {
		return mapCrmBranchByUser;
	}

	public void setMapCrmBranchByUser(
			Map<BigDecimal, CrmBranch> mapCrmBranchByUser) {
		this.mapCrmBranchByUser = mapCrmBranchByUser;
	}

	public List<CrmBranch> getListBranchObject() {
		if (listBranchObject == null) {
			listBranchObject = tablesService.getListBranchSelected();
		}

		return listBranchObject;
	}

	public BranchDataModel getBranchDataModel() {
		if (listBranchObject == null) {
			listBranchObject = tablesService.getListBranchSelected();
			branchDataModel = new BranchDataModel(listBranchObject);
		}
		return branchDataModel;
	}

	public void setBranchDataModel(BranchDataModel branchDataModel) {
		this.branchDataModel = branchDataModel;
	}

	public CrmBranch[] getSelectedsBranchObject() {
		return selectedsBranchObject;
	}

	public void setSelectedsBranchObject(CrmBranch[] selectedsBranchObject) {
		this.selectedsBranchObject = selectedsBranchObject;
	}

	public void setListBranchObject(List<CrmBranch> listBranchObject) {
		this.listBranchObject = listBranchObject;
	}

	public Map<BigDecimal, CrmBranch> getMapCrmBranch() {
		return mapCrmBranch;
	}

	public void setMapCrmBranch(Map<BigDecimal, CrmBranch> mapCrmBranch) {
		this.mapCrmBranch = mapCrmBranch;
	}

	public BigDecimal getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(BigDecimal idBranch) {
		this.idBranch = idBranch;
	}

	public List<String> getListBranchSelected() {
		return listBranchSelected;
	}

	public void setListBranchSelected(List<String> listBranchSelected) {
		this.listBranchSelected = listBranchSelected;
	}

	public List<SelectItem> getListCrmSpeciality() {
		if (listCrmSpeciality == null) {
			listCrmSpeciality = new LinkedList<SelectItem>();
			mapCrmSpeciality = new LinkedHashMap<BigDecimal, CrmSpeciality>();
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listCrmSpeciality
					.add(new SelectItem(Constant.DEFAULT_VALUE, label));
			for (CrmSpeciality row : tablesService.getListSpecialityActive()) {
				mapCrmSpeciality.put(row.getId(), row);
				listCrmSpeciality.add(new SelectItem(row.getId(), row
						.getDescription()));
			}
		}
		return listCrmSpeciality;
	}

	public void setListCrmSpeciality(List<SelectItem> listCrmSpeciality) {
		this.listCrmSpeciality = listCrmSpeciality;
	}

	public Map<BigDecimal, CrmSpeciality> getMapCrmSpeciality() {
		return mapCrmSpeciality;
	}

	public void setMapCrmSpeciality(
			Map<BigDecimal, CrmSpeciality> mapCrmSpeciality) {
		this.mapCrmSpeciality = mapCrmSpeciality;
	}

	public BigDecimal getIdSpeciality() {
		return idSpeciality;
	}

	public void setIdSpeciality(BigDecimal idSpeciality) {
		this.idSpeciality = idSpeciality;
	}

	public List<SelectItem> getListCrmBusinessGroup() {
		if (listCrmBusinessGroup == null) {
			listCrmBusinessGroup = new LinkedList<SelectItem>();
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listCrmBusinessGroup.add(new SelectItem(Constant.DEFAULT_VALUE,
					label));
			label = FacesUtil.getMessage(Constant.ALL_LABEL);
			listCrmBusinessGroup.add(new SelectItem(new BigDecimal(0), label));
			for (CrmBusinessGroup row : tablesService
					.getListBusinessGroupActive()) {
				listCrmBusinessGroup.add(new SelectItem(row.getId(), row
						.getName()));
			}
		}
		return listCrmBusinessGroup;
	}

	public void setListCrmBusinessGroup(List<SelectItem> listCrmBusinessGroup) {
		this.listCrmBusinessGroup = listCrmBusinessGroup;
	}

	public Integer getIdBusinessGroup() {
		return idBusinessGroup;
	}

	public void setIdBusinessGroup(Integer idBusinessGroup) {
		this.idBusinessGroup = idBusinessGroup;
	}

	public List<SelectItem> getListWSGroupSellers() {
		if (listWSGroupSellers == null) {
			List<WSBean> result = FacesUtil.getCurrentUserData()
					.getListWSGroupSellers();

			listWSGroupSellers = new ArrayList<SelectItem>();
			mapWSGroupSellers = new LinkedHashMap<String, String>();

			for (WSBean row : result) {
				mapWSGroupSellers.put(row.getCode(), row.getNames());
			}

			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listWSGroupSellers.add(new SelectItem(
					Constant.DEFAULT_VALUE_STRING, label));

			for (Map.Entry<String, String> entry : mapWSGroupSellers.entrySet()) {
				listWSGroupSellers.add(new SelectItem(entry.getKey(), entry
						.getKey() + " - " + entry.getValue()));
			}

		}
		return listWSGroupSellers;
	}

	public void setListWSGroupSellers(List<SelectItem> listWSGroupSellers) {
		this.listWSGroupSellers = listWSGroupSellers;
	}

	public Map<String, String> getMapWSGroupSellers() {
		return mapWSGroupSellers;
	}

	public void setMapWSGroupSellers(Map<String, String> mapWSGroupSellers) {
		this.mapWSGroupSellers = mapWSGroupSellers;
	}

	public String getSelectedWSGroupSellers() {
		return selectedWSGroupSellers;
	}

	public void setSelectedWSGroupSellers(String selectedWSGroupSellers) {
		this.selectedWSGroupSellers = selectedWSGroupSellers;
	}

	public List<SelectItem> getListCountry() {
		if (listCountry == null) {
			listCountry = new LinkedList<SelectItem>();
			mapCountry = new HashMap<BigDecimal, CrmCountry>();
			for (CrmCountry row : tablesService.getListCountry()) {
				listCountry.add(new SelectItem(row.getId(), row.getName()));
				mapCountry.put(row.getId(), row);
			}

			if (listCountry.size() > 0) {
				idCountry = (BigDecimal) listCountry.get(0).getValue();
				listAllRegion = tablesService.getListRegion();
				listAllCity = tablesService.getListCity();
				handleCountryChange();
			}
		}
		return listCountry;
	}

	public void setListCountry(List<SelectItem> listCountry) {
		this.listCountry = listCountry;
	}

	public BigDecimal getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(BigDecimal idCountry) {
		this.idCountry = idCountry;
	}

	public Map<BigDecimal, CrmCountry> getMapCountry() {
		return mapCountry;
	}

	public void setMapCountry(Map<BigDecimal, CrmCountry> mapCountry) {
		this.mapCountry = mapCountry;
	}

	public List<CrmRegion> getListAllRegion() {
		return listAllRegion;
	}

	public void setListAllRegion(List<CrmRegion> listAllRegion) {
		this.listAllRegion = listAllRegion;
	}

	public List<SelectItem> getListRegion() {
		return listRegion;
	}

	public void setListRegion(List<SelectItem> listRegion) {
		this.listRegion = listRegion;
	}

	public BigDecimal getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(BigDecimal idRegion) {
		this.idRegion = idRegion;
	}

	public Map<BigDecimal, CrmRegion> getMapRegion() {
		return mapRegion;
	}

	public void setMapRegion(Map<BigDecimal, CrmRegion> mapRegion) {
		this.mapRegion = mapRegion;
	}

	public List<CrmCity> getListAllCity() {
		return listAllCity;
	}

	public void setListAllCity(List<CrmCity> listAllCity) {
		this.listAllCity = listAllCity;
	}

	public List<SelectItem> getListCity() {
		return listCity;
	}

	public void setListCity(List<SelectItem> listCity) {
		this.listCity = listCity;
	}

	public BigDecimal getIdCity() {
		return idCity;
	}

	public void setIdCity(BigDecimal idCity) {
		this.idCity = idCity;
	}

	public Map<BigDecimal, CrmCity> getMapCity() {
		return mapCity;
	}

	public void setMapCity(Map<BigDecimal, CrmCity> mapCity) {
		this.mapCity = mapCity;
	}

	public List<SelectItem> getListBranch() {
		if (listBranch == null) {
			listBranch = new LinkedList<SelectItem>();
			for (CrmBranch row : FacesUtil.getCurrentUserData().getListBranch()) {
				listBranch.add(new SelectItem(row.getCode(), row.getName()
						+ " (" + row.getSociety() + ")"));
			}
		}
		return listBranch;
	}

	public void setListBranch(List<SelectItem> listBranch) {
		this.listBranch = listBranch;
	}

	public List<SelectItem> getListMaritalStatus() {
		if (listMaritalStatus == null) {
			listMaritalStatus = new LinkedList<SelectItem>();
			for (CrmDomain row : tablesService.getListDomain("ESTADO_CIVIL")) {
				listMaritalStatus.add(new SelectItem(row.getId(), row
						.getItemValue()));
			}
		}
		return listMaritalStatus;
	}

	public void setListMaritalStatus(List<SelectItem> listMaritalStatus) {
		this.listMaritalStatus = listMaritalStatus;
	}

	public BigDecimal getIdMaritalStatus() {
		return idMaritalStatus;
	}

	public void setIdMaritalStatus(BigDecimal idMaritalStatus) {
		this.idMaritalStatus = idMaritalStatus;
	}

	public Map<BigDecimal, CrmCountry> getMapMaritalStatus() {
		return mapMaritalStatus;
	}

	public void setMapMaritalStatus(Map<BigDecimal, CrmCountry> mapMaritalStatus) {
		this.mapMaritalStatus = mapMaritalStatus;
	}

	public List<SelectItem> getListMembershipType() {
		if (listMembershipType == null) {
			listMembershipType = new LinkedList<SelectItem>();
			for (CrmDomain row : tablesService.getListDomain("TIPO_AFILIACION")) {
				listMembershipType.add(new SelectItem(row.getId(), row
						.getItemValue()));
			}
		}
		return listMembershipType;
	}

	public void setListMembershipType(List<SelectItem> listMembershipType) {
		this.listMembershipType = listMembershipType;
	}

	public BigDecimal getIdMembershipType() {
		return idMembershipType;
	}

	public void setIdMembershipType(BigDecimal idMembershipType) {
		this.idMembershipType = idMembershipType;
	}

	public Map<BigDecimal, CrmCountry> getMapMembershipType() {
		return mapMembershipType;
	}

	public void setMapMembershipType(
			Map<BigDecimal, CrmCountry> mapMembershipType) {
		this.mapMembershipType = mapMembershipType;
	}

	public List<SelectItem> getListEps() {
		if (listEps == null) {
			listEps = new LinkedList<SelectItem>();
			for (CrmEps row : tablesService.getListEPSActive()) {
				listEps.add(new SelectItem(row.getId(), row.getName()));
			}
		}
		return listEps;
	}

	public void setListEps(List<SelectItem> listEps) {
		this.listEps = listEps;
	}

	public BigDecimal getIdEps() {
		return idEps;
	}

	public void setIdEps(BigDecimal idEps) {
		this.idEps = idEps;
	}

	public List<SelectItem> getListKin() {
		if (listKin == null) {
			listKin = new LinkedList<SelectItem>();
			String labelDefaultValue = FacesUtil
					.getMessage(Constant.DEFAULT_LABEL);
			listKin.add(new SelectItem(Constant.DEFAULT_VALUE,
					labelDefaultValue));
			for (CrmDomain row : tablesService.getListDomain("PARENTESCO")) {
				listKin.add(new SelectItem(row.getId(), row.getItemValue()));
			}
		}
		return listKin;
	}

	public void setListKin(List<SelectItem> listKin) {
		this.listKin = listKin;
	}

	public BigDecimal getIdKin() {
		return idKin;
	}

	public void setIdKin(BigDecimal idKin) {
		this.idKin = idKin;
	}

	public Map<BigDecimal, CrmCountry> getMapKin() {
		return mapKin;
	}

	public void setMapKin(Map<BigDecimal, CrmCountry> mapKin) {
		this.mapKin = mapKin;
	}

	public String getSalesOff() {
		return salesOff;
	}

	public void setSalesOff(String salesOff) {
		this.salesOff = salesOff;
	}

	public String getRolePrincipal() {
		return FacesUtil.getCurrentUserData().getRolePrincipal();
	}

	public boolean isOpenAppointment() {
		return FacesUtil.getCurrentUserData().isOpenAppointment();
	}

	public boolean isPrintFormula() {
		return FacesUtil.getCurrentUserData().isPrintFormula();
	}

	public boolean isPrintHistorial() {
		return FacesUtil.getCurrentUserData().isPrintHistorial();
	}

	public boolean isPrintHistorialOdo() {
		return FacesUtil.getCurrentUserData().isPrintHistorialOdo();
	}

	public boolean isViewHistorial() {
		return FacesUtil.getCurrentUserData().isViewHistorial();
	}

	public boolean isViewHistorialOdo() {
		return FacesUtil.getCurrentUserData().isViewHistorialOdo();
	}

	public List<SelectItem> getListOccupation() {
		if (listOccupation == null) {
			listOccupation = new LinkedList<SelectItem>();
			mapOccupation = new HashMap<BigDecimal, CrmOccupation>();
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listOccupation.add(new SelectItem(null, label));
			for (CrmOccupation row : tablesService
					.getListOccupationActive(Constant.OCCUPATION_TYPE_OCCUPATION)) {
				mapOccupation.put(row.getId(), row);
				listOccupation.add(new SelectItem(row.getId(), row.getName()));
			}
		}
		return listOccupation;
	}

	public void setListOccupation(List<SelectItem> listOccupation) {
		this.listOccupation = listOccupation;
	}

	public List<SelectItem> getListJob() {
		if (listJob == null) {
			listJob = new LinkedList<SelectItem>();
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listJob.add(new SelectItem(null, label));
			for (CrmOccupation row : tablesService
					.getListOccupationActive(Constant.OCCUPATION_TYPE_JOB)) {
				listJob.add(new SelectItem(row.getId(), row.getName()));
			}
		}
		return listJob;
	}

	public void setListJob(List<SelectItem> listJob) {
		this.listJob = listJob;
	}

	public List<String> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<String> listStatus) {
		this.listStatus = listStatus;
	}

	public Map<BigDecimal, CrmOccupation> getMapOccupation() {
		return mapOccupation;
	}

	public void setMapOccupation(Map<BigDecimal, CrmOccupation> mapOccupation) {
		this.mapOccupation = mapOccupation;
	}

	public BigDecimal getIdOccupation() {
		return idOccupation;
	}

	public void setIdOccupation(BigDecimal idOccupation) {
		this.idOccupation = idOccupation;
	}

	public List<SelectItem> getListRollbackCheck() {
		if (listRollbackCheck == null) {
			listRollbackCheck = new LinkedList<SelectItem>();
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			listRollbackCheck.add(new SelectItem(null, label));
			for (CrmDomain row : tablesService
					.getListDomain("REVERSION_CHECKIN")) {
				listRollbackCheck.add(new SelectItem(row.getId(), row
						.getItemValue()));
			}
		}
		return listRollbackCheck;
	}

	public void setListRollbackCheck(List<SelectItem> listRollbackCheck) {
		this.listRollbackCheck = listRollbackCheck;
	}

	public BigDecimal getIdRollbackCheck() {
		return idRollbackCheck;
	}

	public void setIdRollbackCheck(BigDecimal idRollbackCheck) {
		this.idRollbackCheck = idRollbackCheck;
	}

	public List<SelectItem> getListGroup() {
		listGroup = new LinkedList<SelectItem>();
		String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
		listGroup.add(new SelectItem(null, label));
		for (CrmPatient row : tablesService.getListGroup()) {
			listGroup.add(new SelectItem(row.getId(), row.getFirstnames()));
		}
		return listGroup;
	}

	public void setListGroup(List<SelectItem> listGroup) {
		this.listGroup = listGroup;
	}

	public BigDecimal getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(BigDecimal idGroup) {
		this.idGroup = idGroup;
	}

	public List<SelectItem> getListMarketingActivity() {
		listMarketingActivity = new LinkedList<SelectItem>();
		String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
		listMarketingActivity.add(new SelectItem(null, label));
		for (CrmMarketingActivity row : tablesService
				.getListMarketingActivityActive()) {
			listMarketingActivity
					.add(new SelectItem(row.getId(), row.getName()));
		}
		return listMarketingActivity;
	}

	public void setListMarketingActivity(List<SelectItem> listMarketingActivity) {
		this.listMarketingActivity = listMarketingActivity;
	}

	public Integer getIdMarketingActivity() {
		return idMarketingActivity;
	}

	public void setIdMarketingActivity(Integer idMarketingActivity) {
		this.idMarketingActivity = idMarketingActivity;
	}

	public Integer getNumPhone() {
		return numPhone;
	}

	public void setNumPhone(Integer numPhone) {
		this.numPhone = numPhone;
	}

	public Integer getNumCell() {
		return numCell;
	}

	public void setNumCell(Integer numCell) {
		this.numCell = numCell;
	}

	public String getTypeHousing() {
		return typeHousing;
	}

	public void setTypeHousing(String typeHousing) {
		this.typeHousing = typeHousing;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Date getTodayMax() {
		return todayMax;
	}

	public void setTodayMax(Date todayMax) {
		this.todayMax = todayMax;
	}

	public void newPatientAction(ActionEvent event) {
		optionSearchPatient = 1;
		selectedPatient = new CrmPatient();
		List<CrmPatient> listPatient = new LinkedList<CrmPatient>();
		patientModel = new PatientDataModel(listPatient);
		docPatient = null;
		namePatient = null;
		telPatient = null;
		emailPatient = null;
	}

	public void handleCountryChange() {
		if (idCountry != null) {
			CrmCountry crmCountry = mapCountry.get(idCountry);
			listRegion = new LinkedList<SelectItem>();
			mapRegion = new HashMap<BigDecimal, CrmRegion>();

			for (CrmRegion row : this.listAllRegion) {
				if (row.getCrmCountry().getId().intValue() == crmCountry
						.getId().intValue()) {
					listRegion.add(new SelectItem(row.getId(), row.getName()));
					mapRegion.put(row.getId(), row);
				}
			}

			if (listRegion.size() > 0) {
				idRegion = (BigDecimal) listRegion.get(0).getValue();
				handleRegionChange();
			} else {
				idRegion = null;
				idCity = null;
				listRegion = new LinkedList<SelectItem>();
				listCity = new LinkedList<SelectItem>();
			}

		} else {
			idRegion = null;
			idCity = null;
			listRegion = new LinkedList<SelectItem>();
			listCity = new LinkedList<SelectItem>();
		}
	}

	public void handleRegionChange() {
		CrmRegion crmRegion = mapRegion.get(idRegion);
		listCity = new LinkedList<SelectItem>();
		mapCity = new HashMap<BigDecimal, CrmCity>();
		for (CrmCity row : this.listAllCity) {
			if (row.getCrmRegion().getId().intValue() == crmRegion.getId()
					.intValue()) {
				listCity.add(new SelectItem(row.getId(), row.getName()));
				mapCity.put(row.getId(), row);
			}
		}

		if (listRegion.size() > 0) {
			idCity = (BigDecimal) listCity.get(0).getValue();
		} else {
			idCity = null;
		}
	}

	protected int calculateAge(Date bornDate) {
		int age = 0;
		if (bornDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(bornDate);

			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(new Date());

			age = currentDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

			if ((calendar.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
					|| (calendar.get(Calendar.MONTH) == currentDate
							.get(Calendar.MONTH) && calendar
							.get(Calendar.DAY_OF_MONTH) > currentDate
							.get(Calendar.DAY_OF_MONTH))) {
				age--;
			}
		}
		return age;
	}

	public void addPatient(ActionEvent event) {
		boolean validate = true;
		RequestContext context = RequestContext.getCurrentInstance();
		if (selectedPatientTemp.getId() == null) {
			validate = false;
		} else {
			selectedPatient = selectedPatientTemp;
		}
		context.addCallbackParam("validate", validate);
	}

	protected String getStringSelecteds(List<String> listCrmBranch) {
		String result = "";
		for (String row : listCrmBranch) {
			result = result + row + ",";
		}

		if (result.trim().length() > 0) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	public void addBranchAction(boolean exception) {
		boolean validate = true;
		RequestContext context = RequestContext.getCurrentInstance();

		listDoctor = new LinkedList<SelectItem>();
		listDoctor
				.add(new SelectItem(Constant.DEFAULT_VALUE, DEFAULT_LABEL_ALL));

		listProcedure = new LinkedList<SelectItem>();
		idDoctor = Constant.DEFAULT_VALUE;
		idProcedure = Constant.DEFAULT_VALUE;

		Map<BigDecimal, String> mapDoctor = new HashMap<BigDecimal, String>();
		Map<BigDecimal, String> mapProcedure = new HashMap<BigDecimal, String>();

		if (selectedsBranchObject != null && selectedsBranchObject.length > 0) {
			for (CrmBranch crmranch : selectedsBranchObject) {

				for (CrmDoctor row : tablesService
						.getListDoctorByBranch(crmranch.getId())) {
					mapDoctor.put(row.getId(), row.getNames());
				}

				for (VwProcedure row : tablesService
						.getListVwProcedureByBranch(crmranch.getId())) {
					mapProcedure.put(row.getId(), row.getNameProcedure()
							+ " - " + row.getNameProcedureDetail());
				}
			}

			for (Map.Entry<BigDecimal, String> entry : mapDoctor.entrySet()) {
				listDoctor
						.add(new SelectItem(entry.getKey(), entry.getValue()));
			}

			for (Map.Entry<BigDecimal, String> entry : mapProcedure.entrySet()) {
				listProcedure.add(new SelectItem(entry.getKey(), entry
						.getValue()));
			}
		} else {
			if (exception) {
				validate = false;
				String message = FacesUtil.getMessage("app_no_branch");
				FacesUtil.addInfo(message);
			}
		}
		context.addCallbackParam("validate", validate);
	}

	public void addBranchNewAction() {
		listDoctor = new LinkedList<SelectItem>();
		listProcedure = new LinkedList<SelectItem>();
		idDoctor = Constant.DEFAULT_VALUE;
		idProcedure = Constant.DEFAULT_VALUE;

		if (listBranchSelected != null && !listBranchSelected.isEmpty()) {
			Object[] objectList = listBranchSelected.toArray();
			String[] stringArray = Arrays.copyOf(objectList, objectList.length,
					String[].class);

			List<CrmProcedureDetail> listCrmProcedureDetails = processService
					.getDistinctProcedureDetailByBranch(stringArray);
			for (CrmProcedureDetail row : listCrmProcedureDetails) {
				listProcedure.add(new SelectItem(row.getId(), row.getName()));
			}

			List<CrmDoctor> listCrmdoctor = processService
					.getDistinctDoctorByBranch(stringArray);
			for (CrmDoctor row : listCrmdoctor) {
				listDoctor.add(new SelectItem(row.getId(), row.getNames()));
			}
		}
	}

	public void addBranchNewToogle(ToggleSelectEvent event) {
		SelectCheckboxMenu object = (SelectCheckboxMenu) event.getSource();
		String[] submit = (String[]) object.getSubmittedValue();
		listBranchSelected = new LinkedList<String>();
		listDoctor = new LinkedList<SelectItem>();

		listProcedure = new LinkedList<SelectItem>();
		idDoctor = Constant.DEFAULT_VALUE;
		idProcedure = Constant.DEFAULT_VALUE;

		if (submit != null && submit.length > 0) {

			List<CrmProcedureDetail> listCrmProcedureDetails = processService
					.getDistinctProcedureDetailByBranch(submit);
			for (CrmProcedureDetail row : listCrmProcedureDetails) {
				listProcedure.add(new SelectItem(row.getId(), row.getName()));
			}

			List<CrmDoctor> listCrmdoctor = processService
					.getDistinctDoctorByBranch(submit);
			for (CrmDoctor row : listCrmdoctor) {
				listDoctor.add(new SelectItem(row.getId(), row.getNames()));
			}
		}
	}

	public int getRenderedRecord() {
		int result = 0;
		if (selectedPatient == null) {
			result = 0;
		} else if (selectedPatient.getId() == null) {
			result = 0;
		} else {
			if (selectedPatient.getAge() <= 5) {
				result = 1;
			} else {
				if (selectedPatient.getGender() != null
						&& selectedPatient.getGender().equals("W")) {
					result = 2;
				} else {
					result = 3;
				}
			}
		}
		return result;
	}

	public void printHistoryAction() {
		try {
			GenerateFormulaPDF.historyPDF(selectedPatient.getId(),
					Constant.MEDICAL_HISTORY_TYPE, getRenderedRecord(), 1);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printHistoryOdoAction() {
		try {
			GenerateFormulaPDF.historyPDF(selectedPatient.getId(),
					Constant.ODONTOLOGY_HISTORY_TYPE, getRenderedRecord(), 0);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cleanPatientFields() {
		this.docPatient = null;
		this.namePatient = null;
		this.telPatient = null;
		this.emailPatient = null;
	}

	public void cleanMemberShip() {
		selectedPatient.setIdMemberShip(null);
	}

	public boolean isContactPatient() {
		boolean result = this.selectedPatient == null ? true : FacesUtil
				.isEmptyOrBlank(selectedPatient.getDoc()) ? true
				: this.selectedPatient.getDoc().equalsIgnoreCase(
						this.selectedPatient.getCodeSap());
		return result;
	}

}
