package tw.jm.PTT;

public class PushMessage {
	
	private String push_userid;
	private String push_content;
	private String push_ipdatetime;
	private String push_tag;
	
	public String getPush_userid() {
		return push_userid;
	}
	public void setPush_userid(String push_userid) {
		this.push_userid = push_userid;
	}
	public String getPush_content() {
		return push_content;
	}
	public void setPush_content(String push_content) {
		this.push_content = push_content;
	}
	public String getPush_ipdatetime() {
		return push_ipdatetime;
	}
	public void setPush_ipdatetime(String push_ipdatetime) {
		this.push_ipdatetime = push_ipdatetime;
	}
	public String getPush_tag() {
		return push_tag;
	}
	public void setPush_tag(String push_tag) {
		this.push_tag = push_tag;
	}
	@Override
	public String toString() {
		return "PushMessage [push_userid=" + push_userid + ", push_content=" + push_content + ", push_ipdatetime="
				+ push_ipdatetime + ", push_tag=" + push_tag + "]";
	}
	
}
