package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.List;

import com.cyclops.library.mtg.domain.SetBean;

public interface SetMgtService {

	void addMTGSet(SetBean mtgSetBean);
	
	public List<SetBean> findAll();

	List<SetBean> retrieveAllSets() throws IOException;

	public void update(int id, SetBean mtgSetBean);

}
