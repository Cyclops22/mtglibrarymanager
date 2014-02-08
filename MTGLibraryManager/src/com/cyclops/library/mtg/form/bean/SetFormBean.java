package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;



public class SetFormBean {

	private int id;
	private String name;
	private String category;
	private String abbreviation;
	private String language;
	private String url;
	private String imageUrl;
	private String releaseDate;
	
	private List<CardFormBean> cards = new ArrayList<>();

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<CardFormBean> getCards() {
		return cards;
	}

	public void setCards(List<CardFormBean> cards) {
		this.cards = cards;
	}
}
