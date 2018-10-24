import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Zpl {

	public static void main(String[] args) throws IOException {
		System.out.println("Готовлю к печати");
		try(Socket s = new Socket("192.168.22.96", 6101)){
		OutputStream out = s.getOutputStream();
		PrintWriter writer = new PrintWriter(out, true);
	      String ZPLString=   	    		  
	    		  "^XA"+
	    		  "^FB400,6,10,C,0"+
	              "^CI33"+
	    		  "^FO50,200^A@N,30,30,E:TT0003M_.FNT"+	              
	    		  "^FDМалим Ака^FS"+
	    		  "^FO50,250^BY2"+
	    		  "^BCN,100,N,N,N"+
	    		  "^FD-1703330383^FS"+
	    		  "^XZ";
	                
	        writer.println(ZPLString);
	        writer.flush();
	        System.out.println("Отправил на печать");
		}
	}

}
