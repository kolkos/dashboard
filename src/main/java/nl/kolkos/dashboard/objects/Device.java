package nl.kolkos.dashboard.objects;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Device {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private int idx;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "sub_device_type_id")
	private SubDeviceType subDeviceType;
	
	
	
	public SubDeviceType getSubDeviceType() {
		return subDeviceType;
	}
	public void setSubDeviceType(SubDeviceType subDeviceType) {
		this.subDeviceType = subDeviceType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	
	
}
