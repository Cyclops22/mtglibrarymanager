package com.cyclops.library.mtg.service.bean;

import java.util.LinkedHashSet;
import java.util.Set;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.SetBean;

public class EditionCardBean {
	
	private CardBean card;
	private Set<SetBean> sets = new LinkedHashSet<>();
	
	public CardBean getCard() {
		return card;
	}

	public void setCard(CardBean card) {
		this.card = card;
	}

	public Set<SetBean> getSets() {
		return sets;
	}

	public void setSets(Set<SetBean> sets) {
		this.sets = sets;
	}
	
	
}
