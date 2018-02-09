package nl.kolkos.dashboard.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="domoticz")
public class AvailableDeviceTypeConfiguration {
	
	private List<DeviceType> deviceTypes = new ArrayList<>();
	
	public List<DeviceType> getDeviceTypes() {
		return deviceTypes;
	}
	public void setDeviceTypes(List<DeviceType> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}
	
	
}
