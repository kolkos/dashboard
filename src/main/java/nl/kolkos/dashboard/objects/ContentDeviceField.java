package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ContentDeviceField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_field_id")
	private SubDeviceTypeField subDeviceField;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "content_device_id")
	private ContentDevice contentDevice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SubDeviceTypeField getSubDeviceField() {
		return subDeviceField;
	}

	public void setSubDeviceField(SubDeviceTypeField subDeviceField) {
		this.subDeviceField = subDeviceField;
	}

	public ContentDevice getContentDevice() {
		return contentDevice;
	}

	public void setContentDevice(ContentDevice contentDevice) {
		this.contentDevice = contentDevice;
	}
	
	
}
