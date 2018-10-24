import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadXML1C {

	public static void main(String[] args) {

		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document document = documentBuilder.parse("Arts1C.xml");
			Node root = document.getDocumentElement();
			NodeList articles = root.getChildNodes();
			for (int i = 0; i < articles.getLength(); i++) {
				Node article = articles.item(i);
				if (article.getNodeType() != Node.TEXT_NODE) {
					System.out.println("--------\n");

					NodeList articleDetails = article.getChildNodes();
					for(int j=0; j<articleDetails.getLength(); j++){
						Node articleDetail = articleDetails.item(j);
						if(articleDetail.getNodeType() != Node.TEXT_NODE){
							if(!articleDetail.getNodeName().equals("Nomenclature")){
							System.out.println(articleDetail.getNodeName()+ " : " + articleDetail.getTextContent());
							} else {

								NodeList articleSubDetails = articleDetail.getChildNodes();
								for(int z = 0; z < articleSubDetails.getLength(); z++){
									Node articleSubDetail = articleSubDetails.item(z);
									if(articleSubDetail.getNodeType() != Node.TEXT_NODE){
										System.out.println(articleSubDetail.getNodeName()+" ::: "+articleSubDetail.getTextContent());
									}
								}

							}




						}
					}
				}
			}

		} catch (ParserConfigurationException ex) {
			ex.printStackTrace(System.out);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			// session.close();
			System.out.println("finally");
		}

	}




}
