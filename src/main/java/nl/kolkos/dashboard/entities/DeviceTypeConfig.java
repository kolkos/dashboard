package nl.kolkos.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DeviceTypeConfig {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private boolean dimmer;
	private boolean staticDevice;
	
	private String getStatusTemplateUrl;
	private String setStatusTemplateUrl;
	private String setLevelTemplateUrl;
	private String iconStatusJsonField;
	
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

	public boolean isDimmer() {
		return dimmer;
	}

	public void setDimmer(boolean dimmer) {
		this.dimmer = dimmer;
	}

	public boolean isStaticDevice() {
		return staticDevice;
	}

	public void setStaticDevice(boolean staticDevice) {
		this.staticDevice = staticDevice;
	}

	public String getGetStatusTemplateUrl() {
		return getStatusTemplateUrl;
	}

	public void setGetStatusTemplateUrl(String getStatusTemplateUrl) {
		this.getStatusTemplateUrl = getStatusTemplateUrl;
	}

	public String getSetStatusTemplateUrl() {
		return setStatusTemplateUrl;
	}

	public void setSetStatusTemplateUrl(String setStatusTemplateUrl) {
		this.setStatusTemplateUrl = setStatusTemplateUrl;
	}

	public String getSetLevelTemplateUrl() {
		return setLevelTemplateUrl;
	}

	public void setSetLevelTemplateUrl(String setLevelTemplateUrl) {
		this.setLevelTemplateUrl = setLevelTemplateUrl;
	}

	public String getIconStatusJsonField() {
		return iconStatusJsonField;
	}

	public void setIconStatusJsonField(String iconStatusJsonField) {
		this.iconStatusJsonField = iconStatusJsonField;
	}
	
	
	
}
