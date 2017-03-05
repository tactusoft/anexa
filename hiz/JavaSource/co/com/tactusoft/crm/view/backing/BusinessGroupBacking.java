package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.primefaces.model.DualListModel;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmBusinessGroup;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.BusinessGroupDataModel;

@Named
@Scope("view")
public class BusinessGroupBacking extends BaseBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	private CrmBusinessGroup selected;
	private List<CrmBusinessGroup> list;
	private BusinessGroupDataModel model;
	private DualListModel<CrmBranch> listDetail;

	public BusinessGroupBacking() {

	}

	@PostConstruct
	public void init() {
		newAction();
		list = tablesService.getListBusinessGroup();
		model = new BusinessGroupDataModel(list);
		handleClose(null);
	}

	public CrmBusinessGroup getSelected() {
		return selected;
	}

	public void setSelected(CrmBusinessGroup selected) {
		this.selected = selected;
	}

	public List<CrmBusinessGroup> getList() {
		return list;
	}

	public void setList(List<CrmBusinessGroup> list) {
		this.list = list;
	}

	public BusinessGroupDataModel getModel() {
		return model;
	}

	public void setModel(BusinessGroupDataModel model) {
		this.model = model;
	}

	public DualListModel<CrmBranch> getListDetail() {
		return listDetail;
	}

	public void setListDetail(DualListModel<CrmBranch> listDetail) {
		this.listDetail = listDetail;
	}

	public void newAction() {
		selected = new CrmBusinessGroup();
		selected.setStatus(Constant.STATE_ACTIVE);
		List<CrmBranch> target = new LinkedList<CrmBranch>();
		List<CrmBranch> source = tablesService.getListBranchActive1000();
		listDetail = new DualListModel<>(source, target);
	}

	public void handleClose(CloseEvent event) {
		if (list != null & !list.isEmpty()) {
			selected = list.get(0);
		}
	}

	public void refreshDetailAction() {
		List<CrmBranch> source = new LinkedList<CrmBranch>();
		List<CrmBranch> target = tablesService
				.getListBranchbyBusinessGroup(selected.getId());
		if (target == null || target.isEmpty()) {
			source = tablesService.getListBranchActive1000();
		} else {
			for (CrmBranch row : tablesService.getListBranchActive1000()) {
				boolean exits = false;
				for (CrmBranch avb : target) {
					if (avb.getId().intValue() == row.getId().intValue()) {
						exits = true;
						break;
					}
				}

				if (!exits) {
					source.add(row);
				}
			}
		}
		listDetail = new DualListModel<>(source, target);
	}

	public void saveAction() {
		String message = null;
		int result = tablesService.save(selected);
		if (result == 0) {
			tablesService.updateBranchbyBusinessGroup(selected.getId(),
					listDetail.getTarget());
			list = tablesService.getListBusinessGroup();
			model = new BusinessGroupDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
		} else if (result == -1) {
			String paramValue = FacesUtil.getMessage("bra_sales_org");
			message = FacesUtil.getMessage("msg_record_unique_exception",
					paramValue);
			FacesUtil.addError(message);

		}
	}

}
