package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmMaterial;

import com.tactusoft.webservice.client.beans.WSBean;

public class CrmMaterialDataModel extends ListDataModel<CrmMaterial> implements
		SelectableDataModel<com.tactusoft.webservice.client.beans.WSBean>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CrmMaterialDataModel() {
	}

	public CrmMaterialDataModel(List<CrmMaterial> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WSBean getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<WSBean> list = (List<WSBean>) getWrappedData();

		for (WSBean row : list) {
			if (row.getCode().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(WSBean car) {
		return car.getCode();
	}
}