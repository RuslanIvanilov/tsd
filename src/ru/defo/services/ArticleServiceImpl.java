package ru.defo.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import ru.defo.managers.ArticleManager;
import ru.defo.model.ArticleService;
import ru.defo.model.WmArticle;

public class ArticleServiceImpl implements ArticleService {

	private static Map<Long, WmArticle> articles;

	public ArticleServiceImpl(){
		if(articles == null)
			articles = new HashMap<Long, WmArticle>();
	}

	@Override
	public boolean addArticle(WmArticle article) {
		if(articles.get(article.getArticleId()) != null) return false;

		articles.put(article.getArticleId(), article);
		return true;
	}

	@Override
	public boolean deleteArticle(Long articleid) {
		if(articles.get(articleid) == null) return false;
		articles.remove(articleid);
		return true;
	}

	@Override
	public WmArticle getArticle(Long articleid) {
		return articles.get(articleid);
	}

	@Override
	public WmArticle[] getArticles() {
		Set<Long> ids = articles.keySet();
		WmArticle[] a = new WmArticle[ids.size()];
		int i=0;
		for(Long articleId : ids){
			a[i] = articles.get(articleId);
			i++;
		}

		return a;
	}

	public String getArticlesXML(){
		/*
		List<WmArticle> articles = null;
		try{
		JAXBContext jaxbContext = JAXBContext.newInstance(WmArticle.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			articles = new ArticleManager().getArticleList();
			for(WmArticle article : articles){
				jaxbMarshaller.marshal(article, System.out);
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		*/

		try {
			JAXBContext context = JAXBContext.newInstance(WmArticle.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			WmArticle article = new ArticleManager().getArticleByCode("8#22603+00");

			System.out.println(article.getDescription());

			JAXBElement<WmArticle> jaxbElement = new JAXBElement<WmArticle>(new QName("article"), WmArticle.class,article);
			marshaller.marshal(jaxbElement, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return "";
	}


}
