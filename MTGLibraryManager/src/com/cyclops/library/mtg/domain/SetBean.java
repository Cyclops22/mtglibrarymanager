package com.cyclops.library.mtg.domain;

import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class SetBean {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name="set_id", referencedColumnName="id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AliasBean> aliases;
	
	private String abbreviation;
	
	@NotNull
	private String language = Locale.ENGLISH.getLanguage();
	
	private String url;
	
	private String imageUrl;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name="set_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CardBean> cards;
	
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

	public List<AliasBean> getAliases() {
		return aliases;
	}

	public void setAliases(List<AliasBean> aliases) {
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
	
	public List<CardBean> getCards() {
		return cards;
	}

	public void setCards(List<CardBean> cards) {
		this.cards = cards;
	}
}
