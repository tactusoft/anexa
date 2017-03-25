package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.CrmInfunsion1;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class ReportInfusion1Backing extends BaseBacking implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CrmInfunsion1> list;

	public ReportInfusion1Backing() {
		newAction();
	}

	public List<CrmInfunsion1> getList() {
		return list;
	}

	public void setList(List<CrmInfunsion1> list) {
		this.list = list;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		if (listStatus != null && listStatus.size() > 0) {
			String startDateString = FacesUtil.formatDate(startDate,
					"yyyy-MM-dd");
			String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
			list = processService.getInfunsionbyStatus(startDateString,
					endDateString, listStatus);
		} else {
			String message = FacesUtil.getMessage("app_no_status");
			FacesUtil.addInfo(message);
		}
	}

	public void exportElastix() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(",Nombres,Correo,Estado,Enfermedad");
		stringBuilder.append(Constant.NEW_LINE);
		for (CrmInfunsion1 row : list) {
			if (!FacesUtil.isEmptyOrBlank(row.getPhone())) {
				stringBuilder.append(row.getPhone()
						+ ","
						+ row.getNames()
						+ ","
						+ row.getEmail()
						+ ","
						+ (row.getStatus() == 0 ? "Sin Procesar" : row
								.getStatus() == 1 ? "Con cita" : "Sin Cita")
						+ "," + row.getDisease());
				stringBuilder.append(Constant.NEW_LINE);
			}
		}

		String nameFile = "REGSINCITA_"
				+ FacesUtil.formatDate(startDate, "yyyyMMdd") + "_"
				+ FacesUtil.formatDate(endDate, "yyyyMMdd");
		FacesUtil.exportStringBuilder(nameFile, stringBuilder);
		FacesUtil.addInfo("Archivo generados exitosamente");
	}
}
