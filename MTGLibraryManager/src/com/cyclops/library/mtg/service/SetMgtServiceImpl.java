package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.comparator.EditionCardBeanCardNameComparator;
import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.html.parsing.MagicCardsInfoParser;
import com.cyclops.library.mtg.html.parsing.TCGPlayerParser;
import com.cyclops.library.mtg.html.parsing.WizardsParser;
import com.cyclops.library.mtg.repository.SetMgtDAO;
import com.cyclops.library.mtg.service.bean.EditionCardBean;

@Service("setMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SetMgtServiceImpl implements SetMgtService {

	private SetMgtDAO mtgLibraryDAO;
	
	@Autowired
	private TCGPlayerParser tcgPlayerParser;
	
	@Autowired
	private MagicCardsInfoParser magicCardsInfoParser;
	
	@Autowired
	private WizardsParser wizardsParser;

	@Autowired
	public SetMgtServiceImpl(SetMgtDAO mtgLibraryDAO) {
		this.mtgLibraryDAO = mtgLibraryDAO;
	}

	public List<SetBean> retrieveAllSets() throws IOException {
		List<SetBean> allSets = tcgPlayerParser.retrieveAllSets();
		allSets = wizardsParser.retrieveSetsDetails(allSets);
		allSets = magicCardsInfoParser.retrieveSetsDetails(allSets);
		
		return allSets;
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
	
	public List<EditionCardBean> getAllCards() {
		Map<String, EditionCardBean> editionSetByCardName = new HashMap<>();
		
		List<SetBean> sets = findAll();
		for (SetBean currSetBean : sets) {
			for (CardBean currCardBean : currSetBean.getCards()) {
				EditionCardBean editionCardBean = editionSetByCardName.get(currCardBean.getName());
				
				if (editionCardBean == null) {
					editionCardBean = new EditionCardBean();
					
					editionCardBean.setCard(currCardBean);
				}
				
				editionCardBean.getSets().add(currSetBean);
				
				editionSetByCardName.put(currCardBean.getName(), editionCardBean);
			}
		}
		
		List<EditionCardBean> result = new ArrayList<>(editionSetByCardName.values());
		Collections.sort(result, new EditionCardBeanCardNameComparator());
		
		return result;
	}

	@Override
	public void update(int id, SetBean mtgSetFormBean) {
		// TODO Auto-generated method stub
		
	}

	public TCGPlayerParser getTcgPlayerParser() {
		return tcgPlayerParser;
	}

	public void setTcgPlayerParser(TCGPlayerParser tcgPlayerParser) {
		this.tcgPlayerParser = tcgPlayerParser;
	}

	public MagicCardsInfoParser getMagicCardsInfoParser() {
		return magicCardsInfoParser;
	}

	public void setMagicCardsInfoParser(MagicCardsInfoParser magicCardsInfoParser) {
		this.magicCardsInfoParser = magicCardsInfoParser;
	}

	public WizardsParser getWizardsParser() {
		return wizardsParser;
	}

	public void setWizardsParser(WizardsParser wizardsParser) {
		this.wizardsParser = wizardsParser;
	}
}
