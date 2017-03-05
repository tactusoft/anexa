package co.com.tactusoft.crm.controller.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.tactusoft.crm.model.dao.CustomHibernateDao;
import co.com.tactusoft.crm.model.entities.CrmCalcScheme;
import co.com.tactusoft.crm.model.entities.CrmCalcSchemeDetail;
import co.com.tactusoft.crm.model.entities.CrmCalcSchemeResult;
import co.com.tactusoft.crm.model.entities.CrmTerm;
import co.com.tactusoft.crm.model.entities.CrmTermMaterial;

@Service
public class BillingBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomHibernateDao dao;

	public List<CrmCalcSchemeDetail> getCalSchemaActive() {
		CrmCalcScheme header = (CrmCalcScheme) dao.find(
				"FROM CrmCalcScheme o WHERE o.status = 1").get(0);
		return dao
				.find("FROM CrmCalcSchemeDetail o WHERE o.crmCalcScheme.id = "
						+ header.getId());
	}

	public List<CrmCalcSchemeResult> execute(String salesOrg, String distrChan,
			String codMaterial, BigDecimal price) {
		Double result = price.doubleValue();
		Double operation = 0d;
		List<CrmCalcSchemeResult> returnList = new LinkedList<>();
		try {
			List<CrmCalcSchemeDetail> list = this.getCalSchemaActive();

			System.out.println("Precio: " + price);
			returnList.add(new CrmCalcSchemeResult(null, "Precio",
					new BigDecimal(result), true, false));
			for (CrmCalcSchemeDetail row : list) {
				CrmTerm crmTerm = row.getCrmTerm();
				if (crmTerm.getSalesOrg().equals(salesOrg)
						&& crmTerm.getDistrChan().equals(distrChan)) {
					List<CrmTermMaterial> listMaterial = crmTerm
							.getCrmTermMaterials();
					boolean exists = false;
					for (CrmTermMaterial det : listMaterial) {
						if (det.getCodMaterial().equals(codMaterial)) {
							exists = true;
							break;
						}
					}
					if (exists) {
						String type = crmTerm.getTypeTerm();
						String sign = crmTerm.getSign();
						operation = row.getValue().doubleValue();
						if (type.equals("I")) {
							if (sign.equals("N")) {
								operation = operation * -1;
							}
						} else {
							operation = (result * row.getValue().doubleValue()) / 100;
							if (sign.equals("N")) {
								operation = operation * -1;
							}
						}
						System.out
								.println(crmTerm.getName() + ": " + operation);
						result = result + operation;
						returnList.add(new CrmCalcSchemeResult(crmTerm, crmTerm
								.getName(), new BigDecimal(operation), false,
								false));
					}
				}
			}

			returnList.add(new CrmCalcSchemeResult(null, "Total",
					new BigDecimal(result), false, true));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnList;
	}

}
