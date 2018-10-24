package ru.defo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 

public class AppUtil {

	public static int getSkuCountMap(HashMap<Integer, String> map)
	{
		int sum = 0;

		if(map != null)
			for(Entry<Integer, String> entry : map.entrySet())
			{
				sum=sum+getValueDataList(entry.getValue(),":");
			}

		return sum;
	}

	public static int getValueDataList(String value, String delimiter){
		if(value == null) return 0;
   	    try{
   	    	return  Integer.decode(value.substring(value.indexOf(delimiter)+1, value.length()).trim());
   	    }catch(NumberFormatException e){
   	    	return 0;
   	    }

	}


	public static String getLocalDateTime(Timestamp ts)
	{
		return ts.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
	}

	public static Timestamp convert1Cdate(String date1C){
		try {
			return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date1C.substring(6,10)+"-"+date1C.substring(3,5)+"-"+date1C.substring(0,2)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Timestamp getTodayTamestamp(){
		return AppUtil.stringToTimestamp(AppUtil.getToday());
	}

	public static Timestamp getCurrentTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getToday() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currDateWoTime = null;
		try {
			currDateWoTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(currDateWoTime);
	}

	public static Timestamp stringToTimestamp(String dateFilter){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
		try {
			date = format.parse(dateFilter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * @param defaultValue (for example: AppUtil.getToday())
	 * {@value} correct Param from jsp
	 * */
	public static String getReqSessionAttribute(HttpServletRequest request, HttpSession session, String attributeName, String defaultValue, boolean asBc) {
		if(asBc){
			return request.getParameter(attributeName) == null ? (session.getAttribute(attributeName) == null
					? defaultValue : session.getAttribute(attributeName).toString())
					: ru.defo.util.Bc.symbols(request.getParameter(attributeName).trim());
		} else {
		return request.getParameter(attributeName) == null ? (session.getAttribute(attributeName) == null
				? defaultValue : session.getAttribute(attributeName).toString())
				: request.getParameter(attributeName).trim();
		}
	}

	public static Object getReqSessionAttribute(HttpServletRequest request, HttpSession session, String attributeName, Object defaultValue, boolean asBc) {
		if(asBc){
			return request.getParameter(attributeName) == null ? (session.getAttribute(attributeName) == null
					? defaultValue : session.getAttribute(attributeName).toString())
					: ru.defo.util.Bc.symbols(request.getParameter(attributeName));
		} else {
		return request.getParameter(attributeName) == null ? (session.getAttribute(attributeName) == null
				? defaultValue : session.getAttribute(attributeName).toString())
				: request.getParameter(attributeName);
		}
	}

	public static Timestamp getNextDay(String dateFilter){

		Timestamp taday = stringToTimestamp(dateFilter);
		Timestamp nextDay = null;

		if(taday != null)
			try{
				nextDay = new Timestamp(taday.getTime()+Long.valueOf(1*24*60*60*1000).longValue());
			} catch(Exception e){
				e.printStackTrace();
			}

		return nextDay;
	}

	/**
	 * @category : get package name only...for servlet
	 * */
	public static String getPackageName(Object t){
		return t.getClass().getPackage().toString().substring(t.getClass().getPackage().toString().indexOf("servlets")+9);
	}

	public static String getServletName(Object t){
		return t.getClass().getSimpleName();
	}

	public static Long strToLong(String param){
		try{
			return Long.valueOf(param);
		}catch(NumberFormatException e){
			return null;
		}

	}

}
