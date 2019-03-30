package tw.jm.PTT;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tw.jm.Crawler.DoneList;
import tw.jm.Crawler.Processor;
import tw.jm.Crawler.Task;
import tw.jm.Crawler.TaskList;

public class PttDispatcher implements Processor {

	private static final Logger logger = LogManager.getLogger("Crawler");

	private TaskList taskList;
	private String board;
	private Integer popularity;
	private Integer currentPage;

	public PttDispatcher(TaskList taskList, String board, Integer popularity, Integer currentPage) {
		super();
		this.taskList = taskList;
		this.board = board;
		this.popularity = popularity;
		this.currentPage = currentPage;
	}

	@Override
	public void execute() {

		String urlBoard = board.split("-")[1];
		String url = "https://www.ptt.cc/bbs/" + urlBoard + "/index" + currentPage + ".html";
		Map<String, String> doneMap = DoneList.getInstance().getDoneMap(board);

		try {
			Document doc = Jsoup.connect(url).cookie("over18", "1").get();
			Elements target = doc.select(".r-ent");

			for (Element line : target) {

				String articleUrl = line.select(".title a").attr("href");
				String articlePop = line.select(".nrec span").text();

				if (articlePop.length() > 0) {
					if (articlePop.equals("爆")) {
						try {
							if (doneMap.containsKey(articleUrl) == false && articleUrl.length() > 0) {
								taskList.setTask(createTask(line, board));
							}

						} catch (InterruptedException e) {
							logger.error("PTT Dispatcher", e);
						}
					}
					if (isNumber(articlePop)) {
						if (Integer.parseInt(articlePop) >= popularity) {
							try {
								if (doneMap.containsKey(articleUrl) == false && articleUrl.length() > 0) {
									taskList.setTask(createTask(line, board));
								}
							} catch (InterruptedException e) {
								logger.error("PTT Dispatcher", e);
							}
						}
					}
				}
			}

		} catch (IOException e) {
			logger.error("Dispatcher Connection Exception", e);
		}

	}

	public Task createTask(Element line, String board) {

		Task task = new Task();
		String url = line.select(".title a").attr("href");
		String date = line.select(".meta .date").text();

		task.setWebsite("PTT");
		task.setBoard(board);
		task.setUrl(url);
		task.setArticleDate(date);
		DoneList.getInstance().setDoneArticle(board, url, date);
		logger.info("PTT Dispatcher " + task);
		return task;
	}

	public static boolean isNumber(String input) {
		try {
			double d = Double.parseDouble(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
