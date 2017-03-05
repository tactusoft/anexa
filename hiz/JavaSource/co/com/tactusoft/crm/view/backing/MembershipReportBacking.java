package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmMembershipReport;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.MembershipRDataModel;

@Named
@Scope("view")
public class MembershipReportBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tableService;

	private List<CrmMembershipReport> list;
	private MembershipRDataModel model;
	private CrmMembershipReport selected;

	private List<CrmMembershipReport> listReport;
	private MembershipRDataModel modelReport;

	public MembershipReportBacking() {
		newAction();
	}

	public List<CrmMembershipReport> getList() {
		return list;
	}

	public void setList(List<CrmMembershipReport> list) {
		this.list = list;
	}

	public MembershipRDataModel getModel() {
		if (model == null) {
			list = tableService.getListMembershipReport();
			model = new MembershipRDataModel(list);

			if (list.size() > 0) {
				selected = list.get(0);
			}
		}
		return model;
	}

	public void setModel(MembershipRDataModel model) {
		this.model = model;
	}

	public List<CrmMembershipReport> getListReport() {
		return listReport;
	}

	public void setListReport(List<CrmMembershipReport> listReport) {
		this.listReport = listReport;
	}

	public MembershipRDataModel getModelReport() {
		return modelReport;
	}

	public void setModelReport(MembershipRDataModel modelReport) {
		this.modelReport = modelReport;
	}

	public CrmMembershipReport getSelected() {
		return selected;
	}

	public void setSelected(CrmMembershipReport selected) {
		this.selected = selected;
	}

	public void newAction() {
		selected = new CrmMembershipReport();
	}

	public void editAction() {
		if (selected != null && selected.getId() != null) {
			idBranch = selected.getCrmBranch().getId();
		}
	}

	public void saveAction() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = false;
		String message = null;
		selected.setCrmBranch(new CrmBranch(idBranch));
		selected.setCrmUser(FacesUtil.getCurrentUser());
		selected.setCreatedDate(new Date());
		int result = tableService.save(selected);
		if (result == 0) {
			list = tableService.getListMembershipReport();
			model = new MembershipRDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
			saved = true;
		} else if (result == -1) {
			String paramValue = FacesUtil.getMessage("doc_code");
			message = FacesUtil.getMessage("msg_record_unique_exception",
					paramValue);
			FacesUtil.addError(message);
		}
		context.addCallbackParam("saved", saved);
	}

	public void searchAction() {
		if (listBranchSelected != null && listBranchSelected.size() > 0) {
			String startDateString = FacesUtil.formatDate(startDate,
					"yyyy-MM-dd");
			String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
			listReport = tableService.getListMembershipReportView(
					listBranchSelected, startDateString, endDateString);
			modelReport = new MembershipRDataModel(listReport);
		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addInfo(message);
		}
	}

}
