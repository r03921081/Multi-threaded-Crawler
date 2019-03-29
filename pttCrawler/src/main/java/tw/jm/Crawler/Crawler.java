package tw.jm.Crawler;

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
			try {
				
				Thread.sleep((int) (Math.random() * 1000));
				
				Task task = taskList.getTask();
				
				Processor processor = Configuration.getInstance().setCrawler(task);
				processor.execute();
				
			} catch (InterruptedException e) {
				logger.warn(Thread.currentThread().getName() + " is interrupted.");
				break;
			}
		}
	}
	
}
