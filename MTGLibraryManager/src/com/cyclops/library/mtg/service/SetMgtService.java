package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.List;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.service.bean.EditionCardBean;

public interface SetMgtService {

	void addMTGSet(SetBean mtgSetBean);
	
	List<SetBean> findAll();

	List<SetBean> retrieveAllSets() throws IOException;

	void update(int id, SetBean mtgSetBean);

	List<EditionCardBean> getAllCards();

}
