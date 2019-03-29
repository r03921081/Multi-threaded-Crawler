package tw.jm.Crawler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.jm.AnotherWebsite.AnotherCrawler;
import tw.jm.AnotherWebsite.AnotherDispatcher;
import tw.jm.AppleNews.AppleCrawler;
import tw.jm.AppleNews.AppleDispatcher;
import tw.jm.PTT.PttGetBoardLatestPage;
import tw.jm.PTT.PttCrawler;
import tw.jm.PTT.PttDispatcher;
import tw.jm.PTT.PttPageList;

public class Configuration {

	private static final Logger logger = LogManager.getLogger("Crawler");

	private static Configuration instance = null;

	private Configuration() {

	}

	public static Configuration getInstance() {
		if (instance == null) {
			synchronized (Configuration.class) {
				if (instance == null) {
					instance = new Configuration();
				}
			}
		}
		return instance;
	}

	private List<String> waitingTargets;
	private TaskList taskList = new TaskList(1000000);

	public List<String> getWaitingTargets() {
		return waitingTargets;
	}

	public void setWaitingTargets() {
		FileOperation f = new FileOperation();
		List<String> targets = f.getBoardConfig();
		this.waitingTargets = targets;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	public void initDoneList() {
		DoneList.getInstance().initDoneMap();
		logger.info("DoneList: " + DoneList.getInstance().getDoneList());
	}

	public void initDispatcher(String target) {
		if (target.contains("PTT")) {
			try {
				Thread getLastPage = new Thread(new PttGetBoardLatestPage(target));
				getLastPage.start();
				getLastPage.join();
				PageQueue.getInstance().setPage(
						PttPageList.getInstance().getPage(target) - UserDefinedFunction.needPages + 1,
						PttPageList.getInstance().getPage(target));
			} catch (InterruptedException e) {
				logger.error("Initial", e);
			}
		} else if (target.contains("APPLE")) {
			try {
				PageQueue.getInstance().setPage(1, UserDefinedFunction.needPages);
			} catch (InterruptedException e) {
				logger.error("Initial", e);
			}
		} else {
			try {
				PageQueue.getInstance().setPage(1, UserDefinedFunction.needPages);
			} catch (InterruptedException e) {
				logger.error("Initial", e);
			}
		}
		logger.info(PageQueue.getInstance().toString());
	}

	public Processor setDispatcher(TaskList taskList, String target, Integer currentPage) {
		if (target.contains("PTT")) {
			DoneList.getInstance().checkBoardInDoneMap(target);
			return new PttDispatcher(taskList, target, UserDefinedFunction.popularity, currentPage);
		} else if (target.contains("APPLE")) {
			DoneList.getInstance().checkBoardInDoneMap(target);
			return new AppleDispatcher(taskList, target, currentPage);
		} else {
			return new AnotherDispatcher();
		}
	}

	public Processor setCrawler(Task task) {
		if (task.getWebsite().equals("PTT")) {
			return new PttCrawler(task);
		} else if (task.getWebsite().equals("APPLE")) {
			return new AppleCrawler(task);
		} else {
			return new AnotherCrawler();
		}
	}

}
