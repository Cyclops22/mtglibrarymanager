package com.cyclops.library.mtg.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class LibraryCardBean {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="card_id")
	private CardBean referencedCard;
	
	private int quantity;
	
	private int foilQuantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CardBean getReferencedCard() {
		return referencedCard;
	}

	public void setReferencedCard(CardBean referencedCard) {
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
