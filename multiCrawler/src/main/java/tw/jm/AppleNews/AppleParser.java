package tw.jm.AppleNews;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			
			String myTime = list.select(".ndArticle_creat").text();
			Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}");
			Matcher matcher = pattern.matcher(myTime);
			if (matcher.find())
			{
				String dateInString = matcher.group(0);
			    
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.TAIWAN);
			    Date date = formatter.parse(dateInString);
			    article.setDate(date.toString());
			}
			
			article.setContent(list.select(".ndArticle_margin p").text());
			article.setUrl(url);

			List<String> keywords = new ArrayList<String>();
			for (String key : list.select(".ndgKeyword h3").text().split(" ")) {
				keywords.add(key.trim());
			}
			article.setKeywords(keywords);
			logger.info(article);
		} catch (IOException e) {
			logger.error("Parser Error", e);
		} catch (ParseException e) {
			logger.error("Parser Error", e);
		}
		return article;
	}
}
