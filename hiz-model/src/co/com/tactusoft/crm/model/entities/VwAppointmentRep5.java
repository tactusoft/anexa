package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_appointment_rep_5", catalog = "crm_db")
public class VwAppointmentRep5 implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private BigDecimal patientId;
	private String patientNames;
	private String patientDoc;
	private String patientPhoneNumber;
	private String patientCellNumber;
	private String patientEmail;
	private String appCode1;
	private Date appDate1;
	private String appBranch1;
	private String appProcedure1;
	private String appCode2;
	private Date appDate2;
	private String appBranch2;
	private String cityInidicative;

	public VwAppointmentRep5() {

	}

	@Id
	@Column(name = "id", nullable = false, scale = 0)
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "patient_id")
	public BigDecimal getPatientId() {
		return patientId;
	}

	public void setPatientId(BigDecimal patientId) {
		this.patientId = patientId;
	}

	@Column(name = "patient_names")
	public String getPatientNames() {
		return patientNames;
	}

	public void setPatientNames(String patientNames) {
		this.patientNames = patientNames;
	}

	@Column(name = "patient_doc")
	public String getPatientDoc() {
		return patientDoc;
	}

	public void setPatientDoc(String patientDoc) {
		this.patientDoc = patientDoc;
	}

	@Column(name = "patient_phone_number")
	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	@Column(name = "patient_cell_number")
	public String getPatientCellNumber() {
		return patientCellNumber;
	}

	public void setPatientCellNumber(String patientCellNumber) {
		this.patientCellNumber = patientCellNumber;
	}

	@Column(name = "patient_email")
	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	@Column(name = "app_code_1")
	public String getAppCode1() {
		return appCode1;
	}

	public void setAppCode1(String appCode1) {
		this.appCode1 = appCode1;
	}

	@Column(name = "app_date_1", nullable = false, length = 19)
	public Date getAppDate1() {
		return appDate1;
	}

	public void setAppDate1(Date appDate1) {
		this.appDate1 = appDate1;
	}

	@Column(name = "app_branch_1")
	public String getAppBranch1() {
		return appBranch1;
	}

	public void setAppBranch1(String appBranch1) {
		this.appBranch1 = appBranch1;
	}

	@Column(name = "app_procedure_1")
	public String getAppProcedure1() {
		return appProcedure1;
	}

	public void setAppProcedure1(String appProcedure1) {
		this.appProcedure1 = appProcedure1;
	}

	@Column(name = "app_code_2")
	public String getAppCode2() {
		return appCode2;
	}

	public void setAppCode2(String appCode2) {
		this.appCode2 = appCode2;
	}

	@Column(name = "app_date_2", nullable = false, length = 19)
	public Date getAppDate2() {
		return appDate2;
	}

	public void setAppDate2(Date appDate2) {
		this.appDate2 = appDate2;
	}

	@Column(name = "app_branch_2")
	public String getAppBranch2() {
		return appBranch2;
	}

	public void setAppBranch2(String appBranch2) {
		this.appBranch2 = appBranch2;
	}

	@Column(name = "city_indicative")
	public String getCityInidicative() {
		return cityInidicative;
	}

	public void setCityInidicative(String cityInidicative) {
		this.cityInidicative = cityInidicative;
	}

}
