package webservice.client.wmsmsk;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.defo.managers.PreOrderManager;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;

public class WSReciveController {

	SAD_WMSObmenPortTypeProxy proxy;
	PreOrderManager preOrdMgr = new PreOrderManager();

	@SuppressWarnings("unused")
	public WSReciveController() {
		proxy = new SAD_WMSObmenPortTypeProxy();

		WmPreOrder preOrder;

		List<WmPreOrderPos> preOrderPosList = new ArrayList<WmPreOrderPos>();

		ServiceMessageType[] typeArray = ServiceMessageType.getMessageTypesArray();
		for (ServiceMessageType messageType : typeArray) {
			System.out.println("\n\n\n...:::::: " + messageType.typeName());
			try {

				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(getXML(messageType)));
				System.out.println("is ..... "+is.getCharacterStream().toString());
				Document doc = builder.parse(is);



			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(":::::::::::::: End of chapter.");


	}

	public void closeConnection(){
		preOrdMgr.closeConnection();
	}

	private String getXML(ServiceMessageType messageType) throws RemoteException {

		String result;

		switch (messageType) {
		case SHIPMENT:
			result = proxy.getShipments("WMS");
			break;
		case ADVICE:
			result = proxy.getAdviceInvoices("WMS");
			break;
		case MOVEMENT:
			result = proxy.getMovements("WMS");
			break;
		case ARTICLE:
			result = proxy.getSkladskoySostav("WMS", true, null);
			break;
		default:
			result = "";
			break;
		}

		return result;
	}



}
