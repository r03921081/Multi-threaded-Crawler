package tw.jm.Crawler;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger logger = LogManager.getLogger("Crawler");

	private static boolean canExecuteNextBoard = true;
	private static ExecutorService executor = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws InterruptedException {
		
		Configuration.getInstance().setWaitingTargets();
		Configuration.getInstance().initDoneList();
		
		TaskList taskList = Configuration.getInstance().getTaskList();
		Crawler crawler = new Crawler(taskList);
		executeThreads(crawler);

		for (String target : Configuration.getInstance().getWaitingTargets()) {
			while (canExecuteNextBoard != true) {
			}

			logger.info("--- Process " + target + " ---");
			canExecuteNextBoard = false;
			
			Configuration.getInstance().initDispatcher(target);
			CountDownLatch waitingPages = new CountDownLatch(UserDefinedFunction.needPages);
			Dispatcher dispatcher = new Dispatcher(taskList, target, waitingPages);
			executeThreads(dispatcher);

			while (waitingPages.getCount() > 0) {
			}
			canExecuteNextBoard = true;
			logger.info("--- Stop " + target + " ---");
		}

		TimeUnit.SECONDS.sleep(5);
		terminateExecutorService(taskList);
//		checkAllThreadsStatus();

	}

	public static void executeThreads(Runnable job) {
		for (int i = 1; i <= 5; i++) {
			executor.submit(job);
		}
	}

	public static void terminateExecutorService(TaskList taskList) {
		try {
			while (taskList.getTaskQueueSize() > 0) {
			}
			DoneList.getInstance().saveDoneList();

			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.warn("Tasks interrupted.");
		} finally {
			if (!executor.isTerminated()) {
				logger.warn("All threads are canceled.");
			}
			executor.shutdownNow();
		}
	}

	public static void checkAllThreadsStatus() {
		Set<Thread> threads = Thread.getAllStackTraces().keySet();

		for (Thread t : threads) {
			String name = t.getName();
			Thread.State state = t.getState();
			int priority = t.getPriority();
			String type = t.isDaemon() ? "Daemon" : "Normal";
			logger.info(name + "\t" + state + "\t" + priority + "\t" + type);
		}
	}

}
