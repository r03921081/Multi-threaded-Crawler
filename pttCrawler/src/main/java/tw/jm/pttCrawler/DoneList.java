package tw.jm.pttCrawler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoneList {

	private static DoneList instance = null;

	private DoneList() {
	}

	public static DoneList getInstance() {
		if (instance == null) {
			synchronized (DoneList.class) {
				if (instance == null) {
					instance = new DoneList();
				}
			}
		}
		return instance;
	}

	private Map<String, Map<String, String>> doneMap = new HashMap<>();

	public void initDoneMap() {
		FileOperation f = new FileOperation();
		f.getDoneListConfig();
	}

	public void checkBoardInDoneMap(String board) {
		if (this.doneMap.containsKey(board) == false) {
			this.doneMap.put(board, new HashMap<>());
		}
	}

	public void setDoneArticle(String board, String articleUrl, String articleDate) {
		this.doneMap.get(board).put(articleUrl, articleDate);
	}
	
	public Map<String, Map<String, String>> getDoneList() {
		return doneMap;
	}

	public Map<String, String> getDoneMap(String board) {
		return doneMap.get(board);
	}

	public void saveDoneList() {
		FileOperation f = new FileOperation();
		f.setDoneListConfig();
	}

	public static boolean compareDate(String articleDate) {

		int todayM = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int todayD = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

		int articleM = Integer.parseInt(articleDate.split("/")[0]);
		int articleD = Integer.parseInt(articleDate.split("/")[1]);

		if (todayM == articleM && todayD == articleD) {
			return false;
		} else if (todayM == articleM && todayD - articleD == 1) {
			return false;
		} else {
			return true;
		}
	}
	
//	Future improvement
	public void cleanDoneList() {

		for (Map.Entry<String, Map<String, String>> boardEntrySet : doneMap.entrySet()) {
			for (Map.Entry<String, String> entry : boardEntrySet.getValue().entrySet()) {
				if (!compareDate(entry.getValue())) {
					doneMap.remove(entry.getKey());
				}
			}
		}
	}

}
