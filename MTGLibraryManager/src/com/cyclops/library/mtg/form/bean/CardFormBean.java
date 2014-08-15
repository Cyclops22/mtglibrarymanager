package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CardFormBean {
	
	private static final Pattern MANA_COST_PATTERN = Pattern.compile("(\\{\\w(?:\\/\\w)?\\})");

	private int id;
	private String layout;
	private String name;
	private String names;
	private String manaCost;
	private String cmc;
	private String colors;
	private String type;
	private String supertypes;
	private String types;
	private String subtypes;
	private String rarity;
	private String text;
	private String flavor;
	private String artist;
	private String number;
	private String power;
	private String toughness;
	private String loyalty;
	private String multiverseid;
	private String variations;
	private String imageName;
	private String watermark;
	private String border;
	private boolean timeshifted;
	private String hand;
	private String life;
	private boolean reserved;
	
	private List<String> manaCostElements;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getManaCost() {
		return manaCost;
	}

	public void setManaCost(String manaCost) {
		this.manaCost = manaCost;
		
		manaCostElements = new ArrayList<>();
		
		if (StringUtils.isNotBlank(manaCost)) {
			Matcher matcher = MANA_COST_PATTERN.matcher(manaCost);
			while (matcher.find()) {
				String singleCost = StringUtils.remove(matcher.group(0), '{');
				singleCost = StringUtils.remove(singleCost, '}');
				singleCost = StringUtils.remove(singleCost, '/');
				
				manaCostElements.add(singleCost);
			}
		}
	}

	public String getCmc() {
		return cmc;
	}

	public void setCmc(String cmc) {
		this.cmc = cmc;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(String supertypes) {
		this.supertypes = supertypes;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(String subtypes) {
		this.subtypes = subtypes;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(String loyalty) {
		this.loyalty = loyalty;
	}

	public String getMultiverseid() {
		return multiverseid;
	}

	public void setMultiverseid(String multiverseid) {
		this.multiverseid = multiverseid;
	}

	public String getVariations() {
		return variations;
	}

	public void setVariations(String variations) {
		this.variations = variations;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public boolean isTimeshifted() {
		return timeshifted;
	}

	public void setTimeshifted(boolean timeshifted) {
		this.timeshifted = timeshifted;
	}

	public String getHand() {
		return hand;
	}

	public void setHand(String hand) {
		this.hand = hand;
	}

	public String getLife() {
		return life;
	}

	public void setLife(String life) {
		this.life = life;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public List<String> getManaCostElements() {
		return manaCostElements;
	}
}
