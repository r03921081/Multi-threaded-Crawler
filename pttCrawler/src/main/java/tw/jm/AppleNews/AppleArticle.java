package tw.jm.AppleNews;

import java.util.List;

public class AppleArticle {
	private String title;
	private String view;
	private String date;
	private String imageURL;
	private String imageContent;
	private String content;
	private String url;
	private List<String> keywords;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageContent() {
		return imageContent;
	}

	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "AppleArticle [title=" + title + ", view=" + view + ", date=" + date + ", imageURL=" + imageURL
				+ ", imageContent=" + imageContent + ", content=" + content + ", url=" + url + ", keywords=" + keywords
				+ "]";
	}

}
