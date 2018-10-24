package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import ru.defo.controllers.XmlDocController;
import ru.defo.util.WrongParamData;

public class WebServiceShptLoader {

	@Test
	public void test() throws IOException, DOMException, WrongParamData, ParserConfigurationException, SAXException {

		try{
			List<String> lines = Files.readAllLines(new File("D:\\test_shpt_data3.xml").toPath());

			StringBuilder sb = new StringBuilder();
			for(String str : lines){
				sb.append(str);
			}

			new XmlDocController(sb.toString()).processShipmentData(true);

		} catch(FileNotFoundException e){
			System.out.println("file not found!");
		}


	}

}
