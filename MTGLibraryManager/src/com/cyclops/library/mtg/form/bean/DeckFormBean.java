package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;



public class DeckFormBean {

	private boolean selected;
	private int id;
	private String name;
	
	private List<LibraryCardFormBean> cards = new ArrayList<>();
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

	public List<LibraryCardFormBean> getCards() {
		return cards;
	}

	public void setCards(List<LibraryCardFormBean> cards) {
		this.cards = cards;
	}
}
