package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.PanelStatusField;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;
import nl.kolkos.dashboard.repositories.PanelStatusFieldRepository;

@Service
public class PanelStatusFieldService {
	@Autowired
	private PanelStatusFieldRepository panelStatusFieldRepository;
	
	
	public PanelStatusField getFieldByPanelAndSubDeviceTypeStatusField(Panel panel, SubDeviceTypeStatusField subDeviceTypeStatusField) {
		return panelStatusFieldRepository.findByPanelAndSubDeviceTypeStatusField(panel, subDeviceTypeStatusField);
	}
	
	public void save(PanelStatusField panelStatusField) {
		panelStatusFieldRepository.save(panelStatusField);
	}
	
	public void save(List<PanelStatusField> panelStatusFields) {
		for(PanelStatusField panelStatusField : panelStatusFields) {
			this.save(panelStatusField);
		}
	}
	
	public void delete(PanelStatusField panelStatusField) {
		panelStatusFieldRepository.delete(panelStatusField);
	}
	
	public void delete(List<PanelStatusField> panelStatusFields) {
		for(PanelStatusField panelStatusField : panelStatusFields) {
			this.delete(panelStatusField);
		}
	}
	
	public List<PanelStatusField> findStatusFieldsForPanel(Panel panel){
		return panelStatusFieldRepository.findByPanelOrderByPositionAsc(panel);
	}
	
	public PanelStatusField findById(long id) {
		return panelStatusFieldRepository.findById(id);
	}
	
	public void syncPanelFields(Panel panel) {
		// first remove the old fields
		this.deleteOldPanelFields(panel);
		
		// now sync the new fields
		this.addNewPanelFields(panel);
		
	}
	
	private void deleteOldPanelFields(Panel panel) {
		// get the current fields from the database
		List<PanelStatusField> currentPanelStatusFields = panelStatusFieldRepository.findByPanel(panel);
		
		// check if the panel is still linked to a device, if not delete all the fields
		if(panel.getDevicePanel() == null) {
			this.delete(currentPanelStatusFields);
			// done
			return;
		}
		
		// get the current sub device type
		SubDeviceType currentSubDeviceType = panel.getDevicePanel().getDevice().getSubDeviceType();
		
		// list for fields to remove
		List<PanelStatusField> panelStatusFieldsToRemove = new ArrayList<>();
		
		for(PanelStatusField panelStatusField : currentPanelStatusFields) {
			// get the SubDeviceType of this field
			SubDeviceType fieldSubDeviceType = panelStatusField.getSubDeviceTypeStatusField().getSubDeviceType();
			
			// now check if the SubDeviceTypes are equal
			if(! fieldSubDeviceType.equals(currentSubDeviceType)) {
				// not equal, remove the field
				panelStatusFieldsToRemove.add(panelStatusField);
			}
		}
		
		this.delete(panelStatusFieldsToRemove);
		
	}
	
	private void addNewPanelFields(Panel panel) {
		// check if the panel is linked to a device
		if(panel.getDevicePanel() == null) {
			return;
		}
		
		// get all the SubDeviceTypeFields for the sub device type
		List<SubDeviceTypeStatusField> subDeviceTypeStatusFields = panel.getDevicePanel().getDevice().getSubDeviceType().getSubDeviceTypeStatusFields();
		
		// now get all the panel fields
		List<PanelStatusField> newPanelStatusFields = new ArrayList<>();
		
		// now loop through the SubDeviceStatusFields
		for(SubDeviceTypeStatusField subDeviceTypeStatusField : subDeviceTypeStatusFields) {
			// first check if the status field for the panel already exists
			if(this.getFieldByPanelAndSubDeviceTypeStatusField(panel, subDeviceTypeStatusField) == null) {
				// the field does not exist, create it
				PanelStatusField panelStatusField = new PanelStatusField();
				panelStatusField.setSubDeviceTypeStatusField(subDeviceTypeStatusField);
				panelStatusField.setPanel(panel);
				panelStatusField.setSize(1);
				panelStatusField.setDisplayed(true);
				
				// find the next position
				int newPosition;
				try {
					int lastLocation = panelStatusFieldRepository.findFirstByPanelOrderByPositionDesc(panel).getPosition();
					newPosition = lastLocation + 1;
				} catch(NullPointerException e) {
					newPosition = 0;
				}
				panelStatusField.setPosition(newPosition);
				
				// add to the list
				newPanelStatusFields.add(panelStatusField);
			}
		}
		// save the new Panel Status Fields
		this.save(newPanelStatusFields);
	}
	
	public void moveUp(PanelStatusField panelStatusFieldtoMove, List<PanelStatusField> panelStatusFields) {
		int newPosition = panelStatusFieldtoMove.getPosition() - 1;
		
		for(PanelStatusField panelStatusField : panelStatusFields) {
			// check if this is the panel foe;d we wish to move
			if(panelStatusField.getId() == panelStatusFieldtoMove.getId()) {
				// set the new position
				panelStatusField.setPosition(newPosition);
				continue;
			}
			
			int thisFieldPosition = panelStatusField.getPosition();
			if(thisFieldPosition == newPosition) {
				panelStatusField.setPosition(panelStatusField.getPosition() + 1);
			} else if(thisFieldPosition > newPosition) {
				panelStatusField.setPosition(panelStatusField.getPosition() + 1);
			}else {
				continue;
			}
		}
	}
	
	public void moveDown(PanelStatusField panelStatusFieldtoMove, List<PanelStatusField> panelStatusFields) {
		int newPosition = panelStatusFieldtoMove.getPosition() + 1;
		
		for(PanelStatusField panelStatusField : panelStatusFields) {
			// check if this is the panel foe;d we wish to move
			if(panelStatusField.getId() == panelStatusFieldtoMove.getId()) {
				// set the new position
				panelStatusField.setPosition(newPosition);
				continue;
			}
			
			int thisFieldPosition = panelStatusField.getPosition();
			if(thisFieldPosition == newPosition) {
				panelStatusField.setPosition(panelStatusField.getPosition() - 1);
			} else if(thisFieldPosition > newPosition) {
				panelStatusField.setPosition(panelStatusField.getPosition() + 1);
			}else {
				panelStatusField.setPosition(panelStatusField.getPosition() - 1);
			}
		}
	}
	
	private void fixPositions(List<PanelStatusField> panelStatusFields) {
		panelStatusFields = panelStatusFields.stream().sorted(Comparator.comparing(PanelStatusField::getPosition)).collect(Collectors.toList());
		
		int position = 0;
		for(PanelStatusField panelStatusField : panelStatusFields) {
			panelStatusField.setPosition(position);
			position++;
		}
	}
	
	public void saveNewPositions(List<PanelStatusField> panelStatusFields) {
		this.fixPositions(panelStatusFields);
		for(PanelStatusField panelStatusField : panelStatusFields) {
			panelStatusFieldRepository.save(panelStatusField);
		}
	}
}
