package com.cyclops.library.mtg.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class SetBean {

	@Id
	@GeneratedValue
	private int id;

	private String name;
	
	private String code;
	
	private String gathererCode;
	
	private String oldCode;
	
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	private String border;
	
	private String setType;
	
	private String block;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name="set_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CardBean> cards;
	
	@Override
	public String toString() {
		return name;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGathererCode() {
		return gathererCode;
	}

	public void setGathererCode(String gathererCode) {
		this.gathererCode = gathererCode;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public List<CardBean> getCards() {
		return cards;
	}

	public void setCards(List<CardBean> cards) {
		this.cards = cards;
	}
}
