package nl.kolkos.dashboard.objects;

import java.util.List;

/**
 * This object is used to build a page
 * @author antonvanderkolk
 *
 */
public class Page {
	// the current screen
	private Screen screen;

	// the other screens
	private List<Screen> screens;
	
	// the rows on this page
	// rows are filled with panels
	private List<PageRow> pageRows;

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public List<Screen> getScreens() {
		return screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}

	public List<PageRow> getPageRows() {
		return pageRows;
	}

	public void setPageRows(List<PageRow> pageRows) {
		this.pageRows = pageRows;
	}
	
	
}
