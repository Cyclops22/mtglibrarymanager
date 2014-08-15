package com.cyclops.library.mtg.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.SetBean;

@Repository("setMgtDAO")
@Transactional
public class SetMgtDAOImpl implements SetMgtDAO {

	private EntityManager em;
	 
    public EntityManager getEm() {
        return em;
    }
 
    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void create(SetBean setBean) {        
        em.persist(setBean);
        return;         
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SetBean> findAll() {
		Query query = em.createQuery("SELECT s FROM " + SetBean.class.getName() + " s");
		return query.getResultList();
    }
	
	@Override
	public SetBean findByCode(String code) {
		Query query = em.createQuery("SELECT s FROM " + SetBean.class.getName() + " s where s.code = :code");
		query.setParameter("code", code);
		
		return (SetBean) query.getSingleResult();
	}

	@Override
	public SetBean findById(int id) {
		return em.find(SetBean.class, id);
	}
}
