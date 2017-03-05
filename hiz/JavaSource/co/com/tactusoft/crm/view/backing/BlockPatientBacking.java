package co.com.tactusoft.crm.view.backing;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmBlockHistory;
import co.com.tactusoft.crm.model.entities.CrmBlockHistoryDetail;
import co.com.tactusoft.crm.model.entities.CrmCampaignType;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.PatientDataModel;

@Named
@Scope("view")
public class BlockPatientBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private CrmBlockHistory crmBlockHistory;

	private Integer[] selectedCampaignTypes;
	
	private boolean saved;

	@PostConstruct
	public void init() {
		newAction(null);
	}

	public CrmBlockHistory getCrmBlockHistory() {
		return crmBlockHistory;
	}

	public void setCrmBlockHistory(CrmBlockHistory crmBlockHistory) {
		this.crmBlockHistory = crmBlockHistory;
	}

	public Integer[] getSelectedCampaignTypes() {
		return selectedCampaignTypes;
	}

	public void setSelectedCampaignTypes(Integer[] selectedCampaignTypes) {
		this.selectedCampaignTypes = selectedCampaignTypes;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void newAction(ActionEvent event) {
		crmBlockHistory = new CrmBlockHistory();

		// Busquedas
		optionSearchPatient = 1;
		cleanPatientFields();
		patientModel = new PatientDataModel();
		selectedPatientTemp = null;
		selectedPatient = null;
		selectedCampaignTypes = null;
		saved = false;
	}

	public void saveAction(ActionEvent event) {
		String message = null;
		crmBlockHistory.setCrmPatient(this.selectedPatient);
		crmBlockHistory.setCreatedDate(new Date());
		crmBlockHistory.setCrmUser(FacesUtil.getCurrentUser());
		int result = tablesService.save(crmBlockHistory);
		if (result == 0) {
			if (selectedCampaignTypes.length > 0) {
				for (Integer row : selectedCampaignTypes) {
					CrmBlockHistoryDetail crmBlockHistoryDetail = new CrmBlockHistoryDetail();
					crmBlockHistoryDetail.setCrmBlockHistory(crmBlockHistory);
					crmBlockHistoryDetail
							.setCrmCampaignType(new CrmCampaignType(row));
					result = tablesService.save(crmBlockHistoryDetail);
				}
				this.selectedPatient.setCycle(false);
			} else {
				this.selectedPatient.setCycle(true);
			}
			result = tablesService.save(selectedPatient);
			saved = true;
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
		} else {
			message = FacesUtil.getMessage("gln_general_err");
			FacesUtil.addError(message);	
		}
	}

	@Override
	public void addPatient(ActionEvent event) {
		super.addPatient(event);
		List<CrmBlockHistoryDetail> listDetail = tablesService
				.getListBlockHistoryDetailLast(selectedPatient.getId());
		selectedCampaignTypes =  new Integer[listDetail.size()];
		int index = 0;
		for(CrmBlockHistoryDetail row:listDetail){
			selectedCampaignTypes[index] = row.getCrmCampaignType().getId();
			index++;
		}
	}

}
