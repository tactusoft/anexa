package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmInfusionTag;

public class InfusionTagDataModel extends ListDataModel<CrmInfusionTag> implements
		SelectableDataModel<CrmInfusionTag>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfusionTagDataModel() {
	}

	public InfusionTagDataModel(List<CrmInfusionTag> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmInfusionTag getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<CrmInfusionTag> list = (List<CrmInfusionTag>) getWrappedData();

		for (CrmInfusionTag row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(CrmInfusionTag car) {
		return car.getId();
	}
}