package com.mobapphome.avtolowpenal.other;

import java.io.Serializable;


public class PParArtSubArt  implements Serializable{
	private PParentArticle parArt;
	private PSubArticle subArt;
	
	public PParArtSubArt(PParentArticle parArt, PSubArticle subArt) {
		this.parArt = parArt;
		this.subArt = subArt;
	}

	public PParentArticle getParArt() {
		return parArt;
	}

	public PSubArticle getSubArt() {
		return subArt;
	}
	
}
