package com.taobaoke.cms.model;

public class ExtraItem {

	private int id,type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExtraItem [id=" + id + ", key=" + key + ", value=" + value
				+ ", name=" + name + "]";
	}

	private String key,value,name;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setKey(String key) {
		this.key =(key!=null)? key.trim():key;
	}

	public void setValue(String value) {
		this.value =(value!=null)? value.trim():value;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
