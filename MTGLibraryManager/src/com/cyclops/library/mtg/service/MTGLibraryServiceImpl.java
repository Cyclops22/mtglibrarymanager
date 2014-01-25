package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.MTGSetBean;
import com.cyclops.library.mtg.form.bean.MTGSetFormBean;
import com.cyclops.library.mtg.html.parsing.MagicCardsInfoParser;
import com.cyclops.library.mtg.html.parsing.TCGPlayerParser;
import com.cyclops.library.mtg.repository.MTGLibraryDAO;

@Service("mtgLibraryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MTGLibraryServiceImpl implements MTGLibraryService {

	private MTGLibraryDAO mtgLibraryDAO;

	@Autowired
	public MTGLibraryServiceImpl(MTGLibraryDAO mtgLibraryDAO) {
		this.mtgLibraryDAO = mtgLibraryDAO;
	}

	public MTGLibraryServiceImpl() {

	}
	
	public List<MTGSetBean> retrieveAllSets() throws IOException {
		return new TCGPlayerParser().retrieveAllSets();
	}
	
	public List<MTGSetFormBean> populateSets(List<MTGSetFormBean> mtgSetFormBeans) throws IOException {
		return new MagicCardsInfoParser().retrieveSetsDetails(mtgSetFormBeans);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addMTGSet(MTGSetBean mtgSetBean) {
		mtgLibraryDAO.create(mtgSetBean);
	}

	@Override
	public List<MTGSetBean> findAll() {
		return mtgLibraryDAO.findAll();
	}
}
