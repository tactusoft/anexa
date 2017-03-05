package co.com.tactusoft.crm.view.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import co.com.tactusoft.crm.model.entities.CrmDomain;

public class DomainDataModel extends ListDataModel<CrmDomain> implements
		SelectableDataModel<CrmDomain>, Serializable {

	private static final long serialVersionUID = 1L;

	public DomainDataModel() {
	}

	public DomainDataModel(List<CrmDomain> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrmDomain getRowData(String rowKey) {
		// In a real app, a more effiDomainnt way like a query by rowKey should
		// be
		// implemented to deal with huge data

		List<CrmDomain> list = (List<CrmDomain>) getWrappedData();

		for (CrmDomain row : list) {
			if (row.getId().equals(rowKey))
				return row;
		}

		return null;
	}

	@Override
	public Object getRowKey(CrmDomain obj) {
		return obj.getId();
	}
}