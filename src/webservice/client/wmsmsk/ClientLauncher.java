package webservice.client.wmsmsk;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.defo.controllers.ArticleController;
import ru.defo.managers.PreAdviceManager;
import ru.defo.managers.PreAdvicePosManager;
import ru.defo.model.WmArticle;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.util.ErrorMessage;

public class ClientLauncher {

	static String ADVICE_NODE = "AdvicesInvoice"; //"OrdersMovements";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		String advicesXML = new SAD_WMSObmenPortTypeProxy().getAdviceInvoices("WMS");

		System.out.println("advicesXML\n"+advicesXML);

		//String movementXML = new SAD_WMSObmenPortTypeProxy().getMovements("WMS");
		//String shipmentXML =  new SAD_WMSObmenPortTypeProxy().getShipments("WMS");

		//System.out.println("getAdvices20mtemp : "+advicesXML);
    	//System.out.println("getAdvices20mtemp : "+shipmentXML);
		PreAdviceManager advMgr = new PreAdviceManager();
		WmPreAdvice advice;
		List<WmPreAdvicePos> advPosList = new ArrayList<WmPreAdvicePos>();

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(advicesXML));
		Document doc = builder.parse(is);

		Node root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++){

			Node node = nodes.item(i);
			if(node != null && node.getNodeName() == ADVICE_NODE)
			{

				advice = new WmPreAdvice();
				advice.setAdviceId(Long.valueOf(advMgr.getNextPreAdviceId()));
				advice.setAdviceTypeId(1L);
				advice.setStatusId(1L);
				advice.setClientId(1L);

				NodeList nodes0 = node.getChildNodes();
				for(int k = 0; k <= nodes0.getLength(); k++)
				{
					Node node0 = nodes0.item(k);
					if(node0 != null && node0.getNodeType() != Node.TEXT_NODE)
					{
						createWmsDoc(node0, advice, advPosList);
					}
				}

				if(advice != null){
					advMgr.saveOrUpdatePreAdvice(advice);
				/* Записывать строки */
					for(WmPreAdvicePos advicePos0 : advPosList){
						if(new PreAdviceManager().getPreAdviceById(advicePos0.getAdviceId())!= null)
							new PreAdvicePosManager().addOrUpdatePreAdvicePos(advicePos0);
					}

				}


			} //else { System.out.println("WebClient Result Text: "+advicesXML);  }
		}

		System.out.println("WebClient process success END.");
	}


	private static Map<String, String> getSubNode(Node parentNode){
		Map<String, String> map = new LinkedHashMap<String, String>();
		NodeList childNodesList = parentNode.getChildNodes();

		for(int i=0; i<= childNodesList.getLength(); i++){
			Node childNode = childNodesList.item(i);
			if(childNode != null && childNode.getNodeType() != Node.TEXT_NODE)
			{
				map.put(childNode.getNodeName(), childNode.getTextContent());
			}
		}
		return map;
	}

	private static void createWmsDoc(Node node, WmPreAdvice advice, List<WmPreAdvicePos> advPosList){

		/* Создавая документ и строки проверять, что этого еще нет в БД!!! */
		if(node.getNodeType() != Node.TEXT_NODE){
		switch(node.getNodeName()){
		case "Number":
			System.out.println("Number: "+node.getTextContent());
			advPosList.clear();
			advice.setAdviceCode(node.getTextContent());
			break;

		case "Date":
			System.out.println("Date: "+node.getTextContent());
			advice.setExpectedDate(Timestamp.valueOf(node.getTextContent().replace('T', ' ')));
			break;

		case "Comment":
			System.out.println("Comment: "+node.getTextContent());
			advice.setErrorComment(node.getTextContent());
			break;

		case "Client":
			for(Map.Entry<String, String> entry : getSubNode(node).entrySet()){
				System.out.println(entry.getKey()+": "+entry.getValue());
				if(entry.getKey().contains("Code")){
					advice.setClientDocCode(entry.getValue());
				}
			}
			break;

		case "Nomenclature":
			System.out.println("Nomenclature:::::::");
			NodeList nomenclatureNodes = node.getChildNodes();
			for(int f = 0; f <= nomenclatureNodes.getLength(); f++){
				Node nomenclatureNod = nomenclatureNodes.item(f);
				if(nomenclatureNod != null && nomenclatureNod.getNodeType() != Node.TEXT_NODE){

					WmPreAdvicePos advicePos = new WmPreAdvicePos();
					for(Map.Entry<String, String> entry : getSubNode(nomenclatureNod).entrySet())
					{


						/* Создание строк */
						System.out.println("advId : "+advice.getAdviceCode()+" ::: "+entry.getKey()+": "+entry.getValue());
					 	if(entry.getKey().contains("Code")){
					 		ArticleController artCtrl = new ArticleController();

					 		WmArticle article = artCtrl.getArticleByCode(entry.getValue());
					 		if(article == null){
					 			advicePos.setErrorId(3L);
					 			advicePos.setErrorComment(ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(Arrays.asList(entry.getValue()))));
					 			advice.setStatusId(10L);
					 		} else{
					 			Long articleId = article.getArticleId();
					 			Long skuId = artCtrl.getBaseSkuByArticleId(articleId).getSkuId();
					 			advicePos.setArticleId(articleId);
					 			advicePos.setSkuId(skuId);
					 		}

					 		advicePos.setAdviceId(advice.getAdviceId());
					 		advicePos.setAdvicePosId((long) (advPosList.size()+1));
					 		advicePos.setStatusId(1L);
					 		advicePos.setCreateUser(0L);
					 		advicePos.setCreateDate(new Timestamp(System.currentTimeMillis()));
					 		advicePos.setStatusId(1L);
					 	}

					 	if(entry.getKey().contains("Quantity")){
					 		advicePos.setExpectQuantity(Long.valueOf(entry.getValue()));
					 	}

					}

					if(advicePos.getExpectQuantity() != null){
						advPosList.add(advicePos);
					}

				}
			}
			break;

		}

		//System.out.println("->"+node.getNodeName());
		}

	}

}
