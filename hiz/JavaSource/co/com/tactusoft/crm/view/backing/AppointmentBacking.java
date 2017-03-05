package co.com.tactusoft.crm.view.backing;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.apache.xmlrpc.XmlRpcException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmDoctor;
import co.com.tactusoft.crm.model.entities.CrmHoliday;
import co.com.tactusoft.crm.model.entities.CrmMedication;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmProcedure;
import co.com.tactusoft.crm.model.entities.CrmProcedureDetail;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.InfunsionSoft;
import co.com.tactusoft.crm.view.beans.Candidate;
import co.com.tactusoft.crm.view.beans.ResultSearchAppointment;
import co.com.tactusoft.crm.view.datamodel.CandidateDataModel;
import co.com.tactusoft.crm.view.datamodel.PatientDataModel;

import com.tactusoft.webservice.client.beans.WSBean;

@Named
@Scope("session")
public class AppointmentBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private CrmAppointment selected;

	private List<SelectItem> listBranch;
	private Map<BigDecimal, CrmBranch> mapBranch;
	private BigDecimal idBranch;

	private List<SelectItem> listProcedure;
	private Map<BigDecimal, CrmProcedure> mapProcedure;
	private BigDecimal idProcedure;

	private List<SelectItem> listProcedureDetail;
	private Map<BigDecimal, CrmProcedureDetail> mapProcedureDetail;
	private BigDecimal idProcedureDetail;
	private CrmProcedureDetail procedureDetail;

	private List<SelectItem> listTherapyMaterials;
	private Map<Long, CrmMedication> mapTherapyMaterials;
	private BigDecimal idTherapyMaterials;

	private List<SelectItem> listSearch;
	private BigDecimal idSearch;

	private List<SelectItem> listDoctor;
	private Map<BigDecimal, CrmDoctor> mapDoctor;

	private List<SelectItem> listTimes;

	private Date currentDate;
	private Long currentTime;
	private int appointmentsNumber;

	private List<Candidate> listAppointment;
	private CandidateDataModel modelAppointment;
	private Candidate selectedCandidate;
	private Candidate selectedCandidateTemp;

	private boolean renderedForDate;
	private boolean renderedForDoctor;
	private boolean renderedDoctorWithoutTime;
	private boolean disabledSearch;

	private String infoMessage;
	private boolean saved;
	private boolean edit;
	private boolean validate;
	private boolean generateNew;

	private int minutes = 0;
	private String timeType = null;
	private String infoMessageDate;

	private String fromPage;
	private ResultSearchAppointment resultSearchAppointment;

	public AppointmentBacking() {
		newAction(null);
	}

	public CrmAppointment getSelected() {
		return selected;
	}

	public void setSelected(CrmAppointment selected) {
		this.selected = selected;
	}

	public List<SelectItem> getListBranch() {
		if (listBranch == null) {
			listBranch = new LinkedList<SelectItem>();
			mapBranch = new LinkedHashMap<BigDecimal, CrmBranch>();
			for (CrmBranch row : FacesUtil.getCurrentUserData().getListBranch()) {
				mapBranch.put(row.getId(), row);
				listBranch.add(new SelectItem(row.getId(), row.getName()));
			}

			idBranch = null;
			if (listBranch.size() > 0) {
				idBranch = (BigDecimal) listBranch.get(0).getValue();
			}
		}
		return listBranch;
	}

	public void setListBranch(List<SelectItem> listBranch) {
		this.listBranch = listBranch;
	}

	public Map<BigDecimal, CrmBranch> getMapBranch() {
		return mapBranch;
	}

	public void setMapBranch(Map<BigDecimal, CrmBranch> mapBranch) {
		this.mapBranch = mapBranch;
	}

	public BigDecimal getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(BigDecimal idBranch) {
		this.idBranch = idBranch;
	}

	public List<SelectItem> getListProcedure() {
		return listProcedure;
	}

	public void setListProcedure(List<SelectItem> listProcedure) {
		this.listProcedure = listProcedure;
	}

	public Map<BigDecimal, CrmProcedure> getMapProcedure() {
		return mapProcedure;
	}

	public void setMapProcedure(Map<BigDecimal, CrmProcedure> mapProcedure) {
		this.mapProcedure = mapProcedure;
	}

	public BigDecimal getIdProcedure() {
		return idProcedure;
	}

	public void setIdProcedure(BigDecimal idProcedure) {
		this.idProcedure = idProcedure;
	}

	public List<SelectItem> getListProcedureDetail() {
		return listProcedureDetail;
	}

	public List<SelectItem> getListTherapyMaterials() {
		return listTherapyMaterials;
	}

	public void setListTherapyMaterials(List<SelectItem> listTherapyMaterials) {
		this.listTherapyMaterials = listTherapyMaterials;
	}

	public Map<Long, CrmMedication> getMapTherapyMaterials() {
		return mapTherapyMaterials;
	}

	public void setMapTherapyMaterials(
			Map<Long, CrmMedication> mapTherapyMaterials) {
		this.mapTherapyMaterials = mapTherapyMaterials;
	}

	public BigDecimal getIdTherapyMaterials() {
		return idTherapyMaterials;
	}

	public void setIdTherapyMaterials(BigDecimal idTherapyMaterials) {
		this.idTherapyMaterials = idTherapyMaterials;
	}

	public void setListProcedureDetail(List<SelectItem> listProcedureDetail) {
		this.listProcedureDetail = listProcedureDetail;
	}

	public Map<BigDecimal, CrmProcedureDetail> getMapProcedureDetail() {
		return mapProcedureDetail;
	}

	public void setMapProcedureDetail(
			Map<BigDecimal, CrmProcedureDetail> mapProcedureDetail) {
		this.mapProcedureDetail = mapProcedureDetail;
	}

	public BigDecimal getIdProcedureDetail() {
		return idProcedureDetail;
	}

	public void setIdProcedureDetail(BigDecimal idProcedureDetail) {
		this.idProcedureDetail = idProcedureDetail;
	}

	public List<SelectItem> getListSearch() {
		if (listSearch == null) {
			listSearch = new LinkedList<SelectItem>();
			listSearch.add(new SelectItem(Constant.DEFAULT_VALUE, FacesUtil
					.getMessage(Constant.DEFAULT_LABEL)));
			listSearch.add(new SelectItem(Constant.APP_TYPE_FOR_DATE_VALUE,
					FacesUtil.getMessage(Constant.APP_TYPE_FOR_DATE_DESC)));
			listSearch.add(new SelectItem(Constant.APP_TYPE_FOR_DOCTOR_VALUE,
					FacesUtil.getMessage(Constant.APP_TYPE_FOR_DOCTOR_DESC)));
		}
		return listSearch;
	}

	public void setListSearch(List<SelectItem> listSearch) {
		this.listSearch = listSearch;
	}

	public BigDecimal getIdSearch() {
		return idSearch;
	}

	public void setIdSearch(BigDecimal idSearch) {
		this.idSearch = idSearch;
	}

	public List<SelectItem> getListDoctor() {
		return listDoctor;
	}

	public void setListDoctor(List<SelectItem> listDoctor) {
		this.listDoctor = listDoctor;
	}

	public Map<BigDecimal, CrmDoctor> getMapDoctor() {
		return mapDoctor;
	}

	public void setMapDoctor(Map<BigDecimal, CrmDoctor> mapDoctor) {
		this.mapDoctor = mapDoctor;
	}

	public List<SelectItem> getListTimes() {
		return listTimes;
	}

	public void setListTimes(List<SelectItem> listTimes) {
		this.listTimes = listTimes;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
	}

	public int getAppointmentsNumber() {
		return appointmentsNumber;
	}

	public void setAppointmentsNumber(int appointmentsNumber) {
		this.appointmentsNumber = appointmentsNumber;
	}

	public List<Candidate> getListAppointment() {
		return listAppointment;
	}

	public void setListAppointment(List<Candidate> listAppointment) {
		this.listAppointment = listAppointment;
	}

	public CandidateDataModel getModelAppointment() {
		return modelAppointment;
	}

	public void setModelAppointment(CandidateDataModel modelAppointment) {
		this.modelAppointment = modelAppointment;
	}

	public Candidate getSelectedCandidate() {
		return selectedCandidate;
	}

	public void setSelectedCandidate(Candidate selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}

	public Candidate getSelectedCandidateTemp() {
		return selectedCandidateTemp;
	}

	public void setSelectedCandidateTemp(Candidate selectedCandidateTemp) {
		this.selectedCandidateTemp = selectedCandidateTemp;
	}

	public boolean isRenderedForDate() {
		return renderedForDate;
	}

	public void setRenderedForDate(boolean renderedForDate) {
		this.renderedForDate = renderedForDate;
	}

	public boolean isRenderedForDoctor() {
		return renderedForDoctor;
	}

	public void setRenderedForDoctor(boolean renderedForDoctor) {
		this.renderedForDoctor = renderedForDoctor;
	}

	public boolean isRenderedDoctorWithoutTime() {
		return renderedDoctorWithoutTime;
	}

	public void setRenderedDoctorWithoutTime(boolean renderedDoctorWithoutTime) {
		this.renderedDoctorWithoutTime = renderedDoctorWithoutTime;
	}

	public boolean isDisabledSearch() {
		return disabledSearch;
	}

	public void setDisabledSearch(boolean disabledSearch) {
		this.disabledSearch = disabledSearch;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public boolean isGenerateNew() {
		return generateNew;
	}

	public void setGenerateNew(boolean generateNew) {
		this.generateNew = generateNew;
	}

	public String getInfoMessageDate() {
		return infoMessageDate;
	}

	public void setInfoMessageDate(String infoMessageDate) {
		this.infoMessageDate = infoMessageDate;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public boolean isDependProcedureDetail() {
		return procedureDetail != null ? procedureDetail.isDependent() : false;
	}

	public void newAction(ActionEvent event) {
		listBranch = null;

		listPatient = new LinkedList<CrmPatient>();
		patientModel = new PatientDataModel(listPatient);
		idSearch = Constant.DEFAULT_VALUE;

		selected = new CrmAppointment();
		selected.setCrmBranch(new CrmBranch());
		selected.setCrmDoctor(new CrmDoctor());
		selected.setCrmProcedureDetail(new CrmProcedureDetail());

		selectedPatient = null;
		selectedPatientTemp = null;
		currentDate = new Date();

		renderedForDate = false;
		renderedForDoctor = false;
		disabledSearch = true;

		optionSearchPatient = 1;
		cleanPatientFields();
		selectedWSGroupSellers = "-1";

		listAppointment = new LinkedList<Candidate>();
		modelAppointment = new CandidateDataModel(listAppointment);
		selectedCandidate = null;
		selectedCandidateTemp = null;

		infoMessage = null;
		saved = false;
		edit = false;
		generateNew = true;
		fromPage = null;

		String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
		listProcedureDetail = new ArrayList<SelectItem>();
		listProcedureDetail.add(new SelectItem(Constant.DEFAULT_VALUE, label));
		idProcedureDetail = null;
		procedureDetail = new CrmProcedureDetail();

		today = new Date();
		todayMax = FacesUtil.addHoursToDate(
				FacesUtil.getDateWithoutTime(today), 18);
	}

	public void handleBranchChange() {
		listProcedureDetail = new LinkedList<SelectItem>();
		mapProcedureDetail = new LinkedHashMap<BigDecimal, CrmProcedureDetail>();
		List<CrmProcedureDetail> listProcedureDetailTemp = tablesService
				.getListProcedureByBranch(idBranch);
		if (listProcedureDetailTemp != null) {
			for (CrmProcedureDetail row : listProcedureDetailTemp) {
				mapProcedureDetail.put(row.getId(), row);
				listProcedureDetail.add(new SelectItem(row.getId(), row
						.getName()));
			}
		}

		if (listProcedureDetail.size() > 0) {
			idProcedureDetail = (BigDecimal) listProcedureDetail.get(0)
					.getValue();
			handleProcedureDetailChange();
		}

		currentDate = new Date();
	}

	public void handleProcedureDetailChange() {
		if (listProcedureDetail.size() > 0) {
			String label = FacesUtil.getMessage(Constant.DEFAULT_LABEL);
			try {
				// String codeBranch = mapBranch.get(idBranch).getCode();

				List<WSBean> result = FacesUtil.getCurrentUserData()
						.getListWSGroupSellers();

				listWSGroupSellers = new ArrayList<SelectItem>();
				mapWSGroupSellers = new TreeMap<String, String>();
				listWSGroupSellers.add(new SelectItem(
						Constant.DEFAULT_VALUE_STRING, label));
				for (WSBean row : result) {
					// if (row.getBranch().equals(codeBranch)) {
					mapWSGroupSellers.put(row.getCode(), row.getNames());
					listWSGroupSellers.add(new SelectItem(row.getCode(), row
							.getNames()));
					// }
				}
			} catch (Exception ex) {
				listWSGroupSellers = new ArrayList<SelectItem>();
				listWSGroupSellers.add(new SelectItem(
						Constant.DEFAULT_VALUE_STRING, label));
			}

			selectedWSGroupSellers = "-1";

			procedureDetail = mapProcedureDetail.get(idProcedureDetail);

			String codPublicity = procedureDetail.getCodPublicity();
			if (!FacesUtil.isEmptyOrBlank(codPublicity)
					&& !codPublicity.equals(Constant.DEFAULT_VALUE_STRING)) {

				String namePublicity = null;
				List<WSBean> result = FacesUtil.getCurrentUserData()
						.getListWSGroupSellers();
				for (WSBean row : result) {
					if (row.getCode().equals(codPublicity)) {
						namePublicity = row.getNames();
						break;
					}
				}

				listWSGroupSellers = new ArrayList<SelectItem>();
				mapWSGroupSellers = new TreeMap<String, String>();
				mapWSGroupSellers.put(codPublicity, namePublicity);
				listWSGroupSellers.add(new SelectItem(codPublicity,
						namePublicity));
			}

			if (!procedureDetail.isAvailability()) {
				idSearch = Constant.APP_TYPE_FOR_DOCTOR_VALUE;
				renderedForDate = false;
				renderedDoctorWithoutTime = false;
			} else {
				idSearch = Constant.DEFAULT_VALUE;
				renderedDoctorWithoutTime = true;
			}

			if ((procedureDetail.getTimeDoctor() >= procedureDetail
					.getTimeNurses())
					&& (procedureDetail.getTimeDoctor() >= procedureDetail
							.getTimeStretchers())) {
				minutes = procedureDetail.getTimeDoctor();
				timeType = Constant.TIME_TYPE_DOCTOR;
			} else if ((procedureDetail.getTimeNurses() > procedureDetail
					.getTimeDoctor())
					&& (procedureDetail.getTimeNurses() > procedureDetail
							.getTimeStretchers())) {
				minutes = procedureDetail.getTimeNurses();
				timeType = Constant.TIME_TYPE_NURSE;
			} else {
				minutes = procedureDetail.getTimeStretchers();
				timeType = Constant.TIME_TYPE_STRETCHERS;
			}

			if (procedureDetail.isDependent()) {
				List<CrmMedication> listTherapyMaterialsTemp = processService
						.getListTherapyByPatient(selectedPatient.getId());
				listTherapyMaterials = new ArrayList<SelectItem>();
				listTherapyMaterials.add(new SelectItem(
						Constant.DEFAULT_VALUE_STRING, label));
				mapTherapyMaterials = new LinkedHashMap<Long, CrmMedication>();
				for (CrmMedication row : listTherapyMaterialsTemp) {
					mapTherapyMaterials.put(row.getId().longValue(), row);
					listTherapyMaterials.add(new SelectItem(row.getId(), row
							.getDescMaterial()));
				}
			} else {
				listTherapyMaterials = new ArrayList<SelectItem>();
			}

			String speciality = "1,2";
			if (procedureDetail.getTypeHistory().equalsIgnoreCase("ODONTOLOGY")) {
				speciality = "3";
			} else if (procedureDetail.getTypeHistory().equalsIgnoreCase("BEAUTICIAN")) {
				speciality = "4";
			}

			listDoctor = new LinkedList<SelectItem>();
			mapDoctor = new LinkedHashMap<BigDecimal, CrmDoctor>();
			for (CrmDoctor row : tablesService.getListDoctorByBranch(idBranch,
					speciality)) {
				mapDoctor.put(row.getId(), row);
				listDoctor.add(new SelectItem(row.getId(), row.getNames()));
			}

			handleSearchChange();
			selectedCandidate = null;
		}
	}

	public void handleDateSelect(SelectEvent event) {
		if (event != null) {
			currentDate = (Date) event.getObject();
		} else {
			currentDate = FacesUtil.getDateWithoutTime(today);
		}

		List<CrmHoliday> listHoliday = processService.getListHoliday(
				currentDate, idBranch);
		if (listHoliday.size() > 0) {
			infoMessageDate = FacesUtil.getMessage("app_msg_error_1");
		} else {
			infoMessageDate = null;
		}
	}

	public void handleSearchChange() {
		if (this.idSearch.intValue() == Constant.APP_TYPE_FOR_DATE_VALUE
				.intValue()) {
			this.renderedForDate = true;
			this.renderedForDoctor = false;
			this.disabledSearch = false;
			handleDateSelect(null);
		} else if (this.idSearch.intValue() == Constant.APP_TYPE_FOR_DOCTOR_VALUE
				.intValue()) {
			this.renderedForDate = false;
			this.renderedForDoctor = true;
			this.disabledSearch = false;
			handleDateSelect(null);
		} else {
			disabledSearch = false;
			this.renderedForDate = false;
			this.renderedForDoctor = false;
			this.disabledSearch = true;
		}
	}

	public void handleClose(CloseEvent event) {
		if (saved) {
			newAction(null);
		}
	}

	public String getDetSelectedCandidate() {
		String result = "";
		if (selectedCandidate != null) {
			String message = FacesUtil.getMessage("app_msg_selected");
			if (this.idSearch.intValue() == Constant.APP_TYPE_FOR_DATE_VALUE
					.intValue()) {
				result = message + " " + selectedCandidate.getDateDetail();
			} else {
				result = message + " " + selectedCandidate.getDoctorDetail();
			}
		}
		return result;
	}

	public void addAppointmentAction() {
		if (this.renderedForDate) {
			for (Candidate row : resultSearchAppointment
					.getListDoctorCandidate()) {
				if (row.getStartDate().compareTo(
						this.selectedCandidateTemp.getStartDate()) == 0
						&& row.getEndDate().compareTo(
								this.selectedCandidateTemp.getEndDate()) == 0) {
					this.selectedCandidate = row;
					break;
				}
			}
		} else {
			this.selectedCandidate = this.selectedCandidateTemp;
		}
	}

	public void searchAppointMentAction() {
		boolean validateNoRepeat = true;
		infoMessage = null;

		if (selectedPatient == null) {
			infoMessage = FacesUtil.getMessage("app_msg_error_pat");
		} else if (selectedPatient.getId() == null) {
			infoMessage = FacesUtil.getMessage("pat_msg_exists_sap_2");
		} else {
			if (procedureDetail.isNoRepeat()
					&& procedureDetail.getNoRepeatDays() > 0
					&& infoMessage == null) {
				Date maxDate = processService.getMaxDateByProcedure(
						selectedPatient.getId(), procedureDetail.getId());
				if (maxDate != null) {
					maxDate = FacesUtil.getDateWithoutTime(maxDate);
					Date currentDate = new Date(this.currentDate.getTime());
					currentDate = FacesUtil.getDateWithoutTime(currentDate);
					long diff = currentDate.getTime() - maxDate.getTime();
					diff = diff / (1000 * 60 * 60 * 24);
					if (diff < procedureDetail.getNoRepeatDays()) {
						validateNoRepeat = false;
					}
				}
			}
		}

		if ((validateNoRepeat || edit) && infoMessage == null) {
			if (this.renderedForDate) {
				resultSearchAppointment = processService
						.getScheduleAppointmentForDate(mapBranch.get(idBranch),
								this.currentDate, procedureDetail, minutes,
								timeType, this.appointmentsNumber);
				listAppointment = resultSearchAppointment.getListCandidate();
				infoMessage = resultSearchAppointment.getMessage();
			} else if (this.renderedForDoctor) {
				CrmDoctor doctor = mapDoctor.get(selected.getCrmDoctor()
						.getId());
				resultSearchAppointment = processService
						.getScheduleAppointmentForDoctor(
								mapBranch.get(idBranch), doctor,
								this.appointmentsNumber, procedureDetail,
								this.currentDate);
				listAppointment = resultSearchAppointment.getListCandidate();
				infoMessage = resultSearchAppointment.getMessage();
			}

			modelAppointment = new CandidateDataModel(listAppointment);

			if (listAppointment.size() > 0) {
				selectedCandidateTemp = listAppointment.get(0);
			} else {
				selectedCandidate = null;
				selectedCandidateTemp = null;
			}
		} else {
			listAppointment = new ArrayList<Candidate>();
			modelAppointment = new CandidateDataModel(listAppointment);
			selectedCandidate = null;
			selectedCandidateTemp = null;
			if (!validateNoRepeat) {
				String message = procedureDetail.getNoRepeatDays().toString();
				infoMessage = FacesUtil.getMessage("app_msg_error_procedure",
						message);
			}
		}
	}

	public void validateAction() {
		String appType = FacesUtil.getParam("APP_TYPE");
		infoMessage = "";
		validate = true;

		// validar Selección Pauta
		if (this.selectedWSGroupSellers.equals(Constant.DEFAULT_VALUE_STRING)) {
			String field = FacesUtil.getMessage("app_seller_group");
			infoMessage = FacesUtil.getMessage("glb_required", field);
			validate = false;
		}

		// Tipo de procedimiento
		if (this.procedureDetail.isDependent()
				&& this.idTherapyMaterials.intValue() == Constant.DEFAULT_VALUE
						.intValue()) {
			String field = FacesUtil.getMessage("app_procedure_type");
			infoMessage = FacesUtil.getMessage("glb_required", field);
			validate = false;
		}

		// validar Selección Paciente
		if (this.selectedPatient == null) {
			infoMessage = FacesUtil.getMessage("app_msg_error_pat");
			validate = false;
		} else {
			if (this.selectedPatient.getId() == null) {
				infoMessage = FacesUtil.getMessage("app_msg_error_pat_sap");
				validate = false;
			}
		}

		// validar Selección Cita
		if ((selectedCandidate == null) && (appType.equals("ORDINARY"))) {
			infoMessage = FacesUtil.getMessage("app_msg_error_app");
			validate = false;
		}
	}

	public void saveAction() {
		String appType = FacesUtil.getParam("APP_TYPE");
		infoMessage = "";
		validate = true;

		// validar Selección Pauta
		if (this.selectedWSGroupSellers.equals(Constant.DEFAULT_VALUE_STRING)) {
			String field = FacesUtil.getMessage("app_seller_group");
			infoMessage = FacesUtil.getMessage("glb_required", field);
			validate = false;
		}

		// validar Selección Paciente
		if (this.selectedPatient == null) {
			infoMessage = FacesUtil.getMessage("app_msg_error_pat");
			validate = false;
		} else {
			if (this.selectedPatient.getId() == null) {
				infoMessage = FacesUtil.getMessage("app_msg_error_pat_sap");
				validate = false;
			} else {
				if (((selectedPatient.getCodeSap() == null) || (selectedPatient
						.getCodeSap().equals(selectedPatient.getDoc())))
						&& appType.equals("UNTIMELY")) {
					infoMessage = FacesUtil
							.getMessage("app_msg_error_pat_update");
					validate = false;
				}
			}
		}

		// validar Selección Cita
		if ((selectedCandidate == null) && (appType.equals("ORDINARY"))) {
			infoMessage = FacesUtil.getMessage("app_msg_error_app");
			validate = false;
		}

		if (validate) {
			CrmBranch branch = mapBranch.get(idBranch);

			int validateApp = 0;
			if (procedureDetail.isAvailability()) {
				if (!this.edit && appType.equals("ORDINARY")) {
					validateApp = processService.validateAppointmentForDate(
							selectedCandidate.getStartDate(),
							selectedPatient.getId());
				} else {
					if (!this.edit) {
						validateApp = processService
								.validateAppointmentForDate(currentDate,
										selectedPatient.getId());
					}
				}
			}

			if (this.generateNew) {
				this.selected.setId(null);
				this.selected.setCrmDiagnosises(null);
				this.selected.setCrmMedications(null);
			}

			if (validateApp == 0) {
				if (appType.equals("ORDINARY")) {
					validateApp = processService.validateAppointmentForDate(
							branch.getId(), selectedCandidate.getStartDate(),
							selectedCandidate.getEndDate(), procedureDetail,
							selectedCandidate.getDoctor().getId(),
							selectedPatient.getId(), timeType, edit);
				} else {
					CrmDoctor doctor = mapDoctor.get(selected.getCrmDoctor()
							.getId());
					selectedCandidate = new Candidate();
					selectedCandidate.setBranch(branch.getName());
					selectedCandidate.setProcedure(procedureDetail.getName());
					selectedCandidate.setStartDate(currentDate);
					selectedCandidate.setEndDate(FacesUtil.addMinutesToDate(
							currentDate, procedureDetail.getTimeDoctor()));
					selectedCandidate.setDoctor(doctor);
				}
			}

			if (validateApp != 0) {
				switch (validateApp) {
				case -1:
					infoMessage = FacesUtil.getMessage("app_msg_error_1");
					break;
				case -2:
					infoMessage = FacesUtil.getMessage("app_msg_error_2");
					break;
				case -3:
					infoMessage = FacesUtil.getMessage("app_msg_error_3");
					break;
				case -4:
					infoMessage = FacesUtil.getMessage("app_msg_error_4");
					break;
				case -5:
					infoMessage = FacesUtil.getMessage("app_msg_error_5");
					break;
				}

			} else {
				selected.setCrmPatient(selectedPatient);
				selected.setPatientNames(selectedPatient.getFirstnames() + " "
						+ selectedPatient.getSurnames());
				selected.setCrmDoctor(selectedCandidate.getDoctor());
				selected.setCrmBranch(branch);
				selected.setCrmProcedureDetail(procedureDetail);

				selected.setCodPublicity(selectedWSGroupSellers);
				selected.setNamePublicity(mapWSGroupSellers
						.get(selectedWSGroupSellers));

				selected.setStartAppointmentDate(selectedCandidate
						.getStartDate());
				selected.setEndAppointmentDate(selectedCandidate.getEndDate());
				selected.setCloseAppointment(false);

				if (appType.equals("ORDINARY")) {
					selected.setUntimely(false);
					selected.setState(Constant.APP_STATE_CONFIRMED);
				} else {
					selected.setUntimely(true);
					selected.setState(Constant.APP_STATE_CHECKED);
					selected.setCrmUserByIdUserChecked(FacesUtil
							.getCurrentUser());
					selected.setDateChecked(new Date());
				}

				if (procedureDetail.isDependent()) {
					CrmMedication vwTherapyMaterials = mapTherapyMaterials
							.get(idTherapyMaterials.longValue());
					selected.setSapMaterialCode(vwTherapyMaterials.getId()
							.longValue());
					selected.setSapMaterialDesc(vwTherapyMaterials
							.getDescMaterial());
				}

				if (this.edit) {
					selected.setCrmUserByIdUserModified(FacesUtil
							.getCurrentUser());
					selected.setDateModified(new Date());
				} else {
					selected.setCrmUserByIdUserCreate(FacesUtil
							.getCurrentUser());
					selected.setDateCreate(new Date());
				}

				CrmAppointment crmAppointment = processService
						.saveAppointment(selected);

				if (crmAppointment != null) {
					infoMessage = FacesUtil.getMessage("app_msg_ok",
							crmAppointment.getCode());
					saved = true;
					
					try {
						InfunsionSoft.assignAppointment(selectedPatient.getEmail(), selected.getStartAppointmentDate(),
								branch.getName(), branch.getAddress());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (XmlRpcException e) {
						e.printStackTrace();
					}
					
				} else {
					infoMessage = FacesUtil.getMessage("app_msg_error");
					saved = false;
				}
			}
		}
	}

	public void addPatient(ActionEvent event) {
		boolean validate = true;
		RequestContext context = RequestContext.getCurrentInstance();
		if (selectedPatientTemp.getId() == null) {
			validate = false;
		} else {
			selectedPatient = selectedPatientTemp;
			handleBranchChange();
		}
		context.addCallbackParam("validate", validate);
	}

	public String searchRedirectPatient() {
		PatientBacking patientBacking = FacesUtil.findBean("patientBacking");
		patientBacking.setSelectedPatient(new CrmPatient());
		patientBacking.setOptionSearchPatient(1);
		patientBacking.setDocPatient(this.docPatient);
		patientBacking.searchAction();
		return "/pages/processes/patient?faces-redirect=true";
	}

	public void closeDialog() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	@Override
	public void newPatientAction(ActionEvent event) {
		super.newPatientAction(event);
		newAction(event);
	}

}
