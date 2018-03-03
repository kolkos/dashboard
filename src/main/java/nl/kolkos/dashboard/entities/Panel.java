package nl.kolkos.dashboard.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Panel {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "screen_id")
	private Screen screen;
	
	// positional elements
	private int rowStart;      // the row where the panel starts
	private int columnStart;   // the column where the panel starts
	private int height;   // the height (in rows) of this panel
	private int width;    // the width (in columns) of this panel
	
	private String panelId; // the html id for this panel. needs to be unique
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "content_type_id")
	private ContentType contentType;
	
	@JsonIgnore
	@OneToOne(mappedBy = "panel", cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, optional = false)
	private DevicePanel devicePanel;
	
	private boolean showTitle = true;
	
	@JsonIgnore
	@OneToMany(mappedBy = "panel", cascade = CascadeType.ALL)
	List<PanelStatusField> panelStatusFields;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getColumnStart() {
		return columnStart;
	}

	public void setColumnStart(int columnStart) {
		this.columnStart = columnStart;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getPanelId() {
		return panelId;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public DevicePanel getDevicePanel() {
		return devicePanel;
	}

	public void setDevicePanel(DevicePanel devicePanel) {
		this.devicePanel = devicePanel;
	}
	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public List<PanelStatusField> getPanelStatusFields() {
		return panelStatusFields;
	}

	public void setPanelStatusFields(List<PanelStatusField> panelStatusFields) {
		this.panelStatusFields = panelStatusFields;
	}


	
}
