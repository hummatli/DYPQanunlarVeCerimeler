package com.mobapphome.avtolowpenal.other;

import java.io.Serializable;

public class PParentArticle  implements Serializable{
	int id;
	String name;
	
	public PParentArticle(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
