package com.cyclops.library.mtg.repository;

import java.util.List;

import com.cyclops.library.mtg.domain.SetBean;

public interface SetMgtDAO {

	void create(SetBean mtgSetBean);
	
	public List<SetBean> findAll();

	SetBean findByCode(String code);

	SetBean findById(int setId);

}
