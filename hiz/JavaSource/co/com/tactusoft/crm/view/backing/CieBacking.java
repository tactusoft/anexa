package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmCie;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.CieDataModel;

@Named
@Scope("view")
public class CieBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tablesService;

	private List<CrmCie> list;
	private CieDataModel model;
	private CrmCie selected;

	public CieBacking() {
		newAction();
	}

	public List<CrmCie> getList() {
		return list;
	}

	public void setList(List<CrmCie> list) {
		this.list = list;
	}

	public CieDataModel getModel() {
		if (model == null) {
			list = tablesService.getListCie();
			model = new CieDataModel(list);

			if (list.size() > 0) {
				selected = list.get(0);
			}
		}
		return model;
	}

	public void setModel(CieDataModel model) {
		this.model = model;
	}

	public CrmCie getSelected() {
		return selected;
	}

	public void setSelected(CrmCie selected) {
		this.selected = selected;
	}

	public void newAction() {
		selected = new CrmCie();
	}

	public void saveAction() {
		String message = null;

		int result = tablesService.saveWithoutId(selected);
		if (result == 0) {
			list = tablesService.getListCie();
			model = new CieDataModel(list);
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
