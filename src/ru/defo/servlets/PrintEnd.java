package ru.defo.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket; 
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.defo.controllers.ArticleController;
import ru.defo.controllers.BarcodeController;
import ru.defo.controllers.EquipmentController;
import ru.defo.model.WmBarcode;
import ru.defo.model.WmEquipment;
import ru.defo.util.ErrorMessage;


/**
 * Servlet implementation class PrintStart
 */
@WebServlet("/PrintEnd")
public class PrintEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

		System.out.println("/PrintEnd");

		if(request.getParameter("enteredqty") == null && session.getAttribute("quantity")==null){
			request.getRequestDispatcher("enter_qty_labels.jsp").forward(request, response);
			return;
		} else {
			if(request.getParameter("enteredqty") != null)
			   session.setAttribute("quantity", request.getParameter("enteredqty"));
		}

		//System.out.println("enteredqty : "+request.getParameter("enteredqty")+" :: "+session.getAttribute("quantity"));

		if(request.getParameter("printer_ip_text") == null){
		  session.setAttribute("backpage","PrinterIpRequest");
	   	  request.getRequestDispatcher("printer_ip_request.jsp").forward(request, response);
		  return;
		}

		//Проверка, что  штрих-код содержит IP-адрес принтера
		WmEquipment equipment = new EquipmentController().getEquipmentByIp(ru.defo.util.Bc.symbols(request.getParameter("printer_ip_text")));
		if(equipment == null || equipment.getEquipmentTypeId() != 1){
			session.setAttribute("message", ErrorMessage.WRONG_PRINTER_IP
					.message(new ArrayList<String>(Arrays.asList(ru.defo.util.Bc.symbols(request.getParameter("printer_ip_text"))))));
			session.setAttribute("action", "PrintEnd");
			request.getRequestDispatcher("errorn.jsp").forward(request, response);
			return;
		}

		addBCtoArticle(session);

		System.out.println("Печать\n"+"артикул : "+session.getAttribute("articlecode")+"\nbarcode : "+session.getAttribute("barcode")+
				"\nDescription : "+session.getAttribute("articlename")+"\nqty : "+session.getAttribute("quantity")+
				"\n--> printer : "+request.getParameter("printer_ip_text"));

		for(int i=0; i<Integer.decode(String.valueOf(session.getAttribute("quantity")));i++){

			try(Socket s = new Socket(equipment.getIpAddressCode(), 6101)){
				OutputStream out = s.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(out, "cp1251");
				PrintWriter writer = new PrintWriter(osw, true);
			      String ZPLString=
			    		  "^XA"+
			    		 // "^FO80,25^A@N,40,60,E:TT0003M_.FNT"+
			    		 "^FO80,30^A@N,40,60,E:TT0003M_.FNT"+
			    	      "^FD"+session.getAttribute("articlecode").toString()+"^FS"+
			    		  //"^FB400,6,10,C,0"+
			    		  "^FB400,5,10,C,0"+
			              "^CI33"+
			    		  "^FO10,60^A@N,30,30,E:TT0003M_.FNT"+
			    		  "^FD"+session.getAttribute("articlename").toString()+"^FS"+
			    		  "^FO50,220^BY2"+
			    		  "^BCN,100,Y,N,N"+
			    		  "^FD"+session.getAttribute("barcode").toString()+"^FS"+
			    		  "^XZ";

			        //writer.println(new String(ZPLString.getBytes(Charset.forName("cp1251")) ));
			        writer. println(ZPLString);

			        //System.out.println("ZPLString \n[\n"+ZPLString+"\n]");

			        /*session.setAttribute("message", "ZPLString \n[\n"+ZPLString+"\n]");
					session.setAttribute("action", "PrintEnd");
					request.getRequestDispatcher("errorn.jsp").forward(request, response);*/

			        writer.flush();
				} catch(IOException e){
					//e.printStackTrace();
					session.setAttribute("message", ErrorMessage.PRINTER_NOT_AVAILABLE
							.message(new ArrayList<String>(Arrays.asList(ru.defo.util.Bc.symbols(request.getParameter("printer_ip_text"))))));
					session.setAttribute("action", "PrintEnd");
					request.getRequestDispatcher("errorn.jsp").forward(request, response);
				}
		}

		if(session.getAttribute("fromadvice")!= null){
			session.setAttribute("fromadvice", null);
			request.getRequestDispatcher("AdviceUnit").forward(request, response);
			return;
		}

		request.getRequestDispatcher("PrintStart").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void addBCtoArticle(HttpSession session){
		/* Если штрих-кода еще нет в БД, то добавить в табл. WmBarcode */
		WmBarcode bc = new ArticleController().getBarCode(String.valueOf(session.getAttribute("barcode")));

		if(bc == null){
		  BarcodeController bcCtrl = new BarcodeController();
		  bcCtrl.addBarcode(String.valueOf(session.getAttribute("barcode")).trim(), String.valueOf(session.getAttribute("articleid")), String.valueOf(session.getAttribute("skuid")), String.valueOf(session.getAttribute("userid")));
		}

	}

}
