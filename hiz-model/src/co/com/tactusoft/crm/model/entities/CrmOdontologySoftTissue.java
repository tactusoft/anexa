package co.com.tactusoft.crm.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrmOdontologySoftTissue generated by hbm2java
 */
@Entity
@Table(name = "crm_odontology_soft_tissue", catalog = "crm_db")
public class CrmOdontologySoftTissue implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmAppointment crmAppointment;
	private Integer glandNormal;
	private Integer glandAltered;
	private Integer lymphNormal;
	private Integer lymphAltered;
	private Integer oralMucosaNormal;
	private Integer oralMucosaAltered;
	private Integer bracesNormal;
	private Integer bracesAltered;
	private Integer tongueNormal;
	private Integer tongueAltered;
	private Integer palateNormal;
	private Integer palateAltered;
	private Integer mounthFloorNormal;
	private Integer mounthFloorAltered;
	private Integer oropharynxNormal;
	private Integer oropharynxAltered;
	private Integer tonsilsNormal;
	private Integer tonsilsAltered;
	private Integer cheeksNormal;
	private Integer cheeksAltered;
	private String obs;

	public CrmOdontologySoftTissue() {
	}

	public CrmOdontologySoftTissue(CrmAppointment crmAppointment) {
		this.crmAppointment = crmAppointment;
	}

	public CrmOdontologySoftTissue(CrmAppointment crmAppointment,
			Integer glandNormal, Integer glandAltered, Integer lymphNormal,
			Integer lymphAltered, Integer oralMucosaNormal,
			Integer oralMucosaAltered, Integer bracesNormal,
			Integer bracesAltered, Integer tongueNormal, Integer tongueAltered,
			Integer palateNormal, Integer palateAltered,
			Integer mounthFloorNormal, Integer mounthFloorAltered,
			Integer oropharynxNormal, Integer oropharynxAltered,
			Integer tonsilsNormal, Integer tonsilsAltered,
			Integer cheeksNormal, Integer cheeksAltered, String obs) {
		this.crmAppointment = crmAppointment;
		this.glandNormal = glandNormal;
		this.glandAltered = glandAltered;
		this.lymphNormal = lymphNormal;
		this.lymphAltered = lymphAltered;
		this.oralMucosaNormal = oralMucosaNormal;
		this.oralMucosaAltered = oralMucosaAltered;
		this.bracesNormal = bracesNormal;
		this.bracesAltered = bracesAltered;
		this.tongueNormal = tongueNormal;
		this.tongueAltered = tongueAltered;
		this.palateNormal = palateNormal;
		this.palateAltered = palateAltered;
		this.mounthFloorNormal = mounthFloorNormal;
		this.mounthFloorAltered = mounthFloorAltered;
		this.oropharynxNormal = oropharynxNormal;
		this.oropharynxAltered = oropharynxAltered;
		this.tonsilsNormal = tonsilsNormal;
		this.tonsilsAltered = tonsilsAltered;
		this.cheeksNormal = cheeksNormal;
		this.cheeksAltered = cheeksAltered;
		this.obs = obs;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_appointment", nullable = false)
	public CrmAppointment getCrmAppointment() {
		return this.crmAppointment;
	}

	public void setCrmAppointment(CrmAppointment crmAppointment) {
		this.crmAppointment = crmAppointment;
	}

	@Column(name = "gland_normal")
	public Integer getGlandNormal() {
		return this.glandNormal;
	}

	public void setGlandNormal(Integer glandNormal) {
		this.glandNormal = glandNormal;
	}

	@Column(name = "gland_altered")
	public Integer getGlandAltered() {
		return this.glandAltered;
	}

	public void setGlandAltered(Integer glandAltered) {
		this.glandAltered = glandAltered;
	}

	@Column(name = "lymph_normal")
	public Integer getLymphNormal() {
		return this.lymphNormal;
	}

	public void setLymphNormal(Integer lymphNormal) {
		this.lymphNormal = lymphNormal;
	}

	@Column(name = "lymph_altered")
	public Integer getLymphAltered() {
		return this.lymphAltered;
	}

	public void setLymphAltered(Integer lymphAltered) {
		this.lymphAltered = lymphAltered;
	}

	@Column(name = "oral_mucosa_normal")
	public Integer getOralMucosaNormal() {
		return this.oralMucosaNormal;
	}

	public void setOralMucosaNormal(Integer oralMucosaNormal) {
		this.oralMucosaNormal = oralMucosaNormal;
	}

	@Column(name = "oral_mucosa_altered")
	public Integer getOralMucosaAltered() {
		return this.oralMucosaAltered;
	}

	public void setOralMucosaAltered(Integer oralMucosaAltered) {
		this.oralMucosaAltered = oralMucosaAltered;
	}

	@Column(name = "braces_normal")
	public Integer getBracesNormal() {
		return this.bracesNormal;
	}

	public void setBracesNormal(Integer bracesNormal) {
		this.bracesNormal = bracesNormal;
	}

	@Column(name = "braces_altered")
	public Integer getBracesAltered() {
		return this.bracesAltered;
	}

	public void setBracesAltered(Integer bracesAltered) {
		this.bracesAltered = bracesAltered;
	}

	@Column(name = "tongue_normal")
	public Integer getTongueNormal() {
		return this.tongueNormal;
	}

	public void setTongueNormal(Integer tongueNormal) {
		this.tongueNormal = tongueNormal;
	}

	@Column(name = "tongue_altered")
	public Integer getTongueAltered() {
		return this.tongueAltered;
	}

	public void setTongueAltered(Integer tongueAltered) {
		this.tongueAltered = tongueAltered;
	}

	@Column(name = "palate_normal")
	public Integer getPalateNormal() {
		return this.palateNormal;
	}

	public void setPalateNormal(Integer palateNormal) {
		this.palateNormal = palateNormal;
	}

	@Column(name = "palate_altered")
	public Integer getPalateAltered() {
		return this.palateAltered;
	}

	public void setPalateAltered(Integer palateAltered) {
		this.palateAltered = palateAltered;
	}

	@Column(name = "mounth_floor_normal")
	public Integer getMounthFloorNormal() {
		return this.mounthFloorNormal;
	}

	public void setMounthFloorNormal(Integer mounthFloorNormal) {
		this.mounthFloorNormal = mounthFloorNormal;
	}

	@Column(name = "mounth_floor_altered")
	public Integer getMounthFloorAltered() {
		return this.mounthFloorAltered;
	}

	public void setMounthFloorAltered(Integer mounthFloorAltered) {
		this.mounthFloorAltered = mounthFloorAltered;
	}

	@Column(name = "oropharynx_normal")
	public Integer getOropharynxNormal() {
		return this.oropharynxNormal;
	}

	public void setOropharynxNormal(Integer oropharynxNormal) {
		this.oropharynxNormal = oropharynxNormal;
	}

	@Column(name = "oropharynx_altered")
	public Integer getOropharynxAltered() {
		return this.oropharynxAltered;
	}

	public void setOropharynxAltered(Integer oropharynxAltered) {
		this.oropharynxAltered = oropharynxAltered;
	}

	@Column(name = "tonsils_normal")
	public Integer getTonsilsNormal() {
		return this.tonsilsNormal;
	}

	public void setTonsilsNormal(Integer tonsilsNormal) {
		this.tonsilsNormal = tonsilsNormal;
	}

	@Column(name = "tonsils_altered")
	public Integer getTonsilsAltered() {
		return this.tonsilsAltered;
	}

	public void setTonsilsAltered(Integer tonsilsAltered) {
		this.tonsilsAltered = tonsilsAltered;
	}

	@Column(name = "cheeks_normal")
	public Integer getCheeksNormal() {
		return this.cheeksNormal;
	}

	public void setCheeksNormal(Integer cheeksNormal) {
		this.cheeksNormal = cheeksNormal;
	}

	@Column(name = "cheeks_altered")
	public Integer getCheeksAltered() {
		return this.cheeksAltered;
	}

	public void setCheeksAltered(Integer cheeksAltered) {
		this.cheeksAltered = cheeksAltered;
	}

	@Column(name = "obs", length = 65535)
	public String getObs() {
		return this.obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

}
