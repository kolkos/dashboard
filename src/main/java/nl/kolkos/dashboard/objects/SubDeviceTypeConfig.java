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
	@Transient
	private String iconStatusValue;
	
	// action by clicking on icon
	private String iconClickAction;
	
	// values for the slider (only for dimmers)
	// value of the minimal value
	private Integer sliderMinValue;
	// value of the maximum value
	private String sliderMaxValueField;
	// don't save the actual value
	@Transient
	private int sliderMaxValueValue;
	// value of each step
	private int sliderStepValue;
	// field to determine the current value
	private String sliderCurrentValueField;
	// the current value
	@Transient
	private int sliderCurrentValueValue;
	
	
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
	public String getIconStatusValue() {
		return iconStatusValue;
	}
	public void setIconStatusValue(String iconStatusValue) {
		this.iconStatusValue = iconStatusValue;
	}
	public String getIconClickAction() {
		return iconClickAction;
	}
	public void setIconClickAction(String iconClickAction) {
		this.iconClickAction = iconClickAction;
	}
	public Integer getSliderMinValue() {
		return sliderMinValue;
	}
	public void setSliderMinValue(Integer sliderMinValue) {
		this.sliderMinValue = sliderMinValue;
	}
	public String getSliderMaxValueField() {
		return sliderMaxValueField;
	}
	public void setSliderMaxValueField(String sliderMaxValueField) {
		this.sliderMaxValueField = sliderMaxValueField;
	}
	public int getSliderMaxValueValue() {
		return sliderMaxValueValue;
	}
	public void setSliderMaxValueValue(int sliderMaxValueValue) {
		this.sliderMaxValueValue = sliderMaxValueValue;
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
	public int getSliderCurrentValueValue() {
		return sliderCurrentValueValue;
	}
	public void setSliderCurrentValueValue(int sliderCurrentValueValue) {
		this.sliderCurrentValueValue = sliderCurrentValueValue;
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
