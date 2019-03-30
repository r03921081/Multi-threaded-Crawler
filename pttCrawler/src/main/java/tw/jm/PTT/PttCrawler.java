package tw.jm.PTT;

import tw.jm.Crawler.Processor;
import tw.jm.Crawler.Task;
import tw.jm.Crawler.UserDefinedFunction;

public class PttCrawler implements Processor {
	
	Task task;

	public PttCrawler(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void execute() {
		
		UserDefinedFunction u = new UserDefinedFunction();
		String url = "https://www.ptt.cc/" + task.getUrl();

		PttParser parser = new PttParser();
		PttArticle article = parser.parseArticle(url);
		u.crawlFunction(article);
		
	}

}
