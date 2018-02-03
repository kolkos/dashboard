package nl.kolkos.dashboard.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeviceType {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	private String name;
	private String filter;          // value for the GET field filter
	private String type;            // value for the GET field type
	private String orderBy;         // value for the GET field order
	private boolean used = true;    // value for the GET field used (default is true)
	private String subTypeField;    // the json field which contains the sub type (for example dimmer, contact, Power, etc)
	
	
	public DeviceType() {}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String order) {
		this.orderBy = order;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getSubTypeField() {
		return subTypeField;
	}

	public void setSubTypeField(String subTypeField) {
		this.subTypeField = subTypeField;
	}
	
	
	
	
}
