package co.com.tactusoft.crm.postsale.main;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmBlockHistoryDetail;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmCampaign;
import co.com.tactusoft.crm.model.entities.CrmCampaignDetail;
import co.com.tactusoft.crm.model.entities.CrmCampaignMedication;
import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.model.entities.CrmInfunsion2;
import co.com.tactusoft.crm.model.entities.CrmInfusionTag;
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmLogDetail;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmSapMedication;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationId;
import co.com.tactusoft.crm.model.entities.CrmUser;
import co.com.tactusoft.crm.model.entities.VwAppointmentMedication;
import co.com.tactusoft.crm.model.entities.VwMedication;
import co.com.tactusoft.crm.model.entities.sap.SapMedication;
import co.com.tactusoft.crm.model.util.FacesUtilModel;
import co.com.tactusoft.crm.postsale.bo.ProcessBO;
import co.com.tactusoft.crm.postsale.bo.SapBO;
import co.com.tactusoft.crm.postsale.util.InfunsionSoft;
import co.com.tactusoft.crm.postsale.util.InfusionEntity;
import co.com.tactusoft.crm.postsale.util.Utils;

public class Principal {

	private static int NO_ATTENDET = 1;
	private static int CONFIRMED = 2;
	private static int CONTROL = 3;
	private static int MEDICATION = 4;

	private BeanFactory beanFactory;
	private ProcessBO processBO;
	private SapBO sapBO;

	private Map<BigDecimal, CrmCampaign> mapCampaign;
	private Map<BigDecimal, Date> mapCallDates;
	// private Map<BigDecimal, Date> mapTomorrows;
	private CrmLog crmLog;

	private void insertDetail(CrmCampaign crmCampaign,
			CrmAppointment crmAppointment, int type) {
		CrmBranch crmBranch = crmAppointment.getCrmBranch();
		Date callDate = mapCallDates.get(crmBranch.getId());
		String callDateString = FacesUtilModel.formatDate(
				mapCallDates.get(crmBranch.getId()), "yyyy-MM-dd");

		CrmUser crmUserCreated = new CrmUser();
		crmUserCreated.setId(new BigDecimal(91));

		if (crmCampaign == null) {
			crmCampaign = new CrmCampaign();

			CrmUser crmUser = processBO
					.getUser(crmBranch, callDateString, type);
			if (crmUser == null) {
				crmUser = new CrmUser();
				crmUser.setId(new BigDecimal(3));
			}

			crmCampaign.setCrmLog(crmLog);
			crmCampaign.setCrmPatient(crmAppointment.getCrmPatient());
			crmCampaign.setCrmBranch(crmBranch);
			crmCampaign.setCrmUser(crmUser);
			crmCampaign.setDateCall(callDate);
			crmCampaign.setState(1);
			crmCampaign.setCrmUserByIdUserCreate(crmUserCreated);
			crmCampaign.setDateCreated(new Date());
			processBO.save(crmCampaign);

			mapCampaign
					.put(crmAppointment.getCrmPatient().getId(), crmCampaign);
		}

		CrmCampaignDetail crmCampaignDetail = new CrmCampaignDetail();
		crmCampaignDetail.setCrmCampaign(crmCampaign);
		crmCampaignDetail.setCrmAppointment(crmAppointment);
		crmCampaignDetail.setIdCampaignType(type);
		crmCampaignDetail.setStatus(0);
		crmCampaignDetail.setCallDate(callDate);
		crmCampaignDetail.setCrmUserByIdUserCreate(crmUserCreated);
		crmCampaignDetail.setDateCreated(new Date());
		processBO.save(crmCampaignDetail);
	}

