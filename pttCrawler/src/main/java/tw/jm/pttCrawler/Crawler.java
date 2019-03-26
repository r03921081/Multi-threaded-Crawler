package tw.jm.pttCrawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Crawler implements Runnable {
	
	private static final Logger logger = LogManager.getLogger("Crawler");

	private TaskList taskList;

	public Crawler(TaskList taskList) {
		super();
		this.taskList = taskList;
	}

	@Override
	public void run() {

		while (true) {			
			Task task;
			try {
				
				Thread.sleep((int) (Math.random() * 1000));
				
				task = taskList.getTask();
				UserDefinedFunction u = new UserDefinedFunction();
				String url = "https://www.ptt.cc/" + task.getUrl();
				
				Parser parser = new Parser();
				Article article = parser.parseArticle(url);								
				u.crawlFunction(article);
				
			} catch (InterruptedException e) {
				logger.warn(Thread.currentThread().getName() + " is interrupted.");
				break;
			}
		}
	}
}
