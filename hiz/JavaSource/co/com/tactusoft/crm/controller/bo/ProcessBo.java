package co.com.tactusoft.crm.controller.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.xmlrpc.XmlRpcException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import co.com.tactusoft.crm.model.dao.CustomHibernateDao;
import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmCampaignDetail;
import co.com.tactusoft.crm.model.entities.CrmCaseStudy;
import co.com.tactusoft.crm.model.entities.CrmCie;
import co.com.tactusoft.crm.model.entities.CrmCieMaterial;
import co.com.tactusoft.crm.model.entities.CrmConsent;
import co.com.tactusoft.crm.model.entities.CrmConsentText;
import co.com.tactusoft.crm.model.entities.CrmCustodyBelongings;
import co.com.tactusoft.crm.model.entities.CrmDiagnosis;
import co.com.tactusoft.crm.model.entities.CrmDoctor;
import co.com.tactusoft.crm.model.entities.CrmDoctorException;
import co.com.tactusoft.crm.model.entities.CrmDoctorSchedule;
import co.com.tactusoft.crm.model.entities.CrmHistoryBiometric;
import co.com.tactusoft.crm.model.entities.CrmHistoryConsultant;
import co.com.tactusoft.crm.model.entities.CrmHistoryHistory;
import co.com.tactusoft.crm.model.entities.CrmHistoryHomeopathic;
import co.com.tactusoft.crm.model.entities.CrmHistoryIridology;
import co.com.tactusoft.crm.model.entities.CrmHistoryOrganometry;
import co.com.tactusoft.crm.model.entities.CrmHistoryPhysique;
import co.com.tactusoft.crm.model.entities.CrmHistoryRecord;
import co.com.tactusoft.crm.model.entities.CrmHoliday;
import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.model.entities.CrmInfunsion2;
import co.com.tactusoft.crm.model.entities.CrmMaterialGroup;
import co.com.tactusoft.crm.model.entities.CrmMedication;
import co.com.tactusoft.crm.model.entities.CrmNote;
import co.com.tactusoft.crm.model.entities.CrmNurse;
import co.com.tactusoft.crm.model.entities.CrmOdontologyEvolution;
import co.com.tactusoft.crm.model.entities.CrmOdontologyOdontogram;
import co.com.tactusoft.crm.model.entities.CrmOdontologyPeriodontal;
import co.com.tactusoft.crm.model.entities.CrmOdontologySoftTissue;
import co.com.tactusoft.crm.model.entities.CrmOdontologyStomatolog;
import co.com.tactusoft.crm.model.entities.CrmOdontologySupplExams;
import co.com.tactusoft.crm.model.entities.CrmOdontologyTempJoint;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmPatientTicket;
import co.com.tactusoft.crm.model.entities.CrmProcedureDetail;
import co.com.tactusoft.crm.model.entities.CrmRepMedication;
import co.com.tactusoft.crm.model.entities.CrmRepSymptom;
import co.com.tactusoft.crm.model.entities.IndPatientAppointment;
import co.com.tactusoft.crm.model.entities.IndRepurchase;
import co.com.tactusoft.crm.model.entities.VwAppointment;
import co.com.tactusoft.crm.model.entities.VwAppointmentAvg;
import co.com.tactusoft.crm.model.entities.VwFirstDiagnosis;
import co.com.tactusoft.crm.model.entities.VwMedicationSold;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.InfunsionSoft;
import co.com.tactusoft.crm.view.beans.Candidate;
import co.com.tactusoft.crm.view.beans.OpportunityAgenda;
import co.com.tactusoft.crm.view.beans.ResultSearchAppointment;

