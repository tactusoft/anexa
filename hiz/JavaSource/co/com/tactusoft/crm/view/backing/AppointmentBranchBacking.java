package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.VwAppointmentBranch;
import co.com.tactusoft.crm.model.entities.VwAppointmentBranchId;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class AppointmentBranchBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<VwAppointmentBranch> list;

	public AppointmentBranchBacking() {
		newAction();
	}

	public List<VwAppointmentBranch> getList() {
		return list;
	}

	public void setList(List<VwAppointmentBranch> list) {
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
			List<Object[]> result = processService.getAppointmentByBranch(
					startDateString, endDateString, listBranchSelected);
			list = new LinkedList<>();
			for (Object[] row : result) {
				VwAppointmentBranch object = new VwAppointmentBranch();
				VwAppointmentBranchId id = new VwAppointmentBranchId();
				id.setBranchId((BigDecimal) row[0]);
				id.setStatus((String) row[2]);
				object.setId(id);
				object.setBranchName((String) row[1]);
				object.setCount((Long) row[3]);
				list.add(object);
			}

		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addInfo(message);
		}
	}

}
