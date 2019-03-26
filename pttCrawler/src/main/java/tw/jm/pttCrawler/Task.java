package tw.jm.pttCrawler;

public class Task {
	
	private String website;
	private String url;
	private String articleDate;
	
	@Override
	public String toString() {
		return "Task [website=" + website + ", url=" + url + ", articleDate=" + articleDate + "]";
	}
	
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}
	
}
