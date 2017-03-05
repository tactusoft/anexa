package co.com.tactusoft.crm.model.entities;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import co.com.tactusoft.crm.model.util.FacesUtilModel;

/**
 * CrmPatient generated by hbm2java
 */
@Entity
@Table(name = "crm_patient", catalog = "crm_db", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "doc", "doc_type" }),
		@UniqueConstraint(columnNames = "code_sap") })
public class CrmPatient implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private CrmOccupation crmOccupation;
	private CrmUser crmUserByIdUserCreate;
	private CrmUser crmUserByIdUserModified;
	private CrmProfile crmProfile;
	private CrmCity crmCity;
	private CrmPatient crmPatient;
	private CrmMarketingActivity crmMarketingActivity;
	private String doc;
	private String codeSap;
	private String docType;
	private String firstnames;
	private String surnames;
	private Date bornDate;
	private String gender;
	private String address;
	private String zipCode;
	private String neighborhood;
	private String phoneNumber;
	private String cellNumber;
	private String email;
	private String typeHousing;
	private BigDecimal idCountry;
	private BigDecimal idRegion;
	private BigDecimal idCity;
	private BigDecimal idGuardianType;
	private String guardian;
	private String guardianAddress;
	private String guardianRelationship;
	private String guardianTelephone;
	private String obs;
	private boolean cycle;
	private Boolean sendPhone;
	private Boolean sendEmail;
	private Boolean sendPostal;
	private Boolean sendSms;
	private String salesOrg;
	private Date dateCreate;
	private Date dateModified;
	private BigDecimal idMaritalState;
	private BigDecimal idMemberShip;
	private BigDecimal idEps;
	private String ticket;
	private Integer patientType;
	private Integer idBusinessGroup;
	private Set<CrmHistoryPhysique> crmHistoryPhysiques = new HashSet<CrmHistoryPhysique>(
			0);
	private Set<CrmHistoryHomeopathic> crmHistoryHomeopathics = new HashSet<CrmHistoryHomeopathic>(
			0);
	private Set<CrmHistoryOrganometry> crmHistoryOrganometries = new HashSet<CrmHistoryOrganometry>(
			0);
	private Set<CrmHistoryRecord> crmHistoryRecords = new HashSet<CrmHistoryRecord>(
			0);
	private Set<CrmHistoryHistory> crmHistoryHistories = new HashSet<CrmHistoryHistory>(
			0);
	private Set<CrmAppointment> crmAppointments = new HashSet<CrmAppointment>(0);
	private Set<CrmNote> crmNotes = new HashSet<CrmNote>(0);
	private Set<CrmPatientTicket> crmPatientTickets = new HashSet<CrmPatientTicket>(
			0);

	private String names;
	private int age;

	public CrmPatient() {
	}

	public CrmPatient(BigDecimal id) {
		this.id = id;
	}

	public CrmPatient(BigDecimal id, String doc, String codeSap, String docType) {
		this.id = id;
		this.doc = doc;
		this.codeSap = codeSap;
		this.docType = docType;
	}

	public CrmPatient(BigDecimal id, CrmOccupation crmOccupation,
			CrmUser crmUserByIdUserCreate, CrmUser crmUserByIdUserModified,
			CrmProfile crmProfile, String doc, String codeSap, String docType,
			String firstnames, String surnames, Date bornDate, String gender,
			String address, String zipCode, String neighborhood,
			String phoneNumber, String cellNumber, String email,
			String typeHousing, BigDecimal idCountry, BigDecimal idRegion,
			BigDecimal idCity, String guardian, String guardianAddress,
			String guardianRelationship, String guardianTelephone, String obs,
			Boolean cycle, Boolean sendPhone, Boolean sendEmail,
			Boolean sendPostal, Boolean sendSms, String salesOrg,
			Date dateCreate, Date dateModified,
			Set<CrmHistoryPhysique> crmHistoryPhysiques,
			Set<CrmHistoryHomeopathic> crmHistoryHomeopathics,
			Set<CrmHistoryOrganometry> crmHistoryOrganometries,
			Set<CrmHistoryRecord> crmHistoryRecords,
			Set<CrmHistoryHistory> crmHistoryHistories,
			Set<CrmAppointment> crmAppointments, Set<CrmNote> crmNotes) {
		this.id = id;
		this.crmOccupation = crmOccupation;
		this.crmUserByIdUserCreate = crmUserByIdUserCreate;
		this.crmUserByIdUserModified = crmUserByIdUserModified;
		this.crmProfile = crmProfile;
		this.doc = doc;
		this.codeSap = codeSap;
		this.docType = docType;
		this.firstnames = firstnames;
		this.surnames = surnames;
		this.bornDate = bornDate;
		this.gender = gender;
		this.address = address;
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.phoneNumber = phoneNumber;
		this.cellNumber = cellNumber;
		this.email = email;
		this.typeHousing = typeHousing;
		this.idCountry = idCountry;
		this.idRegion = idRegion;
		this.idCity = idCity;
		this.guardian = guardian;
		this.guardianAddress = guardianAddress;
		this.guardianRelationship = guardianRelationship;
		this.guardianTelephone = guardianTelephone;
		this.obs = obs;
		this.cycle = cycle;
		this.sendPhone = sendPhone;
		this.sendEmail = sendEmail;
		this.sendPostal = sendPostal;
		this.sendSms = sendSms;
		this.salesOrg = salesOrg;
		this.dateCreate = dateCreate;
		this.dateModified = dateModified;
		this.crmHistoryPhysiques = crmHistoryPhysiques;
		this.crmHistoryHomeopathics = crmHistoryHomeopathics;
		this.crmHistoryOrganometries = crmHistoryOrganometries;
		this.crmHistoryRecords = crmHistoryRecords;
		this.crmHistoryHistories = crmHistoryHistories;
		this.crmAppointments = crmAppointments;
		this.crmNotes = crmNotes;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_occupation")
	public CrmOccupation getCrmOccupation() {
		return this.crmOccupation;
	}

	public void setCrmOccupation(CrmOccupation crmOccupation) {
		this.crmOccupation = crmOccupation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_create")
	public CrmUser getCrmUserByIdUserCreate() {
		return this.crmUserByIdUserCreate;
	}

	public void setCrmUserByIdUserCreate(CrmUser crmUserByIdUserCreate) {
		this.crmUserByIdUserCreate = crmUserByIdUserCreate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user_modified")
	public CrmUser getCrmUserByIdUserModified() {
		return this.crmUserByIdUserModified;
	}

	public void setCrmUserByIdUserModified(CrmUser crmUserByIdUserModified) {
		this.crmUserByIdUserModified = crmUserByIdUserModified;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profile")
	public CrmProfile getCrmProfile() {
		return this.crmProfile;
	}

	public void setCrmProfile(CrmProfile crmProfile) {
		this.crmProfile = crmProfile;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_city", updatable = false, insertable = false)
	public CrmCity getCrmCity() {
		return crmCity;
	}

	public void setCrmCity(CrmCity crmCity) {
		this.crmCity = crmCity;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_group")
	public CrmPatient getCrmPatient() {
		return crmPatient;
	}

	public void setCrmPatient(CrmPatient crmPatient) {
		this.crmPatient = crmPatient;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_marketing_activity")
	@NotFound(action = NotFoundAction.IGNORE)
	public CrmMarketingActivity getCrmMarketingActivity() {
		return crmMarketingActivity;
	}

	public void setCrmMarketingActivity(
			CrmMarketingActivity crmMarketingActivity) {
		this.crmMarketingActivity = crmMarketingActivity;
	}

	@Column(name = "doc", nullable = false, length = 45)
	public String getDoc() {
		return this.doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	@Column(name = "code_sap", unique = true, nullable = false, length = 45)
	public String getCodeSap() {
		return this.codeSap;
	}

	public void setCodeSap(String codeSap) {
		this.codeSap = codeSap;
	}

	@Column(name = "doc_type", nullable = false, length = 2)
	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	@Column(name = "firstnames", length = 45)
	public String getFirstnames() {
		return this.firstnames;
	}

	public void setFirstnames(String firstnames) {
		this.firstnames = firstnames;
	}

	@Column(name = "surnames", length = 45)
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "born_date", length = 10)
	public Date getBornDate() {
		return this.bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	@Column(name = "gender", length = 45)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "zip_code", length = 45)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "neighborhood", length = 45)
	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	@Column(name = "phone_number", length = 45)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "cell_number", length = 45)
	public String getCellNumber() {
		return this.cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	@Column(name = "email", length = 1000)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "type_housing", length = 45)
	public String getTypeHousing() {
		return this.typeHousing;
	}

	public void setTypeHousing(String typeHousing) {
		this.typeHousing = typeHousing;
	}

	@Column(name = "id_country", scale = 0)
	public BigDecimal getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(BigDecimal idCountry) {
		this.idCountry = idCountry;
	}

	@Column(name = "id_region", scale = 0)
	public BigDecimal getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(BigDecimal idRegion) {
		this.idRegion = idRegion;
	}

	@Column(name = "id_city", scale = 0)
	public BigDecimal getIdCity() {
		return idCity;
	}

	public void setIdCity(BigDecimal idCity) {
		this.idCity = idCity;
	}

	@Column(name = "id_guardian_type")
	public BigDecimal getIdGuardianType() {
		return idGuardianType;
	}

	public void setIdGuardianType(BigDecimal idGuardianType) {
		this.idGuardianType = idGuardianType;
	}

	@Column(name = "guardian")
	public String getGuardian() {
		return this.guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	@Column(name = "guardian_address")
	public String getGuardianAddress() {
		return this.guardianAddress;
	}

	public void setGuardianAddress(String guardianAddress) {
		this.guardianAddress = guardianAddress;
	}

	@Column(name = "guardian_relationship")
	public String getGuardianRelationship() {
		return this.guardianRelationship;
	}

	public void setGuardianRelationship(String guardianRelationship) {
		this.guardianRelationship = guardianRelationship;
	}

	@Column(name = "guardian_telephone")
	public String getGuardianTelephone() {
		return this.guardianTelephone;
	}

	public void setGuardianTelephone(String guardianTelephone) {
		this.guardianTelephone = guardianTelephone;
	}

	@Column(name = "obs", length = 65535)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Column(name = "cycle")
	public boolean isCycle() {
		return this.cycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	@Column(name = "send_phone")
	public Boolean getSendPhone() {
		return this.sendPhone;
	}

	public void setSendPhone(Boolean sendPhone) {
		this.sendPhone = sendPhone;
	}

	@Column(name = "send_email")
	public Boolean getSendEmail() {
		return this.sendEmail;
	}

	public void setSendEmail(Boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Column(name = "send_postal")
	public Boolean getSendPostal() {
		return this.sendPostal;
	}

	public void setSendPostal(Boolean sendPostal) {
		this.sendPostal = sendPostal;
	}

	@Column(name = "send_sms")
	public Boolean getSendSms() {
		return this.sendSms;
	}

	public void setSendSms(Boolean sendSms) {
		this.sendSms = sendSms;
	}

	@Column(name = "sales_org", length = 45)
	public String getSalesOrg() {
		return this.salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_create", length = 19)
	public Date getDateCreate() {
		return this.dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", length = 19)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Column(name = "id_marital_state", scale = 0)
	public BigDecimal getIdMaritalState() {
		return idMaritalState;
	}

	public void setIdMaritalState(BigDecimal idMaritalState) {
		this.idMaritalState = idMaritalState;
	}

	@Column(name = "id_membership", scale = 0)
	public BigDecimal getIdMemberShip() {
		return idMemberShip;
	}

	public void setIdMemberShip(BigDecimal idMemberShip) {
		this.idMemberShip = idMemberShip;
	}

	@Column(name = "id_eps", scale = 0)
	public BigDecimal getIdEps() {
		return idEps;
	}

	public void setIdEps(BigDecimal idEps) {
		this.idEps = idEps;
	}

	@Column(name = "ticket", length = 45)
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Column(name = "patient_type")
	public Integer getPatientType() {
		return patientType;
	}

	public void setPatientType(Integer patientType) {
		this.patientType = patientType;
	}

	@Column(name = "id_business_group")
	public Integer getIdBusinessGroup() {
		return idBusinessGroup;
	}

	public void setIdBusinessGroup(Integer idBusinessGroup) {
		this.idBusinessGroup = idBusinessGroup;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmHistoryPhysique> getCrmHistoryPhysiques() {
		return this.crmHistoryPhysiques;
	}

	public void setCrmHistoryPhysiques(
			Set<CrmHistoryPhysique> crmHistoryPhysiques) {
		this.crmHistoryPhysiques = crmHistoryPhysiques;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmHistoryHomeopathic> getCrmHistoryHomeopathics() {
		return this.crmHistoryHomeopathics;
	}

	public void setCrmHistoryHomeopathics(
			Set<CrmHistoryHomeopathic> crmHistoryHomeopathics) {
		this.crmHistoryHomeopathics = crmHistoryHomeopathics;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmHistoryOrganometry> getCrmHistoryOrganometries() {
		return this.crmHistoryOrganometries;
	}

	public void setCrmHistoryOrganometries(
			Set<CrmHistoryOrganometry> crmHistoryOrganometries) {
		this.crmHistoryOrganometries = crmHistoryOrganometries;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmHistoryRecord> getCrmHistoryRecords() {
		return this.crmHistoryRecords;
	}

	public void setCrmHistoryRecords(Set<CrmHistoryRecord> crmHistoryRecords) {
		this.crmHistoryRecords = crmHistoryRecords;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmHistoryHistory> getCrmHistoryHistories() {
		return this.crmHistoryHistories;
	}

	public void setCrmHistoryHistories(
			Set<CrmHistoryHistory> crmHistoryHistories) {
		this.crmHistoryHistories = crmHistoryHistories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmAppointment> getCrmAppointments() {
		return this.crmAppointments;
	}

	public void setCrmAppointments(Set<CrmAppointment> crmAppointments) {
		this.crmAppointments = crmAppointments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmNote> getCrmNotes() {
		return this.crmNotes;
	}

	public void setCrmNotes(Set<CrmNote> crmNotes) {
		this.crmNotes = crmNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crmPatient")
	public Set<CrmPatientTicket> getCrmPatientTickets() {
		return crmPatientTickets;
	}

	public void setCrmPatientTickets(Set<CrmPatientTicket> crmPatientTickets) {
		this.crmPatientTickets = crmPatientTickets;
	}

	@Transient
	public String getNames() {
		if (FacesUtilModel.isEmptyOrBlank(this.names)) {
			if (FacesUtilModel.isEmptyOrBlank(this.surnames)) {
				this.names = this.firstnames;
			} else {
				this.names = this.surnames + " " + this.firstnames;
			}
		}
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	@Transient
	public int getAge() {
		if (bornDate != null) {
			Calendar bornDate = Calendar.getInstance();
			bornDate.setTime(this.bornDate);

			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(new Date());

			age = currentDate.get(Calendar.YEAR) - bornDate.get(Calendar.YEAR);

			if ((bornDate.get(Calendar.MONTH) > currentDate.get(Calendar.MONTH))
					|| (bornDate.get(Calendar.MONTH) == currentDate
							.get(Calendar.MONTH) && bornDate
							.get(Calendar.DAY_OF_MONTH) > currentDate
							.get(Calendar.DAY_OF_MONTH))) {
				age--;
			}
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Transient
	public boolean isContact() {
		if (id == null) {
			return false;
		} else {
			if (FacesUtilModel.isEmptyOrBlank(codeSap) || codeSap.equals(doc)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
