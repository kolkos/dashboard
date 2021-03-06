package nl.kolkos.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class DeviceTypeLookup {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String type;
	private String subTypeField;
	private String subTypeValue;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "device_type_config_id")
	private DeviceTypeConfig deviceTypeConfig;
	
	
	public DeviceTypeLookup() {}
	public DeviceTypeLookup(String type, String subTypeField, String subTypeValue, DeviceTypeConfig deviceTypeConfig) {
		this.setType(type);
		this.setSubTypeField(subTypeField);
		this.setSubTypeValue(subTypeValue);
		this.setDeviceTypeConfig(deviceTypeConfig);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubTypeField() {
		return subTypeField;
	}

	public void setSubTypeField(String subTypeField) {
		this.subTypeField = subTypeField;
	}

	public String getSubTypeValue() {
		return subTypeValue;
	}

	public void setSubTypeValue(String subTypeValue) {
		this.subTypeValue = subTypeValue;
	}

	public DeviceTypeConfig getDeviceTypeConfig() {
		return deviceTypeConfig;
	}

	public void setDeviceTypeConfig(DeviceTypeConfig deviceTypeConfig) {
		this.deviceTypeConfig = deviceTypeConfig;
	}
	
	
}


