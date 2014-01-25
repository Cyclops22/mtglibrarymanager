package com.cyclops.library.mtg.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class MTGSetBean implements Serializable {

	private static final long serialVersionUID = -7808111240012138847L;

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name="set_id", referencedColumnName="id")
//	@JoinTable(name = "aliasbean", joinColumns = @JoinColumn(name = "set_id"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<AliasBean> aliases;
	
	private String abbreviation;
	
	@NotNull
	private String language = Locale.ENGLISH.getLanguage();
	
	private String url;
	
	private String imageUrl;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
