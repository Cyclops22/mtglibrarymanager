package com.cyclops.library.mtg.service;

import java.util.List;

import com.cyclops.library.mtg.form.bean.ImportSetFormBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;

public interface SetMgtService {
	
	List<SetFormBean> findAll();
	
//	void addSet(SetBeanOld mtgSetBean);
	
	void addSets(List<ImportSetFormBean> importSetFormBeans);

	List<ImportSetFormBean> getAvailableSets();

	SetFormBean getSetByCode(String code);

//	List<SetBeanOld> findAll();
//
//	List<SetBeanOld> retrieveAllSets() throws IOException;
//
//	void update(int id, SetBeanOld mtgSetBean);
//
//	List<EditionCardBean> getAllCards();

}
