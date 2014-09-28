package com.cyclops.library.mtg.json.bean;

public class SetJsonBean {
	private String name;
	private String code;
	private String gathererCode;
	private String oldCode;
	private String releaseDate;
	private String border;
	private String type;
	private String block;
	private String onlineOnly;
	private Object[] booster;
	private CardJsonBean[] cards;
	private Object magicRaritiesCodes;
	
	public SetJsonBean() {}
	
	@Override
	public String toString() {
		return name;
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

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getOnlineOnly() {
		return onlineOnly;
	}

	public void setOnlineOnly(String onlineOnly) {
		this.onlineOnly = onlineOnly;
	}

	public Object[] getBooster() {
		return booster;
	}

	public void setBooster(Object[] booster) {
		this.booster = booster;
	}

	public CardJsonBean[] getCards() {
		return cards;
	}

	public void setCards(CardJsonBean[] cards) {
		this.cards = cards;
	}

	public Object getMagicRaritiesCodes() {
		return magicRaritiesCodes;
	}

	public void setMagicRaritiesCodes(Object magicRaritiesCodes) {
		this.magicRaritiesCodes = magicRaritiesCodes;
	}
}
