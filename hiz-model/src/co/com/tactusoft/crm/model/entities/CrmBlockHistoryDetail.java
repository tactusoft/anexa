package co.com.tactusoft.crm.model.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrmBlockHistoryDetail generated by hbm2java
 */
@Entity
@Table(name = "crm_block_history_detail", catalog = "crm_db")
public class CrmBlockHistoryDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmCampaignType crmCampaignType;
	private CrmBlockHistory crmBlockHistory;

	public CrmBlockHistoryDetail() {
	}

	public CrmBlockHistoryDetail(CrmCampaignType crmCampaignType,
			CrmBlockHistory crmBlockHistory) {
		this.crmCampaignType = crmCampaignType;
		this.crmBlockHistory = crmBlockHistory;
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
	@JoinColumn(name = "id_campaign_type", nullable = false)
	public CrmCampaignType getCrmCampaignType() {
		return this.crmCampaignType;
	}

	public void setCrmCampaignType(CrmCampaignType crmCampaignType) {
		this.crmCampaignType = crmCampaignType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_block_history", nullable = false)
	public CrmBlockHistory getCrmBlockHistory() {
		return this.crmBlockHistory;
	}

	public void setCrmBlockHistory(CrmBlockHistory crmBlockHistory) {
		this.crmBlockHistory = crmBlockHistory;
	}

	@Override
	public String toString() {
		return this.crmCampaignType.getDescription();
	}

}
