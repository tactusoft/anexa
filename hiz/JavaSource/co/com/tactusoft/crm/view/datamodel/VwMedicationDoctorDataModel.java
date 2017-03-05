package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.VwMedicationDoctor;

public class VwMedicationDoctorDataModel extends
		ListDataModel<VwMedicationDoctor> implements
		SelectableDataModel<VwMedicationDoctor>, Serializable {

	private static final long serialVersionUID = 1L;

	public VwMedicationDoctorDataModel() {
	}

	public VwMedicationDoctorDataModel(List<VwMedicationDoctor> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public VwMedicationDoctor getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data
		List<VwMedicationDoctor> list = (List<VwMedicationDoctor>) getWrappedData();
		for (VwMedicationDoctor row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}
		return null;
	}

	@Override
	public Object getRowKey(VwMedicationDoctor row) {
		return row.getId();
	}
}