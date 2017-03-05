package co.com.tactusoft.crm.view.backing;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.VwPatientAccountMarketing;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.VwPatientAccountMarketingDataModel;

@Named
@Scope("view")
public class ProspectMarketingBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private List<VwPatientAccountMarketing> listProspect;
	private VwPatientAccountMarketingDataModel modelProspect;

	private Date startDate;
	private Date endDate;

	public ProspectMarketingBacking() {
		newAction();
	}

	@PostConstruct
	public void init() {

	}

	public List<VwPatientAccountMarketing> getListProspect() {
		return listProspect;
	}

	public void setListProspect(List<VwPatientAccountMarketing> listProspect) {
		this.listProspect = listProspect;
	}

	public VwPatientAccountMarketingDataModel getModelProspect() {
		return modelProspect;
	}

	public void setModelProspect(
			VwPatientAccountMarketingDataModel modelProspect) {
		this.modelProspect = modelProspect;
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

	public void newAction() {
		selectedsBranchObject = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction(ActionEvent event) {
		String message = null;
		if (listBranchSelected == null || listBranchSelected.size() == 0) {
			message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addInfo(message);
		} else if (idGroup == null || idGroup.intValue() == 0) {
			message = FacesUtil.getMessage("app_no_group");
			FacesUtil.addInfo(message);
		} else {
			String startDateString = FacesUtil.formatDate(startDate,
					"yyyy-MM-dd");
			String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");

			listProspect = tablesService
					.getListPatientByAccount(listBranchSelected,
							startDateString, endDateString, idGroup);
			modelProspect = new VwPatientAccountMarketingDataModel(listProspect);
		}
	}
}
