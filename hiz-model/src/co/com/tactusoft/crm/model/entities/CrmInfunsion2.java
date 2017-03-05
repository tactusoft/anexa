package co.com.tactusoft.crm.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;

@Entity
@Table(name = "crm_infunsion_2", catalog = "crm_db")
public class CrmInfunsion2 implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmInfusionTag crmInfusionTag;
	private Date eventDate;
	private Integer countForms;

	public CrmInfunsion2() {

	}

	public CrmInfunsion2(Integer id, CrmInfusionTag crmInfusionTag,
			Date eventDate, Integer countForms) {
		super();
		this.id = id;
		this.crmInfusionTag = crmInfusionTag;
		this.eventDate = eventDate;
		this.countForms = countForms;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tag", nullable = true)
	public CrmInfusionTag getCrmInfusionTag() {
		return crmInfusionTag;
	}

	public void setCrmInfusionTag(CrmInfusionTag crmInfusionTag) {
		this.crmInfusionTag = crmInfusionTag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "event_date", nullable = false, length = 10)
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "count_forms", nullable = false)
	public Integer getCountForms() {
		return countForms;
	}

	public void setCountForms(Integer countForms) {
		this.countForms = countForms;
	}

}
