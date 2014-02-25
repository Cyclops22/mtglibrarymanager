package com.cyclops.library.mtg.service;

import java.util.List;

import com.cyclops.library.mtg.domain.DeckBean;

public interface DeckMgtService {
	
	void addDeck(DeckBean deckBean);

	List<DeckBean> findAll();
	
	DeckBean findById(int id);

}
