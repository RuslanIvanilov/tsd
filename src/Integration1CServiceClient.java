import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.io.IOUtils;

public class Integration1CServiceClient {

	public static void main(String[] args) throws IOException{

		URL url = new URL("http://obmen:obmen@192.168.100.127/testmsk/ws/SAD_WMSObmen?wsdl");
		/*
		QName qname = new QName("wmsmsk", "SAD_WMSObmenSoap");

		Service service = Service.create(url, qname);
		*/
		//service.getPort("SAD_WMSObmenSoap")

		System.out.println( "Opening connection.." );

		HttpURLConnection h = (HttpURLConnection) url.openConnection();

		h.setRequestMethod( "GET");

		System.out.println( "Getting response code.." );

		int responseCode = h.getResponseCode();

		System.out.println( "Response code: " + responseCode );

		if ( responseCode == 200 )

		{

			String text = IOUtils.toString(h.getInputStream(), StandardCharsets.UTF_8.name());
			System.out.println(text);

			Service service = Service.create(url, new QName("wmsmsk", "SAD_WMSObmen")); //SAD_WMSObmen
			Iterator<QName> iterator = service.getPorts();
			while(iterator.hasNext()){
				QName qname = iterator.next();
			   System.out.println("getLocalPart : "+qname.getLocalPart()+"\ngetNamespaceURI.."+qname.getNamespaceURI());
			}



		}



	}

}
