package tw.jm.PTT;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PttParser {
	
	private static final Logger logger = LogManager.getLogger("Crawler");
	
	public PttArticle parseArticle(String url) {

		PttArticle article = new PttArticle();

		try {

			Document doc = Jsoup.connect(url).cookie("over18", "1").get();
			Elements mainContent = doc.select("#main-content");

			article.setAuthor(mainContent.select(".article-metaline .article-meta-value").get(0).text().split(" ")[0]);
			article.setBoard(mainContent.select(".article-metaline-right .article-meta-value").get(0).text());
			article.setTitle(mainContent.select(".article-metaline .article-meta-value").get(1).text());
			article.setDate(mainContent.select(".article-metaline .article-meta-value").get(2).text());

			String ipContent = mainContent.select(".f2:contains(※ 發信站:)").text();
			Pattern pattern = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
			Matcher matcher = pattern.matcher(ipContent);

			if (matcher.find()) {
				article.setIp(matcher.group());
			} else {
				article.setIp("NONE");
			}

			article.setUrl(mainContent.select(".f2 a").attr("href"));

			for (int i = 0; i < mainContent.select(".push").size(); i++) {
				PushMessage pm = new PushMessage();
				Element reply = mainContent.select(".push").get(i);

				pm.setPush_userid(reply.select(".push-userid").text());
				pm.setPush_tag(reply.select(".push-tag").text().trim());
				pm.setPush_ipdatetime(reply.select(".push-ipdatetime").text());
				pm.setPush_content(reply.select(".push-content").text().trim());

				article.setPushList(pm);
			}

			Element tmp = mainContent.select("div").remove().get(0);
			String[] contentObject = tmp.text().split("��");
			Integer contentSize = contentObject.length;

			String articleContent = "";

			for (int i = 0; i < contentSize - 2; i++) {
				articleContent += contentObject[i];
			}
			article.setContent(articleContent);
		} catch (IOException e) {
			logger.error("Parser Error", e);
		}
		return article;
	}
}
