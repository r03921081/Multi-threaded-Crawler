package tw.jm.Crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.jm.AppleNews.AppleArticle;
import tw.jm.PTT.PttArticle;
import tw.jm.PTT.PttParser;

public class UserDefinedFunction {
	
	private static final Logger logger = LogManager.getLogger("Crawler");
	
	public static final String doneListPath = "config\\doneList";
	public static final String pttBoardPath = "config\\pttBoard";

	public static final Integer popularity = 50;
	public static final Integer needPages = 10;
	
//	An "article" is an object that contains all the information about each article.
	public void crawlPTT(PttArticle article) {
		logger.info(article.toString());
	}
	
	public void crawlAPPLE(AppleArticle article) {
		logger.info(article.toString());
	}
	
//	Parse one article.
	public void crawlPTTArticle() {
		String url = "https://www.ptt.cc/bbs/joke/M.1552840011.A.851.html";
		
		PttParser parser = new PttParser();
		PttArticle article = parser.parseArticle(url);
		logger.info(article.toString());
	}
}
