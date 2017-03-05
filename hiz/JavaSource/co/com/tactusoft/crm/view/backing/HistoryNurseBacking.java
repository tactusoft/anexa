package co.com.tactusoft.crm.view.backing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmConsent;
import co.com.tactusoft.crm.model.entities.CrmConsentText;
import co.com.tactusoft.crm.model.entities.CrmCustodyBelongings;
import co.com.tactusoft.crm.model.entities.CrmNote;
import co.com.tactusoft.crm.model.entities.CrmNurse;
import co.com.tactusoft.crm.model.entities.CrmTherapy;
import co.com.tactusoft.crm.model.entities.VwAppointment;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.VwAppointmentDataModel;

@Named
@Scope("view")
public class HistoryNurseBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	protected CrmNurse currentNurse;
	protected CrmAppointment currentAppointment;
	protected CrmNote currentNote;

	protected List<VwAppointment> listAppointment;
	protected VwAppointmentDataModel appointmentModel;
	protected VwAppointment selectedAppointment;

	protected List<CrmConsent> listConsentView;
	protected List<CrmNote> listNoteView;
	protected List<SelectItem> listNoteTherapyItem;
	protected Integer idNoteTherapy;
	protected boolean autoNote;
	protected String noteDoctor;
	protected Map<Integer, Object> mapNoteTherapy;
	protected HtmlPanelGrid containerComponent;

	protected boolean modeEdit;
	protected boolean modeHistorial;

	private String belongings;
	private List<CrmCustodyBelongings> listBelongings;

	private String consentType;
	private String consentText;
	private String consentSign;
	private String consentHTML;

	@PostConstruct
	public void init() {
		consentType = "M";
		modeEdit = false;
		modeHistorial = false;
		appointmentModel = null;
		containerComponent = new HtmlPanelGrid();
		currentNurse = processService.getCrmNurse();

		List<CrmTherapy> listNoteTherapy = tablesService.getListTherapyNurse();
		if (listNoteTherapy.size() > 0) {
			idNoteTherapy = listNoteTherapy.get(0).getId();
			autoNote = listNoteTherapy.get(0).getAuto() == 1 ? true : false;
			listNoteTherapyItem = FacesUtil.entityToSelectItem(listNoteTherapy,
					"getId", "getName");
			try {
				mapNoteTherapy = FacesUtil.entityToMapInteger(listNoteTherapy,
						"getId");
			} catch (Exception e) {
				e.printStackTrace();
			}

			idNoteTherapy = listNoteTherapy.get(0).getId();

			if (listNoteTherapyItem != null && listNoteTherapyItem.size() > 0) {
				this.noteDoctor = ((CrmTherapy) mapNoteTherapy
						.get(idNoteTherapy)).getDescription();
				handleTherapyChange();
			}
		}
	}

	public CrmNurse getCurrentNurse() {
		return currentNurse;
	}

	public void setCurrentNurse(CrmNurse currentNurse) {
		this.currentNurse = currentNurse;
	}

	public CrmAppointment getCurrentAppointment() {
		return currentAppointment;
	}

	public void setCurrentAppointment(CrmAppointment currentAppointment) {
		this.currentAppointment = currentAppointment;
	}

	public CrmNote getCurrentNote() {
		return currentNote;
	}

	public void setCurrentNote(CrmNote currentNote) {
		this.currentNote = currentNote;
	}

	public List<VwAppointment> getListAppointment() {
		return listAppointment;
	}

	public void setListAppointment(List<VwAppointment> listAppointment) {
		this.listAppointment = listAppointment;
	}

	public VwAppointmentDataModel getAppointmentModel() {
		if (appointmentModel == null) {
			refreshAction();
		}
		return appointmentModel;
	}

	public void setAppointmentModel(VwAppointmentDataModel appointmentModel) {
		this.appointmentModel = appointmentModel;
	}

	public VwAppointment getSelectedAppointment() {
		return selectedAppointment;
	}

	public void setSelectedAppointment(VwAppointment selectedAppointment) {
		this.selectedAppointment = selectedAppointment;
	}

	public List<CrmConsent> getListConsentView() {
		return listConsentView;
	}

	public void setListConsentView(List<CrmConsent> listConsentView) {
		this.listConsentView = listConsentView;
	}

	public List<CrmNote> getListNoteView() {
		return listNoteView;
	}

	public void setListNoteView(List<CrmNote> listNoteView) {
		this.listNoteView = listNoteView;
	}

	public boolean isModeEdit() {
		return modeEdit;
	}

	public void setModeEdit(boolean modeEdit) {
		this.modeEdit = modeEdit;
	}

	public boolean isModeHistorial() {
		return modeHistorial;
	}

	public void setModeHistorial(boolean modeHistorial) {
		this.modeHistorial = modeHistorial;
	}

	public List<SelectItem> getListNoteTherapyItem() {
		return listNoteTherapyItem;
	}

	public void setListNoteTherapyItem(List<SelectItem> listNoteTherapyItem) {
		this.listNoteTherapyItem = listNoteTherapyItem;
	}

	public Integer getIdNoteTherapy() {
		return idNoteTherapy;
	}

	public void setIdNoteTherapy(Integer idNoteTherapy) {
		this.idNoteTherapy = idNoteTherapy;
	}

	public boolean isAutoNote() {
		return autoNote;
	}

	public void setAutoNote(boolean autoNote) {
		this.autoNote = autoNote;
	}

	public String getNoteDoctor() {
		return noteDoctor;
	}

	public void setNoteDoctor(String noteDoctor) {
		this.noteDoctor = noteDoctor;
	}

	public Map<Integer, Object> getMapNoteTherapy() {
		return mapNoteTherapy;
	}

	public void setMapNoteTherapy(Map<Integer, Object> mapNoteTherapy) {
		this.mapNoteTherapy = mapNoteTherapy;
	}

	public HtmlPanelGrid getContainerComponent() {
		return containerComponent;
	}

	public void setContainerComponent(HtmlPanelGrid containerComponent) {
		this.containerComponent = containerComponent;
	}

	public String getConsentType() {
		return consentType;
	}

	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}

	public String getConsentText() {
		return consentText;
	}

	public void setConsentText(String consentText) {
		this.consentText = consentText;
	}

	public String getConsentSign() {
		return consentSign;
	}

	public void setConsentSign(String consentSign) {
		this.consentSign = consentSign;
	}

	public String getConsentHTML() {
		return consentHTML;
	}

	public void setConsentHTML(String consentHTML) {
		this.consentHTML = consentHTML;
	}

	public String getTitle() {
		return modeEdit ? FacesUtil.getMessage("title_view_history") + "  No. "
				+ selectedPatient.getDoc() : FacesUtil
				.getMessage("title_view_history");
	}

	public String getBelongings() {
		return belongings;
	}

	public void setBelongings(String belongings) {
		this.belongings = belongings;
	}

	public List<CrmCustodyBelongings> getListBelongings() {
		return listBelongings;
	}

	public void setListBelongings(List<CrmCustodyBelongings> listBelongings) {
		this.listBelongings = listBelongings;
	}

	public void refreshAction() {
		modeEdit = false;
		if (currentNurse != null) {
			BigDecimal idBranch = FacesUtil.getCurrentUserData()
					.getListBranch().get(0).getId();
			listAppointment = processService
					.getListVwAppointmenTheraphytByBranch(idBranch, FacesUtil
							.getCurrentUserData().getListBranch());
			appointmentModel = new VwAppointmentDataModel(listAppointment);
			if (listAppointment != null && listAppointment.size() > 0) {
				selectedAppointment = listAppointment.get(0);
			}
		}
	}

	public void editAppointmentAction() {
		consentType = "M";
		modeEdit = true;
		modeHistorial = false;
		currentAppointment = processService.getAppointment(selectedAppointment
				.getId());
		selectedPatient = currentAppointment.getCrmPatient();

		listConsentView = processService
				.getListConsentByAppointment(currentAppointment.getId());
		listNoteView = processService
				.getListNoteByAppointment(currentAppointment.getId());
		listBelongings = processService
				.getListCustodyBelongingsByAppointment(currentAppointment
						.getId());
		if (listBelongings.isEmpty()) {
			listBelongings = new LinkedList<CrmCustodyBelongings>();
		}

		UploadBacking uploadBacking = FacesUtil.findBean("uploadBacking");
		uploadBacking.setUploadedFile(null);
		uploadBacking.setUploadedFile2(null);
		generateConsent();
	}

	private void generateConsent() {
		consentText = FacesUtil.getMessage("consent_text");
		boolean exit = true;
		int id = 1;
		int index = 0;
		int count = 0;

		while (exit) {
			int pos = consentText.indexOf(":" + id, index);
			if (pos == -1) {
				exit = false;
			} else {
				switch (id) {
				case 1:
					consentText = consentText.replace(":" + id,
							currentAppointment.getCrmDoctor().getNames());
					break;
				case 2:
					consentText = consentText.replace(":" + id,
							this.selectedAppointment.getBranchName());
					break;
				case 3:
					consentText = consentText.replace(":" + id,
							selectedPatient.getNames());
					break;
				case 4:
					consentText = consentText.replace(":" + id, FacesUtil
							.formatDate(currentAppointment
									.getStartAppointmentDate(),
									"dd/MM/yyyy HH:mm"));
					break;
				case 5:
					consentText = consentText.replace(":" + id,
							currentAppointment.getCrmDoctor().getNames());
					break;
				case 6:
					consentText = consentText.replace(":" + id,
							currentAppointment.getSapMaterialDesc());
					break;
				case 7:
					consentText = consentText.replace(":" + id, FacesUtil
							.formatDate(new Date(), "dd/MM/yyyy HH:mm"));
					break;
				}
				id++;
				count = String.valueOf(id).length() + 1;
				index = pos + count;
			}
		}
	}

	private void saveConsent(byte[] consentFile, String fileName) {
		CrmConsent crmConsent = new CrmConsent();
		crmConsent.setCrmPatient(this.selectedPatient);
		crmConsent.setCrmAppointment(currentAppointment);
		crmConsent.setConsentFile(consentFile);
		crmConsent.setConsentType(Constant.MEDICAL_HISTORY_TYPE);
		crmConsent.setDateInformed(new Date());
		if (fileName != null) {
			crmConsent.setFileType(FacesUtil.getExtensionFile(fileName));
		} else {
			crmConsent.setFileType("AUTO");
		}
		processService.persist(crmConsent);

		if (crmConsent.getFileType().equals("AUTO")) {
			CrmConsentText crmConsentText = new CrmConsentText();
			crmConsentText.setConsentText(this.consentText.replace(
					FacesUtil.getMessage("consent_image"),
					"<img width='500' height='100' src='" + this.consentSign
							+ "'/>"));
			crmConsentText.setCrmConsent(crmConsent);
			processService.persist(crmConsentText);
		}

		listConsentView.add(crmConsent);
	}

	public void saveConsentAction() {
		RequestContext context = RequestContext.getCurrentInstance();
		String message = null;
		boolean saved = false;

		UploadBacking uploadBacking = FacesUtil.findBean("uploadBacking");
		if (uploadBacking.getUploadedFile() != null) {
			byte[] consentFile = uploadBacking.getUploadedFile().getContents();
			saveConsent(consentFile, uploadBacking.getUploadedFile()
					.getFileName());
			saved = true;
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
			uploadBacking = FacesUtil.findBean("uploadBacking");
			uploadBacking.setUploadedFile(null);
			uploadBacking.setUploadedFile2(null);
		} else {
			saved = false;
			String field = FacesUtil.getMessage("con_consent");
			message = FacesUtil.getMessage("glb_required", field);
			FacesUtil.addWarn(message);
		}
		context.addCallbackParam("saved", saved);
	}

	public void saveConsentTextAction() {
		RequestContext context = RequestContext.getCurrentInstance();
		String message = null;
		boolean saved = false;
		this.saveConsent("DEFAULT".getBytes(), null);
		message = FacesUtil.getMessage("msg_record_ok");
		FacesUtil.addInfo(message);
		context.addCallbackParam("saved", saved);
	}

	public void closeAppointmentAction() {
		String message = null;
		if (listNoteView.isEmpty() || listConsentView.isEmpty()) {
			message = FacesUtil.getMessage("his_msg_message_con_not_1");
			FacesUtil.addInfo(message);
		} else {
			currentAppointment.setCloseAppointment(true);
			currentAppointment.setCrmUserByIdUserClosed(FacesUtil
					.getCurrentUser());
			currentAppointment.setDateClosed(new Date());
			currentAppointment.setState(Constant.APP_STATE_ATTENDED);
			processService.saveAppointment(currentAppointment);
			message = FacesUtil.getMessage("his_msg_message_med_ok");
			FacesUtil.addInfo(message);
			refreshAction();
		}
	}

	public StreamedContent getFile(CrmConsent row) {
		byte[] consentFile = row.getConsentFile();
		String type = row.getFileType();
		if (FacesUtil.isEmptyOrBlank(type)) {
			type = "pdf";
		}
		StreamedContent file = new DefaultStreamedContent(
				new ByteArrayInputStream(consentFile), "application/" + type,
				"consentimiento." + type);
		return file;
	}

	public void dropFile(CrmConsent row) {
		processService.remove(row);
		listConsentView.remove(row);
	}

	public void addComponent(String text) {
		// clean previous component
		containerComponent.getChildren().clear();
		boolean exit = true;
		int id = 1;
		int index = 0;
		int count = 0;

		// dynamically add Child Components to Container Component
		while (exit) {
			int pos = text.indexOf(":" + id, index);
			if (pos == -1) {
				exit = false;
			} else {
				UIColumn col = new UIColumn();
				col.setId("col_" + id);
				HtmlOutputText ot = new HtmlOutputText();
				ot.setId("output_" + id);
				String subText = text.substring(index, pos);
				ot.setValue(subText);
				col.getChildren().add(ot);
				HtmlInputText it = new HtmlInputText();
				it.setId("text_" + id);
				it.setValue("");
				col.getChildren().add(it);

				containerComponent.getChildren().add(col);
				id++;
				count = String.valueOf(id).length() + 1;
				index = pos + count;
			}
		}
	}

	public void handleTherapyChange() {
		if (listNoteTherapyItem != null && listNoteTherapyItem.size() > 0) {
			CrmTherapy currentTheraphy = (CrmTherapy) mapNoteTherapy
					.get(idNoteTherapy);
			this.noteDoctor = currentTheraphy.getDescription();
			autoNote = currentTheraphy.getAuto() == 1 ? true : false;
			if (autoNote) {
				addComponent(this.noteDoctor);
			}
		}
	}

	public void saveNoteAction(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		String message = null;
		boolean saved = false;

		if (this.autoNote) {
			if (this.validateComponent()) {
				message = FacesUtil.getMessage("glb_required_all");
				FacesUtil.addWarn(message);
			}
		} else {
			if (FacesUtil.isEmptyOrBlank(noteDoctor)) {
				String field = FacesUtil.getMessage("his_history_note");
				message = FacesUtil.getMessage("glb_required", field);
				FacesUtil.addWarn(message);
			}
		}

		if (message == null) {
			CrmNote crmNote = new CrmNote();
			crmNote.setCrmPatient(this.selectedPatient);
			crmNote.setCrmNurse(this.currentNurse);
			crmNote.setNote(this.noteDoctor);
			crmNote.setNoteType(Constant.NOTE_TYPE_NURSE);
			crmNote.setNoteDate(new Date());
			crmNote.setCrmAppointment(currentAppointment);
			crmNote.setOtherNoteType(((CrmTherapy) mapNoteTherapy
					.get(idNoteTherapy)).getName());

			processService.saveNotes(crmNote);
			listNoteView.add(crmNote);
			message = FacesUtil.getMessage("msg_record_ok");
			this.noteDoctor = null;
			FacesUtil.addInfo(message);
			saved = true;
		}

		context.addCallbackParam("saved", saved);
	}

	private boolean validateComponent() {
		String tmpText = this.noteDoctor;
		int id = 1;
		boolean required = false;

		outerloop: for (UIComponent component : containerComponent
				.getChildren()) {
			List<UIComponent> list = (List<UIComponent>) component
					.getChildren();
			for (UIComponent component2 : list) {
				try {
					HtmlInputText it = (HtmlInputText) component2;
					String value = it.getValue().toString();
					if (!FacesUtil.isEmptyOrBlank(value)) {
						tmpText = tmpText.replaceFirst(":" + id, value);
						id++;
					} else {
						required = true;
						break outerloop;
					}
				} catch (ClassCastException ex) {

				}
			}
		}

		if (!required) {
			this.noteDoctor = tmpText;
		}

		return required;
	}

	public void showHistorialAction() {
		modeHistorial = true;
		listNoteView = processService.getListNoteByPatient(selectedPatient
				.getId());
		listConsentView = processService.getListConsentByPatient(
				selectedPatient.getId(), Constant.MEDICAL_HISTORY_TYPE);
	}

	public void showSignEmpty() {
		String message = FacesUtil.getMessage("con_err_1");
		FacesUtil.addInfo(message);
	}

	public void viewConsent(Long consentId) {
		CrmConsentText crmConsentText = processService
				.getConsentTextByConsent(consentId);
		this.consentHTML = crmConsentText.getConsentText();
	}

	public void addBelongings(ActionEvent event) {
		CrmCustodyBelongings crmCustodyBelongings = new CrmCustodyBelongings();
		crmCustodyBelongings.setCrmAppointment(currentAppointment);
		crmCustodyBelongings.setBelongings(belongings);
		listBelongings.add(crmCustodyBelongings);
		belongings = null;
	}

	public void removeBelongings(CrmCustodyBelongings rec) {
		listBelongings.remove(rec);
	}

	public void saveBelongings(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		String message = null;
		boolean saved = false;

		if (listBelongings.isEmpty()) {
			message = FacesUtil.getMessage("cus_error_1");
			FacesUtil.addWarn(message);
		} else {
			processService.saveCustodyBelongings(currentAppointment,
					listBelongings);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
			saved = true;
		}

		context.addCallbackParam("saved", saved);
	}

}
