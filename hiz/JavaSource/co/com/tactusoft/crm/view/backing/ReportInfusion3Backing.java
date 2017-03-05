package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmInfunsion2;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class ReportInfusion3Backing extends BaseBacking implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CrmInfunsion2> list;

	public ReportInfusion3Backing() {
		newAction();
	}

	public List<CrmInfunsion2> getList() {
		return list;
	}

	public void setList(List<CrmInfunsion2> list) {
		this.list = list;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		// if (listStatus != null && listStatus.size() > 0) {
		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
		list = processService.getInfunsionbyDates(startDateString,
				endDateString);
		// } else {
		// String message = FacesUtil.getMessage("app_no_status");
		// FacesUtil.addInfo(message);
		// }
	}

}
