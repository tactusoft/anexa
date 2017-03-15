package co.com.tactusoft.crm.postsale.main;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmCampaign;
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.postsale.bo.PostsaleBO;
import co.com.tactusoft.crm.postsale.bo.SapBO;
import co.com.tactusoft.crm.postsale.util.Utils;

public class PrincipalLite {

	private static int NO_ATTENDET = 1;
	private static int CONFIRMED = 2;
	private static int CONTROL = 3;
	private static int MEDICATION = 4;

	private BeanFactory beanFactory;
	private PostsaleBO processBO;
	private SapBO sapBO;

	private Map<BigDecimal, CrmCampaign> mapCampaign;
	private Map<BigDecimal, Date> mapCallDates;
	private Map<BigDecimal, Date> mapTomorrows;
	private CrmLog crmLog;

	public void execute() {
		System.out.println("INCIANDO PROCESO...");
		Date currentDate = new Date();
		currentDate = Utils.stringTOSDate("13/03/2017", "dd/MM/yyyy");
		String currentDateString = Utils.formatDate(currentDate, "yyyy-MM-dd");

		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			processBO = beanFactory.getBean(PostsaleBO.class);
			sapBO = beanFactory.getBean(SapBO.class);

			int count = 0;
			int numDays = 90;
			// numDays--;
			Date processDate = Utils.addDaysToDate(currentDate, numDays * -1);
			String processDateString = Utils.formatDate(processDate,
					"yyyy-MM-dd");

			count = 0;
			List<CrmSapMedicationDistinct> listDistinct = processBO
					.getListSapMedicationByLoadStateDistinct(processDateString);
			for (CrmSapMedicationDistinct row : listDistinct) {
				String rowInitDateString = Utils.formatDate(
						Utils.addDaysToDate(row.getDateBill(), -3),
						"yyyy-MM-dd");
				CrmAppointment crmAppointment = processBO
						.getAppointmentNoAttended(row.getDocPatient(),
								rowInitDateString, Utils.formatDate(
										row.getDateBill(), "yyyy-MM-dd"), row
										.getTypeBill());

				if (crmAppointment == null) {
					rowInitDateString = Utils.formatDate(
							Utils.addDaysToDate(row.getDateBill(), -30),
							"yyyy-MM-dd");
					crmAppointment = processBO.getAppointmentNoAttended(
							row.getDocPatient(), rowInitDateString,
							Utils.formatDate(row.getDateBill(), "yyyy-MM-dd"),
							row.getTypeBill());
				}

				if (crmAppointment != null) {
					crmAppointment.setInvalidStatus(true);
					processBO.save(crmAppointment);
					processBO.updateSapMedicationById(row.getIdBill(),
							row.getTypeBill(), crmAppointment.getId(),
							0);
					count++;
				}
			}

			System.out.println(count);
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static void main(String[] args) {
		PrincipalLite PrincipalMedication = new PrincipalLite();
		PrincipalMedication.execute();
	}
}
