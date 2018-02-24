package nl.kolkos.dashboard.entities;

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

@Entity
public class PanelConfiguration {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "panel_id")
	private Panel panel;
	
	private boolean showTitle;
	
	@OneToMany(mappedBy = "panelConfiguration", cascade = CascadeType.ALL)
	List<PanelStatusField> panelStatusFields;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public List<PanelStatusField> getPanelStatusFields() {
		return panelStatusFields;
	}

	public void setPanelStatusFields(List<PanelStatusField> panelStatusFields) {
		this.panelStatusFields = panelStatusFields;
	}
	
	
}
