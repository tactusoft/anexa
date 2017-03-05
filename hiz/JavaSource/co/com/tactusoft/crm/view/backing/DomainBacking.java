package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmDomain;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.DomainDataModel;

@Named
@Scope("view")
public class DomainBacking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tablesService;

	private List<CrmDomain> list;
	private DomainDataModel model;
	private CrmDomain selected;

	private List<String> listGroup;

	public DomainBacking() {
		newAction();
	}

	public List<CrmDomain> getList() {
		return list;
	}

	public void setList(List<CrmDomain> list) {
		this.list = list;
	}

	public DomainDataModel getModel() {
		if (model == null) {
			list = tablesService.getListDomain();
			model = new DomainDataModel(list);
			if (list.size() > 0) {
				selected = list.get(0);
				Map<String, String> map = new HashMap<>();
				for (CrmDomain row : list) {
					map.put(row.getGroupBy(), row.getGroupBy());
				}

				listGroup = new LinkedList<>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					listGroup.add(entry.getValue());
				}
			}
		}
		return model;
	}

	public void setModel(DomainDataModel model) {
		this.model = model;
	}

	public CrmDomain getSelected() {
		return selected;
	}

	public void setSelected(CrmDomain selected) {
		this.selected = selected;
	}

	public List<String> getListGroup() {
		return listGroup;
	}

	public void setListGroup(List<String> listGroup) {
		this.listGroup = listGroup;
	}

	public void newAction() {
		selected = new CrmDomain();
	}

	public void saveAction() {
		String message = null;
		boolean validate = false;
		try {
			BigDecimal id = tablesService.getId(CrmDomain.class);
			if (selected.getId() == null) {
				selected.setId(id);
			}
			tablesService.saveWithExeception(selected);
			list.add(selected);
			model = new DomainDataModel(list);
			message = FacesUtil.getMessage("msg_record_ok");
			FacesUtil.addInfo(message);
			validate = true;
		} catch (ConstraintViolationException ex) {
			String paramValue = FacesUtil.getMessage("title_domain");
			message = FacesUtil.getMessage("msg_record_unique_exception",
					paramValue);
			FacesUtil.addError(message);
		} catch (Exception ex) {
			message = ex.getMessage();
			FacesUtil.addError(message);
		}
		RequestContext.getCurrentInstance().addCallbackParam("validate",
				validate);
	}
}
