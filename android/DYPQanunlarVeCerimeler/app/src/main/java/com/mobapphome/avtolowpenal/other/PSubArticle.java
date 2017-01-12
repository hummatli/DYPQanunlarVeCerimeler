package com.mobapphome.avtolowpenal.other;

import java.io.Serializable;

public class PSubArticle  implements Serializable{
	int id;
	String name;
	String desc;
	String penalCount;
	String penalBal;
	int parentArtID;
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer penal = new StringBuffer();
		if (getPenalBal()!=null) {
			penal.append(getPenalBal());
		}
		if(getPenalCount()!=null){
			if(getPenalBal() != null){
				penal.append(", ");
			}
			penal.append(getPenalCount());
		}
		return penal.toString();
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPenalCount() {
		return penalCount;
	}

	public void setPenalCount(String penalCount) {
		this.penalCount = penalCount;
	}

	public String getPenalBal() {
		return penalBal;
	}

	public void setPenalBal(String penalBal) {
		this.penalBal = penalBal;
	}

	public int getParentArtID() {
		return parentArtID;
	}

	public void setParentArtID(int parentArtID) {
		this.parentArtID = parentArtID;
	}

	public PSubArticle(int id, String name, String desc, String penalCount,
			String penalBal, int parentArtID) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.penalCount = penalCount;
		this.penalBal = penalBal;
		this.parentArtID = parentArtID;
	}
	

}
