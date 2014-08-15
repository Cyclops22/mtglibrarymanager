package com.cyclops.library.mtg.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibraryCardBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.domain.SetBean;

@Repository("libraryMgtDAO")
@Transactional
public class LibraryMgtDAOImpl implements LibraryMgtDAO {
	
	private EntityManager em;
	
	@Autowired
	private SetMgtDAO setMgtDao;
	 
    public EntityManager getEm() {
        return em;
    }
 
    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void createLibrary(LibraryBean libraryBean){        
        em.persist(libraryBean);
    }
    
    public void addSetToLibrary(int libraryId, int setId) {
    	List<LibraryCardBean> cards = new ArrayList<>();
    	
    	LibraryBean library = findLibraryById(libraryId);
		SetBean set = setMgtDao.findById(setId);
		
		for (CardBean currSetCard : set.getCards()) {
			LibraryCardBean libraryCardBean = new LibraryCardBean();
			
			libraryCardBean.setReferencedCard(currSetCard);
			
			cards.add(libraryCardBean);
		}
		
		LibrarySetBean librarySet = new LibrarySetBean();
		librarySet.setReferencedSet(set);
		librarySet.setCards(cards);
		
		library.getSets().add(librarySet);
		
    	em.persist(library);
    }
    
    public void removeSetFromLibrary(int libraryId, int setId){
    	LibraryBean library = findLibraryById(libraryId);
    	
    	for (Iterator<LibrarySetBean> iter = library.getSets().iterator(); iter.hasNext(); ) {
    		LibrarySetBean currLibrarySetBean = iter.next();
    		
    		if (currLibrarySetBean.getReferencedSet().getId() == setId) {
    			iter.remove();
    			break;
    		}
    	}
    	
        em.persist(library);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<LibraryBean> findAllLibraries() {
		Query query = em.createQuery("SELECT l FROM " + LibraryBean.class.getName() + " l");
		return query.getResultList();
    }

	@Override
	public LibraryBean findLibraryById(int id) {
		return em.find(LibraryBean.class, id);
	}
	
	@Override
	public LibrarySetBean findLibrarySetById(int id) {
		return em.find(LibrarySetBean.class, id);
	}

	@Override
	public void updateLibrarySetQuantities(LibrarySetBean bean) {
		Map<Integer, LibraryCardBean> editedCardBeanById = new HashMap<Integer, LibraryCardBean>();
		
		for (LibraryCardBean currEditedLibraryCard : bean.getCards()) {
			editedCardBeanById.put(currEditedLibraryCard.getId(), currEditedLibraryCard);
		}
		
		LibrarySetBean persistedLibrarySet = findLibrarySetById(bean.getId());
		for (LibraryCardBean currLibraryCard : persistedLibrarySet.getCards()) {
			LibraryCardBean newValues = editedCardBeanById.get(currLibraryCard.getId());
			
			currLibraryCard.setFoilQuantity(newValues.getFoilQuantity());
			currLibraryCard.setQuantity(newValues.getQuantity());
		}
		
		em.persist(persistedLibrarySet);
	}
}
