package co.com.tactusoft.crm.util;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmProfile;
import co.com.tactusoft.crm.view.beans.InfusionEntity;

public class InfunsionSoft {

	final static String KEY = "ed9e16015fc0c0cc5f6f301f8c339dd8";
	final static int TAG_APPOINTMENT = 194;
	final static int TAG_NO_ATTENDET = 130;
	final static int TAG_START_MEDICATION = 128;
	final static int TAG_NO_START_MEDICATION = 148;
	final static int TAG_CONTROL_1 = 132;
	final static int TAG_CONTROL_2 = 138;
	final static int TAG_CONTROL_3 = 142;
	final static int TAG_NO_CONTROL_1 = 136;
	final static int TAG_NO_CONTROL_2 = 140;
	final static int TAG_NO_CONTROL_3 = 144;

	public final static String EVENT_TYPE_1 = "REGISTRO_FORMULARIO";
	public final static String EVENT_TYPE_2 = "CAMPANA_NO_ATENDIDO";
	public final static String EVENT_TYPE_3 = "CAMPANA_NO_COMENZO_MEDICACION";

	public static XmlRpcClient getClient() throws MalformedURLException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("https://ui306.infusionsoft.com/api/xmlrpc"));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}

	private static CrmPatient getPatient(Map<?, ?> contact) {
		System.out.println("InfusionSoft1: " + contact.get("Id"));
		String contactId = contact.get("Id").toString();
		CrmPatient result = new CrmPatient();
		result.setCrmProfile(new CrmProfile());
		result.setDoc(contact.get("_Cedula") != null ? contact.get("_Cedula")
				.toString() : "");
		result.setCodeSap(contactId);
		result.setFirstnames(contact.get("FirstName") != null ? contact.get(
				"FirstName").toString() : "");
		result.setSurnames(contact.get("LastName") != null ? contact.get(
				"LastName").toString() : "");
		result.setEmail(contact.get("Email") != null ? contact.get("Email")
				.toString() : "");
		result.setAddress(contact.get("StreetAddress1") != null ? contact.get(
				"StreetAddress1").toString() : "");
		result.setCellNumber(contact.get("Phone1") != null ? contact
				.get("Phone1").toString().replace("(", "").replace(")", "")
				.replace("-", "").replace(" ", "") : "");
		result.setPhoneNumber(contact.get("Phone2") != null ? contact
				.get("Phone2").toString().replace("(", "").replace(")", "")
				.replace("-", "").replace(" ", "") : "");
		result.setCycle(true);
		result.setIdCountry(new BigDecimal(1));
		return result;
	}

	public static CrmPatient getContactByDoc(String doc)
			throws MalformedURLException, XmlRpcException {
		CrmPatient result = null;
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>();
		fields.add("Id");
		fields.add("FirstName");
		fields.add("LastName");
		fields.add("Email");
		fields.add("StreetAddress1");
		fields.add("Phone1");
		fields.add("Phone2");
		fields.add("City");
		fields.add("State");
		fields.add("Country");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("Contact"); // What table we are looking in
		parameters.add(50); // How many records to return
		parameters.add(0); // Which page of results to display
		parameters.add("_Cedula"); // The field we are querying on
		parameters.add(!FacesUtil.isEmptyOrBlank(doc) ? doc : "");
		parameters.add(fields); // what fields to select on return

		Object[] contacts = (Object[]) client.execute(
				"DataService.findByField", parameters);
		for (Object contact1 : contacts) {
			Map<?, ?> contact = (Map<?, ?>) contact1;
			result = getPatient(contact);
			break;
		}
		return result;
	}

	public static CrmPatient getContactByPhone(String phone)
			throws MalformedURLException, XmlRpcException {
		String phoneNum = phone.replaceFirst("(\\d{3})(\\d{3})(\\d+)",
				"($1) $2-$3");
		CrmPatient result = null;
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>();
		fields.add("Id");
		fields.add("FirstName");
		fields.add("LastName");
		fields.add("Email");
		fields.add("StreetAddress1");
		fields.add("Phone1");
		fields.add("Phone2");
		fields.add("City");
		fields.add("State");
		fields.add("Country");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("Contact"); // What table we are looking in
		parameters.add(50); // How many records to return
		parameters.add(0); // Which page of results to display
		parameters.add("Phone1"); // The field we are querying on
		parameters.add(!FacesUtil.isEmptyOrBlank(phoneNum) ? phoneNum : "");
		parameters.add(fields); // what fields to select on return

		Object[] contacts = (Object[]) client.execute(
				"DataService.findByField", parameters);
		for (Object contact1 : contacts) {
			Map<?, ?> contact = (Map<?, ?>) contact1;
			result = getPatient(contact);
			break;
		}
		return result;
	}

	public static CrmPatient getContactByEmail(String email)
			throws MalformedURLException, XmlRpcException {
		CrmPatient result = null;
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>();
		fields.add("Id");
		fields.add("FirstName");
		fields.add("LastName");
		fields.add("_Cedula");
		fields.add("StreetAddress1");
		fields.add("Phone1");
		fields.add("Phone2");
		fields.add("City");
		fields.add("State");
		fields.add("Country");
		fields.add("Email");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("Contact"); // What table we are looking in
		parameters.add(50); // How many records to return
		parameters.add(0); // Which page of results to display
		parameters.add("Email"); // The field we are querying on
		parameters.add(!FacesUtil.isEmptyOrBlank(email) ? email : "");
		parameters.add(fields); // what fields to select on return

		Object[] contacts = (Object[]) client.execute(
				"DataService.findByField", parameters);
		for (Object contact1 : contacts) {
			Map<?, ?> contact = (Map<?, ?>) contact1;
			result = getPatient(contact);
			break;
		}
		return result;
	}

	public static String getContactId(String email)
			throws MalformedURLException, XmlRpcException {
		String contactId = null;
		if (!FacesUtil.isEmptyOrBlank(email)) {
			XmlRpcClient client = getClient();

			List<Object> fields = new ArrayList<>();
			fields.add("Id");

			List<Object> parameters = new ArrayList<>();
			parameters.add(KEY); // Secure key
			parameters.add("Contact"); // What table we are looking in
			parameters.add(50); // How many records to return
			parameters.add(0); // Which page of results to display
			parameters.add("email"); // The field we are querying on
			parameters.add(email); // THe data to query on
			parameters.add(fields); // what fields to select on return

			Object[] contacts = (Object[]) client.execute(
					"DataService.findByField", parameters);
			for (Object contact1 : contacts) {
				// Each item in the array is a struct
				Map<?, ?> contact = (Map<?, ?>) contact1;
				System.out.println("InfusionSoft1: " + contact.get("Id"));
				contactId = contact.get("Id").toString();
				break;
			}
		}
		return contactId;
	}

	public static String getDiseaseByEmail(String email)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>(); // What fields we will be
													// selecting
		fields.add("_Enfermedad0");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("Contact"); // What table we are looking in
		parameters.add(50); // How many records to return
		parameters.add(0); // Which page of results to display
		parameters.add("email"); // The field we are querying on
		parameters.add(email); // THe data to query on
		parameters.add(fields); // what fields to select on return

		String contactId = null;
		Object[] contacts = (Object[]) client.execute(
				"DataService.findByField", parameters);
		for (Object contact1 : contacts) {
			// Each item in the array is a struct
			Map<?, ?> contact = (Map<?, ?>) contact1;
			// System.out.println("InfusionSoft1: " +
			// contact.get("_Enfermedad0"));
			contactId = contact.get("_Enfermedad0") != null ? contact.get(
					"_Enfermedad0").toString() : "";
			break;
		}
		return contactId;
	}

	public static Boolean startMedication(String email) {
		try {
			String contactId = InfunsionSoft.getContactId(email);
			if (!FacesUtil.isEmptyOrBlank(contactId)) {
				return addTag(contactId, TAG_START_MEDICATION);
			} else {
				System.out.println("INFUSIONSOFT: No se encontro email: "
						+ email);
			}
		} catch (MalformedURLException | XmlRpcException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Boolean assignAppointment(String email, Date date,
			String branch, String address) throws MalformedURLException,
			XmlRpcException {
		XmlRpcClient client = getClient();
		String contactId = InfunsionSoft.getContactId(email);
		Boolean success = null;
		if (!FacesUtil.isEmptyOrBlank(contactId)) {
			List<Object> parameters = new ArrayList<>();
			Map<String, String> contactData = new HashMap<String, String>();
			contactData.put("_Fechadecita",
					FacesUtil.formatDate(date, "yyyMMdd"));
			contactData.put("_Horadecita", FacesUtil.formatDate(date, "HH:mm"));
			contactData.put("_Clinicas", branch);
			contactData.put("_Direcciondecita",
					FacesUtil.isEmptyOrBlank(address) ? branch : address);

			parameters = new ArrayList<>();
			parameters.add(KEY); // The secure key
			parameters.add(contactId); // The secure key
			parameters.add(contactData); // The data to be added

			// Make the call
			Integer result = (Integer) client.execute("ContactService.update",
					parameters);
			System.out.println("Contact added was " + result);

			removeTag(contactId, TAG_NO_ATTENDET);
			removeTag(contactId, TAG_APPOINTMENT);
			addTag(contactId, TAG_APPOINTMENT);
		} else {
			System.out.println("INFUSIONSOFT: No se encontro email: " + email);
		}
		return success;
	}

	public static boolean assignNoAttended(String email)
			throws MalformedURLException, XmlRpcException {
		String contactId = InfunsionSoft.getContactId(email);
		Boolean success = false;
		if (!FacesUtil.isEmptyOrBlank(contactId)) {
			removeTag(contactId, TAG_APPOINTMENT);
			removeTag(contactId, TAG_NO_ATTENDET);
			addTag(contactId, TAG_NO_ATTENDET);
			success = true;
		}
		return success;
	}

	private static boolean removeTag(String contactId, int tag)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();
		List<Object> parameters = new ArrayList<>();
		parameters = new ArrayList<>();
		parameters.add(KEY); // The secure key
		parameters.add(contactId); // The secure key
		parameters.add(tag); // The data to be added

		// Make the call
		Boolean success = (Boolean) client.execute(
				"ContactService.removeFromGroup", parameters);
		System.out.println("INFUSIONSOFT: Se removió TAG " + tag + " - "
				+ success);

		return success;
	}

	private static boolean addTag(String contactId, int tag)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();
		List<Object> parameters = new ArrayList<>();
		parameters = new ArrayList<>();
		parameters.add(KEY); // The secure key
		parameters.add(contactId); // The secure key
		parameters.add(tag); // The data to be added

		// Make the call
		Boolean success = (Boolean) client.execute("ContactService.addToGroup",
				parameters);
		System.out.println("INFUSIONSOFT: Se agregó TAG " + tag + " - "
				+ success);

		return success;
	}

	public static boolean assignNoStartMedication(String email)
			throws MalformedURLException, XmlRpcException {
		String contactId = InfunsionSoft.getContactId(email);
		Boolean success = false;
		if (!FacesUtil.isEmptyOrBlank(contactId)) {
			removeTag(contactId, TAG_START_MEDICATION);
			removeTag(contactId, TAG_NO_START_MEDICATION);
			addTag(contactId, TAG_NO_START_MEDICATION);
			success = true;
		}
		return success;
	}

	private static List<InfusionEntity> getContactsByGroupPage(
			List<Integer> groupIds, Date dateCreated, int page)
			throws MalformedURLException, XmlRpcException {
		List<InfusionEntity> results = new LinkedList<>();
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>(); // What fields we will be
													// selecting
		fields.add("Contact.Email");
		fields.add("Contact.Phone1");
		fields.add("Contact.FirstName");
		fields.add("Contact.LastName");
		fields.add("DateCreated");

		Map<String, Object> queryData = new HashMap<String, Object>();
		queryData.put("GroupId", groupIds);
		queryData.put("DateCreated",
				FacesUtil.formatDate(dateCreated, "yyyy-MM-dd") + "%");
		// queryData.put("DateCreated", "~>=~20170201");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("ContactGroupAssign"); // What table we are looking in
		parameters.add(1000); // How many records to return
		parameters.add(page); // Which page of results to display
		parameters.add(queryData); // THe data to query on
		parameters.add(fields); // what fields to select on return

		Object[] contacts = (Object[]) client.execute("DataService.query",
				parameters);
		for (Object contact1 : contacts) {
			// Each item in the array is a struct
			Map<?, ?> contact = (Map<?, ?>) contact1;
			Object email = contact.get("Contact.Email");
			if (email != null) {
				Date date = FacesUtil.stringTOSDateEN(contact
						.get("DateCreated").toString(),
						"EEEE MMM dd HH:mm:ss ZZZ yyyy");

				String names = "";
				if (contact.get("Contact.FirstName") != null) {
					names = contact.get("Contact.FirstName").toString();
				}
				if (contact.get("Contact.LastName") != null) {
					names += contact.get("Contact.LastName").toString();
				}

				String phoneNum = null;
				if (contact.get("Contact.Phone1") != null) {
					phoneNum = contact.get("Contact.Phone1").toString()
							.replace("(", "").replace(")", "").replace("-", "")
							.replace(" ", "");
				}

				InfusionEntity infusionEntity = new InfusionEntity();
				infusionEntity.setEmail(email.toString());
				infusionEntity.setPhone(phoneNum);
				infusionEntity.setNames(names);
				infusionEntity.setEventDate(date);
				String disease = getDiseaseByEmail(email.toString());
				infusionEntity.setDisease(disease);
				results.add(infusionEntity);
			}
		}
		return results;
	}

	public static List<InfusionEntity> getContactsByGroup(
			List<Integer> groupIds, Date dateCreated)
			throws MalformedURLException, XmlRpcException {
		List<InfusionEntity> results = new LinkedList<>();
		int page = 0;
		while (true) {
			List<InfusionEntity> res = getContactsByGroupPage(groupIds,
					dateCreated, page);
			results.addAll(res);
			page++;
			if (res.size() < 1000) {
				break;
			}
		}
		return results;
	}

	public static String getContactIdByPhone(String phoneNum)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();

		List<Object> fields = new ArrayList<>(); // What fields we will be
													// selecting
		fields.add("Id");

		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // Secure key
		parameters.add("Contact"); // What table we are looking in
		parameters.add(50); // How many records to return
		parameters.add(0); // Which page of results to display
		parameters.add("Phone1"); // The field we are querying on
		parameters.add(!FacesUtil.isEmptyOrBlank(phoneNum) ? phoneNum : "");
		parameters.add(fields); // what fields to select on return

		String contactId = null;
		Object[] contacts = (Object[]) client.execute(
				"DataService.findByField", parameters);
		for (Object contact1 : contacts) {
			// Each item in the array is a struct
			Map<?, ?> contact = (Map<?, ?>) contact1;
			System.out.println("InfusionSoft1: " + contact.get("Id"));
			contactId = contact.get("Id").toString();
			break;
		}
		return contactId;
	}

	public static String createContact(String operation, String contactId,
			String doc, String names, String lastNames, String email,
			String address, String mobilePhone, String homePhone,
			String country, String state, String city)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();
		List<Object> parameters = new ArrayList<>();

		if (!FacesUtil.isEmptyOrBlank(email)) {
			String ci = getContactId(email);
			if (!FacesUtil.isEmptyOrBlank(contactId)) {
				operation = Constant.CONTACT_UPDATE;
				contactId = ci;
			} else if (!FacesUtil.isEmptyOrBlank(mobilePhone)) {
				ci = getContactIdByPhone(mobilePhone);
				if (!FacesUtil.isEmptyOrBlank(contactId)) {
					operation = Constant.CONTACT_UPDATE;
					contactId = ci;
				}
			}
		}

		Map<String, String> contactData = new HashMap<>();
		contactData.put("_Cedula", FacesUtil.isEmptyOrBlank(doc) ? "" : doc);
		contactData.put("FirstName", names);
		contactData.put("LastName", lastNames);
		contactData.put("Email", email);
		contactData.put("StreetAddress1",
				FacesUtil.isEmptyOrBlank(address) ? "" : address);
		contactData.put("Phone1Type", "Mobile");
		contactData.put("Phone1", FacesUtil.isEmptyOrBlank(mobilePhone) ? ""
				: mobilePhone);
		contactData.put("Phone2Type", "Home");
		contactData.put("Phone2", FacesUtil.isEmptyOrBlank(homePhone) ? ""
				: homePhone);
		contactData.put("City", FacesUtil.isEmptyOrBlank(city) ? "" : city);
		contactData.put("State", FacesUtil.isEmptyOrBlank(state) ? "" : state);
		contactData.put("Country", FacesUtil.isEmptyOrBlank(country) ? ""
				: country);
		contactData.put("ContactType", "Prospect");

		parameters.add(KEY); // The secure key
		String operationType = "ContactService.addWithDupCheck";
		if (operation.equals(Constant.CONTACT_UPDATE)) {
			operationType = "ContactService.addWithDupCheck";
			parameters.add(contactId); // The secure key
		}
		parameters.add(contactData); // The data to be added
		parameters.add("Email");

		// Make the call
		String result = client.execute(operationType, parameters).toString();

		parameters = new ArrayList<>();
		parameters.add(KEY);
		parameters.add(email);
		parameters.add("Contactos desde el sistema de citas");
		Boolean success = (Boolean) client.execute("APIEmailService.optIn",
				parameters);
		if (success) {

		}
		return result;
	}

	public static void main(String[] args) {
		try {
			// InfunsionSoft.assignAppointment("john@doe.com", new Date(),
			// "Clinica", "Carrera 20");

			// InfunsionSoft.getContactId("Anaagudelo1980@gmail.com");

			/*
			 * Date currentDate = FacesUtil.stringTOSDate("13/02/2017 21",
			 * "dd/MM/yyyy HH");
			 * 
			 * List<Integer> ids = new ArrayList<Integer>(); ids.add(1374);
			 * ids.add(1380);
			 * 
			 * List<InfusionEntity> list374 = InfunsionSoft.getContactsByGroup(
			 * ids, currentDate); for (InfusionEntity row : list374) {
			 * CrmInfunsion1 crmInfunsion1 = new CrmInfunsion1();
			 * crmInfunsion1.setEventType(EVENT_TYPE_1);
			 * crmInfunsion1.setEmail(row.getEmail());
			 * crmInfunsion1.setEventDate(row.getEventDate());
			 * crmInfunsion1.setStatus(0);
			 * crmInfunsion1.setDisease(row.getDisease()); }
			 */

			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); Date
			 * currentDate = FacesUtil.stringTOSDate("01/02/2017",
			 * "dd/MM/yyyy"); Date endDate = new Date();
			 * 
			 * GregorianCalendar gcal = new GregorianCalendar();
			 * gcal.setTime(currentDate); while (!gcal.getTime().after(endDate))
			 * { Date date = gcal.getTime(); List<Integer> ids = new
			 * ArrayList<Integer>(); ids.add(1372); List<InfusionEntity>
			 * formsList = InfunsionSoft.getContactsByGroup( ids, date);
			 * System.out.println(date + " - " + formsList.size());
			 * gcal.add(Calendar.DAY_OF_WEEK, 1); }
			 */

			List<Integer> ids = new ArrayList<Integer>();
			ids.add(1374);
			ids.add(1380);

			Date date = FacesUtil.stringTOSDate("15/03/2017", "dd/MM/yyyy");

			List<InfusionEntity> formsList = InfunsionSoft.getContactsByGroup(
					ids, date);
			for (InfusionEntity row : formsList) {
				System.out.println(row.getNames() + ";" + row.getEmail() + ";"
						+ row.getEventDate() + ";" + row.getDisease());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

}
