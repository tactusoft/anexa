package com.tactusoft.webservice.client.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.tactusoft.webservice.client.beans.WSBean;
import com.tactusoft.webservice.client.beans.WSBeanComparator;
import com.tactusoft.webservice.client.customlists.ZWEBLISTStub;
import com.tactusoft.webservice.client.customlists.ZWeblists;
import com.tactusoft.webservice.client.customlists.ZWeblistsResponse;
import com.tactusoft.webservice.client.customlists.Zweblist;
import com.tactusoft.webservice.client.customlists.Zweblistline;

public class CustomListsExecute {

	public static Zweblistline[] getCustomLists(String url, String user,
			String password, String type) {

		Zweblistline[] result = null;

		try {
			ZWeblists parameter = new ZWeblists();
			parameter.setZdatoslista(new Zweblist());
			parameter.setZlista(type);

			ZWEBLISTStub stub = new ZWEBLISTStub(url);

			ServiceClient sc = stub._getServiceClient();
			Options options = sc.getOptions();
			HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
			auth.setPreemptiveAuthentication(true);
			auth.setUsername(user);
			auth.setPassword(password);
			options.setProperty(HTTPConstants.AUTHENTICATE, auth);

			ZWeblistsResponse response = stub.zWeblists(parameter);
			return response.getZdatoslista().getItem();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static List<WSBean> getSalesArea(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"ZONAS_VENTA");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getBranchs(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"OFICINAS_VENTA");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {
				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				bean.setSociety(row.getText4().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getGroupSellers(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"GRUPOS_VENDEDORES");

		for (Zweblistline row : result) {
			if (!row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				bean.setBranch(row.getText1().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getClientGroup(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"GRUPOS_CLIENTES");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText1().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getDoctors(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"INTERLOCUTORES_COMERCIALES");

		for (Zweblistline row : result) {
			if (!row.getText1().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {
				list.add(new WSBean(row.getText1().toString(), row.getText3()
						.toString()));
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getPaymentMethod(String url, String user,
			String password, String country) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password, "VIAS_PAGO");

		for (Zweblistline row : result) {
			if (row.getText1().toString().toUpperCase()
					.equals(country.toUpperCase())
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getPaymentTerm(String url, String user,
			String password, String country) {
		List<WSBean> list = new ArrayList<WSBean>();
		/*
		 * Zweblistline[] result = getCustomLists(url, user, password,
		 * "CONDICION_PAGO");
		 * 
		 * for (Zweblistline row : result) { if
		 * (row.getText1().toString().toUpperCase()
		 * .equals(country.toUpperCase()) &&
		 * !row.getText2().toString().isEmpty() &&
		 * !row.getText3().toString().isEmpty()) {
		 * 
		 * WSBean bean = new WSBean(); bean.setCode(row.getText2().toString());
		 * bean.setNames(row.getText3().toString()); list.add(bean); } }
		 */

		WSBean bean = new WSBean();
		bean.setCode("Z001");
		bean.setNames("Pagadero inmediato");
		list.add(bean);

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getMaterials(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"MATERIALES");

		for (Zweblistline row : result) {
			if (!row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				bean.setType(row.getText5().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getDocTypes(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"TIPOS_DOCUMENTO");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				bean.setType(row.getText4().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getCenters(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"GRUPOS_CUENTA");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static List<WSBean> getSalesDocuments(String url, String user,
			String password) {
		List<WSBean> list = new ArrayList<WSBean>();
		Zweblistline[] result = getCustomLists(url, user, password,
				"CLASES_DOCUMENTOS_VENTA");

		for (Zweblistline row : result) {
			if (row.getText1().toString().equals("S")
					&& !row.getText2().toString().isEmpty()
					&& !row.getText3().toString().isEmpty()) {

				WSBean bean = new WSBean();
				bean.setCode(row.getText2().toString());
				bean.setNames(row.getText3().toString());
				list.add(bean);
			}
		}

		Collections.sort(list, new WSBeanComparator());
		return list;
	}

	public static void main(String args[]) {

		List<WSBean> list = getMaterials(
				"http://192.168.1.210:8000/sap/bc/srt/rfc/sap/zweblist/300/zweblist/zweblist",
				"CRMWS", "K4N5L2X9H5");

		/*
		 * List<WSBean> list = getPaymentTerm(
		 * "http://192.168.1.210:8000/sap/bc/srt/rfc/sap/zweblist/300/zweblist/zweblist"
		 * , "CRMWS", "K4N5L2X9H5","co");
		 */
		System.out.println("INICIO");
		Map<String, WSBean> map = new HashMap<String, WSBean>();
		for (WSBean row : list) {
			map.put(row.getCode(), row);
			// if (row.getCode().contains("400130")) {
			//System.out.println(row.getCode() + " ; " + row.getNames());
			// }
		}
		StringBuilder sb = new StringBuilder();
		int count=0;
		for (Map.Entry<String, WSBean> entry : map.entrySet()) {
			sb.append(entry.getKey() + ";" + entry.getValue().getNames()+ ";" + entry.getValue().getType()+"\n");
			System.out.println(entry.getKey() + ";" + entry.getValue());
			count++;
		}
		
		try {          
		    
		    File file = new File("E:/war/medicamentos.txt");

		    if (!file.exists()) {
		        file.createNewFile();
		    }

		    FileWriter fw = new FileWriter(file.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(sb.toString());
		    bw.close();

		    System.out.println("Done: "+count);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
