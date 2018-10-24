package ru.defo.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ru.defo.managers.ArticleManager;
import ru.defo.managers.CarManager;
import ru.defo.managers.DeliveryManager;
import ru.defo.managers.ErrorManager;
import ru.defo.managers.InventoryManager;
import ru.defo.managers.PreAdviceManager;
import ru.defo.managers.PreAdvicePosManager;
import ru.defo.managers.PreOrderManager;
import ru.defo.managers.PreOrderPosManager;
import ru.defo.managers.SkuManager;
import ru.defo.model.Sku;
import ru.defo.model.WmArticle;
import ru.defo.model.WmCar;
import ru.defo.model.WmInventory;
import ru.defo.model.WmInventoryPos;
import ru.defo.model.WmPreAdvice;
import ru.defo.model.WmPreAdvicePos;
import ru.defo.model.WmPreOrder;
import ru.defo.model.WmPreOrderPos;
import ru.defo.model.WmRoute;
import ru.defo.model.WmSku;
import ru.defo.model.local.HostSku;
import ru.defo.model.local.advice.AdviceInvoice;
import ru.defo.model.local.advice.Element;
import ru.defo.model.local.advice.Nomenclature;
import ru.defo.util.AppUtil;
import ru.defo.util.ErrorMessage;
import ru.defo.util.HibernateUtil;
import ru.defo.util.WrongParamData;

public class XmlDocController {

	private Document doc;

