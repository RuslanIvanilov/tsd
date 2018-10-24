package webservice.client.wmsmsk;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import ru.defo.controllers.XmlDocController;
import ru.defo.managers.InventoryManager;
import ru.defo.util.DefaultValue;
import ru.defo.util.HibernateUtil;
import ru.defo.util.WrongParamData;

public class BalanceLoader {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, DOMException, WrongParamData {

		boolean result = new InventoryManager().eraseInventory();

		if(result) result = new XmlDocController(new SAD_WMSObmenPortTypeProxy().getBalance("WMS", true, "26.04.2017 0:00:00")).processBalanceData(DefaultValue.INITIATOR_HOST, true);

		if(result){
			System.out.println("WebServiceClient: Balance Data success loaded.");
		} else{
			System.out.println("WebServiceClient: Balance Data NOT loaded!");
		}

		System.out.println("WebClient END.");
		HibernateUtil.closeAll();
	}

}
