package co.com.tactusoft.crm.postsale.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmCampaign;
import co.com.tactusoft.crm.model.entities.CrmCampaignDetail;
import co.com.tactusoft.crm.model.entities.CrmCampaignMedication;
import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.model.entities.CrmInfunsion2;
import co.com.tactusoft.crm.model.entities.CrmInfusionTag;
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmLogDetail;
import co.com.tactusoft.crm.model.entities.CrmSapMedication;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationId;
import co.com.tactusoft.crm.model.entities.VwMedication;
import co.com.tactusoft.crm.model.entities.sap.SapMedication;
import co.com.tactusoft.crm.postsale.bo.ProcessBO;
import co.com.tactusoft.crm.postsale.bo.SapBO;
import co.com.tactusoft.crm.postsale.util.InfunsionSoft;
import co.com.tactusoft.crm.postsale.util.InfusionEntity;
import co.com.tactusoft.crm.postsale.util.Utils;

public class PrincipalLite {

	private static int NO_ATTENDET = 1;
	private static int CONFIRMED = 2;
	private static int CONTROL = 3;
	private static int MEDICATION = 4;

	private BeanFactory beanFactory;
	private ProcessBO processBO;
	private SapBO sapBO;

	private Map<BigDecimal, CrmCampaign> mapCampaign;
	private Map<BigDecimal, Date> mapCallDates;
	private Map<BigDecimal, Date> mapTomorrows;
	private CrmLog crmLog;

	public void execute() {
		System.out.println("INCIANDO PROCESO...");
		Date currentDate = new Date();
		currentDate = Utils.stringTOSDate("21/02/2017", "dd/MM/yyyy");
		//String currentDateString = Utils.formatDate(currentDate, "yyyy-MM-dd");

		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			processBO = beanFactory.getBean(ProcessBO.class);
			sapBO = beanFactory.getBean(SapBO.class);

			/*
			 * List<Integer> ids = new ArrayList<Integer>(); ids.add(1374);
			 * ids.add(1380);
			 * 
			 * List<InfusionEntity> list374 = InfunsionSoft.getContactsByGroup(
			 * ids, currentDate); for (InfusionEntity row : list374) {
			 * CrmInfunsion1 crmInfunsion1 = new CrmInfunsion1();
			 * crmInfunsion1.setEventType(InfunsionSoft.EVENT_TYPE_1);
			 * crmInfunsion1.setEmail(row.getEmail());
			 * crmInfunsion1.setEventDate(row.getEventDate());
			 * crmInfunsion1.setStatus(0);
			 * crmInfunsion1.setPhone(row.getPhone());
			 * crmInfunsion1.setNames(row.getNames());
			 * crmInfunsion1.setIdLog(0);
			 * crmInfunsion1.setDisease(row.getDisease());
			 * processBO.save(crmInfunsion1); }
			 * 
			 * List<CrmInfunsion1> listCrmInfunsion1 = processBO
			 * .getListCrmInfunsion1(currentDateString); for (CrmInfunsion1 row
			 * : listCrmInfunsion1) { String startDate =
			 * Utils.formatDate(row.getEventDate(), "yyyy-MM-dd");
			 * CrmAppointment crmAppointment = processBO
			 * .getAppointmentByEmail(row.getEmail(), startDate, startDate); if
			 * (crmAppointment != null) { row.setStatus(1);
			 * row.setCrmAppointment(crmAppointment); processBO.save(row);
			 * System.out.println("Actualizado: " + row.getEmail()); } else {
			 * row.setStatus(2); processBO.save(row); } }
			 */

			/*
			 * List<CrmAppointment> listNoAttendet = processBO
			 * .getListAppointmentNoAttendet(currentDateString);
			 * 
			 * System.out.println("BUSCANDO CITAS INASISTIDAS..." +
			 * listNoAttendet.size());
			 * 
			 * for (CrmAppointment row : listNoAttendet) {
			 * 
			 * try { boolean res = InfunsionSoft.assignNoAttended(row
			 * .getCrmPatient().getEmail()); if (res) {
			 * System.out.println("Se generó tag de no atendido: " +
			 * row.getCrmPatient().getEmail()); CrmInfunsion1 crmInfunsion1 =
			 * new CrmInfunsion1();
			 * crmInfunsion1.setEmail(row.getCrmPatient().getEmail());
			 * crmInfunsion1.setEventDate(new Date());
			 * crmInfunsion1.setStatus(0);
			 * crmInfunsion1.setEventType(InfunsionSoft.EVENT_TYPE_2);
			 * processBO.save(crmInfunsion1); } else {
			 * System.out.println("No se generó tag de no atendido: " +
			 * row.getCrmPatient().getEmail()); } } catch (Exception ex) {
			 * 
			 * } }
			 */

			List<CrmInfusionTag> listCrmInfunsionTag = processBO
					.getListCrmInfunsionTag();

			Date endDate = Utils.stringTOSDate("24/02/2017 21", "dd/MM/yyyy");
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(currentDate);
			while (!gcal.getTime().after(endDate)) {
				Date date = gcal.getTime();
				for (CrmInfusionTag row : listCrmInfunsionTag) {
					List<Integer> ids = new ArrayList<Integer>();
					ids.add(row.getCode());
					List<InfusionEntity> listCampaign = InfunsionSoft
							.getContactsByGroup(ids, date);
					Map<String, String> map = new HashMap<>();
					for(InfusionEntity row1:listCampaign) {
						map.put(row1.getEmail(), row.getDescription());
					}
					CrmInfunsion2 crmInfunsion2 = new CrmInfunsion2();
					crmInfunsion2.setEventDate(date);
					crmInfunsion2.setCrmInfusionTag(new CrmInfusionTag(row.getId()));
					crmInfunsion2.setCountForms(map.size());
					processBO.save(crmInfunsion2);
				}
				gcal.add(Calendar.DAY_OF_WEEK, 1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static void main(String[] args) {
		PrincipalLite PrincipalMedication = new PrincipalLite();
		PrincipalMedication.execute();
	}
}
