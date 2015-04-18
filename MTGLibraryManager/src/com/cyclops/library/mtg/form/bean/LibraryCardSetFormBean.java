package com.cyclops.library.mtg.form.bean;

import java.util.HashMap;
import java.util.Map;

public class LibraryCardSetFormBean {
	
	private CardFormBean card;
	private Map<SetFormBean, LibraryCardFormBean> libraryCardFormBeansBySet = new HashMap<>();
	
	public int getQuantity() {
		int quantity = 0;
		
		for (LibraryCardFormBean currLibraryCardFormBean : libraryCardFormBeansBySet.values()) {
			quantity += currLibraryCardFormBean.getQuantity();
		}
		
		return quantity;
	}
	
	public int getFoilQuantity() {
		int foilQuantity = 0;
		
		for (LibraryCardFormBean currLibraryCardFormBean : libraryCardFormBeansBySet.values()) {
			foilQuantity += currLibraryCardFormBean.getFoilQuantity();
		}
		
		return foilQuantity;
	} 
	
	public CardFormBean getCard() {
		return card;
	}

	public void setCard(CardFormBean card) {
		this.card = card;
	}

	public Map<SetFormBean, LibraryCardFormBean> getLibraryCardFormBeansBySet() {
		return libraryCardFormBeansBySet;
	}

	public void setLibraryCardFormBeansBySet(Map<SetFormBean, LibraryCardFormBean> libraryCardFormBeansBySet) {
		this.libraryCardFormBeansBySet = libraryCardFormBeansBySet;
	}
	
}
