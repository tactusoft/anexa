package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.VwAppointment;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class ReportInfusion2Backing extends BaseBacking implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<VwAppointment> list;
	private int invalidApp;

	public ReportInfusion2Backing() {
		newAction();
	}

	public List<VwAppointment> getList() {
		return list;
	}

	public void setList(List<VwAppointment> list) {
		this.list = list;
	}

	public int getInvalidApp() {
		return invalidApp;
	}

	public void setInvalidApp(int invalidApp) {
		this.invalidApp = invalidApp;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		// if (listStatus != null && listStatus.size() > 0) {
		list = processService.getListAppointmentNoAttendetByDates(startDate,
				endDate, invalidApp);
		// } else {
		// String message = FacesUtil.getMessage("app_no_status");
		// FacesUtil.addInfo(message);
		// }
	}

	public void exportElastix() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(",Nombres,Correo,Estado,Doctor,FechaHora,Procedimiento,Sucursal,Con Factura sin HC");
		stringBuilder.append(Constant.NEW_LINE);
		for (VwAppointment row : list) {
			if (!FacesUtil.isEmptyOrBlank(row.getPatCellNumber())
					|| !FacesUtil.isEmptyOrBlank(row.getPatPhoneNumber())) {
				stringBuilder.append((FacesUtil.isEmptyOrBlank(row
						.getPatCellNumber()) ? "09" + row.getCityInidicative()
						+ row.getPatPhoneNumber().trim() : row
						.getPatCellNumber().trim())
						+ ","
						+ row.getPatFirstnames()
						+ " "
						+ row.getPatSurnames()
						+ ","
						+ row.getPatEmail()
						+ ","
						+ (row.getState() == 1 ? "Agendada"
								: row.getState() == 2 ? "Cancelada" : row
										.getState() == 3 ? "Ch==ueada" : row
										.getState() == 4 ? "Asistió a Consulta"
										: "No Asistió a Consulta")
						+ ","
						+ row.getDoctorNames()
						+ ","
						+ row.getStartAppointmentDate()
						+ ","
						+ row.getPrcDetName()
						+ ","
						+ row.getBranchName()
						+ ","
						+ ((row.getInvalidStatus() != null && row
								.getInvalidStatus().booleanValue()) ? "SI"
								: "NO"));
				stringBuilder.append(Constant.NEW_LINE);
			}
		}
		String nameFile = "CONFACSINHC_"
				+ FacesUtil.formatDate(startDate, "yyyyMMdd") + "_"
				+ FacesUtil.formatDate(endDate, "yyyyMMdd");
		FacesUtil.exportStringBuilder(nameFile, stringBuilder);
		FacesUtil.addInfo("Archivo generados exitosamente");
	}
}