	public void execute() {
		System.out.println("INCIANDO PROCESO...");
		Date currentDate = new Date();
		currentDate = Utils.stringTOSDate("04/03/2017 21", "dd/MM/yyyy HH");

		String currentDateString = Utils.formatDate(currentDate, "yyyy-MM-dd");
		CrmLogDetail crmLogDetail = new CrmLogDetail();

		try {
			System.out.println("CARGANDO BASE DE DATOS...");
			beanFactory = new ClassPathXmlApplicationContext(
					"spring-config.xml");
			processBO = beanFactory.getBean(ProcessBO.class);
			sapBO = beanFactory.getBean(SapBO.class);
			int count = 0;

			int numDays = processBO.getLogLastDay(currentDate);

			if (numDays > 0) {
				numDays--;
				Date processDate = Utils.addDaysToDate(currentDate, numDays
						* -1);
				String processDateString = Utils.formatDate(processDate,
						"yyyy-MM-dd");

				mapCampaign = new HashMap<BigDecimal, CrmCampaign>();
				mapCallDates = new HashMap<BigDecimal, Date>();
				// mapTomorrows = new HashMap<BigDecimal, Date>();

				crmLog = new CrmLog();
				crmLog.setLogDate(currentDate);
				crmLog.setLogType("POSTVENTA");
				processBO.save(crmLog);

				System.out.println("COMPROBANDO InfunsionSoft...");
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("COMPROBANDO InfunsionSoft...");
				processBO.save(crmLogDetail);
				InfunsionSoft.getContactId("tactusoft@hotmail.com");

				System.out.println("EXTRACCI�N DE POS...");

				List<CrmSapMedication> listAddMedication = new LinkedList<CrmSapMedication>();
				List<SapMedication> listMedication = sapBO
						.getListSAPMedication(numDays);
				for (SapMedication row : listMedication) {
					CrmSapMedication crmSapMedication = new CrmSapMedication(
							new CrmSapMedicationId(row.getId().getIdBill(), row
									.getId().getPosBill()), row.getDateBill(),
							row.getTypeBill(), row.getIdPatient(),
							row.getDocPatient(), row.getIdMaterial(),
							row.getGrpMaterial(), row.getPositionType(),
							row.getNameMaterial(), row.getUnit(),
							row.getAmount(), row.getIdCanal(),
							row.getIdSalesOff(), row.getIdInterlocutor(),
							row.getUserSap(), null, null, row.getTimeSap());
					listAddMedication.add(crmSapMedication);
				}

				for (CrmSapMedication row : listAddMedication) {
					processBO.save(row);
					count++;
				}

				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("EXTRACCI�N DE POS: " + count);
				processBO.save(crmLogDetail);

				System.out.println("Buscar pacientes bloqueados...");

				List<CrmPatient> listBlockPatient = processBO
						.getListPatientBlock();
				Map<String, Boolean> mapBlock = new HashMap<String, Boolean>();
				for (CrmPatient row : listBlockPatient) {
					List<CrmBlockHistoryDetail> listBlockHistoryDetail = processBO
							.getListBlockHistoryDetailLast(row.getId());
					for (CrmBlockHistoryDetail row2 : listBlockHistoryDetail) {
						mapBlock.put(row.getId() + "-"
								+ row2.getCrmCampaignType().getId(), true);
					}
				}

				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("PACIENTES BLOQUEADOS: "
						+ (listBlockPatient == null ? "0" : String
								.valueOf(listBlockPatient.size())));
				processBO.save(crmLogDetail);

				System.out.println("ACTUALIZACI�N DE CAMPA�AS NO ATENDIDAS...");
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail
						.setMessage("ACTUALIZACI�N DE CAMAPA�AS NO ATENDIDAS");
				processBO.save(crmLogDetail);
				processBO.updateCampaign(currentDateString);

				int currentDay = Utils.getCurrentDay(currentDate);

				Date yesterdayDate = null;
				int yesterday = currentDay - 1;
				if (yesterday != 1) {
					yesterdayDate = Utils.addDaysToDate(currentDate, -1);
				} else {
					yesterdayDate = Utils.addDaysToDate(currentDate, -2);
				}
				String yesterdayString = Utils.formatDate(yesterdayDate,
						"yyyy-MM-dd");

				Date tomorrowDate = null;
				int tomorrow = currentDay + 2;
				if (tomorrow != 1) {
					tomorrowDate = Utils.addDaysToDate(currentDate, 2);
				} else {
					tomorrowDate = Utils.addDaysToDate(currentDate, 3);
				}
				String tomorrowString = Utils.formatDate(tomorrowDate,
						"yyyy-MM-dd");

				// Buscando fecha llamada x sucursal
				List<CrmBranch> listCrmBranch = processBO.getListBranchActive();
				for (CrmBranch branch : listCrmBranch) {
					Date callDate = currentDate;
					boolean validate = true;
					do {
						callDate = Utils.addDaysToDate(callDate, 1);
						int day = Utils.getCurrentDay(callDate);
						if (day != 1
								&& processBO.getListHoliday(callDate,
										branch.getId()).isEmpty()) {
							mapCallDates.put(branch.getId(),
									Utils.getDateWithoutTime(callDate));
							validate = false;
							break;
						}
					} while (validate);
				}

				System.out.println("ACTUALIZANDO CITAS...");
				// ACTUALIZAR TODAS LAS CITAS QUE NO FUERON ATENDIDAS

				if (Utils.getCurrentHour(currentDate) >= 20) {
					processBO.updateAppointment(currentDateString);
				} else {
					processBO.updateAppointment(yesterdayString);
				}

				List<CrmAppointment> listNoAttendet = processBO
						.getListAppointmentNoAttendet(currentDateString);

				System.out.println("BUSCANDO CITAS INASISTIDAS...");
				// INASISTENCIA DIA ANTERIOR
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("BUSCANDO CITAS INASISTIDAS... "
						+ listNoAttendet.size() + " - " + currentDateString);
				processBO.save(crmLogDetail);

				count = 0;
				for (CrmAppointment row : listNoAttendet) {
					Boolean result = mapBlock.get(row.getCrmPatient() + "-"
							+ Principal.NO_ATTENDET);
					if (result == null || result == false) {
						CrmCampaign crmCampaign = mapCampaign.get(row
								.getCrmPatient().getId());
						insertDetail(crmCampaign, row, Principal.NO_ATTENDET);
						count++;

						String message = "No se encontro email: "
								+ row.getCrmPatient().getEmail();
						try {
							boolean res = InfunsionSoft.assignNoAttended(row
									.getCrmPatient().getEmail());
							if (res) {
								message = "Se gener� tag de no atendido: "
										+ row.getCrmPatient().getEmail();
								CrmInfunsion1 crmInfunsion1 = new CrmInfunsion1();
								crmInfunsion1.setEmail(row.getCrmPatient()
										.getEmail());
								crmInfunsion1.setEventDate(new Date());
								crmInfunsion1.setStatus(0);
								crmInfunsion1
										.setEventType(InfunsionSoft.EVENT_TYPE_2);
								processBO.save(crmInfunsion1);
							}
						} catch (Exception ex) {

						}

						crmLogDetail = new CrmLogDetail();
						crmLogDetail.setCrmLog(crmLog);
						crmLogDetail.setLogDate(currentDate);
						crmLogDetail.setMessage(message);
						processBO.save(crmLogDetail);
					}
				}

				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("BUSCANDO CITAS INASISTIDAS: " + count);
				processBO.save(crmLogDetail);

				System.out.println("BUSCANDO CITAS CONFIRMADAS...");

				List<CrmAppointment> listConfirmed = processBO
						.getListAppointmentConfirmed(tomorrowString,
								currentDateString);
				count = 0;
				for (CrmAppointment row : listConfirmed) {
					Boolean result = mapBlock.get(row.getCrmPatient() + "-"
							+ Principal.CONFIRMED);
					if (result == null || result == false) {
						CrmCampaign crmCampaign = mapCampaign.get(row
								.getCrmPatient().getId());
						insertDetail(crmCampaign, row, Principal.CONFIRMED);
						count++;
					}
				}

				// CONFIRMADAS DIA SIGUIENTE
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("BUSCANDO CITAS CONFIRMADAS: " + count);
				processBO.save(crmLogDetail);

				System.out
						.println("BUSCANDO PACIENTES CON MAS DE 25 DIAS SIN CITA CONTROL");

				Date ago25Date = Utils.addDaysToDate(currentDate, -25);
				String ago25DateString = Utils.formatDate(ago25Date,
						"yyyy-MM-dd");
				List<CrmAppointment> listControl = processBO
						.getListAppointmentControl(ago25DateString);
				count = 0;
				for (CrmAppointment row : listControl) {
					Boolean result = mapBlock.get(row.getCrmPatient() + "-"
							+ Principal.CONTROL);
					if (result == null || result == false) {
						CrmCampaign crmCampaign = mapCampaign.get(row
								.getCrmPatient().getId());
						insertDetail(crmCampaign, row, Principal.CONTROL);
						count++;
					}
				}

				// SIN CITAS DE CONTROL EN 25 D�AS
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail
						.setMessage("BUSCANDO PACIENTES CON MAS DE 25 DIAS SIN CITA CONTROL: "
								+ count);
				processBO.save(crmLogDetail);

				System.out.println("ACTUALIZANDO FACTURAS CON SUS CITAS");

				List<CrmSapMedicationDistinct> listDistinct = processBO
						.getListSapMedicationByLoadStateDistinct(processDateString);
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
						crmAppointment = processBO.getAppointment(row
								.getDocPatient(), rowInitDateString, Utils
								.formatDate(row.getDateBill(), "yyyy-MM-dd"),
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

				System.out.println("FACTURAS ACTUALIZADAS: " + count);
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("FACTURAS ACTUALIZADAS: " + count);
				processBO.save(crmLogDetail);

				List<CrmAppointment> listClosed = processBO
						.getListAppointmentClosed(processDateString);

				System.out
						.println("BUSCANDO MEDICAMENTOS Y TERAPIAS NO FACTURADAS");
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail
						.setMessage("BUSCANDO MEDICAMENTOS Y TERAPIAS NO FACTURADAS: "
								+ listClosed.size());
				processBO.save(crmLogDetail);

				for (CrmAppointment row : listClosed) {
					List<CrmSapMedication> listSapMedication = processBO
							.getListSapMedicationByAppointment(row.getId());

					boolean validate = false;
					if (listSapMedication.size() > 0) {
						List<VwAppointmentMedication> listVwAppointmentMedication = processBO
								.getListAppointmentMedicationByCode(row
										.getCode());
						if (listVwAppointmentMedication.size() > listSapMedication
								.size()) {
							validate = true;
						}
					} else {
						InfunsionSoft.assignNoStartMedication(row
								.getCrmPatient().getEmail());
						CrmInfunsion1 crmInfunsion1 = new CrmInfunsion1();
						crmInfunsion1.setEmail(row.getCrmPatient().getEmail());
						crmInfunsion1.setEventDate(new Date());
						crmInfunsion1.setStatus(0);
						crmInfunsion1.setEventType(InfunsionSoft.EVENT_TYPE_3);
						processBO.save(crmInfunsion1);
						validate = true;
					}

					if (validate) {
						Boolean result = mapBlock.get(row.getCrmPatient() + "-"
								+ Principal.MEDICATION);
						if (result == null || result == false) {
							CrmCampaign crmCampaign = mapCampaign.get(row
									.getCrmPatient().getId());
							insertDetail(crmCampaign, row, Principal.MEDICATION);
						}
					}
				}

				processBO.updateCrmSapMedication(processDateString,
						crmLog.getId());

				count = 0;
				for (CrmCampaignDetail row : processBO
						.getListCampaignDetailMedication(crmLog)) {
					for (VwMedication row2 : processBO
							.getListVwMedicationByAppointment(row
									.getCrmAppointment().getId())) {
						CrmCampaignMedication crmCampaignMedication = new CrmCampaignMedication(
								row.getCrmCampaign(), String.valueOf(row2
										.getCodMaterial()),
								row2.getDescMaterial(), row2.getSold(),
								row2.getUnit(), row2.getSaleUnit(),
								row2.getPosology());
						processBO.save(crmCampaignMedication);
						count++;
					}
				}

				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail
						.setMessage("BUSCANDO MEDICAMENTOS Y TERAPIAS NO FACTURADAS: "
								+ count);
				processBO.save(crmLogDetail);

				List<Integer> ids = new ArrayList<Integer>();
				ids.add(1374);
				ids.add(1380);

				List<InfusionEntity> list374 = InfunsionSoft
						.getContactsByGroup(ids, currentDate);
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
					processBO.save(crmInfunsion1);
				}

				List<CrmInfunsion1> listCrmInfunsion1 = processBO
						.getListCrmInfunsion1(currentDateString);
				count = 0;
				for (CrmInfunsion1 row : listCrmInfunsion1) {
					String startDate = Utils.formatDate(row.getEventDate(),
							"yyyy-MM-dd");
					String endDate = Utils.formatDate(
							Utils.addDaysToDate(row.getEventDate(), 1),
							"yyyy-MM-dd");
					CrmAppointment crmAppointment = processBO
							.getAppointmentByEmail(row.getEmail(), startDate,
									endDate);
					if (crmAppointment != null) {
						row.setStatus(1);
						row.setCrmAppointment(crmAppointment);
						processBO.save(row);
						count++;
						System.out.println("Actualizado: " + row.getEmail());
					} else {
						row.setStatus(2);
						processBO.save(row);
					}
				}

				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail
						.setMessage("ACTUALIZANDO REGISTROS DE INFUNSIONSOFT: "
								+ count);
				processBO.save(crmLogDetail);

				List<CrmInfusionTag> listCrmInfunsionTag = processBO
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
						processBO.save(crmInfunsion2);
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
				processBO.save(crmLogDetail);

				System.out.println("PROCESO TERMINADO EXITOSAMENTE");
				crmLogDetail = new CrmLogDetail();
				crmLogDetail.setCrmLog(crmLog);
				crmLogDetail.setLogDate(currentDate);
				crmLogDetail.setMessage("PROCESO TERMINADO EXITOSAMENTE");
				processBO.save(crmLogDetail);
			}
		} catch (MalformedURLException | XmlRpcException ex) {
			System.out.println("PROCESO TERMINADO CON ERRORES InfunsionSoft: "
					+ ex.getMessage());
			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail.setMessage("PROCESO TERMINADO CON ERRORES: "
					+ ex.getMessage());
			processBO.save(crmLogDetail);
		} catch (Exception ex) {
			System.out.println("PROCESO TERMINADO CON ERRORES: "
					+ ex.getMessage());
			crmLogDetail = new CrmLogDetail();
			crmLogDetail.setCrmLog(crmLog);
			crmLogDetail.setLogDate(currentDate);
			crmLogDetail.setMessage("PROCESO TERMINADO CON ERRORES: "
					+ ex.getMessage());
			processBO.save(crmLogDetail);
		}

	}

	public static void main(String[] args) {
		Principal principal = new Principal();
		principal.execute();
	}
}
