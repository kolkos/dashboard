package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Panel {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "row_id")
	private Row row;
	
	private String title;
	private int position;
	private int width;
	private String safeName;
	
	private boolean showTitle = true;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "content_type_id")
	private ContentType contentType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Row getRow() {
		return row;
	}
	public void setRow(Row row) {
		this.row = row;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public String getSafeName() {
		return safeName;
	}
	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}
	public ContentType getContentType() {
		return contentType;
	}
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	public boolean isShowTitle() {
		return showTitle;
	}
	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}
	
	
}
