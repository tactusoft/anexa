package co.com.tactusoft.crm.util;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.tactusoft.crm.controller.bo.BillingBO;
import co.com.tactusoft.crm.model.entities.CrmCalcSchemeDetail;
import co.com.tactusoft.crm.model.entities.CrmCalcSchemeResult;
import co.com.tactusoft.crm.model.entities.CrmTerm;
import co.com.tactusoft.crm.model.entities.CrmTermMaterial;

public class Billing {

	private BeanFactory beanFactory;
	private BillingBO billingBO;
	private String salesOrg;
	private String distrChan;

	public Billing(String salesOrg, String distrChan) {
		this.salesOrg = salesOrg;
		this.distrChan = distrChan;
	}

	public List<CrmCalcSchemeResult> execute(String codMaterial, Double price) {
		Double result = price;
		Double operation = 0d;
		List<CrmCalcSchemeResult> returnList = new LinkedList<>();
		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			billingBO = beanFactory.getBean(BillingBO.class);
			List<CrmCalcSchemeDetail> list = billingBO.getCalSchemaActive();

			System.out.println("Precio: " + price);
			returnList.add(new CrmCalcSchemeResult(null, null, null,
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
							operation = (price * row.getValue().doubleValue()) / 100;
							if (sign.equals("N")) {
								operation = operation * -1;
							}
						}
						System.out
								.println(crmTerm.getName() + ": " + operation);
						result = result + operation;
						returnList.add(new CrmCalcSchemeResult(null, null,
								crmTerm, new BigDecimal(result), false, false));
					}
				}
			}

			if (returnList.size() > 1) {
				returnList.get(returnList.size() - 1).setEnd(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnList;
	}

}
