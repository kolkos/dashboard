package nl.kolkos.dashboard.objects;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class SubDeviceTypeConfig {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	// values for the icon status
	private String iconStatusField;
	// don't save the value
		
	// action by clicking on icon
	private String iconClickAction;
	
	// values for the slider (only for dimmers)
	// value of the minimal value
	private int sliderMinValue;
	// value of the maximum value
	private String sliderMaxValueField;
	// value of each step
	private int sliderStepValue;
	// field to determine the current value
	private String sliderCurrentValueField;

	
	// relation with the SubDeviceType
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_device_type_id")
	private SubDeviceType subDeviceType;
	
	
	/*
	 * Status fields
	 * There could be multiple fields to display a status
	 * Therefore I'll use separate entity
	 */
	@OneToMany(mappedBy = "subDeviceTypeConfig", cascade = CascadeType.ALL)
	List<SubDeviceTypeStatusField> subDeviceTypeStatusFields;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIconStatusField() {
		return iconStatusField;
	}
	public void setIconStatusField(String iconStatusField) {
		this.iconStatusField = iconStatusField;
	}
	public String getIconClickAction() {
		return iconClickAction;
	}
	public void setIconClickAction(String iconClickAction) {
		this.iconClickAction = iconClickAction;
	}
	public int getSliderMinValue() {
		return sliderMinValue;
	}
	public void setSliderMinValue(int sliderMinValue) {
		this.sliderMinValue = sliderMinValue;
	}
	public String getSliderMaxValueField() {
		return sliderMaxValueField;
	}
	public void setSliderMaxValueField(String sliderMaxValueField) {
		this.sliderMaxValueField = sliderMaxValueField;
	}
	public int getSliderStepValue() {
		return sliderStepValue;
	}
	public void setSliderStepValue(int sliderStepValue) {
		this.sliderStepValue = sliderStepValue;
	}
	public String getSliderCurrentValueField() {
		return sliderCurrentValueField;
	}
	public void setSliderCurrentValueField(String sliderCurrentValueField) {
		this.sliderCurrentValueField = sliderCurrentValueField;
	}
	public SubDeviceType getSubDeviceType() {
		return subDeviceType;
	}
	public void setSubDeviceType(SubDeviceType subDeviceType) {
		this.subDeviceType = subDeviceType;
	}
	public List<SubDeviceTypeStatusField> getSubDeviceTypeStatusFields() {
		return subDeviceTypeStatusFields;
	}
	public void setSubDeviceTypeStatusFields(List<SubDeviceTypeStatusField> subDeviceTypeStatusFields) {
		this.subDeviceTypeStatusFields = subDeviceTypeStatusFields;
	}
	
	
	
}
