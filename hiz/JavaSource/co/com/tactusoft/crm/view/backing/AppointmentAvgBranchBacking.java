package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.VwAppointmentAvg;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class AppointmentAvgBranchBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<VwAppointmentAvg> list;

	public AppointmentAvgBranchBacking() {
		newAction();
	}

	public List<VwAppointmentAvg> getList() {
		return list;
	}

	public void setList(List<VwAppointmentAvg> list) {
		this.list = list;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		if (listBranchSelected != null && listBranchSelected.size() > 0) {
			String startDateString = FacesUtil.formatDate(startDate,
					"yyyy-MM-dd");
			String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
			list = processService.getAppointmentAvgByBranch(startDateString,
					endDateString, listBranchSelected);
		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addInfo(message);
		}
	}

}
