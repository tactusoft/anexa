package co.com.tactusoft.crm.controller.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import co.com.tactusoft.crm.model.dao.CustomHibernateDao;
import co.com.tactusoft.crm.model.entities.VwRipsAppointment;
import co.com.tactusoft.crm.model.entities.VwRipsMedication;
import co.com.tactusoft.crm.model.entities.VwRipsPatient;
import co.com.tactusoft.crm.model.entities.VwRipsProcedure;
import co.com.tactusoft.crm.model.entities.VwRipsTransaction;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.view.beans.RIPSBean;

@Named
public class RIPSBo implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SEPARATOR = ",";

	@Inject
	private CustomHibernateDao dao;

	public RIPSBean getListPatient(String path, String fileName,
			BigDecimal idBranch, String startDate, String endDate,
			String typeHistory) throws IOException {
		File file = new File(path + "/US" + fileName + ".txt");
		List<VwRipsPatient> list = null;
		OutputStreamWriter fp = new OutputStreamWriter(new FileOutputStream(
				file), "UTF-8");
		// byte buf[] = null;

		String sql = "FROM VwRipsPatient o WHERE o.appointmentDate BETWEEN '"
				+ startDate + "' AND '" + endDate + "' AND o.idBranch = "
				+ idBranch + " AND typeHistory = '" + typeHistory + "'";
		list = dao.find(sql);

		if (typeHistory.equals(Constant.MEDICAL_HISTORY_TYPE)) {
			for (VwRipsPatient row : list) {
				StringBuilder result = new StringBuilder();
				result.append(row.getDocType());
				result.append(SEPARATOR);
				result.append(row.getDoc());
				result.append(SEPARATOR);
				result.append(row.getBranch());
				result.append(SEPARATOR);
				result.append(row.getMembership());
				result.append(SEPARATOR);
				result.append(row.getSurnames());
				result.append(SEPARATOR);
				result.append(row.getSurnames2());
				result.append(SEPARATOR);
				result.append(row.getFirstnames());
				result.append(SEPARATOR);
				result.append(row.getFirstnames2());
				result.append(SEPARATOR);
				result.append(row.getAge().intValue());
				result.append(SEPARATOR);
				result.append(row.getSizeUnit());
				result.append(SEPARATOR);
				result.append(row.getGender());
				result.append(SEPARATOR);
				result.append(row.getRegion());
				result.append(SEPARATOR);
				result.append(row.getCity());
				result.append(SEPARATOR);
				result.append(row.getTypeHousing());
				result.append(Constant.NEW_LINE);
				// buf = result.toString().getBytes();
				fp.write(result.toString());
				fp.flush();
			}
		} else {
			for (VwRipsPatient row : list) {
				StringBuilder result = new StringBuilder();
				result.append(row.getDocType());
				result.append(SEPARATOR);
				result.append(row.getDoc());
				result.append(SEPARATOR);
				result.append(row.getIdEps());
				result.append(SEPARATOR);
				result.append("4");
				result.append(SEPARATOR);
				result.append(row.getSurnames());
				result.append(SEPARATOR);
				result.append(row.getSurnames2());
				result.append(SEPARATOR);
				result.append(row.getFirstnames());
				result.append(SEPARATOR);
				result.append(row.getFirstnames2());
				result.append(SEPARATOR);
				result.append(row.getAge());
				result.append(SEPARATOR);
				result.append(row.getSizeUnit());
				result.append(SEPARATOR);
				result.append(row.getGender());
				result.append(SEPARATOR);
				result.append(row.getRegion());
				result.append(SEPARATOR);
				result.append(row.getCity());
				result.append(SEPARATOR);
				result.append(row.getTypeHousing());
				result.append(Constant.NEW_LINE);
				// buf = result.toString().getBytes();
				fp.write(result.toString());
				fp.flush();
			}
		}

		fp.close();

		return new RIPSBean(file, list != null ? list.size() : 0);
	}

	public RIPSBean getListAppointment(String path, String fileName,
			BigDecimal idBranch, String startDate, String endDate,
			String typeHistory) {
		File file = new File(path + "/AC" + fileName + ".txt");
		List<VwRipsAppointment> list = null;
		try {
			OutputStream fp = new FileOutputStream(file);
			byte buf[] = null;

			String sql = "FROM VwRipsAppointment o WHERE o.id.appointmentDate BETWEEN '"
					+ startDate
					+ "' AND '"
					+ endDate
					+ "' AND o.id.idBranch = "
					+ idBranch
					+ " AND typeHistory = '" + typeHistory + "'";
			list = dao.find(sql);

			for (VwRipsAppointment row : list) {
				try {
					StringBuilder result = new StringBuilder();
					if (row != null) {
						result.append(row.getIdBill());
						result.append(SEPARATOR);
						result.append(row.getClinic());
						result.append(SEPARATOR);
						result.append(row.getDocType());
						result.append(SEPARATOR);
						result.append(row.getDoc());
						result.append(SEPARATOR);
						result.append(FacesUtil.formatDate(
								row.getAppointmentDate(), "dd/MM/yyyy"));
						result.append(SEPARATOR);
						if (typeHistory
								.equals(Constant.ODONTOLOGY_HISTORY_TYPE)) {
							result.append(row.getAppointmentType());
							result.append(SEPARATOR);
						}
						result.append(row.getAutorization());
						result.append(SEPARATOR);
						result.append(row.getAppointmentCode());
						result.append(SEPARATOR);
						result.append(row.getAppointmentTarget());
						result.append(SEPARATOR);
						result.append(row.getAppointmentExternal());
						result.append(SEPARATOR);
						result.append(row.getDiagnosis1());
						result.append(SEPARATOR);
						result.append(row.getDiagnosis2());
						result.append(SEPARATOR);
						result.append(row.getDiagnosis3());
						result.append(SEPARATOR);
						result.append(row.getDiagnosis4());
						result.append(SEPARATOR);
						result.append(row.getDiagnosisType());
						result.append(SEPARATOR);
						result.append(row.getAmount());
						result.append(SEPARATOR);
						result.append(row.getQuote());
						result.append(SEPARATOR);
						result.append(row.getTotal());
						result.append(Constant.NEW_LINE);
						buf = result.toString().getBytes();
						fp.write(buf);
						fp.flush();
					}
				} catch (Exception ex) {
					System.out.println("");
				}
			}
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RIPSBean(file, list != null ? list.size() : 0);
	}

	public RIPSBean getListProcedure(String path, String fileName,
			BigDecimal idBranch, String startDate, String endDate,
			String typeHistory) {
		File file = new File(path + "/AP" + fileName + ".txt");
		List<VwRipsProcedure> list = null;
		try {
			OutputStream fp = new FileOutputStream(file);
			byte buf[] = null;

			String sql = "FROM VwRipsProcedure o WHERE o.id.appointmentDate BETWEEN '"
					+ startDate
					+ "' AND '"
					+ endDate
					+ "' AND o.id.idBranch = "
					+ idBranch
					+ " AND o.id.typeHistory = '" + typeHistory + "'";
			list = dao.find(sql);

			for (VwRipsProcedure row : list) {
				StringBuilder result = new StringBuilder();
				result.append(row.getId().getIdBill());
				result.append(SEPARATOR);
				result.append(row.getId().getClinic());
				result.append(SEPARATOR);
				result.append(row.getId().getDocType());
				result.append(SEPARATOR);
				result.append(row.getId().getDoc());
				result.append(SEPARATOR);
				result.append(FacesUtil.formatDate(row.getId()
						.getAppointmentDate(), "dd/MM/yyyy"));
				result.append(SEPARATOR);
				result.append(row.getId().getAutorization());
				result.append(SEPARATOR);
				if (typeHistory.equals(Constant.ODONTOLOGY_HISTORY_TYPE)) {
					result.append(row.getId().getProcedureType());
					result.append(SEPARATOR);
				}
				result.append(row.getId().getProcedureCode());
				result.append(SEPARATOR);
				// result.append(row.getId().getProcedureScope());
				result.append(1);
				result.append(SEPARATOR);
				// result.append(row.getId().getProcedureTarget());
				result.append(2);
				result.append(SEPARATOR);
				result.append(row.getId().getPersonal());
				result.append(SEPARATOR);
				result.append(row.getId().getDiagnosis1());
				result.append(SEPARATOR);
				if (typeHistory.equals(Constant.MEDICAL_HISTORY_TYPE)) {
					result.append(row.getId().getDiagnosis2());
					result.append(SEPARATOR);
				}
				result.append(row.getId().getComplication());
				result.append(SEPARATOR);
				result.append(row.getId().getSurgical());
				result.append(SEPARATOR);
				result.append(row.getId().getAmount());
				result.append(Constant.NEW_LINE);
				buf = result.toString().getBytes();
				fp.write(buf);
				fp.flush();
			}
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RIPSBean(file, list != null ? list.size() : 0);
	}

	public RIPSBean getListMedication(String path, String fileName,
			BigDecimal idBranch, String startDate, String endDate) {
		File file = new File(path + "/AM" + fileName + ".txt");
		List<VwRipsMedication> list = null;

		try {
			OutputStream fp = new FileOutputStream(file);
			byte buf[] = null;

			String sql = "FROM VwRipsMedication o WHERE o.id.appointmentDate BETWEEN '"
					+ startDate
					+ "' AND '"
					+ endDate
					+ "' AND o.id.idBranch = " + idBranch;
			list = dao.find(sql);

			for (VwRipsMedication row : list) {
				StringBuilder result = new StringBuilder();
				result.append(row.getId().getIdBill());
				result.append(SEPARATOR);
				result.append(row.getId().getClinic());
				result.append(SEPARATOR);
				result.append(row.getId().getDocType());
				result.append(SEPARATOR);
				result.append(row.getId().getDoc());
				result.append(SEPARATOR);
				result.append(row.getId().getAutorization());
				result.append(SEPARATOR);
				result.append(row.getId().getMedicationCode());
				result.append(SEPARATOR);
				// result.append(row.getId().getMedicationType());
				result.append(2);
				result.append(SEPARATOR);
				result.append(row.getId().getMedicationName());
				result.append(SEPARATOR);
				result.append(row.getId().getMedicationForm());
				result.append(SEPARATOR);
				result.append(row.getId().getMedicationConcentration());
				result.append(SEPARATOR);
				result.append(row.getId().getMedicationSize());
				result.append(SEPARATOR);
				result.append(row.getId().getUnit());
				result.append(SEPARATOR);
				result.append(row.getId().getAmount());
				result.append(SEPARATOR);
				result.append(row.getId().getTotal());
				result.append(Constant.NEW_LINE);
				buf = result.toString().getBytes();
				fp.write(buf);
				fp.flush();
			}
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RIPSBean(file, list != null ? list.size() : 0);
	}

	public RIPSBean getListTransaction(String path, String fileName,
			BigDecimal idBranch, String startDate, String endDate,
			String startDateFormat, String endDateFormat, String typeHistory) {
		File file = new File(path + "/AF" + fileName + ".txt");
		List<VwRipsTransaction> list = null;
		try {
			OutputStream fp = new FileOutputStream(file);
			byte buf[] = null;

			String sql = "FROM VwRipsTransaction o WHERE o.id.appointmentDate BETWEEN '"
					+ startDate
					+ "' AND '"
					+ endDate
					+ "' AND o.id.idBranch = "
					+ idBranch
					+ " AND o.id.typeHistory = '" + typeHistory + "'";

			list = dao.find(sql);

			for (VwRipsTransaction row : list) {
				StringBuilder result = new StringBuilder();
				result.append(row.getId().getClinic());
				result.append(SEPARATOR);
				result.append("");
				result.append(SEPARATOR);
				result.append(row.getId().getDocType());
				result.append(SEPARATOR);
				result.append(row.getId().getDoc());
				result.append(SEPARATOR);
				result.append(row.getId().getIdBill());
				result.append(SEPARATOR);
				result.append(FacesUtil.formatDate(row.getId().getDateBill(),
						"dd/MM/yyyy"));
				result.append(SEPARATOR);
				result.append(startDateFormat);
				result.append(SEPARATOR);
				result.append(endDateFormat);
				result.append(SEPARATOR);
				result.append(row.getId().getEpsCode());
				result.append(SEPARATOR);
				result.append(row.getId().getEpsName());
				result.append(SEPARATOR);
				result.append(row.getId().getContract());
				result.append(SEPARATOR);
				result.append(row.getId().getBenefitPlan());
				result.append(SEPARATOR);
				result.append(row.getId().getPolicy());
				result.append(SEPARATOR);
				result.append(row.getId().getCopayment());
				result.append(SEPARATOR);
				result.append(row.getId().getCommission());
				result.append(SEPARATOR);
				result.append(row.getId().getDiscount());
				result.append(SEPARATOR);
				result.append(row.getId().getAmount());
				result.append(Constant.NEW_LINE);
				buf = result.toString().getBytes();
				fp.write(buf);
				fp.flush();
			}
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RIPSBean(file, list != null ? list.size() : 0);
	}

	public RIPSBean getListControl(RIPSBean[] fileList, String path,
			String fileName, String enablingCode) {
		File file = new File(path + "/CT" + fileName + ".txt");
		String dateString = FacesUtil.formatDate(new Date(), "dd/MM/yyyy");
		try {
			OutputStream fp = new FileOutputStream(file);
			byte buf[] = null;
			for (int i = 0; i < fileList.length - 1; i++) {
				RIPSBean row = fileList[i];
				StringBuilder result = new StringBuilder();
				result.append(enablingCode);
				result.append(SEPARATOR);
				result.append(dateString);
				result.append(SEPARATOR);
				result.append(row.getFile().getName().substring(0, 2));
				result.append(SEPARATOR);
				result.append(row.getRowsNumber());
				result.append(Constant.NEW_LINE);
				buf = result.toString().getBytes();
				fp.write(buf);
				fp.flush();
			}
			fp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RIPSBean(file, 0);
	}

}
