package nl.kolkos.dashboard.objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Screen {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "dashboard_id")
	private Dashboard dashboard;
	
	private String name;
	private int location;
	private String backgroundImage;
	private String icon;
	private String safeName;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "screen")
    private List<Row> rows = new ArrayList<>();
	
	
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
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getSafeName() {
		return safeName;
	}
	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	
}
