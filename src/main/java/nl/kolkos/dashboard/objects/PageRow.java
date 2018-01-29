package nl.kolkos.dashboard.objects;

import java.util.List;

/**
 * This object is used to add the panels to a row
 * @author antonvanderkolk
 *
 */
public class PageRow {
	// the current row
	private Row row;
	
	// the panels for this row
	private List<Panel> panels;

	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	public List<Panel> getPanels() {
		return panels;
	}

	public void setPanels(List<Panel> panels) {
		this.panels = panels;
	}
	
	
}
