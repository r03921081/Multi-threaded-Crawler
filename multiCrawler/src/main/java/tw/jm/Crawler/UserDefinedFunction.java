package tw.jm.Crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.jm.PTT.PttArticle;
import tw.jm.PTT.PttParser;

public class UserDefinedFunction {
	
	private static final Logger logger = LogManager.getLogger("Crawler");
	
	public static final String doneListPath = "doneList";
	
	public static final String websitesPath = "websites";
	public static final Integer popularity = 50;
	public static final Integer needPages = 10;
	
//	An "article" is an object that contains all the information about each article.
	public void crawlFunction(Article article) {
		logger.info(article.toString());
		article.saveToFile();
	}
	
	
//	Parse one article.
	public void crawlPTTArticle() {
		String url = "https://www.ptt.cc/bbs/joke/M.1552840011.A.851.html";
		
		PttParser parser = new PttParser();
		PttArticle article = parser.parseArticle(url);
		logger.info(article.toString());
	}
}
