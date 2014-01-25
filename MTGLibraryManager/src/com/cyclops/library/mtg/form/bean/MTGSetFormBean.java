package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;



public class MTGSetFormBean {

	private int id;
	private String name;
	private String aliases;
	private String abbreviation;
	private String language;
	private String url;
	private String imageUrl;
	
	private List<MTGCardFormBean> cards = new ArrayList<>();
	
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

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public List<MTGCardFormBean> getCards() {
		return cards;
	}

	public void setCards(List<MTGCardFormBean> cards) {
		this.cards = cards;
	}
}
