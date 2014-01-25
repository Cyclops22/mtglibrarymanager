package com.cyclops.library.mtg.repository;

import java.util.List;

import com.cyclops.library.mtg.domain.MTGSetBean;

public interface MTGLibraryDAO {

	void create(MTGSetBean mtgSetBean);
	
	public List<MTGSetBean> findAll();

}
