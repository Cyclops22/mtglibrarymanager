package com.cyclops.library.mtg.form.mapper;

import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.DeckBean;
import com.cyclops.library.mtg.form.bean.DeckFormBean;

@Component("deckFormBeanMapper")
public class DeckFormBeanMapper extends AbstractFormBeanMapper<DeckBean, DeckFormBean> {
	
//	private CardFormBeanMapper cardFormBeanMapper = new CardFormBeanMapper();

	@Override
	public DeckBean toBean(DeckFormBean formBean) {
		DeckBean deck = null;
		
		if (formBean != null) {
			deck = new DeckBean();
			
//			deck.setAbbreviation(formBean.getAbbreviation());
//			deck.setCategory(SetCategory.valueOf(formBean.getCategory()));
			deck.setId(formBean.getId());
//			deck.setLogoUrl(formBean.getImageUrl());
//			deck.setLanguage(formBean.getLanguage());
			deck.setName(formBean.getName());
//			deck.setUrl(formBean.getUrl());
//			
//			if (StringUtils.isNotBlank(formBean.getReleaseDate())) {
//				try {
//					deck.setReleaseDate(Constants.RELEASE_DATE_DATEFORMAT.parse(formBean.getReleaseDate()));
//					
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}	
//			}
//			
//			deck.setCards(cardFormBeanMapper.toBean(formBean.getCards()));
		}
		
		return deck;
	}

	@Override
	public DeckFormBean toFormBean(DeckBean bean) {
		DeckFormBean deckForm = null;
		
		if (bean != null) {
			deckForm = new DeckFormBean();
			
//			deckForm.setAbbreviation(bean.getAbbreviation());
//			deckForm.setCategory(bean.getCategory().toString());
			deckForm.setId(bean.getId());
//			deckForm.setImageUrl(bean.getLogoUrl());
//			deckForm.setLanguage(bean.getLanguage());
			deckForm.setName(bean.getName());
//			deckForm.setUrl(bean.getUrl());
//			
//			if (bean.getReleaseDate() != null) {
//				deckForm.setReleaseDate(Constants.RELEASE_DATE_DATEFORMAT.format(bean.getReleaseDate()));
//			}
//			
//			deckForm.setCards(cardFormBeanMapper.toFormBean(bean.getCards()));
		}
		
		return deckForm;
	}
}
