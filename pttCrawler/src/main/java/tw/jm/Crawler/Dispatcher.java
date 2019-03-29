package tw.jm.Crawler;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dispatcher implements Runnable  {
	
	private static final Logger logger = LogManager.getLogger("Crawler");

	private TaskList taskList;
	private String target;
	private CountDownLatch waitingPages;
	
	public Dispatcher(TaskList taskList, String target, CountDownLatch waitingPages) {
		super();
		this.taskList = taskList;
		this.target = target;
		this.waitingPages = waitingPages;
	}
	
	public CountDownLatch getWaitingPages() {
		return waitingPages;
	}

	@Override
	public void run() {

		while (true) {
			
			if(waitingPages.getCount() == 0) {				
				break;
			}
			
			try {
				Integer currentPage = PageQueue.getInstance().getPage();
				logger.info(Thread.currentThread().getName() + " dispatch " + currentPage);
				
				Processor processor = Configuration.getInstance().setDispatcher(taskList, target, currentPage);
				processor.execute();
				
			} catch (InterruptedException e1) {
				logger.error("Dispatcher Exception", e1);
			} finally {
				waitingPages.countDown();
			}
		}
	}

	

}
