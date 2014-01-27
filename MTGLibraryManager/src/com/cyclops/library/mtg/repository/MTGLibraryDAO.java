package com.cyclops.library.mtg.repository;

import java.util.List;

import com.cyclops.library.mtg.domain.SetBean;

public interface MTGLibraryDAO {

	void create(SetBean mtgSetBean);
	
	public List<SetBean> findAll();

}
