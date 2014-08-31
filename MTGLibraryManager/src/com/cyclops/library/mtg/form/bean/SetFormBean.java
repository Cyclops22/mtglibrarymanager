package com.cyclops.library.mtg.form.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



public class SetFormBean {

	private int id;
	private String name;
	private String code;
	private String gathererCode;
	private String oldCode;
	private Date releaseDate;
	private String border;
	private String setType;
	private String block;
	
	private boolean selected;
	
	private List<CardFormBean> cards;
	
	@Override
	public int hashCode() {
		HashCodeBuilder  hcb = new HashCodeBuilder();
		
		hcb.append(name);
		
		return hcb.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof SetFormBean)) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		SetFormBean that = (SetFormBean) obj;
		
		EqualsBuilder eb = new EqualsBuilder();
		
		eb.append(name, that.getName());
		
		return eb.isEquals();
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

	public List<CardFormBean> getCards() {
		return cards;
	}

	public void setCards(List<CardFormBean> cards) {
		this.cards = cards;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
