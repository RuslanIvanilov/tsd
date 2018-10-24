/**
 * SAD_WMSObmenLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.client.wmsmsk;

public class SAD_WMSObmenLocator extends org.apache.axis.client.Service implements webservice.client.wmsmsk.SAD_WMSObmen {

    public SAD_WMSObmenLocator() {
    }


    public SAD_WMSObmenLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SAD_WMSObmenLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SAD_WMSObmenSoap

    /**

     */
    //private java.lang.String SAD_WMSObmenSoap_address = "http://obmen:obmen@192.168.100.127/testmsk/ws/SAD_WMSObmen";
    private java.lang.String SAD_WMSObmenSoap_address = "http://192.168.100.127/msk/ws/SAD_WMSObmen";

    public java.lang.String getSAD_WMSObmenSoapAddress() {
        return SAD_WMSObmenSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SAD_WMSObmenSoapWSDDServiceName = "SAD_WMSObmenSoap";

    public java.lang.String getSAD_WMSObmenSoapWSDDServiceName() {
        return SAD_WMSObmenSoapWSDDServiceName;
    }

    public void setSAD_WMSObmenSoapWSDDServiceName(java.lang.String name) {
        SAD_WMSObmenSoapWSDDServiceName = name;
    }

    public webservice.client.wmsmsk.SAD_WMSObmenPortType getSAD_WMSObmenSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SAD_WMSObmenSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSAD_WMSObmenSoap(endpoint);
    }

    public webservice.client.wmsmsk.SAD_WMSObmenPortType getSAD_WMSObmenSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webservice.client.wmsmsk.SAD_WMSObmenSoapBindingStub _stub = new webservice.client.wmsmsk.SAD_WMSObmenSoapBindingStub(portAddress, this);
            _stub.setPortName(getSAD_WMSObmenSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSAD_WMSObmenSoapEndpointAddress(java.lang.String address) {
        SAD_WMSObmenSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webservice.client.wmsmsk.SAD_WMSObmenPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                webservice.client.wmsmsk.SAD_WMSObmenSoapBindingStub _stub = new webservice.client.wmsmsk.SAD_WMSObmenSoapBindingStub(new java.net.URL(SAD_WMSObmenSoap_address), this);
                _stub.setPortName(getSAD_WMSObmenSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SAD_WMSObmenSoap".equals(inputPortName)) {
            return getSAD_WMSObmenSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("wmsmsk", "SAD_WMSObmen");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("wmsmsk", "SAD_WMSObmenSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("SAD_WMSObmenSoap".equals(portName)) {
            setSAD_WMSObmenSoapEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
