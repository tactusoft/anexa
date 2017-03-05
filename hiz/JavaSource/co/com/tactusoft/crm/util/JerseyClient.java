package co.com.tactusoft.crm.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class JerseyClient {

	private SAPEnvironment sap;

	public JerseyClient() {
		sap = FacesUtil.findBean("SAPEnvironment");
	}

	public String getClient(String docPatient, String country) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client.target(sap.getUrlRest());
		WebTarget resourceWebTarget = webTarget.path("SapWS");
		WebTarget getClientTarget = resourceWebTarget.path("getClient");
		WebTarget queryParams = getClientTarget.queryParam("docPatient",
				docPatient).queryParam("country", country);

		Invocation.Builder invocationBuilder = queryParams
				.request(MediaType.TEXT_PLAIN_TYPE);
		invocationBuilder.header("some-header", "true");

		Response response = invocationBuilder.get();

		return response.readEntity(String.class);
	}
}
