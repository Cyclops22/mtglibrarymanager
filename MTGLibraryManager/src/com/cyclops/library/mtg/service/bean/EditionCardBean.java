package com.cyclops.library.mtg.service.bean;

import com.cyclops.library.mtg.domain.CardBean;

public class EditionCardBean {
	
	private CardBean card;
//	private Set<SetBeanOld> sets = new LinkedHashSet<>();
	
	public CardBean getCard() {
		return card;
	}

	public void setCard(CardBean card) {
		this.card = card;
	}

//	public Set<SetBeanOld> getSets() {
//		return sets;
//	}
//
//	public void setSets(Set<SetBeanOld> sets) {
//		this.sets = sets;
//	}
	
	
}
