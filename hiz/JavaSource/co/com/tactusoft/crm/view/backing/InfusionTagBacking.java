package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmInfusionTag;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.InfusionTagDataModel;

@Named
@Scope("view")
public class InfusionTagBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tableService;

	private List<CrmInfusionTag> list;
	private InfusionTagDataModel model;
	private CrmInfusionTag selected;

	public InfusionTagBacking() {
		newAction();
	}

	public List<CrmInfusionTag> getList() {
		return list;
	}

	public void setList(List<CrmInfusionTag> list) {
		this.list = list;
	}

	public InfusionTagDataModel getModel() {
		if (model == null) {
			list = tableService.getListInfusionTag();
			model = new InfusionTagDataModel(list);
			if (list.size() > 0) {
				selected = list.get(0);
			}
		}
		return model;
	}

	public void setModel(InfusionTagDataModel model) {
		this.model = model;
	}

	public CrmInfusionTag getSelected() {
		return selected;
	}

	public void setSelected(CrmInfusionTag selected) {
		this.selected = selected;
	}

	public void newAction() {
		selected = new CrmInfusionTag();
		selected.setStatus(Constant.STATE_ACTIVE);
	}

	public void saveAction() {
		String message = null;

		int result = tableService.save(selected);
		if (result == 0) {
			list = tableService.getListInfusionTag();
			model = new InfusionTagDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
		} else if (result == -1) {
			String paramValue = FacesUtil.getMessage("doc_code");
			message = FacesUtil.getMessage("msg_record_unique_exception",
					paramValue);
			FacesUtil.addError(message);

		}
	}

}
