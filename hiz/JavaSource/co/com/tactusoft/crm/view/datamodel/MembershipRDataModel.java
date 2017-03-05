package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmMembershipReport;

public class MembershipRDataModel extends ListDataModel<CrmMembershipReport> implements
		SelectableDataModel<CrmMembershipReport>, Serializable {

	private static final long serialVersionUID = 1L;

	public MembershipRDataModel() {
	}

	public MembershipRDataModel(List<CrmMembershipReport> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmMembershipReport getRowData(String rowKey) {
		// In a real app, a more efficient way like a query by rowKey should be
		// implemented to deal with huge data

		List<CrmMembershipReport> list = (List<CrmMembershipReport>) getWrappedData();

		for (CrmMembershipReport row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(CrmMembershipReport car) {
		return car.getId();
	}
}