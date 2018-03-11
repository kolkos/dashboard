package nl.kolkos.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class DevicePanelData {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "device_panel_id")
	private DevicePanel devicePanel;
	
	private String label;
	private String jsonField;
	private int size;
	private int position;
	
	@Transient
	private String value;
	
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

	public DevicePanel getDevicePanel() {
		return devicePanel;
	}

	public void setDevicePanel(DevicePanel devicePanel) {
		this.devicePanel = devicePanel;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getJsonField() {
		return jsonField;
	}

	public void setJsonField(String jsonField) {
		this.jsonField = jsonField;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
