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
 * CrmCampaignMedication generated by hbm2java
 */
@Entity
@Table(name = "crm_campaign_medication", catalog = "crm_db")
public class CrmCampaignMedication implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private CrmCampaign crmCampaign;
	private String codMaterial;
	private String descMaterial;
	private Integer sold;
	private int unit;
	private Integer unitSale;
	private String posology;

	public CrmCampaignMedication() {
	}

	public CrmCampaignMedication(CrmCampaign crmCampaign, String codMaterial,
			String descMaterial, int unit) {
		this.crmCampaign = crmCampaign;
		this.codMaterial = codMaterial;
		this.descMaterial = descMaterial;
		this.unit = unit;
	}

	public CrmCampaignMedication(CrmCampaign crmCampaign, String codMaterial,
			String descMaterial, Integer sold, int unit, Integer unitSale,
			String posology) {
		this.crmCampaign = crmCampaign;
		this.codMaterial = codMaterial;
		this.descMaterial = descMaterial;
		this.sold = sold;
		this.unit = unit;
		this.unitSale = unitSale;
		this.posology = posology;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_campaign", nullable = false)
	public CrmCampaign getCrmCampaign() {
		return this.crmCampaign;
	}

	public void setCrmCampaign(CrmCampaign crmCampaign) {
		this.crmCampaign = crmCampaign;
	}

	@Column(name = "cod_material", nullable = false, length = 18)
	public String getCodMaterial() {
		return this.codMaterial;
	}

	public void setCodMaterial(String codMaterial) {
		this.codMaterial = codMaterial;
	}

	@Column(name = "desc_material", nullable = false, length = 1000)
	public String getDescMaterial() {
		return this.descMaterial;
	}

	public void setDescMaterial(String descMaterial) {
		this.descMaterial = descMaterial;
	}

	@Column(name = "sold")
	public Integer getSold() {
		return this.sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

	@Column(name = "unit", nullable = false)
	public int getUnit() {
		return this.unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	@Column(name = "unit_sale")
	public Integer getUnitSale() {
		return this.unitSale;
	}

	public void setUnitSale(Integer unitSale) {
		this.unitSale = unitSale;
	}

	@Column(name = "posology", length = 65535)
	public String getPosology() {
		return this.posology;
	}

	public void setPosology(String posology) {
		this.posology = posology;
	}

}
