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
public class PanelStatusField {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String label;
	private int size;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "panel_configuration_id")
	private PanelConfiguration panelConfiguration;
	
	@Transient
	private String value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PanelConfiguration getPanelConfiguration() {
		return panelConfiguration;
	}
	public void setPanelConfiguration(PanelConfiguration panelConfiguration) {
		this.panelConfiguration = panelConfiguration;
	}
	
	
	
}
