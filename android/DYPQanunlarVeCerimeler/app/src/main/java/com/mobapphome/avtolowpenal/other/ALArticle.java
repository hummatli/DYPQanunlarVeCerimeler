package com.mobapphome.avtolowpenal.other;

import java.io.Serializable;

public class ALArticle implements Serializable{
	
	
	int id;
	String name;
	String desc_url;
	int type;
	
	public ALArticle(int id, String name, String desc_url, int type) {
		super();
		this.id = id;
		this.name = name;
		this.desc_url = desc_url;
		this.type = type;
	}
	

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc_url() {
		return desc_url;
	}

	public void setDesc_url(String desc_url) {
		this.desc_url = desc_url;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
