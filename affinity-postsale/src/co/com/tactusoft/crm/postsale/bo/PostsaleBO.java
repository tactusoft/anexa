package co.com.tactusoft.crm.postsale.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import co.com.tactusoft.crm.model.dao.CustomHibernateDao;
import co.com.tactusoft.crm.model.entities.CrmAppointment;
import co.com.tactusoft.crm.model.entities.CrmBlockHistoryDetail;
import co.com.tactusoft.crm.model.entities.CrmBranch;
import co.com.tactusoft.crm.model.entities.CrmCampaign;
import co.com.tactusoft.crm.model.entities.CrmCampaignDetail;
import co.com.tactusoft.crm.model.entities.CrmHoliday;
import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.model.entities.CrmInfusionTag;
import co.com.tactusoft.crm.model.entities.CrmLog;
import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmSapMedication;
import co.com.tactusoft.crm.model.entities.CrmSapMedicationDistinct;
import co.com.tactusoft.crm.model.entities.CrmUser;
import co.com.tactusoft.crm.model.entities.VwAppointmentMedication;
import co.com.tactusoft.crm.model.entities.VwMedication;
import co.com.tactusoft.crm.model.util.FacesUtilModel;
import co.com.tactusoft.crm.postsale.util.Utils;

@Service
public class PostsaleBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomHibernateDao dao;

	public void updateAppointment(String dateString) {
		dao.executeHQL("UPDATE CrmAppointment SET state = 4, closeAppointment = 1, dateModified = current_date(), dateClosed = current_date(), crmUserByIdUserClosed.id = 91"
				+ " WHERE state = 4 AND Date(startAppointmentDate) = '"
				+ dateString + "' AND closeAppointment = 0");

		dao.executeHQL("UPDATE CrmAppointment SET state = 5, closeAppointment = 1, dateModified = current_date(), dateClosed = current_date(), crmUserByIdUserClosed.id = 91"
				+ " WHERE state = 1 AND Date(startAppointmentDate) = '"
				+ dateString + "'");

		dao.executeHQL("UPDATE CrmAppointment SET state = 5, closeAppointment = 1, dateModified = current_date(), dateClosed = current_date(), crmUserByIdUserClosed.id = 91"
				+ " WHERE state = 3 AND Date(startAppointmentDate) = '"
				+ dateString + "' AND closeAppointment = 0");

		dao.executeHQL("UPDATE CrmAppointment SET closeAppointment = 1, dateClosed = current_date(), crmUserByIdUserClosed.id = 91"
				+ " WHERE state = 2 AND Date(startAppointmentDate) = '"
				+ dateString + "'");
	}

	public void updateCampaign(String dateString) {
		dao.executeHQL("UPDATE CrmCampaign SET state = 999 WHERE state = 1 AND Date(dateCall) <= '"
				+ dateString
				+ "' AND id IN (SELECT o.crmCampaign.id FROM CrmCampaignDetail o WHERE o.idCampaignType != 4 "
				+ " AND Date(o.callDate) <= '"
				+ dateString
				+ "' AND o.status = 0)");

		dao.executeHQL("UPDATE CrmCampaignDetail SET status = 999 WHERE status = 0 AND Date(callDate) <= '"
				+ dateString + "' AND idCampaignType != 4");
	}

	public List<CrmAppointment> getListAppointmentNoAttendet(String dateString) {
		return dao
				.find("FROM CrmAppointment o WHERE state = 5 AND Date(startAppointmentDate) >= '"
						+ dateString
						+ "' AND id NOT IN (SELECT b.crmAppointment.id FROM CrmCampaignDetail b WHERE b.idCampaignType = 1"
						+ " AND b.crmAppointment.id IS NOT NULL)");
	}

	public List<CrmAppointment> getListAppointmentConfirmed(
			String yesterdayString, String currentDateString) {
		return dao
				.find("FROM CrmAppointment o WHERE o.state = 1 AND Date(o.startAppointmentDate) = '"
						+ yesterdayString
						+ "' AND Date(o.dateCreate) <> '"
						+ currentDateString + "'");
	}

	public List<CrmAppointment> getListAppointmentControl(String dateString) {
		String sql = "select distinct b.* "
				+ "from crm_db.crm_appointment b inner join crm_db.crm_patient a on b.id_patient=a.id inner join crm_db.crm_procedure_detail f on b.id_procedure_detail=f.id "
				+ "where (f.control_type = 1)  "
				+ "and (b.state in (1 , 3 , 4))  "
				+ "and b.start_appointment_date <'"
				+ dateString
				+ "' and b.id  = (select max(d.id) "
				+ "from crm_db.crm_appointment d inner join crm_db.crm_patient c on d.id_patient=c.id inner join crm_db.crm_procedure_detail g on d.id_procedure_detail=g.id "
				+ "where (g.control_type = 1)  "
				+ "and (d.state in (1 , 3 , 4))  "
				+ "and d.start_appointment_date <'"
				+ dateString
				+ "' and c.id = a.id) "
				+ "and b.id not in (select e.id_appointment from crm_campaign_detail e where e.id_campaign_type=3) "
				+ "and b.id_branch not in (select b.id_crm from tmp_hom_branch b )";

		return dao.findNative(sql, CrmAppointment.class);
	}

	public List<CrmAppointment> getListAppointmentClosed(String dateString) {
		return dao
				.find("FROM CrmAppointment o WHERE o.crmProcedureDetail.formulaDocTypePs IS NOT NULL AND "
						+ "state = 4 AND closeAppointment = 1 AND Date(startAppointmentDate) >=  '"
						+ dateString
						+ "' AND o.id IN (SELECT m.crmAppointment.id from CrmMedication m)");
	}

	public CrmUser getUser(CrmBranch crmBranch, String date,
			Integer idCampaignType) {
		CrmUser result = null;
		List<CrmUser> list = dao.findNative("SELECT b.* "
				+ "FROM crm_db.crm_campaign a  "
				+ "right OUTER JOIN crm_db.crm_user b  "
				+ "ON (a.id_user=b.id and date(a.date_call)='" + date + "') "
				+ "LEFT OUTER JOIN crm_db.crm_user_branch_postsale c  "
				+ "ON b.id=c.id_user "
				+ "LEFT OUTER JOIN crm_db.crm_campaign_detail d "
				+ "ON (d.id_campaign = a.id AND d.id_campaign_type = "
				+ idCampaignType + ") WHERE b.state=1 and c.id_branch="
				+ crmBranch.getId() + " GROUP BY b.id ORDER BY count(a.id)",
				CrmUser.class);
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	public CrmBranch getBranch(CrmPatient crmPatient) {
		CrmBranch result = new CrmBranch();
		List<CrmBranch> list = dao
				.find("SELECT o.crmBranch FROM CrmAppointment o WHERE o.crmPatient.id = "
						+ crmPatient.getId()
						+ " ORDER BY o.startAppointmentDate DESC");
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	public void updateCrmSapMedication(String currentDate, int idLog) {
		dao.executeHQL("UPDATE CrmSapMedication SET status = 'PROCESADO' WHERE Date(dateBill) >= '"
				+ currentDate + "' AND idLog = " + idLog);
	}

	public List<CrmSapMedicationDistinct> getListSapMedicationByLoadStateDistinct(
			String currentDate) {
		return dao
				.findNative(
						"SELECT DISTINCT id_bill,doc_patient,id_patient,date_bill,type_bill "
								+ "FROM crm_sap_medication a JOIN crm_patient b ON (a.doc_patient = b.doc) "
								+ "WHERE id_appointment IS NULL AND Date(date_bill) >= '"
								+ currentDate
								+ "' AND type_bill IS NOT NULL ORDER BY id_bill",
						CrmSapMedicationDistinct.class);
	}

	public List<CrmSapMedicationDistinct> getListSapMedicationByLoadStateDistinct() {
		return dao
				.findNative(
						"SELECT DISTINCT id_bill,doc_patient,id_patient,date_bill,type_bill "
								+ "FROM crm_sap_medication a JOIN crm_patient b ON (a.doc_patient = b.doc) "
								+ "WHERE id_appointment IS NULL AND Date(date_bill) >= '2014-12-23' AND type_bill IS NOT NULL ORDER BY id_bill",
						CrmSapMedicationDistinct.class);
	}

	public List<CrmSapMedicationDistinct> getListSapMedicationByLoadStateDistinct(
			String currentDate, String docs) {
		return dao
				.findNative(
						"SELECT DISTINCT id_bill,id_patient,date_bill,type_bill "
								+ "FROM crm_sap_medication a JOIN crm_patient b ON (a.doc_patient = b.doc) "
								+ "WHERE id_appointment IS NULL AND Date(date_bill) >= '"
								+ currentDate
								+ "' AND doc_patient IN ("
								+ docs
								+ ") AND type_bill IS NOT NULL ORDER BY id_bill",
						CrmSapMedicationDistinct.class);
	}

	public List<CrmSapMedicationDistinct> getListSapMedicationByDate(
			String startDate, String finishDate) {
		return dao
				.findNative(
						"SELECT DISTINCT id_bill,id_patient,date_bill,type_bill "
								+ "FROM crm_sap_medication a JOIN crm_patient b ON (a.doc_patient = b.doc) "
								+ "WHERE id_appointment IS NULL AND Date(date_bill) between '"
								+ startDate
								+ "' AND '"
								+ finishDate
								+ "' AND a.id_appointment is null AND type_bill IS NOT NULL ORDER BY id_bill",
						CrmSapMedicationDistinct.class);
	}

	public CrmAppointment getAppointment(String doc, String initDate,
			String endDate, String typeBill) {
		List<CrmAppointment> list = dao
				.find("FROM CrmAppointment o WHERE o.crmPatient.doc = '"
						+ doc
						+ "' AND Date(o.startAppointmentDate) BETWEEN '"
						+ initDate
						+ "' AND '"
						+ endDate
						+ "' AND o.state = 4 "
						+ " AND o.id IN (SELECT b.crmAppointment.id FROM CrmMedication b)"
						+ " ORDER BY o.startAppointmentDate DESC", 1);
		CrmAppointment result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	public CrmAppointment getAppointmentByEmail(String email, String initDate,
			String endDate) {
		List<CrmAppointment> list = dao.find(
				"FROM CrmAppointment o WHERE o.crmPatient.email = '" + email
						+ "' AND Date(o.dateCreate) BETWEEN '" + initDate
						+ "' AND '" + endDate + "' ORDER BY o.dateCreate DESC",
				1);
		CrmAppointment result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	public List<CrmSapMedication> getListSapMedicationByAppointment(
			BigDecimal idAppointment) {
		return dao.find("FROM CrmSapMedication o WHERE o.idAppointment = "
				+ idAppointment + " ORDER BY o.id");
	}

	public int updateSapMedicationById(String idBill, String typeBill,
			BigDecimal idAppointment, Integer idLog) {
		return dao
				.executeHQL("UPDATE CrmSapMedication o SET o.idAppointment = "
						+ idAppointment + ",idLog = " + idLog
						+ ",status='PROCESADO' WHERE o.id.idBill = '" + idBill
						+ "' AND typeBill = '" + typeBill + "'");
	}

	public List<VwAppointmentMedication> getListAppointmentMedicationByCode(
			String code) {
		return dao.find("FROM VwAppointmentMedication o WHERE code = '" + code
				+ "'");
	}

	public List<CrmCampaignDetail> getListCampaignDetailMedication(CrmLog log) {
		return dao
				.find("FROM CrmCampaignDetail o WHERE o.idCampaignType = 4 AND o.crmCampaign.crmLog.id = "
						+ log.getId());
	}

	public List<CrmCampaignDetail> getListCampaignDetailMedication1(CrmLog log) {
		return dao
				.find("FROM CrmCampaignDetail o WHERE o.idCampaignType = 4 AND o.crmCampaign.crmLog.id = "
						+ log.getId()
						+ " AND o.crmCampaign NOT IN (SELECT x.crmCampaign FROM CrmCampaignMedication x)");
	}

	public List<VwMedication> getListVwMedicationByAppointment(
			BigDecimal idAppointment) {
		return dao.find("FROM VwMedication o WHERE idAppointment = "
				+ idAppointment);
	}

	public int save(Object entity) {
		int result = 0;
		try {
			result = dao.persist(entity);
		} catch (RuntimeException ex) {
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

	public List<CrmHoliday> getListHoliday(Date date, BigDecimal idBranch) {
		String currenDate = FacesUtilModel.formatDate(date, "yyyy-MM-dd");
		return dao
				.find("select o.crmHoliday from CrmHolidayBranch o where o.crmHoliday.holiday = '"
						+ currenDate + "' and o.crmBranch.id = " + idBranch);
	}

	public List<CrmBranch> getListBranchActive() {
		return dao.find("from CrmBranch o");
	}

	public List<CrmLog> getListLog(String currentDateString) {
		return dao.find("from CrmLog o where Date(o.logDate) = '"
				+ currentDateString + "'");
	}

	public int getLogLastDay(Date currentDate) {
		CrmLog lastLog = (CrmLog) dao.find(
				"FROM CrmLog o WHERE id = (SELECT MAX(id) FROM CrmLog)").get(0);
		Date today = Utils.getDateWithoutTime(currentDate);
		Date lastDate = Utils.getDateWithoutTime(lastLog.getLogDate());
		long diff = Math.abs(today.getTime() - lastDate.getTime());
		Long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays.intValue();
	}

	public List<CrmCampaign> getListCrmAppointment(Integer idLog) {
		return dao.find("FROM CrmCampaign o WHERE o.crmLog.id = " + idLog);
	}

	public List<CrmPatient> getListPatientBlock() {
		return dao.find("FROM CrmPatient o WHERE o.cycle = 0");
	}

	public List<CrmBlockHistoryDetail> getListBlockHistoryDetailLast(
			BigDecimal idPatient) {
		return dao
				.find("FROM CrmBlockHistoryDetail a WHERE a.crmBlockHistory.id = "
						+ "(SELECT MAX(b.id) FROM CrmBlockHistory b WHERE b.crmPatient.id = "
						+ idPatient + ")");
	}

	public List<CrmInfunsion1> getListCrmInfunsion1(String currentDateString) {
		return dao
				.find("from CrmInfunsion1 o where Date(o.eventDate) = '"
						+ currentDateString
						+ "' AND eventType = 'REGISTRO_FORMULARIO'");
	}
	
	public List<CrmInfusionTag> getListCrmInfunsionTag() {
		return dao
				.find("from CrmInfusionTag o order by o.orderby");
	}

}
