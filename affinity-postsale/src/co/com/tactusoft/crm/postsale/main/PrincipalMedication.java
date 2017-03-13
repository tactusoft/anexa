package co.com.tactusoft.crm.postsale.main;

import java.math.BigDecimal;
import java.util.Date;
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
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmLogDetail;
import co.com.tactusoft.crm.model.entities.CrmSapMedication;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationId;
import co.com.tactusoft.crm.model.entities.VwMedication;
import co.com.tactusoft.crm.model.entities.sap.SapMedication;
import co.com.tactusoft.crm.postsale.bo.PostsaleBO;
import co.com.tactusoft.crm.postsale.bo.SapBO;
import co.com.tactusoft.crm.postsale.util.Utils;

public class PrincipalMedication {

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
		// Date currentDate = new Date();
		// Date currentDate = Utils
		// .stringTOSDate("04/10/2015 21", "dd/MM/yyyy HH");
		// String currentDateString = Utils.formatDate(currentDate,
		// "yyyy-MM-dd");
		CrmLogDetail crmLogDetail = new CrmLogDetail();

		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			processBO = beanFactory.getBean(PostsaleBO.class);
			sapBO = beanFactory.getBean(SapBO.class);
			int count = 0;

			mapCampaign = new HashMap<BigDecimal, CrmCampaign>();
			mapCallDates = new HashMap<BigDecimal, Date>();
			mapTomorrows = new HashMap<BigDecimal, Date>();

			crmLog = new CrmLog();
			crmLog.setId(1118);

			List<CrmSapMedication> listAddMedication = new LinkedList<CrmSapMedication>();
			List<SapMedication> listMedication = sapBO.getListSAPMedication(16);
			for (SapMedication row : listMedication) {
				CrmSapMedication crmSapMedication = new CrmSapMedication(
						new CrmSapMedicationId(row.getId().getIdBill(), row
								.getId().getPosBill()), row.getDateBill(),
						row.getTypeBill(), row.getIdPatient(),
						row.getDocPatient(), row.getIdMaterial(),
						row.getGrpMaterial(), row.getPositionType(),
						row.getNameMaterial(), row.getUnit(), row.getAmount(),
						row.getIdCanal(), row.getIdSalesOff(),
						row.getIdInterlocutor(), row.getUserSap(), null, null,
						row.getTimeSap());
				listAddMedication.add(crmSapMedication);
			}

			for (CrmSapMedication row : listAddMedication) {
				processBO.save(row);
				count++;
			}

			List<CrmSapMedicationDistinct> listDistinct = processBO
					.getListSapMedicationByLoadStateDistinct();
			count = 0;
			for (CrmSapMedicationDistinct row : listDistinct) {
				String rowInitDateString = Utils.formatDate(
						Utils.addDaysToDate(row.getDateBill(), -3),
						"yyyy-MM-dd");
				CrmAppointment crmAppointment = processBO.getAppointment(
						row.getDocPatient(), rowInitDateString,
						Utils.formatDate(row.getDateBill(), "yyyy-MM-dd"),
						row.getTypeBill());

				if (crmAppointment == null) {
					rowInitDateString = Utils.formatDate(
							Utils.addDaysToDate(row.getDateBill(), -30),
							"yyyy-MM-dd");
					crmAppointment = processBO.getAppointment(
							row.getDocPatient(), rowInitDateString,
							Utils.formatDate(row.getDateBill(), "yyyy-MM-dd"),
							row.getTypeBill());
				}

				if (crmAppointment != null) {
					processBO.updateSapMedicationById(row.getIdBill(),
							row.getTypeBill(), crmAppointment.getId(),
							crmLog.getId());
					count++;
					System.out.println("Actualizado: " + row.getIdBill());
				}
			}

			System.out.println("Se actualizaron: " + count);

			/*
			 * List<CrmSapMedicationDistinct> listDistinct = processBO
			 * .getListSapMedicationByLoadStateDistinct(""); count = 0; for
			 * (CrmSapMedicationDistinct row : listDistinct) { String
			 * rowInitDateString = Utils.formatDate(
			 * Utils.addDaysToDate(row.getDateBill(), -3), "yyyy-MM-dd");
			 * CrmAppointment crmAppointment = processBO.getAppointment(
			 * row.getIdPatient(), rowInitDateString,
			 * Utils.formatDate(row.getDateBill(), "yyyy-MM-dd"),
			 * row.getTypeBill()); if (crmAppointment != null) {
			 * processBO.updateSapMedicationById(row.getIdBill(),
			 * row.getTypeBill(), crmAppointment.getId(), crmLog.getId());
			 * count++; System.out.println("Actualizado: " + row.getIdBill()); }
			 * }
			 * 
			 * crmLog = new CrmLog(); crmLog.setId(874); count = 0;
			 * List<CrmCampaignDetail> det = processBO
			 * .getListCampaignDetailMedication1(crmLog); for (CrmCampaignDetail
			 * row : det) { for (VwMedication row2 : processBO
			 * .getListVwMedicationByAppointment(row
			 * .getCrmAppointment().getId())) { CrmCampaignMedication
			 * crmCampaignMedication = new CrmCampaignMedication(
			 * row.getCrmCampaign(), String.valueOf(row2 .getCodMaterial()),
			 * row2.getDescMaterial(), row2.getSold(), row2.getUnit(),
			 * row2.getSaleUnit(), row2.getPosology());
			 * processBO.save(crmCampaignMedication); count++; } }
			 * 
			 * crmLogDetail = new CrmLogDetail(); crmLogDetail.setId(6703);
			 * crmLogDetail.setCrmLog(crmLog);
			 * crmLogDetail.setLogDate(currentDate); crmLogDetail
			 * .setMessage("BUSCANDO MEDICAMENTOS Y TERAPIAS NO FACTURADAS: " +
			 * count); processBO.save(crmLogDetail);
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("PROCESO TERMINADO CON ERRORES");
			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(new Date());
			crmLogDetail.setMessage("PROCESO TERMINADO CON ERRORES: "
					+ ex.getMessage());
			// processBO.save(crmLogDetail);
		}

	}

	public static void main(String[] args) {
		PrincipalMedication PrincipalMedication = new PrincipalMedication();
		PrincipalMedication.execute();
	}
}
