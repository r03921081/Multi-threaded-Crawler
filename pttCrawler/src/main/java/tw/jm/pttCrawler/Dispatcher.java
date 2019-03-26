package tw.jm.pttCrawler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Dispatcher implements Runnable {
	
	private static final Logger logger = LogManager.getLogger("Crawler");

	private TaskList taskList;
	private String board;
	private Integer popularity;
	private CountDownLatch waitingPages;

	public Dispatcher(TaskList taskList, String board, Integer popularity, CountDownLatch waitingPages) {
		super();
		this.taskList = taskList;
		this.board = board;
		this.popularity = popularity;
		this.waitingPages = waitingPages;
	}

	@Override
	public void run() {

		while (true) {
			
			if(waitingPages.getCount() == 0) {				
				break;
			}
			
			Integer currentPage;
			try {
				currentPage = PageQueue.getInstance().getPage();
				logger.info(Thread.currentThread().getName() + " dispatch " + currentPage);
				String url = "https://www.ptt.cc/bbs/" + board + "/index" + currentPage + ".html";
				Map<String, String> doneMap = DoneList.getInstance().getDoneMap(board);

				try {
					Document doc = Jsoup.connect(url).cookie("over18", "1").get();
					Elements target = doc.select(".r-ent");

					for (Element line : target) {
						String articleDate = line.select(".meta .date").text();
						if (compareDate(articleDate)) {
							String articleUrl = line.select(".title a").attr("href");
							String articlePop = line.select(".nrec span").text();

							if (articlePop.length() > 0) {
								if (articlePop.equals("爆")) {
									try {
										if (doneMap.containsKey(articleUrl) == false && articleUrl.length() > 0) {
											taskList.setTask(createTask(line, board));
										}

									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								if (isNumber(articlePop)) {
									if (Integer.parseInt(articlePop) >= popularity) {
										try {
											if (doneMap.containsKey(articleUrl) == false && articleUrl.length() > 0) {
												taskList.setTask(createTask(line, board));
											}
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					}

				} catch (IOException e) {
					logger.error("Dispatcher Connection Exception", e);
				}
			} catch (InterruptedException e1) {
				logger.error("Dispatcher Exception", e1);
			} finally {
				waitingPages.countDown();
			}
		}
	}

	public static Task createTask(Element line, String board) {

		Task task = new Task();
		String url = line.select(".title a").attr("href");
		String date = line.select(".meta .date").text();

		task.setWebsite("PTT-" + board);
		task.setUrl(url);
		task.setArticleDate(date);
		DoneList.getInstance().setDoneArticle(board, url, date);
		return task;
	}

	public static boolean checkTask(Element line, String board) {
		String url = line.select(".title a").attr("href");

		if (DoneList.getInstance().getDoneMap(board).containsKey(url) == false) {
			return false;
		}
		return true;
	}

	public static boolean isNumber(String input) {
		try {
			double d = Double.parseDouble(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean compareDate(String articleDate) {

		int todayM = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int todayD = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		int articleM = Integer.parseInt(articleDate.split("/")[0]);
		int articleD = Integer.parseInt(articleDate.split("/")[1]);

		if (todayM == articleM && todayD - articleD == 1) {
			return true;
		} else if (todayM == articleM && todayD == articleD) {
			return true;
		} else if (todayM - articleM == 1 && todayD < articleD) {
			return true;
		} else {
			return false;
		}

	}

}