@Named
public class ProcessBo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CustomHibernateDao dao;

	public List<CrmAppointment> getListAppointment() {
		return dao.find("from CrmAppointment");
	}

	public List<CrmAppointment> getListHistoryByAppointment(
			BigDecimal idAppointment) {
		return dao
				.find("SELECT o.crmAppointment FROM CrmHistoryHistory o WHERE o.crmAppointment.id = "
						+ idAppointment
						+ " UNION "
						+ "SELECT o.crmAppointment FROM CrmHistoryHomeopathic o WHERE o.crmAppointment.id = "
						+ idAppointment
						+ " UNION "
						+ "SELECT o.crmAppointment FROM CrmHistoryIridology o WHERE o.crmAppointment.id = "
						+ idAppointment
						+ " UNION "
						+ "SELECT o.crmAppointment FROM CrmHistoryPhysique o WHERE o.crmAppointment.id = "
						+ idAppointment
						+ " UNION "
						+ "SELECT o.crmAppointment FROM CrmHistoryRecord o WHERE o.crmAppointment.id = "
						+ idAppointment
						+ " UNION "
						+ "SELECT o.crmAppointment FROM CrmDiagnosis o WHERE o.crmAppointment.id = "
						+ idAppointment);
	}

	public List<VwAppointmentAvg> getAppointmentAvgByBranch(String startDate,
			String finishDate, List<String> listBranch) {

		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		return dao
				.findNative(
						"select r.id as branch_id, "
								+ "p.id as procedure_id, "
								+ "r.name as branch_name, "
								+ "p.name as procedure_name, "
								+ "avg(datediff(t.start_appointment_date , t.date_create)) as appointment_avg, "
								+ "count(1) as appointment_count, "
								+ "sum(datediff(t.start_appointment_date , t.date_create)) as appointment_sum "
								+ "from crm_appointment t left join crm_branch r on t.id_branch = r.id "
								+ "left join crm_procedure_detail p on t.id_procedure_detail = p.id "
								+ "where t.id_branch in (" + branchs + ") "
								+ "and t.state <> 2 "
								+ "and Date(t.date_create) between '"
								+ startDate + "' and '" + finishDate + "' "
								+ "group by r.id,r.name,p.name order by p.id;",
						VwAppointmentAvg.class);
	}

	public List<Object[]> getAppointmentByBranch(String startDate,
			String finishDate, List<String> listBranch) {

		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		return dao
				.find("select a.crmBranch.id as idBranch, a.crmBranch.name as branchName, case when state = 1 then 'AGENDADAS' "
						+ "when state = 2 then 'CANCELADAS' "
						+ "when state = 3 then 'CHEQUEADAS' "
						+ "when state = 4 then 'ASISTIDAS' "
						+ "when state = 5 then 'NO ASISTIDAS' end as status,"
						+ " count(a.id) as count from CrmAppointment a "
						+ "where a.crmBranch.id IN ("
						+ branchs
						+ ") and Date(startAppointmentDate) between '"
						+ startDate
						+ "' and '"
						+ finishDate
						+ "' group by a.crmBranch.id, a.crmBranch.name, "
						+ "case when state = 1 then 'AGENDADAS' "
						+ "when state = 2 then 'CANCELADAS' "
						+ "when state = 3 then 'CHEQUEADAS' "
						+ "when state = 4 then 'ASISTIDAS' "
						+ "when state = 5 then 'NO ASISTIDAS' end");
	}

	public CrmAppointment getAppointment(BigDecimal id) {
		return (CrmAppointment) dao
				.find("from CrmAppointment where id = " + id).get(0);
	}

	public List<VwAppointment> getListVwAppointmentByHistory(BigDecimal idDoctor) {
		String startDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 23:59:59";

		// String startDateString = "2013-12-16 00:00:00";
		// String endDateString = "2013-12-16 23:59:59";
		return dao
				.find("from VwAppointment o where o.doctorId = "
						+ idDoctor
						+ " and ((o.startAppointmentDate >= '"
						+ startDateString
						+ "' and o.endAppointmentDate <= '"
						+ endDateString
						+ "') and closeAppointment = 0 or (o.startAppointmentDate < '"
						+ startDateString
						+ "' and closeAppointment = 0)) and o.state in (3,4) and o.prcAvailability = 1"
						+ "order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmentByBranch(BigDecimal branchId) {
		String startDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 23:59:59";
		return dao.find("from VwAppointment o where o.branchId = " + branchId
				+ " and ((o.startAppointmentDate >= '" + startDateString
				+ "' and o.endAppointmentDate <= '" + endDateString
				+ "') and closeAppointment = 0 or (o.startAppointmentDate < '"
				+ startDateString
				+ "' and closeAppointment = 0)) and o.state in (3,4) "
				+ "order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmenTheraphytByBranch(
			BigDecimal branchId, List<CrmBranch> listBranch) {

		String branchs = "";
		for (CrmBranch crmBranch : listBranch) {
			branchs = branchs + crmBranch.getId() + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String startDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(new Date(), "yyyy-MM-dd")
				+ " 23:59:59";
		return dao
				.find("from VwAppointment o where o.branchId IN ("
						+ branchs
						+ ") and ((o.startAppointmentDate >= '"
						+ startDateString
						+ "' and o.endAppointmentDate <= '"
						+ endDateString
						+ "') and closeAppointment = 0 or (o.startAppointmentDate < '"
						+ startDateString
						+ "' and closeAppointment = 0)) and o.state in (3,4) and o.prcAvailability = 0"
						+ "order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmentByDoctor(BigDecimal idDoctor) {
		Date startDate = FacesUtil.addDaysToDate(new Date(), -30);
		Date endDate = FacesUtil.addDaysToDate(new Date(), 30);

		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ " 23:59:59";
		return dao.find("from VwAppointment o where o.doctorId = " + idDoctor
				+ " and o.startAppointmentDate >= '" + startDateString
				+ "' and o.endAppointmentDate <= '" + endDateString
				+ "' order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmentByDoctorToday(
			BigDecimal idDoctor) {
		return dao.find("from VwAppointment o where o.doctorId = " + idDoctor
				+ " order by o.startAppointmentDate");
	}

	public List<CrmAppointment> getListAppointmentByDoctorWithOutUntimely(
			BigDecimal idDoctor) {
		return dao.find("from CrmAppointment o where o.crmDoctor.id = "
				+ idDoctor
				+ " and o.untimely = 0 order by o.startAppointmentDate");
	}

	public List<CrmAppointment> getListAppointmentByDoctorWithUntimely(
			BigDecimal idDoctor) {
		return dao
				.find("from CrmAppointment o where o.crmDoctor.id = "
						+ idDoctor
						+ " and o.untimely = 1 and o.state = 3 order by o.startAppointmentDate");
	}

	public List<CrmAppointment> getListAppointmentByDoctorConfirmed(
			BigDecimal idDoctor) {
		return dao
				.find("from CrmAppointment o where o.crmDoctor.id = "
						+ idDoctor
						+ " and o.state = 1 order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmentByBranch(
			BigDecimal idBranch, Date startDate, Date endDate) {

		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ " 23:59:59";

		return dao.find("from VwAppointment o where o.branchId = " + idBranch
				+ " and o.startAppointmentDate >= '" + startDateString
				+ "' and o.endAppointmentDate <= '" + endDateString
				+ "' order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListAppointmentByBranchDoctor(
			BigDecimal idBranch, BigDecimal idDoctor, Date startDate,
			Date endDate) {
		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ " 23:59:59";
		return dao.find("from VwAppointment o where o.branchId = " + idBranch
				+ " and o.doctorId = " + idDoctor
				+ " and o.startAppointmentDate >= '" + startDateString
				+ "' and o.endAppointmentDate <= '" + endDateString
				+ "' order by o.startAppointmentDate");
	}

	public List<VwAppointment> getListVwAppointmentByDoctor(CrmDoctor doctor,
			Date startDate) {

		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");

		List<VwAppointment> list = dao
				.find("from VwAppointment o where o.startAppointmentDate >= '"
						+ startDateString
						+ "T00:00:00.000+05:00' and o.doctorId = '"
						+ doctor.getId() + "' and o.state in (1,3,4,5) "
						+ " order by o.startAppointmentDate desc");

		return list;
	}

	public List<VwAppointment> getListByAppointmentByPatient(
			BigDecimal idPatient, String type) {
		return dao
				.find("from VwAppointment o where o.patId = "
						+ idPatient
						+ " and prcTypeHistory = '"
						+ type
						+ "' and o.state in (3,4,5) order by o.startAppointmentDate DESC");
	}

	public List<VwAppointment> getListByAppointmentAllByPatient(
			BigDecimal idPatient) {
		return dao.find("FROM VwAppointment o WHERE o.patId = " + idPatient
				+ " ORDER BY o.startAppointmentDate DESC");
	}

	public List<CrmCie> getListCieMedeicalByAll(String name) {
		return dao.find("FROM CrmCie o WHERE (o.code like '%" + name
				+ "%' OR o.description like '%" + name
				+ "%') AND medical = 1 ORDER BY o.description");
	}

	public List<CrmCie> getListCieOdontologyByAll(String name) {
		return dao.find("FROM CrmCie o WHERE (o.code like '%" + name
				+ "%' OR o.description like '%" + name
				+ "%') AND odontology = 1 ORDER BY o.description");
	}

	/*
	 * public List<CrmCie> getListCieByCodeMedical(String code) { return
	 * dao.find("FROM CrmCie o WHERE o.code like '%" + code +
	 * "%' AND medical = 1 ORDER BY o.description"); }
	 * 
	 * public List<CrmCie> getListCieByCodeOdontology(String code) { return
	 * dao.find("FROM CrmCie o WHERE o.code like '%" + code +
	 * "%' AND odontology = 1 ORDER BY o.description"); }
	 * 
	 * public List<CrmCie> getListCieByNameMedical(String name) { return
	 * dao.find("FROM CrmCie o WHERE o.description like '%" + name +
	 * "%' AND medical = 1 ORDER BY o.description"); }
	 * 
	 * public List<CrmCie> getListCieByNameOdontology(String name) { return
	 * dao.find("FROM CrmCie o WHERE o.description like '%" + name +
	 * "%' AND odontology = 1 ORDER BY o.description"); }
	 */

	public List<CrmMaterialGroup> getListMaterialGroup() {
		return dao.find("from CrmMaterialGroup");
	}

	public List<CrmCieMaterial> getListCieMaterial() {
		return dao.find("from CrmCieMaterial");
	}

	public CrmAppointment saveAppointment(CrmAppointment entity) {
		if (entity.getId() == null) {
			BigDecimal id = getId(CrmAppointment.class);
			entity.setId(id);
			String code = "C" + FacesUtil.lpad(id.toString(), '0', 10);
			entity.setCode(code);
		}

		int result = this.persist(entity);
		if (result == 0) {
			return entity;
		} else {
			return null;
		}
	}

	public <T> void remove(Object entity) {
		dao.delete(entity);
	}

	public <T> BigDecimal getId(Class<T> clasz) {
		return dao.getId(clasz);
	}

	private List<CrmHoliday> getListHoliday(BigDecimal idBranch) {
		String currenDate = FacesUtil.formatDate(new Date(), "yyyy-MM-dd");
		return dao
				.find("select o.crmHoliday from CrmHolidayBranch o where o.crmHoliday.holiday >= '"
						+ currenDate
						+ "T00:00:00.000+05:00' and o.crmBranch.id = "
						+ idBranch);
	}

	public List<CrmHoliday> getListHoliday(Date date, BigDecimal idBranch) {
		String currenDate = FacesUtil.formatDate(date, "yyyy-MM-dd");
		return dao
				.find("select o.crmHoliday from CrmHolidayBranch o where o.crmHoliday.holiday = '"
						+ currenDate + "' and o.crmBranch.id = " + idBranch);
	}

	private boolean validateHoliday(List<CrmHoliday> list, Date date) {
		for (CrmHoliday row : list) {
			if (row.getHoliday().compareTo(date) == 0) {
				return false;
			}
		}
		return true;
	}

	private boolean validateException(
			List<CrmDoctorException> listDoctorException, Date date) {
		for (CrmDoctorException row : listDoctorException) {
			if (date.compareTo(row.getStartHour()) >= 0
					&& date.compareTo(row.getEndHour()) <= 0) {
				return false;
			}
		}

		return true;
	}

	private boolean validateException(
			List<CrmDoctorException> listDoctorException, Date date,
			CrmDoctor crmDoctor) {
		for (CrmDoctorException row : listDoctorException) {
			if (date.compareTo(row.getStartHour()) >= 0
					&& date.compareTo(row.getEndHour()) <= 0
					&& row.getCrmDoctor().getId().intValue() == crmDoctor
							.getId().intValue()) {
				return false;
			}
		}

		return true;
	}

	private boolean validateAvailabilitySchedule(List<Date> candidatesHours,
			List<CrmAppointment> listApp, Date currentDate, String timeType) {

		boolean result = true;
		int numApp = 0;

		// Iterar Horas No Disponibles
		int count = 0;
		List<Date> ocupatedHours = new ArrayList<Date>();
		for (CrmAppointment app : listApp) {
			Date dateRow = FacesUtil.getDateWithoutTime(app
					.getStartAppointmentDate());
			if (currentDate.getTime() == dateRow.getTime()) {
				long diff = app.getEndAppointmentDate().getTime()
						- app.getStartAppointmentDate().getTime();
				double diffInMin = diff / ((double) 1000 * 60);
				int size = (int) (diffInMin / Constant.INTERVAL_TIME_APPOINTMENT) + 1;
				ocupatedHours.add(new Date(app.getStartAppointmentDate()
						.getTime()));
				count++;
				for (int k = 1; k < size; k++) {
					ocupatedHours.add(FacesUtil.addMinutesToDate(
							ocupatedHours.get(count - 1),
							Constant.INTERVAL_TIME_APPOINTMENT));
					count++;
				}
			}

			// validar
			int contValidate = 0;
			for (Date ocup : ocupatedHours) {
				for (Date cand : candidatesHours) {
					if (cand.compareTo(ocup) == 0) {
						contValidate++;
						break;
					}
				}
			}

			if (contValidate > 2) {
				if (timeType.equals(Constant.TIME_TYPE_DOCTOR)) {
					result = false;
					break;
				} else {
					numApp++;
					if (numApp == app.getCrmBranch().getStretchers()) {
						result = false;
						break;
					}
				}
			}

			// Si la fecha
			if (dateRow.getTime() > currentDate.getTime()) {
				break;
			}
		}

		return result;
	}

	private boolean validateAvailabilitySchedule(List<Date> candidatesHours,
			Date startTime, Date endTime) {

		boolean result = true;

		// Iterar Horas No Disponibles
		int count = 0;
		List<Date> ocupatedHours = new ArrayList<Date>();

		long diff = endTime.getTime() - startTime.getTime();
		double diffInMin = diff / ((double) 1000 * 60);
		int size = (int) (diffInMin / Constant.INTERVAL_TIME_APPOINTMENT) + 1;
		ocupatedHours.add(new Date(startTime.getTime()));
		count++;
		for (int k = 1; k < size; k++) {
			ocupatedHours.add(FacesUtil.addMinutesToDate(
					ocupatedHours.get(count - 1),
					Constant.INTERVAL_TIME_APPOINTMENT));
			count++;
		}

		// validar
		int contValidate = 0;
		for (Date ocup : ocupatedHours) {
			for (Date cand : candidatesHours) {
				if (cand.compareTo(ocup) == 0) {
					contValidate++;
					break;
				}
			}
		}

		if (contValidate > 2) {
			result = false;
		}

		return result;
	}

	public List<Candidate> getListOccupatedHours(Date currentDate,
			BigDecimal idBranch) {
		List<Candidate> result = new ArrayList<Candidate>();

		// Validar Festivo
		List<CrmHoliday> listHoliday = getListHoliday(currentDate, idBranch);
		if (listHoliday.size() == 0) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

			// if (currentDay != 1) {// Si no es domingo

			List<CrmDoctorSchedule> listCrmDoctorSchedule = dao
					.find("from CrmDoctorSchedule o where o.crmBranch.id = "
							+ idBranch
							+ " and o.day = "
							+ currentDay
							+ " and o.crmDoctor.id <> 0 and o.crmDoctor.state = 1");

			if (listCrmDoctorSchedule.size() > 0) {

				Date minHour = null;
				Date maxHour = null;

				for (CrmDoctorSchedule row : listCrmDoctorSchedule) {
					if (minHour != null) {
						if (row.getStartHour().compareTo(minHour) < 0) {
							minHour = row.getStartHour();
						}
					} else {
						minHour = row.getStartHour();
					}

					if (maxHour != null) {
						if (row.getEndHour().compareTo(maxHour) > 0) {
							maxHour = row.getEndHour();
						}
					} else {
						maxHour = row.getEndHour();
					}
				}

				String dateString = FacesUtil.formatDate(currentDate,
						"yyyy-MM-dd");
				// Buscar Citas
				List<CrmAppointment> listApp = dao
						.find("from CrmAppointment o where (o.startAppointmentDate between '"
								+ dateString
								+ "T00:00:00.000+05:00' and '"
								+ dateString
								+ "T23:59:59.999+05:00') and o.state in (1,3,4,5) and o.crmBranch.id = "
								+ idBranch
								+ " and o.crmProcedureDetail.availability = TRUE order by o.startAppointmentDate");

				boolean end = false;
				Date scheduleInitHour = minHour;

				do {
					int numInteractions = 0;
					int numApp = 0;

					for (CrmDoctorSchedule row : listCrmDoctorSchedule) {
						if ((scheduleInitHour.compareTo(row.getStartHour()) >= 0)
								&& (scheduleInitHour
										.compareTo(row.getEndHour()) < 0)) {
							numInteractions++;
						}
					}

					if (numInteractions > 0) {
						calendar = Calendar.getInstance();
						calendar.setTime(scheduleInitHour);
						calendar.add(Calendar.MINUTE, 15);
						Date scheduleEndHour = calendar.getTime();
						scheduleEndHour = FacesUtil.addHourToDate(currentDate,
								scheduleEndHour);

						List<Date> candidatesHours = getListcandidatesHours(
								FacesUtil.addHourToDate(currentDate,
										scheduleInitHour), scheduleEndHour);

						for (CrmAppointment row : listApp) {
							boolean validate = validateAvailabilitySchedule(
									candidatesHours,
									row.getStartAppointmentDate(),
									row.getEndAppointmentDate());
							if (!validate) {
								numApp++;
							}
						}

						if (numApp >= numInteractions) {
							Date time = FacesUtil.addHourToDate(currentDate,
									scheduleInitHour);
							Candidate candidate = new Candidate();
							candidate.setStartDate(time);
							candidate.setEndDate(scheduleEndHour);
							result.add(candidate);
						}
					}
					calendar = Calendar.getInstance();
					calendar.setTime(scheduleInitHour);
					calendar.add(Calendar.MINUTE, 15);
					scheduleInitHour = calendar.getTime();

					if (scheduleInitHour.compareTo(maxHour) >= 0) {
						end = true;
					}
				} while (!end);
			}

			// }
		}

		return result;
	}

	private List<Date> getListcandidatesHours(Date initHour, Date endHour) {
		long diff = endHour.getTime() - initHour.getTime();
		double diffInMin = diff / ((double) 1000 * 60);
		int size = (int) (diffInMin / Constant.INTERVAL_TIME_APPOINTMENT) + 1;
		List<Date> candidatesHours = new ArrayList<Date>();
		candidatesHours.add(initHour);
		for (int k = 1; k < size; k++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(candidatesHours.get(k - 1));
			calendar.add(Calendar.MINUTE, Constant.INTERVAL_TIME_APPOINTMENT);
			candidatesHours.add(calendar.getTime());
		}

		return candidatesHours;
	}

	public ResultSearchAppointment getScheduleAppointmentForDoctor(
			CrmBranch branch, CrmDoctor doctor, int numApp,
			CrmProcedureDetail procedureDetail, Date selectedDate) {

		List<Candidate> result = new ArrayList<Candidate>();
		String message = null;
		int id = 1;
		int maxDates = 60;
		int countDates = 0;

		int minutes = 0;
		if ((procedureDetail.getTimeDoctor() >= procedureDetail.getTimeNurses())
				&& (procedureDetail.getTimeDoctor() > procedureDetail
						.getTimeStretchers())) {
			minutes = procedureDetail.getTimeDoctor();
		} else if ((procedureDetail.getTimeNurses() > procedureDetail
				.getTimeDoctor())
				&& (procedureDetail.getTimeNurses() > procedureDetail
						.getTimeStretchers())) {
			minutes = procedureDetail.getTimeNurses();
		} else {
			minutes = procedureDetail.getTimeStretchers();
		}

		BigDecimal idBranch = branch.getId();
		String initDate = FacesUtil.formatDate(selectedDate, "yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectedDate);
		calendar.add(Calendar.DATE, 60); // 60 Días
		String endDate = FacesUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");

		List<CrmDoctorSchedule> listDoctorSchedule = dao
				.find("from CrmDoctorSchedule o where o.crmDoctor.id = "
						+ doctor.getId()
						+ " and o.crmBranch.id = "
						+ idBranch
						+ "  and o.crmDoctor.state = 1 order by o.day, o.startHour");

		// Validar si Doctor tiene Horario
		if (listDoctorSchedule.size() > 0) {

			// Buscar citas x doctor y sucursal
			List<CrmAppointment> listApp = dao
					.find("from CrmAppointment o where (o.startAppointmentDate between '"
							+ initDate
							+ "T00:00:00.000+05:00' and '"
							+ endDate
							+ "T23:59:59.999+05:00') and o.crmDoctor.id = "
							+ doctor.getId()
							+ " and o.state in (1,3,4,5) and o.crmProcedureDetail.availability = TRUE "
							+ "order by o.startAppointmentDate");

			// Buscar los festivos
			List<CrmHoliday> listHoliday = this.getListHoliday(idBranch);

			// Revisar Día a Día disponibilidad de Citas
			Date currentDate = selectedDate;
			calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DATE, -1);
			currentDate = calendar.getTime();

			boolean iterate = true;
			outer: while (iterate) {
				calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DATE, 1);
				currentDate = FacesUtil.getDateWithoutTime(calendar.getTime());
				countDates++;

				String dateString = FacesUtil.formatDate(currentDate,
						"yyyy-MM-dd");

				List<CrmDoctorException> listDoctorException = dao
						.find("from CrmDoctorException o where o.crmDoctor.id = "
								+ doctor.getId()
								+ " and '"
								+ dateString
								+ "' between Date(startHour) and Date(endHour)"
								+ " and (o.crmBranch.id is null OR o.crmBranch.id = "
								+ idBranch + ")");

				if (// (calendar.get(Calendar.DAY_OF_WEEK) != 1) &&
				(this.validateHoliday(listHoliday, currentDate))) {
					for (CrmDoctorSchedule schedule : listDoctorSchedule) {
						if (calendar.get(Calendar.DAY_OF_WEEK) == schedule
								.getDay()) {

							// Sumo el dia + hora de disponibilidad del Doctor
							Date scheduleInitHour = FacesUtil.addHourToDate(
									currentDate, schedule.getStartHour());

							Date scheduleEndHour = FacesUtil.addHourToDate(
									currentDate, schedule.getEndHour());

							Date initHour = new Date(scheduleInitHour.getTime());

							boolean endIterate = true;
							while (endIterate) {
								if (this.validateException(listDoctorException,
										initHour)) {

									Calendar calendar2 = Calendar.getInstance();
									calendar2.setTime(initHour);
									calendar2.add(Calendar.MINUTE, minutes);
									Date endHour = calendar2.getTime();

									// Si la hora final candidata es mayor al
									// tiempo
									// de atencion del doctor salir
									if (endHour.getTime() > scheduleEndHour
											.getTime()) {
										endIterate = false;
										break;
									}

									// Iterar Horas Candidatas
									List<Date> candidatesHours = getListcandidatesHours(
											initHour, endHour);

									// Validar Disponibilidad
									boolean validate = validateAvailabilitySchedule(
											candidatesHours, listApp,
											currentDate,
											Constant.TIME_TYPE_DOCTOR);

									if (validate) {
										validate = validateException(
												listDoctorException, initHour);

										if (FacesUtil.getDateWithoutTime(
												new Date()).compareTo(
												currentDate) == 0) {
											if (new Date().compareTo(initHour) > 0) {
												validate = false;
											}
										}
									}

									if (validate) {
										result.add(new Candidate(id, doctor,
												initHour, endHour, branch
														.getName(),
												procedureDetail.getName()));
										id++;

										// Numero Citas completadas
										if (result.size() == numApp) {
											iterate = false;
											break outer;
										}
									}
								}
								initHour = FacesUtil.addMinutesToDate(initHour,
										minutes);
							}
						}
					}
				} else {
					message = FacesUtil.getMessage("app_msg_error_1");
				}

				// Máximo días de busqueda
				if (countDates == maxDates) {
					break outer;
				}
			}
		}

		ResultSearchAppointment resultSearchAppointment = new ResultSearchAppointment();
		resultSearchAppointment.setListCandidate(result);
		resultSearchAppointment.setMessage(message);

		return resultSearchAppointment;
	}

	public ResultSearchAppointment getScheduleAppointmentForDate(
			CrmBranch branch, Date date, CrmProcedureDetail procedureDetail,
			int minutes, String timeType, int appointmentsNumber) {
		List<Candidate> result = new ArrayList<Candidate>();
		String message = null;

		BigDecimal idBranch = branch.getId();
		Date currentDate = FacesUtil.getDateWithoutTime(date);

		Map<Date, Date> mapResult = new TreeMap<>();

		String speciality = "1,2";
		if (procedureDetail.getTypeHistory().equalsIgnoreCase("ODONTOLOGY")) {
			speciality = "3";
		} else if (procedureDetail.getTypeHistory().equalsIgnoreCase(
				"BEAUTICIAN")) {
			speciality = "4";
		}

		int id = 0;
		int maxDays = 0;
		out: do {
			String dateString = FacesUtil.formatDate(currentDate, "yyyy-MM-dd");

			// Validar Festivo
			List<CrmHoliday> listHoliday = getListHoliday(currentDate, idBranch);
			if (listHoliday.size() == 0) {
				int currentDay = FacesUtil.getDayOfWeek(currentDate);
				// if (currentDay != 1) {// Si no es domingo
				// Buscar horarios
				List<CrmDoctorSchedule> listCrmDoctorSchedule = dao
						.find("FROM CrmDoctorSchedule o WHERE o.crmBranch.id = "
								+ idBranch
								+ " AND o.day = "
								+ currentDay
								+ " AND o.crmDoctor.state = 1 and o.crmDoctor.crmSpeciality.id IN ("
								+ speciality + ") ORDER BY o.startHour");
				maxDays++;

				if (listCrmDoctorSchedule.size() > 0) {
					List<CrmDoctorException> listDoctorException = dao
							.find("FROM CrmDoctorException o WHERE o.crmDoctor.state = 1 AND '"
									+ dateString
									+ "' between Date(startHour) AND Date(endHour)"
									+ " AND (o.crmBranch.id is null OR o.crmBranch.id = "
									+ idBranch + ")");

					for (CrmDoctorSchedule schedule : listCrmDoctorSchedule) {
						String startDateScheduleString = FacesUtil.formatDate(
								FacesUtil.addHourToDate(currentDate,
										schedule.getStartHour()),
								"yyyy-MM-dd HH:mm:ss");

						String endDateScheduleString = FacesUtil.formatDate(
								FacesUtil.addHourToDate(currentDate,
										schedule.getEndHour()),
								"yyyy-MM-dd HH:mm:ss");

						// Buscar Citas
						List<CrmAppointment> listApp = new ArrayList<CrmAppointment>();
						if (procedureDetail.isAvailability()) {
							listApp = dao
									.find("FROM CrmAppointment o WHERE (o.startAppointmentDate between '"
											+ startDateScheduleString
											+ "' AND '"
											+ endDateScheduleString
											+ "') AND o.state in (1,3,4,5) AND "
											+ " o.crmProcedureDetail.availability = TRUE and o.crmBranch.id = "
											+ idBranch
											+ "AND o.crmDoctor.id = "
											+ schedule.getCrmDoctor().getId()
											+ " ORDER BY o.startAppointmentDate");
						}

						Date scheduleInitHour = FacesUtil.addHourToDate(
								currentDate, schedule.getStartHour());

						Date maxHour = FacesUtil.addHourToDate(currentDate,
								schedule.getEndHour());

						boolean exit = false;
						do {
							Date scheduleEndHour = FacesUtil.addMinutesToDate(
									scheduleInitHour, minutes);

							if (scheduleEndHour.compareTo(maxHour) > 0) {
								break;
							}

							List<Date> candidatesHours = getListcandidatesHours(
									scheduleInitHour, scheduleEndHour);

							boolean validateException = validateException(
									listDoctorException, scheduleInitHour,
									schedule.getCrmDoctor());

							if (validateException) {
								boolean validate = true;
								for (CrmAppointment row : listApp) {
									validate = validateAvailabilitySchedule(
											candidatesHours,
											row.getStartAppointmentDate(),
											row.getEndAppointmentDate());

									if (!validate) {
										validate = false;
										break;
									}
								}

								if (validate
										&& new Date()
												.compareTo(scheduleInitHour) <= 0) {
									result.add(new Candidate(id, schedule
											.getCrmDoctor(), scheduleInitHour,
											scheduleEndHour, branch.getName(),
											procedureDetail.getName()));
									mapResult.put(scheduleInitHour,
											scheduleEndHour);
									id++;
								}
							}

							scheduleInitHour = FacesUtil.addMinutesToDate(
									scheduleInitHour, minutes);

							if (mapResult.size() == appointmentsNumber) {
								break out;
							}

						} while (!exit);

						if (mapResult.size() == appointmentsNumber) {
							break out;
						}
					}
				}
				// }
			}
			currentDate = FacesUtil.addDaysToDate(currentDate, 1);
		} while (mapResult.size() < appointmentsNumber && maxDays < 8);

		ResultSearchAppointment resultSearchAppointment = new ResultSearchAppointment();

		Set<Map.Entry<Date, Date>> entrySet = mapResult.entrySet();
		resultSearchAppointment.setListDoctorCandidate(result);
		id = 1;
		result = new ArrayList<Candidate>();
		for (Map.Entry<Date, Date> entry : entrySet) {
			if (id <= appointmentsNumber) {
				result.add(new Candidate(id, null, entry.getKey(), entry
						.getValue(), branch.getName(), procedureDetail
						.getName()));
				id++;
			}
		}

		if (maxDays == 8 && result.size() == 0) {
			message = FacesUtil.getMessage("app_msg_error_6");
		}

		resultSearchAppointment.setListCandidate(result);
		resultSearchAppointment.setMessage(message);

		return resultSearchAppointment;
	}

	private int validateDuplicated(BigDecimal idPatient, Date startDate,
			Date endDate) {
		String dateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");
		String startHourString = FacesUtil.formatDate(startDate, "HH:mm:ss");
		String endHourString = FacesUtil.formatDate(endDate, "HH:mm:ss");

		List<CrmAppointment> listApp = dao
				.find("from CrmAppointment o where o.startAppointmentDate >= '"
						+ dateString
						+ "T"
						+ startHourString
						+ ".000+05:00' and o.startAppointmentDate <= '"
						+ dateString
						+ "T"
						+ endHourString
						+ ".000+05:00' and o.crmPatient.id = "
						+ idPatient
						+ " and o.state in (1,3,4,5) and o.crmProcedureDetail.availability = TRUE "
						+ "order by o.startAppointmentDate");

		return listApp.size();
	}

	public int validateAppointmentForDate(Date currentDate, BigDecimal idPatient) {
		int result = 0;
		String dateString = FacesUtil.formatDate(currentDate, "yyyy-MM-dd");
		List<CrmAppointment> list = dao
				.find("from CrmAppointment o where Date(o.startAppointmentDate) = '"
						+ dateString
						+ "' AND o.crmPatient.id = "
						+ idPatient
						+ " AND state IN (1,3,4) AND o.crmProcedureDetail.availability = true");
		if (list.size() > 0) {
			result = -5;
		}
		return result;
	}

	public int validateAppointmentForDate(BigDecimal idBranch, Date starDate,
			Date endDate, CrmProcedureDetail procedureDetail,
			BigDecimal idDoctor, BigDecimal idPatient, String timeType,
			Boolean edit) {

		int result = 0;

		if (!edit && procedureDetail.isAvailability()) {
			// Validar si Paciente tiene otra cita
			result = validateDuplicated(idPatient, starDate, endDate);
			if (result > 0) {
				return -4;
			}
		}

		// Buscar los festivos
		List<CrmHoliday> listHoliday = this.getListHoliday(idBranch);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starDate);
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		if (// (day != 1) &&
		(this.validateHoliday(listHoliday, starDate))) {

			String dateString = FacesUtil.formatDate(starDate, "yyyy-MM-dd");
			String hourString = FacesUtil.formatDate(endDate, "HH:mm:ss");

			// Buscar horarios Doctor
			List<CrmDoctorSchedule> listDoctorSchedule = dao
					.find("from CrmDoctorSchedule o where o.day = "
							+ day
							+ " and o.crmDoctor.id = "
							+ idDoctor
							+ " AND o.crmBranch.id = "
							+ idBranch
							+ " AND '"
							+ hourString
							+ "' BETWEEN o.startHour AND o.endHour order by o.day, o.startHour");

			// Validar si Doctor tiene Horario
			if (listDoctorSchedule.size() > 0) {

				boolean validateScheduleDoctor = false;

				// Dia Actual sin Hora
				Date currentDate = FacesUtil.getDateWithoutTime(starDate);

				// Iterar Horas Candidatas
				List<Date> candidatesHours = getListcandidatesHours(starDate,
						endDate);

				// Validar Disponibilidad del Doctor
				for (CrmDoctorSchedule schedule : listDoctorSchedule) {

					Date scheduleInitHour = FacesUtil.addHourToDate(
							currentDate, schedule.getStartHour());

					Date scheduleEndHour = FacesUtil.addHourToDate(currentDate,
							schedule.getEndHour());

					List<Date> scheduleHours = getListcandidatesHours(
							scheduleInitHour, scheduleEndHour);

					// validar
					int contValidate = 0;
					for (Date ocup : scheduleHours) {
						for (Date cand : candidatesHours) {
							if (cand.compareTo(ocup) == 0) {
								contValidate++;
							}
						}
					}

					if (contValidate == candidatesHours.size()) {
						validateScheduleDoctor = true;
						break;
					}

				}

				if (validateScheduleDoctor) {
					List<CrmAppointment> listApp = dao
							.find("from CrmAppointment o where o.startAppointmentDate >= '"
									+ dateString
									+ "T00:00:00.000+05:00' and o.startAppointmentDate <= '"
									+ dateString
									+ "T23:59:59.999+05:00'  and o.crmBranch.id = "
									+ idBranch
									+ " and o.crmDoctor.id = "
									+ idDoctor
									+ " and o. state in (1,3,4,5) and o.crmProcedureDetail.availability = TRUE "
									+ "order by o.startAppointmentDate");

					// Validar Disponibilidad
					boolean validate = validateAvailabilitySchedule(
							candidatesHours, listApp, currentDate, timeType);

					if (!validate) {
						// Cita no disponible
						result = -3;
					}
				} else {
					// Doctor no antiende
					result = -2;
				}
			} else {
				// Doctor no antiende
				result = -2;
			}
		} else {
			// Dia Festivo no habil
			result = -1;
		}

		return result;
	}

	public List<CrmAppointment> listAppointmentByPatient(BigDecimal idPatient,
			int state) {
		List<CrmAppointment> list = new ArrayList<CrmAppointment>();
		list = dao.find("from CrmAppointment o where o.crmPatient.id = "
				+ idPatient + " and o.state = " + state
				+ " order by o.startAppointmentDate desc");
		return list;
	}

	public List<CrmAppointment> listAppointmentByPatient(BigDecimal idPatient,
			String state) {
		List<CrmAppointment> list = new ArrayList<CrmAppointment>();
		list = dao.find("from CrmAppointment o where o.crmPatient.id = "
				+ idPatient + " and o.state in (" + state
				+ ") order by o.startAppointmentDate desc");
		return list;
	}

	public List<CrmAppointment> listAppointmentByPatient(BigDecimal idPatient,
			int state, Date startDate, Date endDate) {

		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
		String stateString;

		if (state == -1) {
			stateString = "o.state";
		} else {
			stateString = String.valueOf(state);
		}

		List<CrmAppointment> list = dao
				.find("from CrmAppointment o where (Date(o.startAppointmentDate) between '"
						+ startDateString
						+ "' and '"
						+ endDateString
						+ "') and o.crmPatient.id = "
						+ idPatient
						+ " and o.state = "
						+ stateString
						+ " order by o.startAppointmentDate desc");

		return list;
	}

	public List<VwAppointment> getListAppointmentByCriteria(String where) {
		List<VwAppointment> list = dao.find(where
				+ " order by o.startAppointmentDate asc");
		return list;
	}

	public CrmDoctor getCrmDoctor() {
		BigDecimal idUser = FacesUtil.getCurrentUser().getId();
		List<CrmDoctor> list = dao
				.find("from CrmDoctor o where o.state = 1 and o.crmUser.id = "
						+ idUser);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CrmNurse getCrmNurse() {
		BigDecimal idUser = FacesUtil.getCurrentUser().getId();
		List<CrmNurse> list = dao
				.find("from CrmNurse o where o.state = 1 and o.crmUser.id = "
						+ idUser);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public CrmAppointment getFirstAppointmentbyPatient(BigDecimal idPatient) {
		List<CrmAppointment> list = dao
				.find("FROM CrmAppointment o WHERE o.crmPatient.id = "
						+ idPatient
						+ " AND state IN (3,4) ORDER BY o.startAppointmentDate",
						1);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<CrmCie> getListCieByPatient(BigDecimal idPatient) {
		List<CrmCie> list = dao
				.find("SELECT o.crmCie FROM CrmDiagnosis o WHERE o.crmAppointment.crmPatient.id = "
						+ idPatient
						+ " AND o.crmCie.id != 0 ORDER BY o.crmCie.description",
						2);
		return list;
	}

	public List<String> getListHistoryByPatient(BigDecimal idPatient) {
		List<String> list = dao
				.find("SELECT o.reason FROM CrmHistoryHistory o WHERE o.crmAppointment.crmPatient.id = "
						+ idPatient + " ORDER BY o.reason");
		return list;
	}

	public int savePatient(CrmPatient entity, boolean automatic,
			boolean existsSAP, String country) {
		if (entity.getId() == null) {
			BigDecimal id = getId(CrmPatient.class);
			if (automatic) {
				String doc = country + FacesUtil.lpad(id.toString(), '0', 8);
				entity.setDoc(doc);
				entity.setCodeSap(doc);
			} else {
				if (!existsSAP) {
					entity.setCodeSap(entity.getDoc());
				}
			}
			entity.setId(id);
		}
		return this.persist(entity);
	}

	public int savePatient(CrmPatient entity, boolean automatic,
			boolean existsSAP, String country, String region, String city) {
		if (entity.getId() == null) {
			BigDecimal id = getId(CrmPatient.class);
			if (!existsSAP) {
				try {
					String contactId = InfunsionSoft.createContact(
							Constant.CONTACT_CREATE, null, entity.getDoc(),
							entity.getFirstnames(), entity.getSurnames(),
							entity.getEmail(), entity.getAddress(),
							entity.getCellNumber(), entity.getPhoneNumber(),
							country, region, city);
					entity.setCodeSap(contactId);
				} catch (MalformedURLException | XmlRpcException e) {
					e.printStackTrace();
				}
			} else {
				entity.setCodeSap(entity.getCodeSap());
			}
			// }
			entity.setId(id);
		}
		return this.persist(entity);
	}

	public void savePatientTicket(CrmPatient crmPatient,
			List<CrmPatientTicket> listTickets) {
		if (listTickets != null && !listTickets.isEmpty()) {
			for (CrmPatientTicket row : listTickets) {
				row.setCrmPatient(crmPatient);
				this.persist(row);
			}
		}
	}

	public void removePatient(BigDecimal id) {
		dao.executeHQL("delete from CrmPatient o where o.id = " + id);
	}

	public List<CrmPatient> getListPatientByField(String field, String value) {
		List<CrmPatient> list = null;

		if (field.equals("DOC")) {
			list = dao.find("FROM CrmPatient o WHERE doc = '" + value
					+ "' AND (o.idBusinessGroup = "
					+ FacesUtil.getCurrentBusinessGroup()
					+ " or o.idBusinessGroup = 0)");
		} else if (field.equals("NAMES")) {
			list = dao
					.find("FROM CrmPatient o WHERE (TRIM(firstnames) LIKE '%"
							+ value
							+ "%' or TRIM(surnames) LIKE '%"
							+ value
							+ "%' OR UPPER(TRIM(firstnames) || ' ' || TRIM(surnames)) LIKE '%"
							+ value
							+ "%' OR UPPER(TRIM(surnames) || ' ' || TRIM(firstnames)) LIKE '%"
							+ value + "%') AND (o.idBusinessGroup = "
							+ FacesUtil.getCurrentBusinessGroup()
							+ " or o.idBusinessGroup = 0)");
		} else if (field.equals("PHONE")) {
			list = dao.find("FROM CrmPatient o WHERE (phoneNumber = '" + value
					+ "' OR cellNumber = '" + value
					+ "') AND (o.idBusinessGroup = "
					+ FacesUtil.getCurrentBusinessGroup()
					+ " OR o.idBusinessGroup = 0)");
		} else if (field.equals("EMAIL")) {
			list = dao.find("FROM CrmPatient o WHERE email = '" + value
					+ "' AND (o.idBusinessGroup = "
					+ FacesUtil.getCurrentBusinessGroup()
					+ " OR o.idBusinessGroup = 0)");
		}

		return list;
	}

	public List<CrmPatient> getListPatientByAll(String value) {
		List<CrmPatient> list = null;
		list = dao.find("FROM CrmPatient o WHERE doc = '" + value
				+ "' OR phoneNumber = '" + value + "' OR cellNumber = '"
				+ value + "' OR o.firstnames LIKE '%" + value
				+ "%' or o.surnames LIKE '%" + value
				+ "%' OR UPPER(firstnames || ' ' || surnames) LIKE '%" + value
				+ "%' OR UPPER(surnames || ' ' || firstnames) LIKE '%" + value
				+ "%' AND (o.idBusinessGroup = "
				+ FacesUtil.getCurrentBusinessGroup()
				+ " or o.idBusinessGroup = 0)");
		return list;
	}

	public CrmPatient getPatientByDoc(String doc) {
		List<CrmPatient> list = null;
		list = dao.find("from CrmPatient o where o.doc = '" + doc
				+ "' AND (o.idBusinessGroup = "
				+ FacesUtil.getCurrentBusinessGroup()
				+ " or o.idBusinessGroup = 0)");
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmPatient();
		}
	}

	public List<CrmPatientTicket> getListPatientTicket(BigDecimal idPatient) {
		return dao.find("FROM CrmPatientTicket o WHERE o.crmPatient.id = "
				+ idPatient);
	}

	public boolean isExistsTickets(String ticket) {
		List<CrmPatientTicket> listTemp = dao
				.find("FROM CrmPatientTicket o WHERE TRIM(ticket) = '"
						+ ticket.trim() + "'");
		if (listTemp.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public CrmHistoryHistory getHistoryHistory(BigDecimal idAppointment) {
		List<CrmHistoryHistory> list = null;
		list = dao.find("from CrmHistoryHistory o where o.crmAppointment.id = "
				+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryHistory();
		}
	}

	public List<CrmHistoryHistory> getListHistoryHistory(BigDecimal idPatient,
			String type) {
		return dao.find("from CrmHistoryHistory o where o.crmPatient.id = "
				+ idPatient
				+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
				+ type + "'");
	}

	public CrmHistoryRecord getHistoryRecord(BigDecimal idAppointment) {
		List<CrmHistoryRecord> list = null;
		list = dao.find("from CrmHistoryRecord o where o.crmAppointment.id = "
				+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryRecord();
		}
	}

	public List<CrmHistoryRecord> getListHistoryRecord(BigDecimal idPatient,
			String type) {
		return dao.find("from CrmHistoryRecord o where o.crmPatient.id = "
				+ idPatient
				+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
				+ type + "'");
	}

	public CrmHistoryHomeopathic getHistoryHomeopathic(BigDecimal idAppointment) {
		List<CrmHistoryHomeopathic> list = null;
		list = dao
				.find("from CrmHistoryHomeopathic o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryHomeopathic();
		}
	}

	public CrmHistoryBiometric getHistoryBiometric(BigDecimal idAppointment) {
		List<CrmHistoryBiometric> list = null;
		list = dao
				.find("from CrmHistoryBiometric o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryBiometric();
		}
	}

	public List<CrmHistoryHomeopathic> getListHistoryHomeopathic(
			BigDecimal idPatient) {
		return dao.find("from CrmHistoryHomeopathic o where o.crmPatient.id = "
				+ idPatient);
	}

	public CrmHistoryPhysique getHistoryPhysique(BigDecimal idAppointment) {
		List<CrmHistoryPhysique> list = null;
		list = dao
				.find("from CrmHistoryPhysique o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryPhysique();
		}
	}

	public CrmHistoryConsultant getHistoryConsultant(BigDecimal idAppointment) {
		List<CrmHistoryConsultant> list = null;
		list = dao
				.find("from CrmHistoryConsultant o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryConsultant();
		}
	}

	public List<CrmCaseStudy> getCaseStudy(BigDecimal idAppointment) {
		List<CrmCaseStudy> list = dao
				.find("from CrmCaseStudy o where o.crmAppointment.id = "
						+ idAppointment);
		return list;
	}

	public CrmHistoryIridology getHistoryIridology(BigDecimal idAppointment) {
		List<CrmHistoryIridology> list = null;
		list = dao
				.find("from CrmHistoryIridology o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryIridology();
		}
	}

	public List<CrmHistoryIridology> getListHistoryIridology(
			BigDecimal idPatient, String type) {
		return dao
				.find("from CrmHistoryIridology o where o.crmAppointment.crmPatient.id = "
						+ idPatient
						+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
						+ type + "'");
	}

	public List<CrmHistoryPhysique> getListHistoryPhysique(
			BigDecimal idPatient, String type) {
		return dao.find("from CrmHistoryPhysique o where o.crmPatient.id = "
				+ idPatient
				+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
				+ type + "'");
	}

	public List<?> getListHistoryOdontology(BigDecimal idPatient, String table) {
		return dao.find("from " + table
				+ " o where o.crmAppointment.crmPatient.id = " + idPatient);
	}

	public CrmHistoryOrganometry getHistoryOrganometry(BigDecimal idAppointment) {
		List<CrmHistoryOrganometry> list = null;
		list = dao
				.find("from CrmHistoryOrganometry o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmHistoryOrganometry();
		}
	}

	public List<CrmHistoryOrganometry> getListHistoryOrganometry(
			BigDecimal idPatient) {
		return dao.find("from CrmHistoryOrganometry o where o.crmPatient.id = "
				+ idPatient);
	}

	public List<CrmHistoryConsultant> getListHistoryConsultant(
			BigDecimal idPatient) {
		return dao
				.find("from CrmHistoryConsultant o where o.crmAppointment.crmPatient.id = "
						+ idPatient);
	}

	public CrmOdontologyStomatolog getOdontologyStomatolog(
			BigDecimal idAppointment) {
		List<CrmOdontologyStomatolog> list = null;
		list = dao
				.find("from CrmOdontologyStomatolog o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologyStomatolog();
		}
	}

	public CrmOdontologyTempJoint getOdontologyTempJoint(
			BigDecimal idAppointment) {
		List<CrmOdontologyTempJoint> list = null;
		list = dao
				.find("from CrmOdontologyTempJoint o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologyTempJoint();
		}
	}

	public CrmOdontologySoftTissue getOdontologySoftTissue(
			BigDecimal idAppointment) {
		List<CrmOdontologySoftTissue> list = null;
		list = dao
				.find("from CrmOdontologySoftTissue o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologySoftTissue();
		}
	}

	public CrmOdontologyPeriodontal getOdontologyPeriodontal(
			BigDecimal idAppointment) {
		List<CrmOdontologyPeriodontal> list = null;
		list = dao
				.find("from CrmOdontologyPeriodontal o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologyPeriodontal();
		}
	}

	public CrmOdontologySupplExams getOdontologySupplExams(
			BigDecimal idAppointment) {
		List<CrmOdontologySupplExams> list = null;
		list = dao
				.find("from CrmOdontologySupplExams o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologySupplExams();
		}
	}

	public CrmOdontologyEvolution getOdontologyEvolution(
			BigDecimal idAppointment) {
		List<CrmOdontologyEvolution> list = null;
		list = dao
				.find("from CrmOdontologyEvolution o where o.crmAppointment.id = "
						+ idAppointment);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmOdontologyEvolution();
		}
	}

	public List<CrmOdontologyOdontogram> getOdontologyOdontogram(
			VwAppointment crmAppointment) {
		List<CrmOdontologyOdontogram> list = null;
		list = dao
				.find("from CrmOdontologyOdontogram o where o.crmAppointment.id = "
						+ "(SELECT MAX(b.crmAppointment.id) FROM CrmOdontologyOdontogram b WHERE b.crmAppointment.crmPatient.id = "
						+ crmAppointment.getPatId() + ")");
		for (CrmOdontologyOdontogram row : list) {
			row.setCrmAppointment(null);
		}
		return list;
	}

	public void removeOdontologyOdontogram(BigDecimal id) {
		dao.executeHQL("delete from CrmOdontologyOdontogram o where o.crmAppointment.id = "
				+ id);
	}

	public Long getDocAutomatic(String country) {
		List<Long> list = null;
		list = dao.find("select count(*) from CrmPatient o where o.country = '"
				+ country + "'");
		if (list.size() > 0) {
			return list.get(0) + 1;
		} else {
			return 1L;
		}
	}

	public int saveHistoryRecord(CrmHistoryRecord entity) {
		if (entity.getId() == null) {
			entity.setId(getId(CrmHistoryRecord.class));
		}
		return this.persist(entity);
	}

	public int saveHistoryHistory(CrmHistoryHistory entity) {
		if (entity.getId() == null) {
			entity.setId(getId(CrmHistoryHistory.class));
		}
		return this.persist(entity);
	}

	public int saveHistoryHomeopathic(CrmHistoryHomeopathic entity) {
		if (entity.getId() == null) {
			entity.setId(getId(CrmHistoryHomeopathic.class));
		}
		return this.persist(entity);
	}

	public int saveHistoryPhysique(CrmHistoryPhysique entity) {
		if (entity.getId() == null) {
			entity.setId(getId(CrmHistoryPhysique.class));
		}
		return this.persist(entity);
	}

	public int saveHistoryOrganometry(CrmHistoryOrganometry entity) {
		if (entity.getId() == null) {
			entity.setId(getId(CrmHistoryOrganometry.class));
		}
		return this.persist(entity);
	}

	public int save(Object entity) {
		return this.persist(entity);
	}

	public Integer saveDiagnosis(CrmAppointment entity, List<CrmDiagnosis> list) {
		int i = 0;

		dao.executeHQL("delete from CrmDiagnosis o where o.crmAppointment.id = "
				+ entity.getId());

		for (CrmDiagnosis row : list) {
			row.setId(getId(CrmDiagnosis.class));
			this.persist(row);
		}

		return i;
	}

	public Integer saveMedication(CrmAppointment entity,
			List<CrmMedication> list, String materialType) {
		int i = 0;

		dao.executeHQL("delete from CrmMedication o where o.crmAppointment.id = "
				+ entity.getId()
				+ " and o.materialType = '"
				+ materialType
				+ "'");

		for (CrmMedication row : list) {
			row.setId(getId(CrmMedication.class));
			this.persist(row);
		}

		return i;
	}

	public Integer saveCaseStudy(CrmAppointment entity, List<CrmCaseStudy> list) {
		int i = 0;

		dao.executeHQL("delete from CrmCaseStudy o where o.crmAppointment.id = "
				+ entity.getId());

		for (CrmCaseStudy row : list) {
			row.setId(null);
			this.saveWithExeception(row);
		}

		return i;
	}

	public Integer saveNotes(CrmNote entity) {
		entity.setId(getId(CrmNote.class));
		return this.persist(entity);
	}

	public Integer saveStudyCase(CrmCaseStudy entity) {
		return this.persist(entity);
	}

	public Integer saveCustodyBelongings(CrmAppointment entity,
			List<CrmCustodyBelongings> list) {
		int i = 0;

		dao.executeHQL("delete from CrmCustodyBelongings o where o.crmAppointment.id = "
				+ entity.getId());

		for (CrmCustodyBelongings row : list) {
			row.setId(null);
			this.persist(row);
		}

		return i;
	}

	public void saveWithExeception(Object entity) {
		dao.save(entity);
	}

	public List<CrmDiagnosis> getListDiagnosisByPatient(BigDecimal idPatient,
			String type) {
		List<CrmDiagnosis> list = dao
				.find("from CrmDiagnosis o where o.crmAppointment.crmPatient.id = "
						+ idPatient
						+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
						+ type
						+ "'"
						+ " order by o.crmAppointment.startAppointmentDate desc");
		return list;
	}

	public List<CrmDiagnosis> getListDiagnosisByAppointment(
			BigDecimal idAppointment) {
		List<CrmDiagnosis> list = dao
				.find("from CrmDiagnosis o where o.crmAppointment.id = "
						+ idAppointment + " order by o.id");
		return list;
	}

	public List<CrmMedication> getListMedicationByPatient(BigDecimal idPatient,
			String type) {
		List<CrmMedication> list = dao
				.find("from CrmMedication o where o.crmAppointment.crmPatient.id = "
						+ idPatient
						+ " and o.crmAppointment.crmProcedureDetail.typeHistory = '"
						+ type
						+ "'"
						+ " order by o.crmAppointment.startAppointmentDate desc");
		return list;
	}

	public List<VwMedicationSold> getListMedicationSoldByPatient(
			BigDecimal idPatient) {
		List<VwMedicationSold> list = dao
				.find("from VwMedicationSold o where o.idPatient = "
						+ idPatient + " order by o.idAppointment desc");
		return list;
	}

	public List<CrmMedication> getListMedicationByAppointmentOrderby(
			BigDecimal idAppointment, String materialType) {
		List<CrmMedication> list = dao
				.find("from CrmMedication o where o.crmAppointment.id = "
						+ idAppointment + " and materialType in ('"
						+ materialType
						+ "') order by o.groupBy ASC, activatePosology DESC");
		return list;
	}

	public List<CrmMedication> getListMedicationByAppointment(
			BigDecimal idAppointment, String materialType) {
		List<CrmMedication> list = dao
				.find("from CrmMedication o where o.crmAppointment.id = "
						+ idAppointment + " and materialType in ('"
						+ materialType + "') order by o.id");
		return list;
	}

	public List<CrmNote> getListNoteByPatient(BigDecimal idPatient) {
		List<CrmNote> list = dao.find("from CrmNote o where o.crmPatient.id = "
				+ idPatient + " order by o.noteDate desc");
		return list;
	}

	public List<CrmConsent> getListConsentByPatient(BigDecimal idPatient,
			String type) {
		List<CrmConsent> list = dao
				.find("from CrmConsent o where o.crmPatient.id = " + idPatient
						+ " and o.consentType = '" + type
						+ "' order by o.dateInformed desc");
		return list;
	}

	public List<CrmConsent> getListConsentByAppointment(BigDecimal idAppointment) {
		List<CrmConsent> list = dao
				.find("from CrmConsent o where o.crmAppointment.id = "
						+ idAppointment + " order by o.id");
		return list;
	}

	public List<CrmNote> getListNoteByAppointment(BigDecimal idAppointment) {
		List<CrmNote> list = dao
				.find("from CrmNote o where o.crmAppointment.id = "
						+ idAppointment + " order by o.id");
		return list;
	}

	public List<CrmCustodyBelongings> getListCustodyBelongingsByAppointment(
			BigDecimal idAppointment) {
		List<CrmCustodyBelongings> list = dao
				.find("from CrmCustodyBelongings o where o.crmAppointment.id = "
						+ idAppointment + " order by o.id");
		return list;
	}

	public CrmConsent getConsentById(Long id) {
		List<CrmConsent> list = dao
				.find("from CrmConsent o where o.id = " + id);
		return list.get(0);
	}

	public CrmConsentText getConsentTextByConsent(Long id) {
		CrmConsentText crmConsentText = new CrmConsentText();
		List<CrmConsentText> list = dao
				.find("from CrmConsentText o where o.crmConsent.id = " + id);
		if (!list.isEmpty()) {
			crmConsentText = list.get(0);
		}
		return crmConsentText;
	}

	public List<CrmCaseStudy> getListSuccessStoryByPatient(BigDecimal idPatient) {
		List<CrmCaseStudy> list = dao
				.find("from CrmCaseStudy o where o.crmAppointment.crmPatient.id = "
						+ idPatient + " order by o.id desc");
		return list;
	}

	public Date getMaxDateByProcedure(BigDecimal idPatient,
			BigDecimal idProcedureDetail) {
		Date result = null;
		List<Date> list = dao
				.find("select max(o.startAppointmentDate) from CrmAppointment o where o.crmPatient.id = "
						+ idPatient
						+ " and o.crmProcedureDetail.id = "
						+ idProcedureDetail + " and o.state in (1,3,4)");

		if (list.size() > 0) {
			result = list.get(0);
		}

		return result;
	}

	public CrmPatient getContactById(BigDecimal id) {
		List<CrmPatient> list = null;
		list = dao.find("FROM CrmPatient o WHERE o.id = " + id);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmPatient();
		}
	}

	public CrmPatient getContactByDoc(String doc) {
		List<CrmPatient> list = null;
		list = dao.find("FROM CrmPatient o WHERE o.doc = '" + doc
				+ "' AND (o.idBusinessGroup = "
				+ FacesUtil.getCurrentBusinessGroup()
				+ " or o.idBusinessGroup = 0)");
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return new CrmPatient();
		}
	}

	public List<CrmPatient> getContactByName(String name) {
		List<CrmPatient> list = null;
		list = dao.find("FROM CrmPatient o WHERE o.firstnames LIKE '%" + name
				+ "%' or o.surnames LIKE '%" + name
				+ "%' OR UPPER(firstnames || ' ' || surnames) LIKE '%" + name
				+ "%' OR UPPER(surnames || ' ' || firstnames) LIKE '%" + name
				+ "%' AND (o.idBusinessGroup = "
				+ FacesUtil.getCurrentBusinessGroup()
				+ " or o.idBusinessGroup = 0)");
		return list;
	}

	public long getContactExist(String doc) {
		long result = (Long) dao.find(
				"SELECT count(*) FROM CrmPatient o WHERE o.doc = '" + doc
						+ "' AND o.codeSap = '" + doc + "'").get(0);
		return result;
	}

	public List<CrmMedication> getListTherapyByPatient(BigDecimal idPatient) {
		return dao
				.find("SELECT DISTINCt o FROM CrmMedication o WHERE o.materialType = 'TERAPIA' "
						+ "AND o.crmAppointment.crmPatient.id = " + idPatient);
	}

	public int persist(Object entity) {
		int result = 0;
		try {
			result = dao.persist(entity);
		} catch (RuntimeException ex) {
			System.out.println("ERROR_CRM");
			ex.printStackTrace();
			if (ex.getCause() instanceof ConstraintViolationException) {
				result = -1;
			} else if (ex.getCause() instanceof DataIntegrityViolationException) {
				result = -2;
			} else {
				result = -3;
			}
		}
		return result;
	}

	private List<CrmProcedureDetail> getListProcedureByBranch(
			BigDecimal idBranch) {
		return dao
				.find("select o.crmProcedure from CrmProcedureBranch o where o.crmProcedure.state = 1 and o.crmBranch.id = "
						+ idBranch + " ORDER BY o.crmProcedure.name");
	}

	public List<OpportunityAgenda> getListOpportunityAgenda(
			List<String> listBranch, Map<BigDecimal, CrmBranch> mapCrmBranch,
			Date startDate, Date endDate) {

		List<OpportunityAgenda> result = new ArrayList<OpportunityAgenda>();
		List<Candidate> listCandidate = new ArrayList<Candidate>();
		int appointmentsNumber = 3;

		for (String branch : listBranch) {
			BigDecimal idBranch = new BigDecimal(branch);
			CrmBranch crmBranch = mapCrmBranch.get(idBranch);
			List<CrmProcedureDetail> listProcedureByBranch = getListProcedureByBranch(idBranch);
			boolean noAppointment = true;
			for (CrmProcedureDetail procedureDetail : listProcedureByBranch) {
				noAppointment = true;
				int minutes;
				if ((procedureDetail.getTimeDoctor() > procedureDetail
						.getTimeNurses())
						&& (procedureDetail.getTimeDoctor() > procedureDetail
								.getTimeStretchers())) {
					minutes = procedureDetail.getTimeDoctor();
				} else if ((procedureDetail.getTimeNurses() > procedureDetail
						.getTimeDoctor())
						&& (procedureDetail.getTimeNurses() > procedureDetail
								.getTimeStretchers())) {
					minutes = procedureDetail.getTimeNurses();
				} else {
					minutes = procedureDetail.getTimeStretchers();
				}

				listCandidate = new ArrayList<Candidate>();
				Date currentDate = FacesUtil.getDateWithoutTime(startDate);

				int id = 0;
				out: do {
					String dateString = FacesUtil.formatDate(currentDate,
							"yyyy-MM-dd");

					// Validar Festivo
					List<CrmHoliday> listHoliday = getListHoliday(currentDate,
							idBranch);
					if (listHoliday.size() == 0) {
						int currentDay = FacesUtil.getDayOfWeek(currentDate);
						// if (currentDay != 1) {// Si no es domingo
						// Buscar horarios
						List<CrmDoctorSchedule> listCrmDoctorSchedule = dao
								.find("FROM CrmDoctorSchedule o WHERE o.crmBranch.id = "
										+ idBranch
										+ " AND o.day = "
										+ currentDay
										+ " AND o.crmDoctor.state = 1 ORDER BY o.startHour");

						if (listCrmDoctorSchedule.size() > 0) {
							List<CrmDoctorException> listDoctorException = dao
									.find("FROM CrmDoctorException o WHERE o.crmDoctor.state = 1 AND '"
											+ dateString
											+ "' between Date(startHour) AND Date(endHour)"
											+ " AND (o.crmBranch.id is null OR o.crmBranch.id = "
											+ idBranch + ")");

							for (CrmDoctorSchedule schedule : listCrmDoctorSchedule) {
								String startDateScheduleString = FacesUtil
										.formatDate(FacesUtil.addHourToDate(
												currentDate,
												schedule.getStartHour()),
												"yyyy-MM-dd HH:mm:ss");

								String endDateScheduleString = FacesUtil
										.formatDate(FacesUtil.addHourToDate(
												currentDate,
												schedule.getEndHour()),
												"yyyy-MM-dd HH:mm:ss");

								// Buscar Citas
								List<CrmAppointment> listApp = new ArrayList<CrmAppointment>();
								if (procedureDetail.isAvailability()) {
									listApp = dao
											.find("FROM CrmAppointment o WHERE (o.startAppointmentDate between '"
													+ startDateScheduleString
													+ "' AND '"
													+ endDateScheduleString
													+ "') AND o.state in (1,3,4,5) AND "
													+ " o.crmProcedureDetail.availability = TRUE and o.crmBranch.id = "
													+ idBranch
													+ "AND o.crmDoctor.id = "
													+ schedule.getCrmDoctor()
															.getId()
													+ " ORDER BY o.startAppointmentDate");
								}

								Date scheduleInitHour = FacesUtil
										.addHourToDate(currentDate,
												schedule.getStartHour());

								Date maxHour = FacesUtil.addHourToDate(
										currentDate, schedule.getEndHour());

								boolean exit = false;
								do {
									Date scheduleEndHour = FacesUtil
											.addMinutesToDate(scheduleInitHour,
													minutes);

									if (scheduleEndHour.compareTo(maxHour) > 0) {
										break;
									}

									List<Date> candidatesHours = getListcandidatesHours(
											scheduleInitHour, scheduleEndHour);

									boolean validateException = validateException(
											listDoctorException,
											scheduleInitHour,
											schedule.getCrmDoctor());

									if (validateException) {
										boolean validate = true;
										for (CrmAppointment row : listApp) {
											validate = validateAvailabilitySchedule(
													candidatesHours,
													row.getStartAppointmentDate(),
													row.getEndAppointmentDate());

											if (!validate) {
												validate = false;
												break;
											}
										}

										if (validate
												&& new Date()
														.compareTo(scheduleInitHour) <= 0) {
											listCandidate.add(new Candidate(id,
													schedule.getCrmDoctor(),
													scheduleInitHour,
													scheduleEndHour, crmBranch
															.getName(),
													procedureDetail.getName()));
											id++;
										}
									}

									scheduleInitHour = FacesUtil
											.addMinutesToDate(scheduleInitHour,
													Constant.INCREASE_MIN);

									if (id == appointmentsNumber) {
										break out;
									}

								} while (!exit);

								if (id == appointmentsNumber) {
									break out;
								}
							}
						}
						// }
					}

					currentDate = FacesUtil.addDaysToDate(currentDate, 1);
					listCandidate = new ArrayList<Candidate>();
					id = 0;
					if (currentDate.compareTo(endDate) == 0) {
						noAppointment = false;
						break;
					}
				} while (id < appointmentsNumber);

				if (noAppointment) {
					result.add(new OpportunityAgenda(crmBranch,
							procedureDetail, currentDate));
				}
			}
		}
		return result;
	}

	public List<IndPatientAppointment> getListIndPatientAppointment(
			List<String> listBranch, Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);
		String sql = "SELECT @row\\:=@row + 1 as id,"
				+ "a.id_branch, c.name branch_name, "
				+ "a.id_patient, b.doc doc_patient, CONCAT(TRIM(b.surnames), ' ', TRIM(b.firstnames)) name_patient, "
				+ "a.id_procedure_detail, d.name procedure_name, count(*) num "
				+ "FROM crm_appointment a JOIN crm_patient b ON (a.id_patient = b.id) "
				+ "JOIN crm_branch c ON (a.id_branch = c.id) "
				+ "JOIN crm_procedure_detail d ON (a.id_procedure_detail = d.id), (SELECT @row\\:=0) r "
				+ "WHERE a.state IN (3,4) AND a.id_branch IN (" + branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd") + "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' GROUP BY id_patient, id_branch " + "ORDER by 1,6,5";
		List<IndPatientAppointment> result = dao.findNative(sql,
				IndPatientAppointment.class);
		return result;
	}

	public List<IndRepurchase> getListIndRepurchase(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);
		/*
		 * String sql = "SELECT *, purchase_total/patients_number avg_purchase "
		 * + "FROM ( " + "SELECT b.id_procedure_detail id, " +
		 * "		c.name procedure_name, " +
		 * "		(SELECT count(distinct e.id_patient) " +
		 * "		FROM crm_db.crm_sap_medication d " +
		 * "		JOIN crm_appointment e ON d.id_appointment = e.id " +
		 * "		WHERE e.id_branch IN (" + branchs +
		 * ") AND Date(e.start_appointment_date) BETWEEN '" +
		 * FacesUtil.formatDate(startDate, "yyyy-MM-dd") + "' AND '" +
		 * FacesUtil.formatDate(endDate, "yyyy-MM-dd") +
		 * "'	AND e.id_procedure_detail = b.id_procedure_detail) as patients_number, "
		 * + "		sum(a.amount) purchase_total " +
		 * "FROM crm_db.crm_sap_medication a JOIN crm_appointment b ON a.id_appointment = b.id "
		 * + "JOIN crm_procedure_detail c ON b.id_procedure_detail = c.id " +
		 * "WHERE b.id_branch IN (" + branchs +
		 * ") AND Date(b.start_appointment_date) BETWEEN '" +
		 * FacesUtil.formatDate(startDate, "yyyy-MM-dd") + "' AND '" +
		 * FacesUtil.formatDate(endDate, "yyyy-MM-dd") +
		 * "' GROUP by b.id_procedure_detail, c.name) f ";
		 */
		String sql = "select nro_citas id, "
				+ "(CASE nro_citas "
				+ "    WHEN 1 THEN 'Primera Cita' "
				+ "    ELSE CONCAT('Control ', CAST(nro_citas AS CHAR)) "
				+ "END) procedure_name, "
				+ "count(1) patients_number, "
				+ "sum(purchase_total) purchase_total, "
				+ "(sum(purchase_total)/count(1)) avg_purchase "
				+ "from( "
				+ "select b.id_patient, count(1) nro_citas, sum(a.amount) purchase_total "
				+ "from crm_sap_medication a JOIN crm_appointment b ON a.id_appointment = b.id "
				+ "where b.state = 4 AND b.id_branch IN (" + branchs
				+ ") AND Date(b.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd") + "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' GROUP BY b.id_patient) x " + "GROUP BY nro_citas ";
		List<IndRepurchase> result = dao.findNative(sql, IndRepurchase.class);
		return result;
	}

	public List<VwFirstDiagnosis> getListFirstDiagnosist(
			CrmBranch[] listBranch, Date startDate, Date endDate) {
		String branchs = "";
		for (CrmBranch crmBranch : listBranch) {
			branchs = branchs + crmBranch.getId() + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT c.code as id_cie, c.description as desc_cie, count(1) as count_cie\n "
				+ "FROM crm_db.crm_diagnosis a JOIN\n "
				+ "(SELECT id_appointment, MIN(id) min_id\n "
				+ "FROM crm_db.crm_diagnosis\n "
				+ "GROUP BY id_appointment) b ON a.id = b.min_id\n "
				+ "JOIN crm_cie c ON c.id = a.id_cie\n "
				+ "JOIN crm_appointment d ON d.id = a.id_appointment\n "
				+ "WHERE d.id_branch IN ("
				+ branchs
				+ ") AND Date(d.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "'\n GROUP BY c.code, c.description ORDER BY 3 DESC";

		List<VwFirstDiagnosis> result = dao.findNative(sql,
				VwFirstDiagnosis.class);
		return result;
	}

	public List<CrmAppointment> getListAppointment100(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT * "
				+ "FROM crm_appointment a "
				+ "WHERE a.state  = 4 AND a.id_branch IN ("
				+ branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' AND (SELECT COUNT(1) FROM crm_medication b WHERE b.id_appointment = a.id) <= "
				+ "(SELECT COUNT(1) FROM crm_sap_medication c WHERE c.id_appointment = a.id AND position_type <> 'ZGRT') "
				+ "ORDER BY 1,6,4";

		List<CrmAppointment> result = dao.findNative(sql, CrmAppointment.class);
		return result;
	}

	public List<CrmAppointment> getListAppointment5099(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT * "
				+ "FROM crm_appointment a "
				+ "WHERE a.state  = 4 AND a.id_branch IN ("
				+ branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' AND (SELECT COUNT(1) FROM crm_sap_medication  b WHERE b.id_appointment = a.id) >= "
				+ "((SELECT COUNT(1) FROM crm_medication c WHERE c.id_appointment = a.id)/2) "
				+ "AND (SELECT COUNT(1) FROM crm_sap_medication c WHERE c.id_appointment = a.id AND position_type <> 'ZGRT') < "
				+ "(SELECT COUNT(1) FROM crm_medication b WHERE b.id_appointment = a.id) "
				+ "ORDER BY 1,6,4";

		List<CrmAppointment> result = dao.findNative(sql, CrmAppointment.class);
		return result;
	}

	public List<CrmAppointment> getListAppointment50(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT * "
				+ "FROM crm_appointment a "
				+ "WHERE a.state  = 4 AND a.id_branch IN ("
				+ branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' AND (SELECT COUNT(1) FROM crm_sap_medication  b WHERE b.id_appointment = a.id AND position_type <> 'ZGRT') < "
				+ "((SELECT COUNT(1) FROM crm_medication c WHERE c.id_appointment = a.id)/2) "
				+ "ORDER BY 1,6,4";

		List<CrmAppointment> result = dao.findNative(sql, CrmAppointment.class);
		return result;
	}

	public List<CrmAppointment> getListAppointment0(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT * "
				+ "FROM crm_appointment a "
				+ "WHERE a.state  = 4 AND a.id_branch IN ("
				+ branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' AND (SELECT COUNT(1) FROM crm_sap_medication c WHERE c.id_appointment = a.id AND position_type <> 'ZGRT') = 0 "
				+ "ORDER BY 1,6,4";

		List<CrmAppointment> result = dao.findNative(sql, CrmAppointment.class);
		return result;
	}

	public List<CrmAppointment> getListAppointmentFree(List<String> listBranch,
			Date startDate, Date endDate) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		String sql = "SELECT * "
				+ "FROM crm_appointment a "
				+ "WHERE a.state = 4 AND a.id_procedure_detail IN (1,7) AND a.id_branch IN ("
				+ branchs
				+ ") AND Date(a.start_appointment_date) BETWEEN '"
				+ FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ "' AND '"
				+ FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ "' AND (SELECT COUNT(1) FROM crm_sap_medication c WHERE c.id_appointment = a.id AND position_type = 'ZGRT') > 0 "
				+ " AND (SELECT COUNT(1) FROM crm_sap_medication c WHERE c.id_appointment = a.id AND position_type <> 'ZGRT') = 0 "
				+ "ORDER BY 1,6,4";

		List<CrmAppointment> result = dao.findNative(sql, CrmAppointment.class);
		return result;
	}

	public List<CrmBranch> getListBranchByDoctor(BigDecimal idDoctor) {
		return dao
				.find("SELECT DISTINCT o.crmBranch from CrmDoctorSchedule o WHERE o.crmDoctor.id = "
						+ idDoctor);
	}

	public List<CrmRepSymptom> getListRepSymptom(String symptom) {
		return dao.find("FROM CrmRepSymptom o WHERE o.description like '%"
				+ symptom + "%' OR o.description like '%" + symptom
				+ "%' ORDER BY o.chapter, o.description");
	}

	public List<CrmRepMedication> getListRepMedication(Integer idSymptom) {
		return dao.find("FROM CrmRepMedication o WHERE o.crmRepSymptom.id = "
				+ idSymptom + " ORDER BY o.description");
	}

	public void updateAppointmentClosed(BigDecimal idDoctor, BigDecimal idUser) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, -48);
		String time = FacesUtil
				.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		String sql = "UPDATE CrmAppointment o SET o.state = 4, o.closeAppointment = 1, o.dateClosed = current_timestamp(), o.crmUserByIdUserClosed.id = "
				+ idUser
				+ " WHERE o.closeAppointment= 0 AND o.crmDoctor.id = "
				+ idDoctor
				+ " AND o.state >=3 AND '"
				+ time
				+ "' >= dateOpened";
		dao.executeHQL(sql);
	}

	public List<CrmInfunsion1> getInfunsionbyStatus(String startDate,
			String finishDate, List<String> listStatus) {

		String status = "";
		for (String row : listStatus) {
			status = status + row + ",";
		}
		status = status.substring(0, status.length() - 1);

		return dao.find("from CrmInfunsion1 o where o.status in (" + status
				+ ") and Date(o.eventDate) between '" + startDate + "' and '"
				+ finishDate + "' AND eventType = 'REGISTRO_FORMULARIO'");
	}

	public List<CrmProcedureDetail> getDistinctProcedureDetailByBranch(
			String[] listBranch) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		return dao
				.find("SELECT DISTINCT a.crmProcedureDetail FROM CrmAppointment a WHERE a.crmBranch.id IN ("
						+ branchs + ")");
	}

	public List<CrmDoctor> getDistinctDoctorByBranch(String[] listBranch) {
		String branchs = "";
		for (String crmBranch : listBranch) {
			branchs = branchs + crmBranch + ",";
		}
		branchs = branchs.substring(0, branchs.length() - 1);

		return dao
				.find("SELECT DISTINCT a.crmDoctor FROM CrmAppointment a WHERE a.crmBranch.id IN ("
						+ branchs + ")");
	}

	public List<VwAppointment> getListAppointmentNoAttendetByDates(
			Date startDate, Date endDate) {
		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd")
				+ " 00:00:00";
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd")
				+ " 23:59:59";
		return dao
				.find("from VwAppointment o where o.state = 5 and o.startAppointmentDate >= '"
						+ startDateString
						+ "' and o.endAppointmentDate <= '"
						+ endDateString + "' order by o.startAppointmentDate");
	}

	public List<CrmInfunsion2> getInfunsionbyDates(String startDate,
			String finishDate) {
		return dao
				.find("from CrmInfunsion2 o where Date(o.eventDate) between '"
						+ startDate + "' and '" + finishDate
						+ "' order by o.crmInfusionTag.orderby");
	}

	public List<CrmCampaignDetail> getListCampaignByDates(Integer type,
			Date startDate, Date endDate) {
		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
		return dao.find("from CrmCampaignDetail o where o.idCampaignType = "
				+ type + " and Date(o.crmCampaign.dateCall) between '"
				+ startDateString + "' and '" + endDateString
				+ "' order by o.crmCampaign.dateCall");
	}
}
