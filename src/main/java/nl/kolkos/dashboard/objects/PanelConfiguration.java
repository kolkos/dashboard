package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PanelConfiguration {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	// link to the panel
	@OneToOne(mappedBy = "panelConfiguration")
	private Panel panel;
	
	private boolean showTitle;
	
	// link to the content type of the panel
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "content_type_id")
	private ContentType contentType;
	
	// text to display on this panel
	// if content type is HTML, then this text will be displayed as html
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	} 
	
	

	
	
}
