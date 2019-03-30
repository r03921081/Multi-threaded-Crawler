package tw.jm.PTT;

import java.util.ArrayList;
import java.util.List;

import tw.jm.Crawler.Article;

public class PttArticle extends Article {
	
	private String author;
	private String title;
	private String board;
	private String content;
	private String date;
	private String ip;
	private String url;
	private List<PushMessage> pushList = new ArrayList<PushMessage>();
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<PushMessage> getPushList() {
		return pushList;
	}
	public void setPushList(PushMessage pushMessage) {
		this.pushList.add(pushMessage);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "PttArticle [author=" + author + ", title=" + title + ", board=" + board + ", content=" + content
				+ ", date=" + date + ", ip=" + ip + ", url=" + url + ", pushList=" + pushList + "]";
	}
	
}
