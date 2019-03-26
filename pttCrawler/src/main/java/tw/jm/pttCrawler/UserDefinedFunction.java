package tw.jm.pttCrawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.jm.pttCrawler.Article;

public class UserDefinedFunction {
	
	private static final Logger logger = LogManager.getLogger("Crawler");
	
	public static final String doneListPath = "config\\doneList";
	public static final String pageListPath = "config\\pageList";
	public static final String pttBoardPath = "config\\pttBoard";

	public static final Integer popularity = 50;
	public static final Integer needPages = 10;
	
//	An "article" is an object that contains all the information about each article.
	public void crawlFunction(Article article) {
		logger.info(article.toString());
	}
	
//	Parser one article.
	public void crawlArticle() {
		String url = "https://www.ptt.cc/bbs/joke/M.1552840011.A.851.html";
		
		Parser parser = new Parser();
		Article article = parser.parseArticle(url);
		logger.info(article.toString());
	}
}
