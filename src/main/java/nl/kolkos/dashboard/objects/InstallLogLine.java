package nl.kolkos.dashboard.objects;

import java.util.Date;

public class InstallLogLine {
	private Date timestamp = new Date();
	private String object;
	private String title;
	private String details;
	
	public InstallLogLine() {}
	public InstallLogLine(String object, String title, String details) {
		this.setObject(object);
		this.setTitle(title);
		this.setDetails(details);
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return String.format("%s, object='%s', title='%s', details='%s'", 
				this.getTimestamp(),
				this.getObject(),
				this.getTitle(),
				this.getDetails());
	}
	
}
