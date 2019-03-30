package tw.jm.AppleNews;

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

public class AppleDispatcher implements Processor {

	private static final Logger logger = LogManager.getLogger("Crawler");

	private TaskList taskList;
	private String board;
	private Integer currentPage;

	public AppleDispatcher(TaskList taskList, String board, Integer currentPage) {
		super();
		this.taskList = taskList;
		this.board = board;
		this.currentPage = currentPage;
	}

	@Override
	public void execute() {
		String url = "https://tw.appledaily.com/new/realtime/" + currentPage;
		Map<String, String> doneMap = DoneList.getInstance().getDoneMap(board);

		try {
			Document doc = Jsoup.connect(url).get();
			Elements target = doc.select(".rtddd li");

			String cssboard = "." + board.split("-")[1];

			for (Element line : target) {
				String articleUrl = line.select(cssboard).select("a").attr("href");
				if (doneMap.containsKey(articleUrl) == false && articleUrl.length() > 0) {
					try {
						taskList.setTask(createTask(line, board));
					} catch (InterruptedException e) {
						logger.error("APPLE Dispatcher", e);
					}
				}
			}

		} catch (IOException e) {
			logger.error("Dispatcher Connection Exception", e);
		}

	}

	public Task createTask(Element line, String board) {
		Task task = new Task();
		String cssboard = "." + board.split("-")[1];
		String url = line.select(cssboard).select("a").attr("href");
		String date = line.select(cssboard).select("time").text();

		task.setWebsite("APPLE");
		task.setBoard(board);
		task.setUrl(url);
		task.setArticleDate(date);
		DoneList.getInstance().setDoneArticle(board, url, date);
		logger.info("APPLE Dispatcher " + task);
		return task;
	}

}
