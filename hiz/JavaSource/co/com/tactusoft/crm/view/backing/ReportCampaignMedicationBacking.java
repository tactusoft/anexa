package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmCampaignDetail;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class ReportCampaignMedicationBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<CrmCampaignDetail> list;

	public ReportCampaignMedicationBacking() {
		newAction();
	}

	public List<CrmCampaignDetail> getList() {
		return list;
	}

	public void setList(List<CrmCampaignDetail> list) {
		this.list = list;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		list = processService.getListCampaignByDates(4, startDate, endDate);
	}

	public void exportElastix() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(",Nombres,Correo,Doctor,FechaHora,Procedimiento,Sucursal");
		stringBuilder.append(Constant.NEW_LINE);
		for (CrmCampaignDetail row : list) {
			if (!FacesUtil.isEmptyOrBlank(row.getCrmCampaign().getCrmPatient()
					.getCellNumber())
					|| !FacesUtil.isEmptyOrBlank(row.getCrmCampaign()
							.getCrmPatient().getPhoneNumber())) {
				stringBuilder
						.append((FacesUtil.isEmptyOrBlank(row.getCrmCampaign()
								.getCrmPatient().getCellNumber()) ? "09"
								+ row.getCrmCampaign().getCrmPatient()
										.getCrmCity().getIndicative()
								+ row.getCrmCampaign().getCrmPatient()
										.getPhoneNumber().trim() : row
								.getCrmCampaign().getCrmPatient()
								.getCellNumber().trim())
								+ ","
								+ row.getCrmCampaign().getCrmPatient()
										.getFirstnames()
								+ " "
								+ row.getCrmCampaign().getCrmPatient()
										.getSurnames()
								+ ","
								+ row.getCrmCampaign().getCrmPatient()
										.getEmail()
								+ ","
								+ row.getCrmAppointment().getCrmDoctor()
										.getNames()
								+ ","
								+ row.getCrmAppointment()
										.getStartAppointmentDate()
								+ ","
								+ row.getCrmAppointment()
										.getCrmProcedureDetail().getName()
								+ ","
								+ row.getCrmAppointment().getCrmBranch()
										.getName());
				stringBuilder.append(Constant.NEW_LINE);
			}
		}
		String nameFile = "POSTMED_"
				+ FacesUtil.formatDate(startDate, "yyyyMMdd") + "_"
				+ FacesUtil.formatDate(endDate, "yyyyMMdd");
		FacesUtil.exportStringBuilder(nameFile, stringBuilder);
		FacesUtil.addInfo("Archivo generados exitosamente");
	}
}
