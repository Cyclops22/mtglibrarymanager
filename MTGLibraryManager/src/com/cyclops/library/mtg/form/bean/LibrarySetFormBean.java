package com.cyclops.library.mtg.form.bean;

import java.util.List;


public class LibrarySetFormBean {

	private boolean selected;
	private int id;
	private SetFormBean referencedSet;
	private List<LibraryCardFormBean> cards;

	
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

	public SetFormBean getReferencedSet() {
		return referencedSet;
	}

	public void setReferencedSet(SetFormBean referencedSet) {
		this.referencedSet = referencedSet;
	}

	public List<LibraryCardFormBean> getCards() {
		return cards;
	}

	public void setCards(List<LibraryCardFormBean> cards) {
		this.cards = cards;
	}
}
