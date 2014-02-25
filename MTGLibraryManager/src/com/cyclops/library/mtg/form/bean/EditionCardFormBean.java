package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;

public class EditionCardFormBean {
	
	private CardFormBean card;
	private List<SetFormBean> sets = new ArrayList<>();
	
	public CardFormBean getCard() {
		return card;
	}

	public void setCard(CardFormBean card) {
		this.card = card;
	}

	public List<SetFormBean> getSets() {
		return sets;
	}

	public void setSets(List<SetFormBean> sets) {
		this.sets = sets;
	}
	
	
}
