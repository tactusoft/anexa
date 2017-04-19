package co.com.tactusoft.crm.view.backing;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmMaterial;
import co.com.tactusoft.crm.util.FacesUtil;

import com.tactusoft.webservice.client.beans.WSBean;

@Named
@Scope("application")
public class ApplicationBacking {

	private Date currentDate;
	private List<WSBean> listGroupSellers;
	private List<CrmMaterial> listMaterials;
	private List<WSBean> listDocTypes;
	private int consecutive;

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<WSBean> getListGroupSellers() {
		return listGroupSellers;
	}

	public void setListGroupSellers(List<WSBean> listGroupSellers) {
		this.listGroupSellers = listGroupSellers;
	}

	public List<CrmMaterial> getListMaterials() {
		return listMaterials;
	}

	public void setListMaterials(List<CrmMaterial> listMaterials) {
		this.listMaterials = listMaterials;
	}

	public List<WSBean> getListDocTypes() {
		return listDocTypes;
	}

	public void setListDocTypes(List<WSBean> listDocTypes) {
		this.listDocTypes = listDocTypes;
	}

	public int getConsecutive() {
		return consecutive;
	}

	public void setConsecutive(int consecutive) {
		this.consecutive = consecutive;
	}

	public void refresh() {
		this.setCurrentDate(FacesUtil.addDaysToDate(
				FacesUtil.getDateWithoutTime(new Date()), -1));
	}

}
