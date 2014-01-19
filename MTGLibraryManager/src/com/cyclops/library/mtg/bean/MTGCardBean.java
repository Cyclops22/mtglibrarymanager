package com.cyclops.library.mtg.bean;

public class MTGCardBean {

	private String editionName;
	private String editionAbbreviation;
	private String language;
	private String name;
	private String type;
	private String mana;
	private String rarity;
	private String url;
	private String imageUrl;
	
	public String getEditionName() {
		return editionName;
	}

	public void setEditionName(String editionName) {
		this.editionName = editionName;
	}

	public String getEditionAbbreviation() {
		return editionAbbreviation;
	}

	public void setEditionAbbreviation(String editionAbbreviation) {
		this.editionAbbreviation = editionAbbreviation;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMana() {
		return mana;
	}

	public void setMana(String mana) {
		this.mana = mana;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
