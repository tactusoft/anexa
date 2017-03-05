package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmHistoryHomeopathic;

public class HistoryHomeopathicDataModel extends ListDataModel<CrmHistoryHomeopathic> implements
		SelectableDataModel<CrmHistoryHomeopathic>, Serializable {

	private static final long serialVersionUID = 1L;

	public HistoryHomeopathicDataModel() {
	}

	public HistoryHomeopathicDataModel(List<CrmHistoryHomeopathic> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmHistoryHomeopathic getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<CrmHistoryHomeopathic> list = (List<CrmHistoryHomeopathic>) getWrappedData();

		for (CrmHistoryHomeopathic row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(CrmHistoryHomeopathic row) {
		return row.getId();
	}
}