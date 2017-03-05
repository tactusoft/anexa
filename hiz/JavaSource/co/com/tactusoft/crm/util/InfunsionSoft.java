package co.com.tactusoft.crm.util;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import co.com.tactusoft.crm.model.entities.CrmPatient;
import co.com.tactusoft.crm.model.entities.CrmProfile;

public class InfunsionSoft {

	final static String KEY = "ed9e16015fc0c0cc5f6f301f8c339dd8";
	final static int TAG_APPOINTMENT = 194;
	final static int TAG_START_MEDICATION = 128;
	final static int TAG_NO_ATTENDET = 130;
	final static int CAMPAIGN_APPOINTMENT = 28;

	public static XmlRpcClient getClient() throws MalformedURLException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("https://ui306.infusionsoft.com/api/xmlrpc"));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		return client;
	}

	public static String getContactId(String email)
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
		parameters.add("email"); // The field we are querying on
		parameters.add(!FacesUtil.isEmptyOrBlank(email) ? email : "");
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

			addTag(contactId, TAG_APPOINTMENT);
		} else {
			System.out.println("INFUSIONSOFT: No se encontro email: " + email);
		}
		return success;
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

	public static Boolean assignNoAttended(String email)
			throws MalformedURLException, XmlRpcException {
		String contactId = InfunsionSoft.getContactId(email);
		Boolean success = null;
		if (!FacesUtil.isEmptyOrBlank(contactId)) {
			addTag(contactId, TAG_NO_ATTENDET);
		} else {
			System.out.println("INFUSIONSOFT: No se encontro email: " + email);
		}
		return success;
	}

	public static boolean removeTag(String contactId, int tag)
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

	public static boolean addCampaign(String contactId, int campaign)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();
		List<Object> parameters = new ArrayList<>();
		parameters.add(KEY); // The secure key
		parameters.add(contactId); // The secure key
		parameters.add(campaign); // The data to be added

		// Make the call
		Boolean success = (Boolean) client.execute(
				"ContactService.addToCampaign", parameters);
		System.out.println("INFUSIONSOFT: Se agregó Secuencia " + campaign
				+ " - " + success);

		return success;
	}

	public static boolean removeCampaign(String contactId, int campaign)
			throws MalformedURLException, XmlRpcException {
		XmlRpcClient client = getClient();
		List<Object> parameters = new ArrayList<>();
		parameters = new ArrayList<>();
		parameters.add(KEY); // The secure key
		parameters.add(contactId); // The secure key
		parameters.add(campaign); // The data to be added

		// Make the call
		Boolean success = (Boolean) client.execute(
				"ContactService.removeFromCampaign", parameters);
		System.out.println("INFUSIONSOFT: Se removió Secuencia " + campaign
				+ " - " + success);

		return success;
	}

	public static String createContact(String operation, String contactId,
			String doc, String names, String lastNames, String email,
			String address, String mobilePhone, String homePhone, String city,
			String state, String country) throws MalformedURLException,
			XmlRpcException {
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
		String operationType = "ContactService.add";
		if (operation.equals(Constant.CONTACT_UPDATE)) {
			operationType = "ContactService.update";
			parameters.add(contactId); // The secure key
		}
		parameters.add(contactData); // The data to be added

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
			String phoneNum = "3104807922";

			// CrmPatient crmPatient =
			// InfunsionSoft.getContactByPhone(phoneNum);
			// System.out.println(crmPatient.getCodeSap());

			String id = createContact(Constant.CONTACT_CREATE, "22088",
					"8647362", "Carlos Arturo", "Sarmiento Royero",
					"tactusoft@hotmail.com", null, "3003044115", "4769778",
					"Bogota", "Cundinamarca", "Colombia");

			System.out.println(id);

			/*
			 * InfunsionSoft.assignAppointment("tactusoft@hotmail.com", new
			 * Date(), "Bogota - Country", "Bogota - Country");
			 */

			// InfunsionSoft.addTag(id, TAG_START_MEDICATION);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
	}

}
