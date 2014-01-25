package com.cyclops.library.mtg.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.MTGSetBean;

@Repository("mtgLibraryDAO")
@Transactional
public class MTGLibraryDAOImpl implements MTGLibraryDAO {

	private EntityManager em;
	 
    public EntityManager getEm() {
        return em;
    }
 
    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void create(MTGSetBean mtgSetBean){        
        em.persist(mtgSetBean);
        return;         
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<MTGSetBean> findAll() {
		Query query = em.createQuery("SELECT s FROM MTGSetBean s");
		return query.getResultList();
    }
    
}
