package nl.kolkos.dashboard.objects;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Device {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private int idx;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "device_type_id")
	private DeviceType deviceType;
	
	
	private String icon; // the icon to show on the tile
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}


	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
