package webservice.client.wmsmsk;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ru.defo.controllers.XmlDocController;
import ru.defo.util.HibernateUtil;

public class ArticleLoader {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		try{
			if(new XmlDocController(new SAD_WMSObmenPortTypeProxy().getSkladskoySostav("WMS", true, null)).loadArticleData())
				System.out.println("WebServiceClient: Article Data success loaded.");
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			System.out.println("WebClient END.");
			HibernateUtil.closeAll();
		}
	}

}
