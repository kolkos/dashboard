package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SubDeviceType {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "device_type_id")
	private DeviceType deviceType;
	
	private String subDeviceType;
	
	private boolean staticDevice = false;
	private String icon;
	private String templatePage;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public String getSubDeviceType() {
		return subDeviceType;
	}
	public void setSubDeviceType(String subDeviceType) {
		this.subDeviceType = subDeviceType;
	}
	public boolean isStaticDevice() {
		return staticDevice;
	}
	public void setStaticDevice(boolean staticDevice) {
		this.staticDevice = staticDevice;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTemplatePage() {
		return templatePage;
	}
	public void setTemplatePage(String templatePage) {
		this.templatePage = templatePage;
	}
		
	
	
}
