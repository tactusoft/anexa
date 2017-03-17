package co.com.tactusoft.crm.postsale.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmCampaign;
import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.model.entities.CrmInfunsion2;
import co.com.tactusoft.crm.model.entities.CrmInfusionTag;
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmLogDetail;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.postsale.bo.PostsaleBO;
import co.com.tactusoft.crm.postsale.bo.SapBO;
import co.com.tactusoft.crm.postsale.util.InfunsionSoft;
import co.com.tactusoft.crm.postsale.util.InfusionEntity;
import co.com.tactusoft.crm.postsale.util.FacesUtil;

public class PrincipalLite {

	private static int NO_ATTENDET = 1;
	private static int CONFIRMED = 2;
	private static int CONTROL = 3;
	private static int MEDICATION = 4;

	private BeanFactory beanFactory;
	private PostsaleBO postsaleBO;
	private SapBO sapBO;

	private Map<BigDecimal, CrmCampaign> mapCampaign;
	private Map<BigDecimal, Date> mapCallDates;
	private Map<BigDecimal, Date> mapTomorrows;
	private CrmLog crmLog;

	public void execute() {
		System.out.println("INCIANDO PROCESO...");
		Date currentDate = new Date();
		currentDate = FacesUtil.stringTOSDate("15/03/2017", "dd/MM/yyyy");
		String currentDateString = FacesUtil.formatDate(currentDate,
				"yyyy-MM-dd");
		crmLog = new CrmLog();
		crmLog.setId(1414);

		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			postsaleBO = beanFactory.getBean(PostsaleBO.class);
			sapBO = beanFactory.getBean(SapBO.class);

			int count = 0;
			int numDays = 0;
			Date processDate = FacesUtil.addDaysToDate(currentDate, numDays
					* -1);
			String processDateString = FacesUtil.formatDate(processDate,
					"yyyy-MM-dd");

			List<Integer> ids = new ArrayList<Integer>();
			ids.add(1374);
			ids.add(1380);

			List<InfusionEntity> list374 = InfunsionSoft.getContactsByGroup(
					ids, currentDate);
			for (InfusionEntity row : list374) {
				CrmInfunsion1 crmInfunsion1 = new CrmInfunsion1();
				crmInfunsion1.setEventType(InfunsionSoft.EVENT_TYPE_1);
				crmInfunsion1.setEmail(row.getEmail());
				crmInfunsion1.setEventDate(row.getEventDate());
				crmInfunsion1.setStatus(0);
				crmInfunsion1.setPhone(row.getPhone());
				crmInfunsion1.setNames(row.getNames());
				crmInfunsion1.setIdLog(crmLog.getId());
				crmInfunsion1.setDisease(row.getDisease());
				postsaleBO.save(crmInfunsion1);
			}

			List<CrmInfunsion1> listCrmInfunsion1 = postsaleBO
					.getListCrmInfunsion1(currentDateString);
			count = 0;
			for (CrmInfunsion1 row : listCrmInfunsion1) {
				String startDate = FacesUtil.formatDate(row.getEventDate(),
						"yyyy-MM-dd");
				String endDate = FacesUtil.formatDate(
						FacesUtil.addDaysToDate(row.getEventDate(), 1),
						"yyyy-MM-dd");
				CrmAppointment crmAppointment = postsaleBO
						.getAppointmentByEmail(row.getEmail(), startDate,
								endDate);
				if (crmAppointment != null) {
					row.setStatus(1);
					row.setCrmAppointment(crmAppointment);
					postsaleBO.save(row);
					count++;
					System.out.println("Actualizado: " + row.getEmail());
				} else {
					row.setStatus(2);
					postsaleBO.save(row);
				}
			}

			CrmLogDetail crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail.setMessage("ACTUALIZANDO REGISTROS DE INFUNSIONSOFT: "
					+ count);
			postsaleBO.save(crmLogDetail);

			List<CrmInfusionTag> listCrmInfunsionTag = postsaleBO
					.getListCrmInfunsionTag();

			Date endDate = new Date();
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(currentDate);
			count = 0;
			while (!gcal.getTime().after(endDate)) {
				Date date = gcal.getTime();
				for (CrmInfusionTag row : listCrmInfunsionTag) {
					List<Integer> ids1 = new ArrayList<Integer>();
					ids1.add(row.getCode());
					List<InfusionEntity> listCampaign = InfunsionSoft
							.getContactsByGroup(ids1, date);
					Map<String, String> map = new HashMap<>();
					for (InfusionEntity row1 : listCampaign) {
						map.put(row1.getEmail(), row.getDescription());
					}
					CrmInfunsion2 crmInfunsion2 = new CrmInfunsion2();
					crmInfunsion2.setEventDate(date);
					crmInfunsion2.setCrmInfusionTag(new CrmInfusionTag(row
							.getId()));
					crmInfunsion2.setCountForms(map.size());
					postsaleBO.save(crmInfunsion2);
					count++;
				}
				gcal.add(Calendar.DAY_OF_WEEK, 1);
			}

			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail
					.setMessage("INSERTANDO REGISTROS REPORTE INFUNSIONSOFT: "
							+ count);
			postsaleBO.save(crmLogDetail);

			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail.setMessage("BUSCANDO CITAS INVALIDAS");
			postsaleBO.save(crmLogDetail);

			List<CrmSapMedicationDistinct> listDistinct = postsaleBO
					.getListSapMedicationByLoadStateDistinct(processDateString);
			for (CrmSapMedicationDistinct row : listDistinct) {
				String rowInitDateString = FacesUtil.formatDate(
						FacesUtil.addDaysToDate(row.getDateBill(), -3),
						"yyyy-MM-dd");
				CrmAppointment crmAppointment = postsaleBO
						.getAppointmentNoAttended(row.getDocPatient(),
								rowInitDateString, FacesUtil.formatDate(
										row.getDateBill(), "yyyy-MM-dd"), row
										.getTypeBill());

				if (crmAppointment == null) {
					rowInitDateString = FacesUtil.formatDate(
							FacesUtil.addDaysToDate(row.getDateBill(), -30),
							"yyyy-MM-dd");
					crmAppointment = postsaleBO.getAppointmentNoAttended(row
							.getDocPatient(), rowInitDateString, FacesUtil
							.formatDate(row.getDateBill(), "yyyy-MM-dd"), row
							.getTypeBill());
				}

				if (crmAppointment != null) {
					crmAppointment.setInvalidStatus(true);
					postsaleBO.save(crmAppointment);
					postsaleBO.updateSapMedicationById(row.getIdBill(),
							row.getTypeBill(), crmAppointment.getId(),
							crmLog.getId());
				}
			}

			System.out.println("PROCESO TERMINADO EXITOSAMENTE");
			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail.setMessage("PROCESO TERMINADO EXITOSAMENTE");
			postsaleBO.save(crmLogDetail);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static void main(String[] args) {
		PrincipalLite PrincipalMedication = new PrincipalLite();
		PrincipalMedication.execute();
	}
}
