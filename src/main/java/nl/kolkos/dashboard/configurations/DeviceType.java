package nl.kolkos.dashboard.configurations;

import org.springframework.stereotype.Component;

@Component
public class DeviceType {
	private String name;
	private String typeField;
	private String icon;
	private boolean staticDevice;
	private String template;
	private String templateUrlSwitchOn;
	private String templateUrlSwitchOff;
	private String templateUrlSwitchToggle;
	private String templateUrlSetLevel;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeField() {
		return typeField;
	}
	public void setTypeField(String typeField) {
		this.typeField = typeField;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isStaticDevice() {
		return staticDevice;
	}
	public void setStaticDevice(boolean staticDevice) {
		this.staticDevice = staticDevice;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTemplateUrlSwitchOn() {
		return templateUrlSwitchOn;
	}
	public void setTemplateUrlSwitchOn(String templateUrlSwitchOn) {
		this.templateUrlSwitchOn = templateUrlSwitchOn;
	}
	public String getTemplateUrlSwitchOff() {
		return templateUrlSwitchOff;
	}
	public void setTemplateUrlSwitchOff(String templateUrlSwitchOff) {
		this.templateUrlSwitchOff = templateUrlSwitchOff;
	}
	public String getTemplateUrlSwitchToggle() {
		return templateUrlSwitchToggle;
	}
	public void setTemplateUrlSwitchToggle(String templateUrlSwitchToggle) {
		this.templateUrlSwitchToggle = templateUrlSwitchToggle;
	}
	public String getTemplateUrlSetLevel() {
		return templateUrlSetLevel;
	}
	public void setTemplateUrlSetLevel(String templateUrlSetLevel) {
		this.templateUrlSetLevel = templateUrlSetLevel;
	}
}
