package com.cyclops.library.mtg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.DeckBean;
import com.cyclops.library.mtg.form.bean.DeckFormBean;
import com.cyclops.library.mtg.form.mapper.DeckFormBeanMapper;

@Service("deckMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeckMgtServiceImpl implements DeckMgtService {

//	private SetMgtDAO mtgLibraryDAO;
//	
//	@Autowired
//	private TCGPlayerParser tcgPlayerParser;
//	
//	@Autowired
//	private MagicCardsInfoParser magicCardsInfoParser;
//	
//	@Autowired
//	private WizardsParser wizardsParser;
	
	@Autowired
	private DeckFormBeanMapper deckFormBeanMapper;

//	@Autowired
//	public DeckMgtServiceImpl(SetMgtDAO mtgLibraryDAO) {
//		this.mtgLibraryDAO = mtgLibraryDAO;
//	}
	
	private static List<DeckBean> decks = new ArrayList<>();
	
	static {
		decks.add(new DeckBean());
		decks.get(0).setId(0);
		decks.get(0).setName("Deck A");
		
		decks.add(new DeckBean());
		decks.get(1).setId(1);
		decks.get(1).setName("Deck B");
		
		decks.add(new DeckBean());
		decks.get(2).setId(2);
		decks.get(2).setName("Deck C");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addDeck(DeckBean deckBean) {
		decks.add(deckBean);
	}

	@Override
	public List<DeckBean> findAll() {
		return new ArrayList<>(decks);
	}
	
	@Override
	public DeckFormBean findById(int id) {
		DeckFormBean deckFormBean = new DeckFormBean();
		
		for (DeckBean currDeckBean : decks) {
			if (currDeckBean.getId() == id) {
				return deckFormBeanMapper.toFormBean(currDeckBean);
			}
		}
		
		return deckFormBean;
	}
	
//	public List<SetBean> retrieveAllSets() throws IOException {
//		List<SetBean> allSets = tcgPlayerParser.retrieveAllSets();
//		allSets = wizardsParser.retrieveSetsDetails(allSets);
//		allSets = magicCardsInfoParser.retrieveSetsDetails(allSets);
//		
//		return allSets;
//	}
//	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//	public void addMTGSet(SetBean mtgSetBean) {
//		mtgLibraryDAO.create(mtgSetBean);
//	}
//
//	@Override
//	public List<SetBean> findAll() {
//		return mtgLibraryDAO.findAll();
//	}
//
//	@Override
//	public void update(int id, SetBean mtgSetFormBean) {
//		// TODO Auto-generated method stub
//		
//	}
}
