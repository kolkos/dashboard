package nl.kolkos.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author antonvanderkolk
 *
 */
@Entity
public class PanelStatusField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private int size;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_type_status_field_id")
	private SubDeviceTypeStatusField subDeviceTypeStatusField;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "panel_id")
	private Panel panel;
	
	private int position;
	
	
	@Transient
	private String value;
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
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
	public SubDeviceTypeStatusField getSubDeviceTypeStatusField() {
		return subDeviceTypeStatusField;
	}
	public void setSubDeviceTypeStatusField(SubDeviceTypeStatusField subDeviceTypeStatusField) {
		this.subDeviceTypeStatusField = subDeviceTypeStatusField;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
	
}
