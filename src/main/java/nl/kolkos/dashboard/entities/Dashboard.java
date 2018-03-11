package nl.kolkos.dashboard.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dashboard {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	private String safeName;
	private String backgroundImage;
	private boolean defaultDashboard = false;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public String getSafeName() {
		return safeName;
	}
	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}
	public boolean isDefaultDashboard() {
		return defaultDashboard;
	}
	public void setDefaultDashboard(boolean defaultDashboard) {
		this.defaultDashboard = defaultDashboard;
	}
	
	
	
	
	
}
