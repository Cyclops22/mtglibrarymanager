package com.cyclops.library.mtg.form.mapper;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.form.bean.CardFormBean;

public class CardFormBeanMapper extends AbstractFormBeanMapper<CardBean, CardFormBean> {
	
	private boolean useSimpleRarity;
	
	public CardFormBeanMapper() {
		this(true);
	}
	
	public CardFormBeanMapper(boolean useSimpleRarity) {
		this.useSimpleRarity = useSimpleRarity;
	}

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
			card.setRarity(simplifyRarity(formBean.getRarity()));
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
			cardForm.setRarity(simplifyRarity(bean.getRarity()));
			cardForm.setType(bean.getType());
			cardForm.setUrl(bean.getUrl());
		}
		
		return cardForm;
	}
	
	private String simplifyRarity(String rarity) {
		String simplifiedRarity = null;
		
		if (useSimpleRarity) {
			int idx = rarity.indexOf('(');
			if (idx != -1) {
				simplifiedRarity = rarity.substring(0, idx).trim();
				
			} else {
				simplifiedRarity = rarity;
				
			}
		}
		
		return simplifiedRarity;
	}

}
