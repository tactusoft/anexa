package co.com.tactusoft.crm.postsale.main;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tactusoft.webservice.client.execute.SugarWS;

import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.postsale.bo.VTigerBO;
import co.com.tactusoft.crm.postsale.util.Utils;

public class VTiger {

	private BeanFactory beanFactory;
	private VTigerBO vTigerBO;

	public void execute() {
		try {
			SugarWS createSugarContact = new SugarWS(
					"http://192.168.1.47/vtigercrm6", "admin",
					"iMc5y2W2cDQg2vJr");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			vTigerBO = beanFactory.getBean(VTigerBO.class);
			List<CrmPatient> list = vTigerBO.getListPatient();
			for (CrmPatient row : list) {
				String city = row.getCrmCity() != null ? row.getCrmCity()
						.getName() : null;
				String region = row.getCrmCity() != null ? row.getCrmCity()
						.getCrmRegion().getName() : null;
				String country = row.getCrmCity() != null ? row.getCrmCity()
						.getCrmRegion().getCrmCountry().getName() : null;

				if (Utils.isEmptyOrBlank(row.getDoc())
						|| row.getDoc().equals(row.getCodeSap())) {
					createSugarContact.createLead(row.getId().intValue(),
							row.getDoc(), row.getSurnames(), row.getNames(),
							country, region, city, row.getAddress(),
							row.getZipCode(), row.getPhoneNumber(),
							row.getCellNumber(), row.getEmail(), row
									.getCrmPatient().getId().intValue());
				} else {
					createSugarContact.createAccount(row.getId().intValue(),
							row.getDoc(), row.getSurnames(), row.getNames(),
							country, region, city, row.getAddress(),
							row.getZipCode(), row.getPhoneNumber(),
							row.getCellNumber(), row.getEmail());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		VTiger tiger = new VTiger();
		tiger.execute();
	}

}
