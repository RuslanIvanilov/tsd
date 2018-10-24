package ru.defo.workers;

import javax.servlet.http.HttpSession;

public class ArticleLoadWorker implements Runnable{

	HttpSession session;

	@Override
	public void run(){
		try {
			if(session.getAttribute("startloader") != null && session.getAttribute("startloader").toString().equals("1")){
				System.out.println("----->");
				Thread.sleep(50000);
				System.out.println("My Thread after sleep -->|");
				session.setAttribute("startloader",null);
			}
		} catch (InterruptedException e) {
			System.out.println("InterruptedException !!!");
			e.printStackTrace();
		}


	}

	public ArticleLoadWorker(HttpSession session){
		this.session = session;
	}




}
