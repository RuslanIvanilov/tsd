package webservice.client.wmsmsk;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import ru.defo.controllers.XmlDocController;
import ru.defo.util.HibernateUtil;
import ru.defo.util.WrongParamData;

public class AdviceLoader {

	public static void main(String[] args) throws DOMException, RemoteException, WrongParamData, ParserConfigurationException, SAXException, IOException
	{
		if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getAdviceInvoices("WMS")).processAdviceData(true))
			System.out.println("WebServiceClient: Advice Data success loaded.");

		System.out.println("WebClient END.");
		HibernateUtil.closeAll();
	}

}