	public XmlDocController(String webServiceXml) throws ParserConfigurationException, SAXException, IOException{

		System.out.println("strXML \n"+webServiceXml);
		//saveToFile(webServiceXml);
		System.out.println("-> Start Date: "+new Date());

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(webServiceXml));
		doc = builder.parse(is);
	}

	private Sku articleDataParser(NodeList nodeList){
		Sku sku = new Sku();
		for(int k = 0; k <= nodeList.getLength(); k++)
		{
			Node node = nodeList.item(k);
			if(node != null && node.getNodeType() != Node.TEXT_NODE)
			{

				switch(node.getNodeName()){

				case "Nomenclature" :

					NodeList nomenList = node.getChildNodes();
					for(int z=0; z<=nomenList.getLength()-1; z++){
						if(nomenList.item(z).getNodeName().equals("Code")) sku.setMasterCode(nomenList.item(z).getTextContent());
						if(nomenList.item(z).getNodeName().equals("Description")) sku.setMasterDescription(nomenList.item(z).getTextContent());
					}

				break;

				case "Code" :
					sku.setSkuCode(node.getTextContent());
				break;

				case "Description" :
					sku.setSkuDescription(node.getTextContent());
				break;

				case "Barcode" :
					sku.addBarcodeToSet(node.getTextContent());
				break;

				}
			}
		}

		return sku;
	}

	public boolean loadArticleData()
	{
		Node root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++){

			Node node = nodes.item(i);
			if(node != null && node.getNodeName() == "SkladskoySostav")
			{
				Sku sku = articleDataParser(node.getChildNodes());
				new ArticleController().createSku(sku);
			}
		}

		return true;
	}


	public boolean processBalanceData(Long initiatorId, boolean persist) throws DOMException, WrongParamData
	{
		WmInventory invent = new InventoryManager().createInventoryToday(initiatorId);
		if(!(invent instanceof WmInventory)) return false;

		List<WmInventoryPos> posList = new ArrayList<WmInventoryPos>();

		NodeList nodes = doc.getDocumentElement().getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++)
		{
			if(nodes.item(i) != null && nodes.item(i).getNodeName() == "BalanceSklad")
				if(processDataBlock(nodes.item(i).getChildNodes(), invent, posList)==false) return false;
		}

		/* Save Header & Lines */
		if(persist){
			Session session = HibernateUtil.getSession();
			try{
				session.getTransaction().begin();
				session.persist(invent);

				for(WmInventoryPos pos  : posList)
				{
					session.persist(pos);
				}
				session.getTransaction().commit();
			} catch(Exception e){
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally{
				session.close();
			}
		}
		return true;
	}

	///////////////////////////////////////////////-------------------------------------------------
	public boolean processShipmentData(boolean persist) throws DOMException, WrongParamData
	{
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++)
		{
			if(nodes.item(i) != null && nodes.item(i).getNodeName() == "OrdersShipments")
					if(parseDataBlock(nodes.item(i).getChildNodes(), persist)==false) return false;

		}

		return true;
	}

	public boolean processAdviceData(boolean persist) throws DOMException, WrongParamData
	{
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++)
		{

			if(nodes.item(i) != null && nodes.item(i).getNodeName() == "AdvicesInvoice")
					if(parseAdviceDataBlock(nodes.item(i).getChildNodes(), persist)==false) return false;
					//if(saveData) persistData(car, route, preOrder, preOrderPosList);

		}

		return true;
	}

	private boolean persistData(AdviceInvoice advInv)
	{
		WmPreAdvice preAdv = new PreAdviceManager().initByAdviceInvoice(advInv);
		List<WmPreAdvicePos> posList = new PreAdvicePosManager().initPosListByAdviceInvoice(preAdv, advInv);

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		try{
			tx.begin();

			if(new PreAdviceManager().toPersist(preAdv)) session.merge(preAdv);

			for(WmPreAdvicePos pos : posList){
				if(new PreAdvicePosManager().toPersist(pos)) session.persist(pos);
			}

			tx.commit();
		}catch(Exception e){
			tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}


		return true;
	}


	private boolean persistData(WmCar car, WmRoute route, WmPreOrder preOrder, List<WmPreOrderPos> preOrderPosList){
		Session session = HibernateUtil.getSession();
		WmCar carNew = null;
		PreOrderPosManager preOrdPosMgr = new PreOrderPosManager();

		try{
			session.getTransaction().begin();

			if(car.getCarCode() != null){
				carNew = new CarManager().createOrUpdate(session, car);
				route.setCarId(carNew.getCarId());
			}

			if(route.getRouteCode() != null){
				WmRoute routeNew = new DeliveryManager().createOrUpdate(session, route);
				preOrder.setRouteId(routeNew.getRouteId());
			}

			WmPreOrder preOrderNew = new PreOrderManager().createOrUpdate(session, preOrder);

			if((preOrderNew instanceof WmPreOrder) && preOrderNew.getWmOrderLink()==null){ //  <--If not job start!
				preOrdPosMgr.markOrderPosBeforeUpdate(preOrderNew);
				for(WmPreOrderPos preOrderPos : preOrderPosList)
				{
					preOrderPos.setOrderId(preOrderNew.getOrderId());
					preOrdPosMgr.createOrUpdate(session, preOrderPos, preOrderNew);
				}
				preOrdPosMgr.deleteRemovedPos(preOrderNew);
			}

			session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			session.close();
		}

		return true;
	}

	private boolean processDataBlock(NodeList nodes, WmInventory invent, List<WmInventoryPos> posList) throws DOMException, WrongParamData{
		HostSku hostSku = new HostSku();
		WmInventoryPos invPos = null;

		for(int i=0; i<=nodes.getLength(); i++){
			if(nodes.item(i) != null && nodes.item(i).getNodeName() !="#text" && nodes.item(i).getTextContent().length()>0)
				parseNode(nodes.item(i), hostSku);


			if(hostSku != null && hostSku.getHostQuantity() > 0){
				invPos =  new InventoryManager().getOrCreateInventPos(invent, hostSku, (long)posList.size());
				posList.add(invPos);
				hostSku = new HostSku();
			}


		}

		return true;
	}



	private void parseNode(Node node, HostSku hostSku){
		if(node.getNodeName().equals("SkladskoySostav")){
			 hostSku.setArticleCode(node.getChildNodes().item(1).getTextContent());
			 hostSku.setDescription(node.getChildNodes().item(3).getTextContent());
		}

		if(node.getNodeName().equals("Balance")){
			hostSku.setHostQuantity(Double.parseDouble(node.getTextContent()));
		}

	}

	private boolean parseAdviceDataBlock(NodeList nodes, boolean persist) throws DOMException, WrongParamData
	{
		AdviceInvoice advInv = new AdviceInvoice();

		for(int i=0; i<=nodes.getLength(); i++){
			if(nodes.item(i) != null && nodes.item(i).getNodeName() !="#text" && nodes.item(i).getTextContent().length()>0){
				separatTagAdv(nodes.item(i), advInv);
			}
		}

		System.out.println("\n\n\t-----------------------");
		//System.out.println("\nAdvice: "+artInv.toString());
		//System.out.println("Client: "+artInv.getClient().toString());

		List<Nomenclature> nomList = advInv.getNomenclatureList();
		for(Nomenclature nom : nomList){
			System.out.println("Item: "+nom.toString());
			List<Element> elementList = nom.getElementList();
			for(Element e : elementList){
				System.out.println("Article: "+e.toString());
			}
			if(elementList.size()==0)advInv.setErrorId(3);

		}

		System.out.println("\nAdvice: "+advInv.toString());

		System.out.println(".............Данные собраны, объекты Adv & Poses можно писать в БД ...............");
		if(persist) persistData(advInv);

		return true;
	}

	private boolean parseDataBlock(NodeList nodes, boolean persist) throws DOMException, WrongParamData{

		WmPreOrder preOrder = new WmPreOrder();
		List<WmPreOrderPos> preOrderPosList = new ArrayList<WmPreOrderPos>();
		WmCar car = new WmCar();
		WmRoute route = new WmRoute();

		for(int i=0; i<=nodes.getLength(); i++){
			if(nodes.item(i) != null && nodes.item(i).getNodeName() !="#text" && nodes.item(i).getTextContent().length()>0){
				separatTag(nodes.item(i), preOrder, preOrderPosList, car, route);

				/*if(preOrder.getErrorComment() != null){
					preOrder.setErrorId(6L);
					preOrder.setErrorComment(null);
				}*/
			}
		}

		if(preOrder.getClientDocCode() != null){

			try{
				if(Integer.parseInt(preOrder.getClientDocCode())==0){

					System.out.println(">>>>>>>>>>>>>>>>>>> "+preOrder.getClientDocCode());

					preOrder = new WmPreOrder();
					preOrderPosList.clear();
					route = new WmRoute();
					car = new WmCar();
				 	}
			}catch(NumberFormatException e){
				System.out.println("ClientCode"+preOrder.getClientDocCode());
			}


		}
		if(preOrder.getOrderCode() != null)
			System.out.println("ORDER ::: code : "+preOrder.getOrderCode()+" date : "+preOrder.getExpectedDate()+" descript : "+preOrder.getClientDocDescription()+(preOrder.getErrorId()!=null?" error_id : "+preOrder.getErrorId():""));

		for(WmPreOrderPos preOrderPos : preOrderPosList){
			System.out.println("ORDER_POS ::: pos_id : "+preOrderPos.getOrderPosId() +" article_id : "+new ArticleManager().getArticleById(preOrderPos.getArticleId()).getArticleCode()+" expected quantity : "+preOrderPos.getExpectQuantity());
		}

		if(route.getRouteCode() != null)
			System.out.println("ROUTE ::: code : "+route.getRouteCode()+" date : "+route.getExpectedDate());

		if(car.getCarMark() != null)
			System.out.println("CAR::: Mark: "+car.getCarMark()+" Code: "+car.getCarCode());

		System.out.println(".............Данные собраны, объекты Ord, Poses, Car & Route можно писать в БД ...............");

		if(persist) persistData(car, route, preOrder, preOrderPosList);

		return true;
	}


	private void separatTagAdv(Node node, AdviceInvoice advInv) throws DOMException, WrongParamData
	{

		//System.out.println("separatTagAdv:: node.getNodeName() : "+node.getNodeName());

		switch(node.getNodeName()){

		case "GUID":
			advInv.setDocGUID(node.getTextContent());
		break;

		case "Number":
			advInv.setDocNumber(node.getTextContent());
		break;

		case "Date":
			advInv.setDocDate(node.getTextContent());
		break;

		case "Client":
			for(Map.Entry<String, String> entryClient : getSubNode(node).entrySet()){
				if(entryClient.getKey().equals("Code")){
					advInv.getClient().setCode(entryClient.getValue());
				}
				if(entryClient.getKey().equals("Description")){
					advInv.getClient().setDescription(entryClient.getValue());
				}
			}
			break;

		case "Nomenclature":
			Nomenclature nomenclature = new Nomenclature();
			for(Map.Entry<String, String> entryNomenclature : getSubNode(node).entrySet()){
				switch(entryNomenclature.getKey()){
				case "Code":
					//System.out.println("Nomenclature --> "+"Code: "+entryNomenclature.getValue());
					nomenclature.setCode(entryNomenclature.getValue());
				break;
				case "Description":
					//System.out.println("Nomenclature --> "+"Description: "+entryNomenclature.getValue());
					nomenclature.setDescription(entryNomenclature.getValue());
				break;
				case "Quantity":
					//System.out.println("Nomenclature --> "+"Quantity: "+entryNomenclature.getValue());
					nomenclature.setQuantity(Integer.decode(entryNomenclature.getValue()));
				break;

				case "SkladskoySostav":
				  //System.out.println("::::> node.getTextContent() : "+entryNomenclature.getValue());
					getNomenclatureElementList(node, nomenclature);
					//System.out.println("SkladskoySostav.elementList.size() : "+nomenclature.getElementList().size());
				  break;
				}
			}
			advInv.getNomenclatureList().add(nomenclature);
		break;

		default:
			//System.out.println("! separatTagAdv:: UNKNOWN nodeName  : "+node.getNodeName());
			break;
		}

	}


	private void separatTag(Node node, WmPreOrder preOrder, List<WmPreOrderPos> preOrderPosList, WmCar car, WmRoute route) throws DOMException, WrongParamData{
			WmPreOrderPos preOrderPos = null;
			String artHeaderCode = null;

			switch(node.getNodeName()){

			case "Number":
				preOrder.setOrderCode(node.getTextContent());
			break;

			case "Date":
				preOrder.setExpectedDate(Timestamp.valueOf(node.getTextContent().replace('T', ' ')));
			break;

			case "Client":
				for(Map.Entry<String, String> entryClient : getSubNode(node).entrySet()){
					//System.out.println(entryClient.getKey()+": "+entryClient.getValue());
					if(entryClient.getKey().equals("Code")){
						preOrder.setClientDocCode(entryClient.getValue());
					}
					if(entryClient.getKey().equals("Description")){
						preOrder.setClientDocDescription(entryClient.getValue());
					}
				}
				break;

			case "Nomenclature":
				NodeList nodeList = (NodeList)node.getChildNodes();
				for(int i=0; i<nodeList.getLength(); i++){
					//System.out.println("*Nomenclature: "+nodeList.item(i).getTextContent().trim());

					if(nodeList.item(i).getNodeName().equals("Code")){
						//System.out.println("*Code: "+nodeList.item(i).getTextContent()+" ::: artHeaderCode: "+artHeaderCode);
						if(artHeaderCode == null || !artHeaderCode.equals( nodeList.item(i).getTextContent().trim()))
							artHeaderCode =  nodeList.item(i).getTextContent();
					}

					/*
					if(nodeList.item(i).getNodeName().equals("Code"){
						WmPreOrder preOrd = new PreOrderManager().getPreOrderByCode(preOrder.getOrderCode(), false);
						if(preOrd != null && ( preOrd.getStatusId()<3L || preOrd.getStatusId()==10L))){
							System.out.println(preOrder.getOrderCode()+" toArt Code: "+nodeList.item(i).getTextContent().trim());
							preOrder.setStatusId(10L);
				 			preOrder.setErrorId(6L);
				 			preOrder.setErrorComment(new ErrorManager().getErrorById(6L).getDescription());
						}
					}*/


					if(nodeList.item(i).getNodeName().equals("SkladskoySostav")){

						for(int r=0; r<nodeList.item(i).getChildNodes().getLength(); r++){
							switch(nodeList.item(i).getChildNodes().item(r).getNodeName()){
							case "Code" :
								if(preOrder.getErrorId() == null) preOrder.setErrorComment(null);
								//System.out.println(i+"\t.<ARTICLE CODE> "+nodeList.item(i).getChildNodes().item(r).getTextContent());
								preOrderPos = new WmPreOrderPos();
								//System.out.println("toArt: "+nodeList.item(i).getTextContent().trim());

								WmArticle article = new ArticleManager().getArticleByCode(nodeList.item(i).getChildNodes().item(r).getTextContent().trim());
								if(!(article instanceof WmArticle)){
									preOrderPos.setErrorId(3L);
									preOrderPos.setErrorComment(ErrorMessage.BC_NOT_SKU.message(new ArrayList<String>(Arrays.asList(nodeList.item(i).getChildNodes().item(r).getTextContent()))));
						 			//preOrder.setStatusId(10L);
						 			//preOrder.setErrorId(6L);
						 			//preOrder.setErrorComment(new ErrorManager().getErrorById(6L).getDescription());
						 			preOrder.setClientDocCode(null);
								} else {
									artHeaderCode = null;
									preOrderPos.setArticleId(article.getArticleId());
									WmSku sku = new SkuManager().getBaseSkyByArticle(preOrderPos.getArticleId());
									if(sku instanceof WmSku){
										preOrderPos.setSkuId(sku.getSkuId());

										/*preOrder.setStatusId(3L);
							 			preOrder.setErrorId(null);
							 			preOrder.setErrorComment(null);*/
									}
								}

								preOrderPos.setOrderPosId((preOrderPosList==null?0L:preOrderPosList.size())+1L);
							break;
							case "Quantity" :

									//System.out.println("<Quantity> "+nodeList.item(i).getChildNodes().item(r).getTextContent());

									preOrderPos.setExpectQuantity(Long.valueOf(nodeList.item(i).getChildNodes().item(r).getTextContent()));

									if(preOrderPos.getArticleId() != null && (preOrderPos.getExpectQuantity()==null?0:preOrderPos.getExpectQuantity())>0)
										preOrderPosList.add(preOrderPos);
							break;

							}
						}

						//if(artHeaderCode !=null) System.out.println("artHeaderCode : "+artHeaderCode);

					} else {
								if(i==1){
									preOrder.setErrorComment(nodeList.item(i).getTextContent().trim());

						 			//preOrder.setErrorId(null);
						 			//preOrder.setErrorComment(null);

									//System.out.println(i+"\t.Нет складской состав: "+nodeList.item(i).getTextContent().trim());
								}
						}


				}
				/* Складского состава небыло! */
				if(artHeaderCode != null){
					preOrder.setStatusId(10L);
		 			preOrder.setErrorId(6L);
		 			preOrder.setErrorComment(new ErrorManager().getErrorById(6L).getDescription());
				}
			break;

			case "TransportNumber":
				route.setRouteCode(node.getTextContent());
			break;

			case "DateDelivery":
				route.setExpectedDate(AppUtil.convert1Cdate(node.getTextContent()));
			break;

			case "Car":
				WmCar car0 = new CarManager().parse1cFormat(node.getTextContent());
				car.setCarCode(car0.getCarCode());
				car.setCarMark(car0.getCarMark());
			break;
			default:
				System.out.println(node.getNodeName());
				break;
		}

	}



    ///////////////////////////////////////////////-------------------------------------------------

	/**@deprecated need use processShipmentData()
	 * */
	public boolean loadShipmentData()
	{
		PreOrderManager preOrdMgr = new PreOrderManager();
		WmPreOrder preOrder;
		WmRoute route;
		List<WmPreOrderPos> preOrderPosList = new ArrayList<WmPreOrderPos>();

		Node root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++){

			Node node = nodes.item(i);
			if(node != null && node.getNodeName() == "OrdersShipments")
			{
				preOrder =  preOrdMgr.initPreOrder();
				route = new WmRoute();

				NodeList nodes0 = node.getChildNodes();
				for(int k = 0; k <= nodes0.getLength(); k++)
				{
					Node node0 = nodes0.item(k);
					if(node0 != null && node0.getNodeType() != Node.TEXT_NODE)
					{
						createPreOrderDoc(node0, preOrder, preOrderPosList, route);
					}
				}

				if(route.getRouteCode() != null){
					try{
						System.out.println("144.XmlDoc.."+route.getRouteCode());
						WmRoute route0 = new DeliveryManager().getByCode(route.getRouteCode());
						if(route0 instanceof WmRoute)
							route.setRouteId(route0.getRouteId());
					}catch(Exception e){
						e.printStackTrace();
					}

					if(route.getRouteId() == null) route.setRouteId(new DeliveryManager().getNextRouteId());
					HibernateUtil.saveOrUpdate(route);
				}

				if(preOrder != null){
					PreOrderPosManager preOrdPosMgr = new PreOrderPosManager();

					if(new PreOrderManager().getPreOrderByCode(preOrder.getOrderCode(), false)==null){
						if(route.getRouteId() != null) preOrder.setRouteId(route.getRouteId());
						preOrdMgr.saveOrUpdatePreOrder(preOrder);
					}

				/* Записывать строки */
					for(WmPreOrderPos preOrderPos0 : preOrderPosList){
						if(new PreOrderManager().getPreOrderById(preOrderPos0.getOrderId())!= null)
							if(preOrdPosMgr.getPreOrderPosByArticleQState(preOrder.getOrderId(), preOrderPos0.getArticleId(), preOrderPos0.getQualityStateId())==null)
								preOrdPosMgr.addOrUpdatePreOrderPos(preOrderPos0);
					}

				}


			}
		}
		return true;
	}

	private void getNomenclatureElementList(Node node, Nomenclature nomenclature){
		NodeList chldNodeList = null;
		NodeList subNodes = node.getChildNodes();
		for(int i =0; i < subNodes.getLength(); i++){
			if(subNodes.item(i).getNodeName().equals("SkladskoySostav")){
				Element element = new Element();
				chldNodeList = subNodes.item(i).getChildNodes();
				for(int k=0; k < chldNodeList.getLength(); k++){
					if(!chldNodeList.item(k).getNodeName().equals("#text")){
						//System.out.println(chldNodeList.item(k).getTextContent()+" <-- "+chldNodeList.item(k).getNodeName());
					    switch(chldNodeList.item(k).getNodeName()){
					    case "Code": element.setCode(chldNodeList.item(k).getTextContent()); break;
					    case "Description" : element.setDescription(chldNodeList.item(k).getTextContent()); break;
					    case "Quantity" : element.setQuantity(Integer.decode(chldNodeList.item(k).getTextContent())); break;
					    }
					//IR_18_03_30...
					}
				}
				//System.out.println("Element ----->"+element.toString());
				nomenclature.getElementList().add(element);
			}
		}

	}


	private Map<String, String> getSubNode(Node parentNode){
		Map<String, String> map = new HashMap<String, String>();
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

	private void createPreOrderDoc(Node node, WmPreOrder preOrder, List<WmPreOrderPos> preOrderPosList, WmRoute route){

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

		case "Car":
			System.out.println("Car: "+node.getTextContent());
			if(!node.getTextContent().isEmpty())
				try {
					WmCar car =  new CarManager().saveCar(new CarManager().parse1cFormat(node.getTextContent()));

					if(route == null) route = new WmRoute();
					route.setCarId(car.getCarId());

					System.out.println("mark : ["+car.getCarMark() + "]  code : ["+car.getCarCode()+"]");
				} catch (DOMException e) {
					e.printStackTrace();
				}
			break;

		case "TransportNumber":
			System.out.println("TransportNumber: "+node.getTextContent());
			if(!node.getTextContent().isEmpty()){
				route.setRouteCode(node.getTextContent());
				route.setStatusId(1L);
				System.out.println("Status: "+route.getStatusId());
			}
			break;

		case "DateDelivery":
			System.out.println("DateDelivery: "+node.getTextContent());
			if(!node.getTextContent().isEmpty())
				route.setExpectedDate(AppUtil.convert1Cdate(node.getTextContent())); //03.11.2017 0:00:00
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

					 		WmArticle article = artCtrl.getArticleByCode(entry.getValue());
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

		}

	}

	/*
	private void createPreOrderDoc(Node node, WmPreOrder preOrder, List<WmPreOrderPos> preOrderPosList, WmRoute route){

		// Создавая документ и строки проверять, что этого еще нет в БД!!!
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

		case "Car":
			System.out.println("Car: "+node.getTextContent());
			if(!node.getTextContent().isEmpty())
				try {
					WmCar car =  new CarManager().saveCar(new CarManager().parse1cFormat(node.getTextContent()));

					if(route == null) route = new WmRoute();
					route.setCarId(car.getCarId());

					System.out.println("mark : ["+car.getCarMark() + "]  code : ["+car.getCarCode()+"]");
				} catch (DOMException e) {
					e.printStackTrace();
				}
			break;

		case "TransportNumber":
			System.out.println("TransportNumber: "+node.getTextContent());
			if(!node.getTextContent().isEmpty()){
				route.setRouteCode(node.getTextContent());
				route.setStatusId(1L);
				System.out.println("Status: "+route.getStatusId());
			}
			break;

		case "DateDelivery":
			System.out.println("DateDelivery: "+node.getTextContent());
			if(!node.getTextContent().isEmpty())
				route.setExpectedDate(AppUtil.convert1Cdate(node.getTextContent())); //03.11.2017 0:00:00
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



						System.out.println("advId : "+preOrder.getOrderCode()+" ::: "+entry.getKey()+": "+entry.getValue());
					 	if(entry.getKey().contains("Code")){
					 		ArticleController artCtrl = new ArticleController();

					 		WmArticle article = artCtrl.getArticleByCode(entry.getValue());
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

		}

	}
	*/

	@SuppressWarnings("unused")
	private void saveToFile(String xML) throws IOException
	{
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("webServiceXml_"+new Date().getTime()+".xml"), "Cp1251"));
		printWriter.print(xML);
		printWriter.close();

		/*
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		File file = new File("webServiceXml_"+new Date().getTime()+".xml");

			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(xML.toString("Cp1251"));

			bufferedWriter.close();
			fileWriter.close();
			System.out.println(file.getAbsolutePath());
			*/
	}


	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public boolean processMovementData() throws DOMException, WrongParamData
	{
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		for(int i=0; i<=nodes.getLength(); i++)
		{
		 	if(nodes.item(i) != null && nodes.item(i).getNodeName() == "OrdersMovements")
					if(parseDataBlock(nodes.item(i).getChildNodes(), true)==false) return false;
		}
		return true;
	}
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
