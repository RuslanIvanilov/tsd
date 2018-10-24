package test.article;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ru.defo.managers.ArticleManager;
import ru.defo.managers.BarcodeManager;
import ru.defo.model.WmBarcode;

public class BarCodeArticle {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		/*String[] bcList = {"7#88147+00", "7#90067+00", "D7#88147+00","A7#88147+00", "DEU00000001", "EU00000001"};

		for(String bcStr : bcList){
			WmBarcode bc  = new BarcodeManager().gerBarCode(bcStr);
			if(bc != null)
				System.out.println(bc.getBarCode()+" / "+bc.getArticleId());
		}*/
		int i=0;
		List<WmBarcode> bcList = new ArticleManager().getBarcodeListAll();
		for(WmBarcode bc : bcList){
			WmBarcode bc0  = new BarcodeManager().gerBarCode(bc.getBarCode());
			if(bc0 == null)
			{
				fail("wrong bc!");
			} /*else {   //System.out.println(bc0.getBarCode()+" / "+bc0.getArticleId());
				i++;
				System.out.println(i+"\t."+bc.getBarCode()+" articleId: "+bc.getArticleId());
			}*/
		}
		assertTrue(bcList != null);

	}

}
