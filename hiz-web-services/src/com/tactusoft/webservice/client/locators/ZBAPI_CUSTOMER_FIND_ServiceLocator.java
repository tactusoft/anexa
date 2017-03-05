/**
 * ZBAPI_CUSTOMER_FIND_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tactusoft.webservice.client.locators;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class ZBAPI_CUSTOMER_FIND_ServiceLocator extends
		org.apache.axis.client.Service implements
		com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_Service {

	private String username;
	private String password;

	// Use to get a proxy class for ZBAPI_CUSTOMER_FIND
	private java.lang.String ZBAPI_CUSTOMER_FIND_address;

	// The WSDD service name defaults to the port name.
	private java.lang.String ZBAPI_CUSTOMER_FINDWSDDServiceName = "ZBAPI_CUSTOMER_FIND";

	public ZBAPI_CUSTOMER_FIND_ServiceLocator() {
		ZBAPI_CUSTOMER_FIND_address = "http://ansrv07.affinitycolombia.com:8000/sap/bc/srt/rfc/sap/zbapi_customer_find/200/zbapi_customer_find/zbapi_customer_find";
	}

	public ZBAPI_CUSTOMER_FIND_ServiceLocator(String url) {
		ZBAPI_CUSTOMER_FIND_address = url;
	}

	public ZBAPI_CUSTOMER_FIND_ServiceLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public ZBAPI_CUSTOMER_FIND_ServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	public java.lang.String getZBAPI_CUSTOMER_FINDAddress() {
		return ZBAPI_CUSTOMER_FIND_address;
	}

	public java.lang.String getZBAPI_CUSTOMER_FINDWSDDServiceName() {
		return ZBAPI_CUSTOMER_FINDWSDDServiceName;
	}

	public void setZBAPI_CUSTOMER_FINDWSDDServiceName(java.lang.String name) {
		ZBAPI_CUSTOMER_FINDWSDDServiceName = name;
	}

	public com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_PortType getZBAPI_CUSTOMER_FIND(
			String username, String password) throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			this.username = username;
			this.password = password;
			endpoint = new java.net.URL(ZBAPI_CUSTOMER_FIND_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getZBAPI_CUSTOMER_FIND(endpoint);
	}

	public com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_PortType getZBAPI_CUSTOMER_FIND(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_BindingStub _stub = new com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_BindingStub(
					portAddress, this);
			_stub.setUsername(this.username);
			_stub.setPassword(this.password);
			_stub.setPortName(getZBAPI_CUSTOMER_FINDWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setZBAPI_CUSTOMER_FINDEndpointAddress(java.lang.String address) {
		ZBAPI_CUSTOMER_FIND_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_PortType.class
					.isAssignableFrom(serviceEndpointInterface)) {
				com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_BindingStub _stub = new com.tactusoft.webservice.client.locators.ZBAPI_CUSTOMER_FIND_BindingStub(
						new java.net.URL(ZBAPI_CUSTOMER_FIND_address), this);
				_stub.setPortName(getZBAPI_CUSTOMER_FINDWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("ZBAPI_CUSTOMER_FIND".equals(inputPortName)) {
			return getZBAPI_CUSTOMER_FIND(this.username, this.password);
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:soap:functions:mc-style",
				"ZBAPI_CUSTOMER_FIND");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"urn:sap-com:document:sap:soap:functions:mc-style",
					"ZBAPI_CUSTOMER_FIND"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("ZBAPI_CUSTOMER_FIND".equals(portName)) {
			setZBAPI_CUSTOMER_FINDEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
