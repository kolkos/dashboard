package nl.kolkos.dashboard.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class DeviceType {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String deviceType;
	private String subDeviceField;
	
	@JsonIgnore
	@OneToMany(mappedBy = "deviceType", cascade = CascadeType.ALL)
	private List<SubDeviceType> subDeviceTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSubDeviceField() {
		return subDeviceField;
	}

	public void setSubDeviceField(String subDeviceField) {
		this.subDeviceField = subDeviceField;
	}

	public List<SubDeviceType> getSubDeviceTypes() {
		return subDeviceTypes;
	}

	public void setSubDeviceTypes(List<SubDeviceType> subDeviceTypes) {
		this.subDeviceTypes = subDeviceTypes;
	}
	
	
}
