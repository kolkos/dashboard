package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class SubDeviceTypeStatusField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String statusField;
	@Transient
	private String value;
	
	// link with the SubDeviceTypeConfig
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_type_config_id")
	private SubDeviceTypeConfig subDeviceTypeConfig;

	public String getStatusField() {
		return statusField;
	}

	public void setStatusField(String statusField) {
		this.statusField = statusField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SubDeviceTypeConfig getSubDeviceTypeConfig() {
		return subDeviceTypeConfig;
	}

	public void setSubDeviceTypeConfig(SubDeviceTypeConfig subDeviceTypeConfig) {
		this.subDeviceTypeConfig = subDeviceTypeConfig;
	}
	
	
}
