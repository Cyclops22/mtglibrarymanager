package com.cyclops.library.mtg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.bean.ImportSetFormBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.mapper.SetFormBeanMapper;
import com.cyclops.library.mtg.json.bean.SetJsonBean;
import com.cyclops.library.mtg.json.mapper.SetBeanMapper;
import com.cyclops.library.mtg.json.parsing.MTGJsonParser;
import com.cyclops.library.mtg.repository.SetMgtDAO;

@Service("setMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SetMgtServiceImpl implements SetMgtService {
	
	private SetMgtDAO mtgLibraryDAO;
	
	@Autowired
	private MTGJsonParser mtgJsonParser;
	
	@Autowired
	private SetBeanMapper setBeanMapper;
	
	@Autowired
	private SetFormBeanMapper setFormBeanMapper;
	
	@Autowired
	public SetMgtServiceImpl(SetMgtDAO mtgLibraryDAO) {
		this.mtgLibraryDAO = mtgLibraryDAO;
	}
	
	@Override
	public List<SetFormBean> findAll() {
		List<SetBean> allSetBeans = mtgLibraryDAO.findAll();
		
		return setFormBeanMapper.toFormBean(allSetBeans);
	}
	
	@Override
	public List<ImportSetFormBean> getAvailableSets() {
		List<ImportSetFormBean> availableSets = new ArrayList<>();
		
		for (SetJsonBean currSetJsonBean : mtgJsonParser.getAvailableSets()) {
			ImportSetFormBean importSetFormBean = new ImportSetFormBean();
			
			importSetFormBean.setCode(currSetJsonBean.getCode());
			importSetFormBean.setName(currSetJsonBean.getName());
			importSetFormBean.setReleaseDate(currSetJsonBean.getReleaseDate());
			
			availableSets.add(importSetFormBean);
		}
		
		return availableSets;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addSets(List<ImportSetFormBean> importSetFormBeans) {
		List<String> setCodesToImport = new ArrayList<>();
		
		for (ImportSetFormBean currImportSetFormBean : importSetFormBeans) {
			setCodesToImport.add(currImportSetFormBean.getCode());
		}
		
		List<SetJsonBean> setJsonBeans = mtgJsonParser.getSets(setCodesToImport);
		
		for (SetBean currSetBean : setBeanMapper.toSetBeans(setJsonBeans)) {
			mtgLibraryDAO.create(currSetBean);
		}
	}
	
	@Override
	public SetFormBean getSetByCode(String code) {
		return setFormBeanMapper.toFormBean(mtgLibraryDAO.findByCode(code));
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
//	public void addSet(SetBean mtgSetBean) {
//		mtgLibraryDAO.create(mtgSetBean);
//	}
//	
//	public List<EditionCardBean> getAllCards() {
//		Map<String, EditionCardBean> editionSetByCardName = new HashMap<>();
//		
//		List<SetBean> sets = findAll();
//		for (SetBean currSetBean : sets) {
//			for (CardBean currCardBean : currSetBean.getCards()) {
//				EditionCardBean editionCardBean = editionSetByCardName.get(currCardBean.getName());
//				
//				if (editionCardBean == null) {
//					editionCardBean = new EditionCardBean();
//					
//					editionCardBean.setCard(currCardBean);
//				}
//				
//				editionCardBean.getSets().add(currSetBean);
//				
//				editionSetByCardName.put(currCardBean.getName(), editionCardBean);
//			}
//		}
//		
//		List<EditionCardBean> result = new ArrayList<>(editionSetByCardName.values());
//		Collections.sort(result, new EditionCardBeanCardNameComparator());
//		
//		return result;
//	}
//
//	@Override
//	public void update(int id, SetBean mtgSetFormBean) {
//		// TODO Auto-generated method stub
//		
//	}
}
