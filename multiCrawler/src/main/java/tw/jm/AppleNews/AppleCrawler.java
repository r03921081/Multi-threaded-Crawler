package tw.jm.AppleNews;

import tw.jm.Crawler.Processor;
import tw.jm.Crawler.Task;
import tw.jm.Crawler.UserDefinedFunction;

public class AppleCrawler implements Processor {

	Task task;

	public AppleCrawler(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void execute() {
		
		UserDefinedFunction u = new UserDefinedFunction();
		String url = task.getUrl();

		AppleParser parser = new AppleParser();
		AppleArticle article = parser.parseArticle(url);
		u.crawlFunction(article);
		
	}

}
