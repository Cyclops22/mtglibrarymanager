package com.cyclops.library.mtg.domain;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name="set_id", referencedColumnName="id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<AliasBean> aliases;
	
	@Enumerated(EnumType.STRING)
	private SetCategory category;
	
	private String abbreviation;
	
	@NotNull
	private String language = Locale.ENGLISH.getLanguage();
	
	private String url;
	
	private String logoUrl;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
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

	public SetCategory getCategory() {
		return category;
	}

	public void setCategory(SetCategory category) {
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

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<CardBean> getCards() {
		return cards;
	}

	public void setCards(List<CardBean> cards) {
		this.cards = cards;
	}
}
