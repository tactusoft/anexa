package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmCity;
import co.com.tactusoft.crm.model.entities.CrmMarketingActivity;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.view.datamodel.MarketingActivityDataModel;

@Named
@Scope("view")
public class MarketingActivityBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tableService;

	private List<CrmMarketingActivity> list;
	private MarketingActivityDataModel model;
	private CrmMarketingActivity selected;

	public MarketingActivityBacking() {
		newAction();
	}

	public List<CrmMarketingActivity> getList() {
		return list;
	}

	public void setList(List<CrmMarketingActivity> list) {
		this.list = list;
	}

	public MarketingActivityDataModel getModel() {
		if (model == null) {
			list = tableService.getListMarketingActivity();
			model = new MarketingActivityDataModel(list);

			if (list.size() > 0) {
				selected = list.get(0);
			}
		}
		return model;
	}

	public void setModel(MarketingActivityDataModel model) {
		this.model = model;
	}

	public CrmMarketingActivity getSelected() {
		return selected;
	}

	public void setSelected(CrmMarketingActivity selected) {
		this.selected = selected;
	}

	public void newAction() {
		selected = new CrmMarketingActivity();
		selected.setStatus(Constant.STATE_ACTIVE);
	}

	public void saveAction() {
		String message = null;
		RequestContext context = RequestContext.getCurrentInstance();
		boolean saved = false;

		CrmCity crmCity = mapCity.get(idCity);
		selected.setCrmCity(crmCity);
		selected.setTemplate("En construcción");

		int result = tableService.save(selected);
		if (result == 0) {
			list = tableService.getListMarketingActivity();
			model = new MarketingActivityDataModel(list);
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

	public void editAction() {
		if (selected != null && selected.getId() != null) {
			idCountry = selected.getCrmCity().getCrmRegion().getCrmCountry()
					.getId();
			handleCountryChange();
			idRegion = selected.getCrmCity().getCrmRegion().getId();
			handleRegionChange();
			idCity = selected.getCrmCity().getId();
		}
	}
}
