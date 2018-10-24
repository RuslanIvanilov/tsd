package webservice.client.wmsmsk;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.defo.controllers.ArticleController;
import ru.defo.model.WmArticle;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;
/**@deprecated
 * */
public class ShipmentLoader {

	static String XML_NODE = "OrdersShipments";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		String shipmentXML =  new SAD_WMSObmenPortTypeProxy().getShipments("WMS");
    	System.out.println("shipmentXML : "+shipmentXML);

    	/*
		PreOrderManager preOrdMgr = new PreOrderManager();
		WmPreOrder preOrder;
		List<WmPreOrderPos> preOrderPosList = new ArrayList<WmPreOrderPos>();

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(shipmentXML));
		Document doc = builder.parse(is);

		Node root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++){

			Node node = nodes.item(i);
			if(node != null && node.getNodeName() == XML_NODE)
			{

				preOrder = new WmPreOrder();
				preOrder.setOrderId(Long.valueOf(preOrdMgr.getNextPreOrderId()));
				preOrder.setOrderTypeId(1L);
				preOrder.setStatusId(1L);
				preOrder.setClientId(1L);

				NodeList nodes0 = node.getChildNodes();
				for(int k = 0; k <= nodes0.getLength(); k++)
				{
					Node node0 = nodes0.item(k);
					if(node0 != null && node0.getNodeType() != Node.TEXT_NODE)
					{
						createWmsDoc(node0, preOrder, preOrderPosList);
					}
				}

				if(preOrder != null){
					PreOrderPosManager preOrdPosMgr = new PreOrderPosManager();

					if(new PreOrderManager().getPreOrderByCode(preOrder.getOrderCode())==null)
						preOrdMgr.saveOrUpdatePreOrder(preOrder);
				// Записывать строки
					for(WmPreOrderPos preOrderPos0 : preOrderPosList){
						if(new PreOrderManager().getPreOrderById(preOrderPos0.getOrderId())!= null)
							if(preOrdPosMgr.getPreOrderPosByArticleQState(preOrder.getOrderId(), preOrderPos0.getArticleId(), preOrderPos0.getQualityStateId())==null)
								preOrdPosMgr.addOrUpdatePreOrderPos(preOrderPos0);
					}

				}


			} //else { System.out.println("WebClient Result Text: "+advicesXML);  }
		}
    	 */
		System.out.println("WebClient process success END.");
		HibernateUtil.closeAll();
		return;

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

	private static void createWmsDoc(Node node, WmPreOrder preOrder, List<WmPreOrderPos> preOrderPosList){

		/* Создавая документ и строки проверять, что этого еще нет в БД!!! */
		if(node.getNodeType() != Node.TEXT_NODE){
		switch(node.getNodeName()){
		case "Number":
			System.out.println("Number: "+node.getTextContent());
			preOrderPosList.clear();
			preOrder.setOrderCode(node.getTextContent());
			break;

		case "Date":
			System.out.println("Date: "+node.getTextContent());
			preOrder.setExpectedDate(Timestamp.valueOf(node.getTextContent().replace('T', ' ')));
			break;

		case "Comment":
			System.out.println("Comment: "+node.getTextContent());
			preOrder.setErrorComment(node.getTextContent());
			break;

		case "Client":
			for(Map.Entry<String, String> entry : getSubNode(node).entrySet()){
				System.out.println(entry.getKey()+": "+entry.getValue());
				if(entry.getKey().contains("Code")){
					preOrder.setClientDocCode(entry.getValue());
				}
				if(entry.getKey().contains("Description")){
					preOrder.setClientDocDescription(entry.getValue());
				}

			}
			break;

		case "Nomenclature":
			System.out.println("Nomenclature:::::::");
			NodeList nomenclatureNodes = node.getChildNodes();
			for(int f = 0; f <= nomenclatureNodes.getLength(); f++){
				Node nomenclatureNod = nomenclatureNodes.item(f);
				if(nomenclatureNod != null && nomenclatureNod.getNodeType() != Node.TEXT_NODE){

					WmPreOrderPos preOrderPos = new WmPreOrderPos();
					for(Map.Entry<String, String> entry : getSubNode(nomenclatureNod).entrySet())
					{


						/* Создание строк */
						System.out.println("advId : "+preOrder.getOrderCode()+" ::: "+entry.getKey()+": "+entry.getValue());
					 	if(entry.getKey().contains("Code")){
					 		ArticleController artCtrl = new ArticleController();

					 		WmArticle article = artCtrl.getArticleByCode(entry.getValue().trim());
					 		if(article == null){
					 			preOrderPos.setErrorId(3L);
					 			preOrderPos.setErrorComment(ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(Arrays.asList(entry.getValue()))));
					 			preOrder.setStatusId(10L);
					 		} else{
					 			Long orderId = article.getArticleId();
					 			Long skuId = artCtrl.getBaseSkuByArticleId(orderId).getSkuId();
					 			preOrderPos.setArticleId(orderId);
					 			preOrderPos.setSkuId(skuId);
					 		}

					 		preOrderPos.setOrderId(preOrder.getOrderId());
					 		preOrderPos.setOrderPosId((long) (preOrderPosList.size()+1));
					 		preOrderPos.setStatusId(1L);
					 		preOrderPos.setCreateUser(0L);
					 		preOrderPos.setCreateDate(new Timestamp(System.currentTimeMillis()));
					 		preOrderPos.setStatusId(1L);
					 	}

					 	if(entry.getKey().contains("Quantity")){
					 		preOrderPos.setExpectQuantity(Long.valueOf(entry.getValue()));
					 	}

					}

					if(preOrderPos.getExpectQuantity() != null){
						preOrderPosList.add(preOrderPos);
					}

				}
			}
			break;

		}

		//System.out.println("->"+node.getNodeName());
		}

	}


}
