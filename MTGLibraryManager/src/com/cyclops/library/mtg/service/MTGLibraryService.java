package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.List;

import com.cyclops.library.mtg.domain.MTGSetBean;
import com.cyclops.library.mtg.form.bean.MTGSetFormBean;

public interface MTGLibraryService {

	public void addMTGSet(MTGSetBean mtgSetBean);
	
	public List<MTGSetBean> findAll();

	List<MTGSetBean> retrieveAllSets() throws IOException;

	List<MTGSetFormBean> populateSets(List<MTGSetFormBean> mtgSetFormBeans) throws IOException;

}
