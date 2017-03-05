package co.com.tactusoft.crm.view.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.IndPatientAppointment;
import co.com.tactusoft.crm.model.entities.IndRepurchase;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.beans.IndicatorBean;
import co.com.tactusoft.crm.view.beans.OpportunityAgenda;

@Named
@Scope("view")
public class IndicatorsBacking extends BaseBacking {

	private static final long serialVersionUID = 1L;

	private int type;
	private Date startDate;
	private Date endDate;

	private List<OpportunityAgenda> listOpportunityAgenda;
	private List<IndPatientAppointment> listIndPatientAppointment;
	private List<IndRepurchase> listIndRepurchase;

	private List<CrmAppointment> listAppointment100;
	private List<CrmAppointment> listAppointment5099;
	private List<CrmAppointment> listAppointment50;
	private List<CrmAppointment> listAppointment0;
	private List<CrmAppointment> listAppointmentFree;
	private List<IndicatorBean> listAppointmentTotal;

	private double totals;
	private double totalPercent;

	private String target;

	private enum IndicatorType {
		OPPORTUNITY_AGENDA, CONTROL_NUM, BEHAVIOR_SALE, REPURCHASE
	}

	public IndicatorsBacking() {
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

	public List<OpportunityAgenda> getListOpportunityAgenda() {
		return listOpportunityAgenda;
	}

	public void setListOpportunityAgenda(
			List<OpportunityAgenda> listOpportunityAgenda) {
		this.listOpportunityAgenda = listOpportunityAgenda;
	}

	public List<IndPatientAppointment> getListIndPatientAppointment() {
		return listIndPatientAppointment;
	}

	public void setListIndPatientAppointment(
			List<IndPatientAppointment> listIndPatientAppointment) {
		this.listIndPatientAppointment = listIndPatientAppointment;
	}

	public List<IndRepurchase> getListIndRepurchase() {
		return listIndRepurchase;
	}

	public void setListIndRepurchase(List<IndRepurchase> listIndRepurchase) {
		this.listIndRepurchase = listIndRepurchase;
	}

	public List<CrmAppointment> getListAppointment100() {
		return listAppointment100;
	}

	public void setListAppointment100(List<CrmAppointment> listAppointment100) {
		this.listAppointment100 = listAppointment100;
	}

	public List<CrmAppointment> getListAppointment5099() {
		return listAppointment5099;
	}

	public void setListAppointment5099(List<CrmAppointment> listAppointment5099) {
		this.listAppointment5099 = listAppointment5099;
	}

	public List<CrmAppointment> getListAppointment50() {
		return listAppointment50;
	}

	public void setListAppointment50(List<CrmAppointment> listAppointment50) {
		this.listAppointment50 = listAppointment50;
	}

	public List<CrmAppointment> getListAppointment0() {
		return listAppointment0;
	}

	public void setListAppointment0(List<CrmAppointment> listAppointment0) {
		this.listAppointment0 = listAppointment0;
	}

	public List<CrmAppointment> getListAppointmentFree() {
		return listAppointmentFree;
	}

	public void setListAppointmentFree(List<CrmAppointment> listAppointmentFree) {
		this.listAppointmentFree = listAppointmentFree;
	}

	public List<IndicatorBean> getListAppointmentTotal() {
		return listAppointmentTotal;
	}

	public void setListAppointmentTotal(List<IndicatorBean> listAppointmentTotal) {
		this.listAppointmentTotal = listAppointmentTotal;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isDisabledExport() {
		boolean result = true;
		if (listOpportunityAgenda != null && listOpportunityAgenda.size() > 0
				&& type == IndicatorType.OPPORTUNITY_AGENDA.ordinal()) {
			result = false;
		} else if (listIndPatientAppointment != null
				&& listIndPatientAppointment.size() > 0
				&& type == IndicatorType.CONTROL_NUM.ordinal()) {
			result = false;
		} else if (listIndRepurchase != null && listIndRepurchase.size() > 0
				&& type == IndicatorType.REPURCHASE.ordinal()) {
			result = false;
		} else if (listAppointment100 != null && listAppointment100.size() > 0
				&& type == IndicatorType.BEHAVIOR_SALE.ordinal()) {
			result = false;
		}
		return result;
	}

	public double getTotals() {
		return totals;
	}

	public void setTotals(double totals) {
		this.totals = totals;
	}

	public double getTotalPercent() {
		return totalPercent;
	}

	public void setTotalPercent(double totalPercent) {
		this.totalPercent = totalPercent;
	}

	public void newAction(ActionEvent event) {
		type = IndicatorType.OPPORTUNITY_AGENDA.ordinal();

		listOpportunityAgenda = new ArrayList<OpportunityAgenda>();
		listIndPatientAppointment = new ArrayList<IndPatientAppointment>();

		startDate = new Date();
		endDate = FacesUtil.addDaysToDate(startDate, 30);

		target = "selectedTable";
	}

	public void searchAction(ActionEvent event) {
		if (listBranchSelected != null && listBranchSelected.size() > 0) {
			if (type == IndicatorType.OPPORTUNITY_AGENDA.ordinal()) {
				listOpportunityAgenda = processService
						.getListOpportunityAgenda(listBranchSelected,
								mapCrmBranchByUser, startDate, endDate);
			} else if (type == IndicatorType.CONTROL_NUM.ordinal()) {
				listIndPatientAppointment = processService
						.getListIndPatientAppointment(listBranchSelected,
								startDate, endDate);
			} else if (type == IndicatorType.REPURCHASE.ordinal()) {
				listIndRepurchase = processService.getListIndRepurchase(
						listBranchSelected, startDate, endDate);
			} else {
				listAppointment100 = processService.getListAppointment100(
						listBranchSelected, startDate, endDate);
				listAppointment5099 = processService.getListAppointment5099(
						listBranchSelected, startDate, endDate);
				listAppointment50 = processService.getListAppointment50(
						listBranchSelected, startDate, endDate);
				listAppointment0 = processService.getListAppointment0(
						listBranchSelected, startDate, endDate);
				listAppointmentFree = processService.getListAppointmentFree(
						listBranchSelected, startDate, endDate);

				int valueAppointment100 = listAppointment100 != null ? listAppointment100
						.size() : 0;
				int valueAppointment5099 = listAppointment5099 != null ? listAppointment5099
						.size() : 0;
				int valueAppointment50 = listAppointment50 != null ? listAppointment50
						.size() : 0;
				int valueAppointment0 = listAppointment0 != null ? listAppointment0
						.size() : 0;
				int valueAppointmentFree = listAppointmentFree != null ? listAppointmentFree
						.size() : 0;

				totals = valueAppointment100 + valueAppointment5099
						+ valueAppointment50 + valueAppointment0
						+ valueAppointmentFree;

				listAppointmentTotal = new ArrayList<IndicatorBean>();

				String label = FacesUtil.getMessage("ind_3_1");
				double percent = (valueAppointment100 / totals) * 100;
				totalPercent = percent;
				listAppointmentTotal.add(new IndicatorBean(label,
						valueAppointment100, percent));
				label = FacesUtil.getMessage("ind_3_2");
				percent = (valueAppointment5099 / totals) * 100;
				totalPercent = totalPercent + percent;
				listAppointmentTotal.add(new IndicatorBean(label,
						valueAppointment5099, percent));
				label = FacesUtil.getMessage("ind_3_3");
				percent = (valueAppointment50 / totals) * 100;
				totalPercent = totalPercent + percent;
				listAppointmentTotal.add(new IndicatorBean(label,
						valueAppointment50, percent));
				label = FacesUtil.getMessage("ind_3_4");
				percent = (valueAppointment0 / totals) * 100;
				totalPercent = totalPercent + percent;
				listAppointmentTotal.add(new IndicatorBean(label,
						valueAppointment0, percent));
				label = FacesUtil.getMessage("ind_3_5");
				percent = (valueAppointmentFree / totals) * 100;
				totalPercent = totalPercent + percent;
				totalPercent = Math.ceil(totalPercent);
				listAppointmentTotal.add(new IndicatorBean(label,
						valueAppointmentFree, percent));
			}
		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addWarn(message);
		}
	}

	public void handleTypeChange() {
		if (type == IndicatorType.OPPORTUNITY_AGENDA.ordinal()) {
			target = "selectedTable";
		} else if (type == IndicatorType.CONTROL_NUM.ordinal()) {
			target = "selectedTableControlNum";
		} else if (type == IndicatorType.REPURCHASE.ordinal()) {
			target = "selectedTableRepurchase";
		} else {
			target = null;
		}
	}

}
