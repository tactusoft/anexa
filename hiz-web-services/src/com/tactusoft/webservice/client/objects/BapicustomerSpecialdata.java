/**
 * BapicustomerSpecialdata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.tactusoft.webservice.client.objects;

public class BapicustomerSpecialdata  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String customer;

    private java.lang.String acc1Time;

    public BapicustomerSpecialdata() {
    }

    public BapicustomerSpecialdata(
           java.lang.String customer,
           java.lang.String acc1Time) {
           this.customer = customer;
           this.acc1Time = acc1Time;
    }


    /**
     * Gets the customer value for this BapicustomerSpecialdata.
     * 
     * @return customer
     */
    public java.lang.String getCustomer() {
        return customer;
    }


    /**
     * Sets the customer value for this BapicustomerSpecialdata.
     * 
     * @param customer
     */
    public void setCustomer(java.lang.String customer) {
        this.customer = customer;
    }


    /**
     * Gets the acc1Time value for this BapicustomerSpecialdata.
     * 
     * @return acc1Time
     */
    public java.lang.String getAcc1Time() {
        return acc1Time;
    }


    /**
     * Sets the acc1Time value for this BapicustomerSpecialdata.
     * 
     * @param acc1Time
     */
    public void setAcc1Time(java.lang.String acc1Time) {
        this.acc1Time = acc1Time;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BapicustomerSpecialdata)) return false;
        BapicustomerSpecialdata other = (BapicustomerSpecialdata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customer==null && other.getCustomer()==null) || 
             (this.customer!=null &&
              this.customer.equals(other.getCustomer()))) &&
            ((this.acc1Time==null && other.getAcc1Time()==null) || 
             (this.acc1Time!=null &&
              this.acc1Time.equals(other.getAcc1Time())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCustomer() != null) {
            _hashCode += getCustomer().hashCode();
        }
        if (getAcc1Time() != null) {
            _hashCode += getAcc1Time().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BapicustomerSpecialdata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:soap:functions:mc-style", "BapicustomerSpecialdata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Customer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acc1Time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Acc1Time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
