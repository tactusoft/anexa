package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.model.entities.VwAppointmentRep5;
import co.com.tactusoft.crm.util.Constant;
import co.com.tactusoft.crm.util.FacesUtil;

@Named
@Scope("view")
public class RepAppointmentRep5Backing extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<VwAppointmentRep5> list;

	public RepAppointmentRep5Backing() {
		newAction();
	}

	public List<VwAppointmentRep5> getList() {
		return list;
	}

	public void setList(List<VwAppointmentRep5> list) {
		this.list = list;
	}

	public void newAction() {
		startDate = new Date();
		endDate = new Date();
	}

	public void searchAction() {
		String startDateString = FacesUtil.formatDate(startDate, "yyyy-MM-dd");
		String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
		list = processService.getVwAppointmentRep5ByDates(startDateString,
				endDateString);
	}

	public void exportElastix() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(",Nombres,Correo,FechaHora,Procedimiento,Sucursal,Pacientes Con 2 citas seguidas Sin asistir");
		stringBuilder.append(Constant.NEW_LINE);
		for (VwAppointmentRep5 row : list) {
			if (!FacesUtil.isEmptyOrBlank(row.getPatientCellNumber())
					|| !FacesUtil.isEmptyOrBlank(row.getPatientPhoneNumber())) {
				stringBuilder.append((FacesUtil.isEmptyOrBlank(row
						.getPatientCellNumber()) ? "09"
						+ row.getCityInidicative()
						+ row.getPatientPhoneNumber().trim() : row
						.getPatientCellNumber().trim())
						+ ","
						+ row.getPatientNames()
						+ ","
						+ row.getPatientEmail()
						+ ","
						+ row.getAppDate1()
						+ ","
						+ row.getAppProcedure1() + "," + row.getAppBranch1());
				stringBuilder.append(Constant.NEW_LINE);
			}
		}
		FacesUtil.exportStringBuilder(stringBuilder);
		FacesUtil.addInfo("Archivo generados exitosamente");
	}

}
