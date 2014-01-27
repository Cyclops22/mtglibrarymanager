package com.cyclops.library.mtg.form.bean;


public class LibraryCardFormBean {

	private int id;
	private CardFormBean referencedCard;
	private int quantity;
	private int foilQuantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CardFormBean getReferencedCard() {
		return referencedCard;
	}

	public void setReferencedCard(CardFormBean referencedCard) {
		this.referencedCard = referencedCard;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getFoilQuantity() {
		return foilQuantity;
	}

	public void setFoilQuantity(int foilQuantity) {
		this.foilQuantity = foilQuantity;
	}
}
