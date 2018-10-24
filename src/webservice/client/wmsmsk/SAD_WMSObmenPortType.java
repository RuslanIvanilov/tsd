/**
 * SAD_WMSObmenPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservice.client.wmsmsk;

public interface SAD_WMSObmenPortType extends java.rmi.Remote {
    public java.lang.String getBalance(java.lang.String codeNode, boolean changes, java.lang.String balanceDate) throws java.rmi.RemoteException;
    public java.lang.String getAdvices(java.lang.String codeNode) throws java.rmi.RemoteException;
    public java.lang.String getSkladskoySostav(java.lang.String codeNode, boolean changes, java.lang.String codeSklSost) throws java.rmi.RemoteException;
    public java.lang.String getShipments(java.lang.String codeNode) throws java.rmi.RemoteException;
    public java.lang.String getMovements(java.lang.String codeNode) throws java.rmi.RemoteException;
    public java.lang.String getAdviceInvoices(java.lang.String codeNode) throws java.rmi.RemoteException;
}
