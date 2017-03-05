package co.com.tactusoft.crm.view.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.IndPatientAppointment;
import co.com.tactusoft.crm.model.entities.IndRepurchase;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.beans.IndicatorBean;
import co.com.tactusoft.crm.view.beans.OpportunityAgenda;

@Named
@Scope("view")
public class PatientReportBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private int type;
	private Date startDate;
	private Date endDate;

	private List<CrmPatient> list;

	private enum IndicatorType {
		PROSPECT, ACCOUNT, ALL
	}

	public PatientReportBacking() {
		newAction(null);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public List<CrmPatient> getList() {
		return list;
	}

	public void setList(List<CrmPatient> list) {
		this.list = list;
	}

	public boolean isDisabledExport() {
		boolean result = true;
		if (list != null && list.isEmpty()) {
			result = false;
		}
		return result;
	}

	public void newAction(ActionEvent event) {
		type = IndicatorType.ALL.ordinal();
		list = new ArrayList<CrmPatient>();
		startDate = new Date();
		endDate = FacesUtil.addDaysToDate(startDate, 30);
	}

	public void searchAction(ActionEvent event) {
		if (listBranchSelected != null && listBranchSelected.size() > 0) {
			if (type == IndicatorType.ALL.ordinal()) {

			} else if (type == IndicatorType.PROSPECT.ordinal()) {

			} else if (type == IndicatorType.ACCOUNT.ordinal()) {

			}
		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addWarn(message);
		}
	}
}
