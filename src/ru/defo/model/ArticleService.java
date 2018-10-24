package ru.defo.model;

public interface ArticleService {
     public boolean addArticle(WmArticle article);
     public boolean deleteArticle(Long articleid);
     public WmArticle getArticle(Long articleid);
     public WmArticle[] getArticles();
     public String getArticlesXML();
}
