package tw.jm.AppleNews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AppleParser {

private static final Logger logger = LogManager.getLogger("Crawler");
	
	public AppleArticle parseArticle(String url) {
		
		AppleArticle article = new AppleArticle();
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements list = doc.select(".ndArticle_leftColumn");
			
			article.setTitle(list.select("h1").text().trim());
			article.setView(list.select(".ndArticle_view").text());
			article.setDate(list.select(".ndArticle_creat").text().split(":")[1].trim());
			article.setImageURL(list.select(".ndAritcle_headPic img").first().attr("src"));
			article.setImageContent(list.select(".ndAritcle_headPic img").first().attr("alt").trim());
			article.setContent(list.select(".ndArticle_margin p").text());
			
			List<String> keywords = new ArrayList<String>();
			for(String key: list.select(".ndgKeyword h3").text().split(" ")) {
				keywords.add(key.trim());
			}			
			article.setKeywords(keywords);
		} catch (IOException e) {
			logger.error("Parser Error", e);
		}
		return article;
	}
}
