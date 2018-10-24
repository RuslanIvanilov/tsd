package webservice.client.wmsmsk;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import ru.defo.controllers.XmlDocController;
import ru.defo.util.HibernateUtil;
import ru.defo.util.WrongParamData;

public class ShptLoader {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, DOMException, WrongParamData {

		//if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getShipments("WMS")).loadShipmentData())
		//	System.out.println("WebServiceClient: Shipment Data success loaded.");
		try{
			if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getShipments("WMS")).processShipmentData(true))
				System.out.println("WebServiceClient: Shipment Data success loaded.");

	    	if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getMovements("WMS")).processMovementData())
				System.out.println("WebServiceClient: Movement Data success loaded.");
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			System.out.println("WebClient END.");
			HibernateUtil.closeAll();
		}
	}
}
