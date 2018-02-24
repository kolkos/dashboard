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
public class SubDeviceTypeStatusField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String statusField;
	private String label;

	
	// link with the SubDeviceTypeConfig
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_type_id")
	private SubDeviceType subDeviceType;

	public String getStatusField() {
		return statusField;
	}

	public void setStatusField(String statusField) {
		this.statusField = statusField;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public SubDeviceType getSubDeviceType() {
		return subDeviceType;
	}

	public void setSubDeviceType(SubDeviceType subDeviceType) {
		this.subDeviceType = subDeviceType;
	}
	
	
}
