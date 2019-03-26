package tw.jm.pttCrawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetBoardLatestPage implements Runnable {
	
	private static final Logger logger = LogManager.getLogger("Crawler");

	private String board;

	public GetBoardLatestPage(String board) {
		this.board = board;
	}

	@Override
	public void run() {
		String url = "https://www.ptt.cc/bbs/" + board + "/index.html";

		Document doc;
		Integer latestPage = 0;

		try {
			doc = Jsoup.connect(url).cookie("over18", "1").get();
			Elements pagingController = doc.select(".btn-group-paging a");
			Elements lastestPage = pagingController.next();
			String latestUrl = lastestPage.attr("href");
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(latestUrl);

			if (matcher.find()) {
				latestPage = Integer.parseInt(matcher.group(0)) + 1;
				PageList.getInstance().setBoardLatePages(board, latestPage);
				logger.info(board + "'s latestPage is " + latestPage);
			}

		} catch (IOException e) {
			logger.error("Get Latest Page Error", e);;
		}
	}

}
