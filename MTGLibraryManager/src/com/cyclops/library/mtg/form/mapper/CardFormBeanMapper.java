package com.cyclops.library.mtg.form.mapper;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.form.bean.CardFormBean;

public class CardFormBeanMapper extends AbstractFormBeanMapper<CardBean, CardFormBean> {

	@Override
	public CardBean toBean(CardFormBean formBean) {
		CardBean card = null;
		
		if (formBean != null) {
			card = new CardBean();
			
			card.setId(formBean.getId());
			card.setImageUrl(formBean.getImageUrl());
			card.setMana(formBean.getMana());
			card.setName(formBean.getName());
			card.setNumber(formBean.getNumber());
			card.setRarity(formBean.getRarity());
			card.setType(formBean.getType());
			card.setUrl(formBean.getUrl());
		}
		
		return card;
	}

	@Override
	public CardFormBean toFormBean(CardBean bean) {
		CardFormBean cardForm = null;
		
		if (bean != null) {
			cardForm = new CardFormBean();
			
			cardForm.setId(bean.getId());
			cardForm.setImageUrl(bean.getImageUrl());
			cardForm.setMana(bean.getMana());
			cardForm.setName(bean.getName());
			cardForm.setNumber(bean.getNumber());
			cardForm.setRarity(bean.getRarity());
			cardForm.setType(bean.getType());
			cardForm.setUrl(bean.getUrl());
		}
		
		return cardForm;
	}

}
