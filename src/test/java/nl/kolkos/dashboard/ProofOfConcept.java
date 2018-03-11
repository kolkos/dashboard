package nl.kolkos.dashboard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;


public class ProofOfConcept {
	
	private List<String> usedIds = new ArrayList<>();
	
	private List<Panel> panels = new ArrayList<>();

	@Test
	public void test() {
		// fill some 'used' ids
		usedIds.add("test_name");
		usedIds.add("test_name_1");
		usedIds.add("test_name_2");
		
		String panelId = "test_name";
		System.out.println(this.generateUniquePanelId(panelId));
		
		//fail("Not yet implemented");
	}
	
	public String generateUniquePanelId(String panelId) {
		// check if the name is already used
		if(! usedIds.contains(panelId)) {
			return panelId;
		} else {
			
			// check if the id ends with a number 
			Pattern pattern = Pattern.compile("(^.*)(_\\d{1,}$)");
			Matcher match = pattern.matcher(panelId);
			
			int nr = 0;
			// check if there is a match
			// if there is no match, it means there is no number suffix
			if(!match.find()) {
				// just add add the number one and we are done
				nr = 1;
			}else {
				String numberSuffix = match.group(2);
				numberSuffix = numberSuffix.replaceAll("_", "");
				nr = Integer.parseInt(numberSuffix);
				nr++;
				
				panelId = match.group(1);
			}
			
			return generateUniquePanelId(panelId + "_" + nr);
			
		}
	}
	
	@Test
	public void testPanelPositionChecker() {
		Panel testPanel1 = new Panel();
		testPanel1.setName("Panel1: Row 1 to 2, Column 1 to 3");
		testPanel1.setRowStart(1);
		testPanel1.setHeight(2);
		testPanel1.setColumnStart(1);
		testPanel1.setWidth(4);
		
		Panel testPanel2 = new Panel();
		testPanel2.setName("Panel2: Row 1 to 1, Column 4 to 9");
		testPanel2.setRowStart(1);
		testPanel2.setHeight(1);
		testPanel2.setColumnStart(5);
		testPanel2.setWidth(4);
		
		Panel testPanel3 = new Panel();
		testPanel3.setName("Panel3: Row 3 to 4, Column 4 to 5");
		testPanel3.setRowStart(3);
		testPanel3.setHeight(2);
		testPanel3.setColumnStart(4);
		testPanel3.setWidth(1);
		
		panels.add(testPanel1);
		panels.add(testPanel2);
		panels.add(testPanel3);
		
		Panel newPanel = new Panel();
		newPanel.setName("My new panel");
		newPanel.setRowStart(1);
		newPanel.setColumnStart(1);
		newPanel.setWidth(1);
		newPanel.setHeight(1);
		
		List<String> takenPositions = this.getTakenPositions();
		
		newPanel = this.findFreePosition(newPanel, takenPositions);
	}
	
	public List<String> getTakenPositions(){
		
		List<String> takenPositions = new ArrayList<>();
		
		// normally we get this from the database with the screen
		// Instead of a List it is Iterable
		// loop through the panels
		for(Panel panel : panels) {
			// loop through the rows
			for(int row = panel.getRowStart(); row <= (panel.getRowStart() + panel.getHeight() - 1); row++) {
				// loop through columns
				for(int column = panel.getColumnStart(); column <= (panel.getColumnStart() + panel.getWidth() - 1); column++) {
					String position = "" + row + column;
					System.out.println(panel.getName() + " has taken position: " + position);
					takenPositions.add(position);
				}
			}
		}
		
		
		return takenPositions;
	}
	
	public Panel findFreePosition(Panel panel, List<String> takenPositions) {
		// loop through the rows
		for(int row = 1; row <= 8; row++) {
			for(int column = 1; column <= 8; column++) {
				String cursor = "" + row + column;
				// check if the cursor is on a free position
				if(! takenPositions.contains(cursor)) {
					// this position is free
					System.out.println("Found a free position at: " + cursor);
					panel.setRowStart(row);
					panel.setColumnStart(column);
					
					return panel;
				}
				System.out.println(cursor + " has been taken");
			}
		}
		
		// if no free position could be found, we will return the default position (so 1,1)
		return panel;
	}
	
	
	
	

}
