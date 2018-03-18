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
public class Device {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private int idx;
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "device_type_config_id")
	private DeviceTypeConfig deviceTypeConfig;
	
	@Transient
	private int iconStatus;
	
	@Transient
	private boolean deviceRegistered = false;
	
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

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIconStatus() {
		return iconStatus;
	}

	public void setIconStatus(int iconStatus) {
		this.iconStatus = iconStatus;
	}

	public DeviceTypeConfig getDeviceTypeConfig() {
		return deviceTypeConfig;
	}

	public void setDeviceTypeConfig(DeviceTypeConfig deviceTypeConfig) {
		this.deviceTypeConfig = deviceTypeConfig;
	}

	public boolean isDeviceRegistered() {
		return deviceRegistered;
	}

	public void setDeviceRegistered(boolean deviceRegistered) {
		this.deviceRegistered = deviceRegistered;
	}
	
	
	
	
	
	
}
