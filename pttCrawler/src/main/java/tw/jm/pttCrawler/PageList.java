package tw.jm.pttCrawler;

import java.util.HashMap;
import java.util.Map;

public class PageList {

	private static PageList instance = null;

	private PageList() {
	}

	public static PageList getInstance() {
		if (instance == null) {
			synchronized (PageList.class) {
				if (instance == null) {
					instance = new PageList();
				}
			}
		}
		return instance;
	}

	private Map<String, Integer> latePages = new HashMap<>();

	public Map<String, Integer> getLatePages() {
		return latePages;
	}

	public void setLatePages(Map<String, Integer> latePages) {
		this.latePages = latePages;
	}

	public Integer getBoardLatePages(String board) {
		return latePages.get(board);
	}

	public void setBoardLatePages(String board, Integer page) {
		this.latePages.put(board, page);
	}

	public Integer getPage(String board) {
		return this.latePages.get(board);
	}

}

