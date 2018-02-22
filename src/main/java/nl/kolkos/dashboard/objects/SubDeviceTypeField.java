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
public class SubDeviceTypeField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_type_id")
	private SubDeviceType subDeviceType;
	
	private String field;
	
	private boolean useField = false;
	
	@Transient
	private String value;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubDeviceType getSubDeviceType() {
		return subDeviceType;
	}

	public void setSubDeviceType(SubDeviceType subDeviceType) {
		this.subDeviceType = subDeviceType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isUseField() {
		return useField;
	}

	public void setUseField(boolean useField) {
		this.useField = useField;
	}
	
}
