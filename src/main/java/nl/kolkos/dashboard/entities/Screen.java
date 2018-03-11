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
public class Screen {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "dashboard_id")
	private Dashboard dashboard;
	
	private String name;
	private int position;
	private String backgroundImage; // overwrite the dashboard background image
	private String icon;
	private String safeName;
	
	@Transient
	private boolean lastScreen = false;
		
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
	public Dashboard getDashboard() {
		return dashboard;
	}
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getSafeName() {
		return safeName;
	}
	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}
	public boolean isLastScreen() {
		return lastScreen;
	}
	public void setLastScreen(boolean lastScreen) {
		this.lastScreen = lastScreen;
	}

	
	
}
