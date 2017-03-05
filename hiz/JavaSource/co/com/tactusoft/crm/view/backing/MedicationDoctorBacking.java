package co.com.tactusoft.crm.view.backing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import co.com.tactusoft.crm.controller.bo.TablesBo;
import co.com.tactusoft.crm.model.entities.CrmMaterial;
import co.com.tactusoft.crm.model.entities.VwMedicationDoctor;
import co.com.tactusoft.crm.util.FacesUtil;
import co.com.tactusoft.crm.view.datamodel.VwMedicationDoctorDataModel;

@Named
@Scope("view")
public class MedicationDoctorBacking extends BaseBacking implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TablesBo tableService;

	private List<VwMedicationDoctor> list;
	private VwMedicationDoctorDataModel model;
	private List<SelectItem> listMedication;
	private List<String> listMedicationSelected;

	public MedicationDoctorBacking() {
		newAction();
	}

	public List<VwMedicationDoctor> getList() {
		return list;
	}

	public void setList(List<VwMedicationDoctor> list) {
		this.list = list;
	}

	public VwMedicationDoctorDataModel getModel() {
		return model;
	}

	public void setModel(VwMedicationDoctorDataModel model) {
		this.model = model;
	}

	public List<SelectItem> getListMedication() {
		return listMedication;
	}

	public void setListMedication(List<SelectItem> listMedication) {
		this.listMedication = listMedication;
	}

	public List<String> getListMedicationSelected() {
		return listMedicationSelected;
	}

	public void setListMedicationSelected(List<String> listMedicationSelected) {
		this.listMedicationSelected = listMedicationSelected;
	}

	public void newAction() {
		listBranchSelected = null;
		startDate = new Date();
		endDate = new Date();

		try {
			List<CrmMaterial> listMaterial = FacesUtil.getCurrentUserData()
					.getListWSMaterials();
			listMedication = new LinkedList<>();
			for (CrmMaterial row : listMaterial) {
				listMedication
						.add(new SelectItem(row.getCode(), row.getName()));
			}
		} catch (Exception ex) {

		}
	}

	public void searchAction() {
		if (listBranchSelected != null && listBranchSelected.size() > 0) {
			String startDateString = FacesUtil.formatDate(startDate,
					"yyyy-MM-dd");
			String endDateString = FacesUtil.formatDate(endDate, "yyyy-MM-dd");
			List<Object[]> result = tableService.getListVwMedicationDoctor(
					listBranchSelected, listMedicationSelected,listDoctorSelected,
					startDateString, endDateString);
			list = new LinkedList<>();
			for (Object[] row : result) {
				VwMedicationDoctor object = new VwMedicationDoctor();
				object.setBranchId((BigDecimal) row[0]);
				object.setBranchCode((String) row[1]);
				object.setBranchName((String) row[2]);
				object.setDoctorCode((String) row[3]);
				object.setDoctorName((String) row[4]);
				object.setDescMaterial((String) row[5]);
				object.setCodMaterial((Long) row[6]);
				object.setUnit((Long) row[7]);
				list.add(object);
			}
			model = new VwMedicationDoctorDataModel(list);
		} else {
			String message = FacesUtil.getMessage("app_no_branch");
			FacesUtil.addInfo(message);
		}
	}

}
