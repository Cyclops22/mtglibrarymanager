package com.cyclops.library.mtg.form.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.cyclops.library.mtg.form.bean.EditionCardFormBean;
import com.cyclops.library.mtg.service.bean.EditionCardBean;

public class EditionCardFormBeanMapper extends AbstractFormBeanMapper<EditionCardBean, EditionCardFormBean> {
	
	private CardFormBeanMapper cardFormBeanMapper = new CardFormBeanMapper();
	private SetFormBeanMapper setFormBeanMapper = new SetFormBeanMapper();

	@Override
	public EditionCardBean toBean(EditionCardFormBean formBean) {
		EditionCardBean editionCard = null;
		
		if (formBean != null) {
			editionCard = new EditionCardBean();
			
			editionCard.setCard(cardFormBeanMapper.toBean(formBean.getCard()));
			editionCard.setSets(new LinkedHashSet<>(setFormBeanMapper.toBean(formBean.getSets())));
		}
		
		return editionCard;
	}

	@Override
	public EditionCardFormBean toFormBean(EditionCardBean bean) {
		EditionCardFormBean editionCardForm = null;
		
		if (bean != null) {
			editionCardForm = new EditionCardFormBean();
			
			editionCardForm.setCard(cardFormBeanMapper.toFormBean(bean.getCard()));
			editionCardForm.setSets(setFormBeanMapper.toFormBean(new ArrayList<>(bean.getSets())));
		}
		
		return editionCardForm;
	}
}
