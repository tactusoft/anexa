package co.com.tactusoft.crm.postsale.bo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tactusoft.crm.model.entities.sap.SapMedication;
import co.com.tactusoft.crm.model.util.Parameter;
import co.com.tactusoft.crm.postsale.model.dao.sap.SapCustomHibernateDao;

@Service
public class SapBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SapCustomHibernateDao dao;

	public List<SapMedication> getListSAPMedication(int days) {
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter("pdays", days));
		return dao.findNamedQuery("SapMedication.findByDateBill", parameters);
	}
}
