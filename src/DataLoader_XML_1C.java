import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.defo.controllers.ArticleController;
import ru.defo.util.HibernateUtil;

public class DataLoader_XML_1C {
	/*
	private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().
                    configure("hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
	*/
	public static void main(String[] args) {
		//final Session session = getSession();

		try {
			ArticleController articleCtrl = new ArticleController();
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document document = documentBuilder.parse("Art4.xml");
			Node root = document.getDocumentElement();
			NodeList articles = root.getChildNodes();
			for (int i = 0; i < articles.getLength(); i++) {
				Node article = articles.item(i);
				if (article.getNodeType() != Node.TEXT_NODE) {

					System.out.println("\n---------------------------------------\n"+article.getNodeName()
							+ "\n��� : "+ article.getAttributes().getNamedItem("���").getNodeValue()
							+ "\n������� : "+ article.getAttributes().getNamedItem("�������").getNodeValue()
							+ "\n������������ : "+ article.getAttributes().getNamedItem("������������").getNodeValue()
							);
					Long parentArticleId = articleCtrl.createArticleSku(article.getAttributes().getNamedItem("���").getNodeValue(), article.getAttributes().getNamedItem("������������").getNodeValue(), article.getAttributes().getNamedItem("�������").getNodeValue(), 1L, 1L, null, "A", true, "");


					NodeList articleDetails = article.getChildNodes();
					for(int j=0; j<articleDetails.getLength(); j++){
						Node articleDetail = articleDetails.item(j);
						if(articleDetail.getNodeType() != Node.TEXT_NODE){
						   System.out.println(":::::"+articleDetail.getNodeName()+" ->\n��� : "+articleDetail.getAttributes().getNamedItem("���").getNodeValue()
								   +"\n������������ : "+articleDetail.getAttributes().getNamedItem("������������").getNodeValue()
								   +"\n�������� : "+articleDetail.getAttributes().getNamedItem("��������").getNodeValue()
								   +"\n������� ��������� : "+articleDetail.getParentNode().getAttributes().getNamedItem("���").getNodeValue()
								   );

						   articleCtrl.createArticleSku(articleDetail.getAttributes().getNamedItem("���").getNodeValue().trim(), articleDetail.getAttributes().getNamedItem("������������").getNodeValue(), null, 1L, 1L, parentArticleId, "A", true, articleDetail.getAttributes().getNamedItem("��������").getNodeValue().trim());

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
           // ourSessionFactory.close();
			HibernateUtil.closeAll();
        }


	}

}
