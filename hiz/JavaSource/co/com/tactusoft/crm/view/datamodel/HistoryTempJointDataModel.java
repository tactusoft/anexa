package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmOdontologyTempJoint;

public class HistoryTempJointDataModel extends
		ListDataModel<CrmOdontologyTempJoint> implements
		SelectableDataModel<CrmOdontologyTempJoint>, Serializable {

	private static final long serialVersionUID = 1L;

	public HistoryTempJointDataModel() {
	}

	public HistoryTempJointDataModel(List<CrmOdontologyTempJoint> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmOdontologyTempJoint getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<CrmOdontologyTempJoint> list = (List<CrmOdontologyTempJoint>) getWrappedData();

		for (CrmOdontologyTempJoint row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(CrmOdontologyTempJoint row) {
		return row.getId();
	}
}