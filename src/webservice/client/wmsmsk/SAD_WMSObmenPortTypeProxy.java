package webservice.client.wmsmsk;

public class SAD_WMSObmenPortTypeProxy implements webservice.client.wmsmsk.SAD_WMSObmenPortType {
  private String _endpoint = null;
  private webservice.client.wmsmsk.SAD_WMSObmenPortType sAD_WMSObmenPortType = null;

  public SAD_WMSObmenPortTypeProxy() {
    _initSAD_WMSObmenPortTypeProxy();
  }

  public SAD_WMSObmenPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initSAD_WMSObmenPortTypeProxy();
  }

  private void _initSAD_WMSObmenPortTypeProxy() {
    try {
      sAD_WMSObmenPortType = (new webservice.client.wmsmsk.SAD_WMSObmenLocator()).getSAD_WMSObmenSoap();
      if (sAD_WMSObmenPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sAD_WMSObmenPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sAD_WMSObmenPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sAD_WMSObmenPortType != null)
      ((javax.xml.rpc.Stub)sAD_WMSObmenPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public webservice.client.wmsmsk.SAD_WMSObmenPortType getSAD_WMSObmenPortType() {
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType;
  }

  public java.lang.String getBalance(java.lang.String codeNode, boolean changes, String balanceDate) throws java.rmi.RemoteException{
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType.getBalance(codeNode, changes, balanceDate);
  }

  public java.lang.String getAdvices(java.lang.String codeNode) throws java.rmi.RemoteException{
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType.getAdvices(codeNode);
  }

  public java.lang.String getSkladskoySostav(java.lang.String codeNode, boolean changes, java.lang.String codeSklSost) throws java.rmi.RemoteException{
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType.getSkladskoySostav(codeNode, changes, codeSklSost);
  }

  public java.lang.String getShipments(java.lang.String codeNode) throws java.rmi.RemoteException{
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType.getShipments(codeNode);
  }

  public java.lang.String getMovements(java.lang.String codeNode) throws java.rmi.RemoteException{
    if (sAD_WMSObmenPortType == null)
      _initSAD_WMSObmenPortTypeProxy();
    return sAD_WMSObmenPortType.getMovements(codeNode);
  }

  public java.lang.String getAdviceInvoices(java.lang.String codeNode) throws java.rmi.RemoteException{
	    if (sAD_WMSObmenPortType == null)
	      _initSAD_WMSObmenPortTypeProxy();
	    return sAD_WMSObmenPortType.getAdviceInvoices(codeNode);
  }
}