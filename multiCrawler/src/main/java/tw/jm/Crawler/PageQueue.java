package tw.jm.Crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PageQueue {
	
	private static PageQueue instance = null;
	
	private PageQueue() {
	}

	public static PageQueue getInstance() {
		if (instance == null) {
			synchronized (PageQueue.class) {
				if (instance == null) {
					instance = new PageQueue();
				}
			}
		}
		return instance;
	}
	
	private BlockingQueue<Integer> pageQueue = new LinkedBlockingQueue<Integer>();

	public Integer getPage() throws InterruptedException {
		if(pageQueue.isEmpty()) {
			wait();
		}
		Integer page = pageQueue.take();
		return page;
	}

	public void setPage(Integer startPage, Integer endPage) throws InterruptedException {
		for (int i = startPage; i <= endPage; i++) {
			pageQueue.put(i);
		}
	}

	@Override
	public String toString() {
		return "PageQueue [pageQueue=" + pageQueue + "]";
	}

}
