package nl.kolkos.dashboard.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
public class Panel {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	private String safeName;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "screen_id")
	private Screen screen;
	
	// positional elements
	private int rowStart;      // the row where the panel starts
	private int columnStart;   // the column where the panel starts
	private int height;   // the height (in rows) of this panel
	private int width;    // the width (in columns) of this panel
	
	private String icon;
	private boolean showTitle;
	
	@Transient
	private String title;
	
	/*
	 * Links to the panel content
	 * Use one or the other
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "device_panel_id")
	private DevicePanel devicePanel;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "static_panel_id")
	private StaticPanel staticPanel;
	
	
	
	/*
	 * ================================================================
	 * Getters & Setters
	 * ================================================================
	 */
	

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

	public String getSafeName() {
		return safeName;
	}

	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DevicePanel getDevicePanel() {
		return devicePanel;
	}

	public void setDevicePanel(DevicePanel devicePanel) {
		this.devicePanel = devicePanel;
	}

	public StaticPanel getStaticPanel() {
		return staticPanel;
	}

	public void setStaticPanel(StaticPanel staticPanel) {
		this.staticPanel = staticPanel;
	}
	
	
	
	


	
}
