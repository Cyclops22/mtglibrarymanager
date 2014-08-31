package com.cyclops.library.mtg.service;

import java.util.List;

import com.cyclops.library.mtg.domain.DeckBean;
import com.cyclops.library.mtg.form.bean.DeckFormBean;

public interface DeckMgtService {
	
	void addDeck(DeckBean deckBean);

	List<DeckBean> findAll();
	
	DeckFormBean findById(int id);

}
