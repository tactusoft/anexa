package co.com.tactusoft.crm.model.entities.sap;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import co.com.tactusoft.crm.model.entities.CrmSapMedicationId;

@Embeddable
public class SapMedicationId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String idBill;
	private String posBill;

	public SapMedicationId() {
	}

	public SapMedicationId(String idBill, String posBill) {
		this.idBill = idBill;
		this.posBill = posBill;
	}

	@Column(name = "id_bill", nullable = false, length = 30)
	public String getIdBill() {
		return this.idBill;
	}

	public void setIdBill(String idBill) {
		this.idBill = idBill;
	}

	@Column(name = "pos_bill", nullable = false, length = 10)
	public String getPosBill() {
		return this.posBill;
	}

	public void setPosBill(String posBill) {
		this.posBill = posBill;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CrmSapMedicationId))
			return false;
		CrmSapMedicationId castOther = (CrmSapMedicationId) other;

		return ((this.getIdBill() == castOther.getIdBill()) || (this
				.getIdBill() != null && castOther.getIdBill() != null && this
				.getIdBill().equals(castOther.getIdBill())))
				&& ((this.getPosBill() == castOther.getPosBill()) || (this
						.getPosBill() != null && castOther.getPosBill() != null && this
						.getPosBill().equals(castOther.getPosBill())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdBill() == null ? 0 : this.getIdBill().hashCode());
		result = 37 * result
				+ (getPosBill() == null ? 0 : this.getPosBill().hashCode());
		return result;
	}

}
