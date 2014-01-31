package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.html.parsing.MagicCardsInfoParser;
import com.cyclops.library.mtg.html.parsing.TCGPlayerParser;
import com.cyclops.library.mtg.repository.SetMgtDAO;

@Service("setMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SetMgtServiceImpl implements SetMgtService {

	private SetMgtDAO mtgLibraryDAO;

	@Autowired
	public SetMgtServiceImpl(SetMgtDAO mtgLibraryDAO) {
		this.mtgLibraryDAO = mtgLibraryDAO;
	}

	public List<SetBean> retrieveAllSets() throws IOException {
		return new TCGPlayerParser().retrieveAllSets();
	}
	
	public List<SetBean> populateSets(List<SetBean> mtgSetBeans) throws IOException {
		return new MagicCardsInfoParser().retrieveSetsDetails(mtgSetBeans);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addMTGSet(SetBean mtgSetBean) {
		mtgLibraryDAO.create(mtgSetBean);
	}

	@Override
	public List<SetBean> findAll() {
		return mtgLibraryDAO.findAll();
	}

	@Override
	public void update(int id, SetBean mtgSetFormBean) {
		// TODO Auto-generated method stub
		
	}
}
