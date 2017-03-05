package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.VwPatientAccountMarketing;

public class VwPatientAccountMarketingDataModel extends ListDataModel<VwPatientAccountMarketing> implements
		SelectableDataModel<VwPatientAccountMarketing>, Serializable {

	private static final long serialVersionUID = 1L;

	public VwPatientAccountMarketingDataModel() {
	}

	public VwPatientAccountMarketingDataModel(List<VwPatientAccountMarketing> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public VwPatientAccountMarketing getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<VwPatientAccountMarketing> list = (List<VwPatientAccountMarketing>) getWrappedData();

		for (VwPatientAccountMarketing row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(VwPatientAccountMarketing car) {
		return car.getId();
	}
}