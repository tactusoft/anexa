package co.com.tactusoft.crm.postsale.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tactusoft.crm.model.dao.CustomHibernateDao;
import co.com.tactusoft.crm.model.entities.CrmPatient;

@Service
public class VTigerBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomHibernateDao dao;

	public List<CrmPatient> getListPatient() {
		return dao.find("FROM CrmPatient o");
	}

}
