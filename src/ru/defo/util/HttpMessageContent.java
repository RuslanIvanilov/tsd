package ru.defo.util;

public class HttpMessageContent {

	String messageText;
	String actionName;
	Object obj;
	String jspUrl;

	/**
	 * @param actionName = Class.SimpleName
	 * @deprecated
	 * */
	public HttpMessageContent(String messageText, String actionName, String jspUrl){
		this.messageText = messageText;
		this.actionName = actionName;
		this.jspUrl = jspUrl;
	}

	public HttpMessageContent(String messageText, Object obj, String jspUrl){
		this.messageText = messageText;
		this.obj = obj;
		this.jspUrl = jspUrl;
	}
	
	public String getMessageText() {
		return messageText;
	}

	public String getActionName() {
		return actionName;
	}

	public String getJspUrl() {
		return jspUrl;
	}

	public String getClassName(){
		return obj.getClass().getSimpleName();
	}

}
