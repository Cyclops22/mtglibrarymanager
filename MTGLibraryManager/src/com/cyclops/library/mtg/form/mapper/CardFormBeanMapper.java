package com.cyclops.library.mtg.form.mapper;

import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.form.bean.CardFormBean;

@Component("cardFormBeanMapper")
public class CardFormBeanMapper extends AbstractFormBeanMapper<CardBean, CardFormBean> {
	
	@Override
	public CardBean toBean(CardFormBean formBean) {
		CardBean card = null;
		
		if (formBean != null) {
			card = new CardBean();
			
			card.setArtist(formBean.getArtist());
			card.setBorder(formBean.getBorder());
			card.setCmc(formBean.getCmc());
			card.setColors(formBean.getColors());
			card.setFlavor(formBean.getFlavor());
			card.setHand(formBean.getHand());
			card.setId(formBean.getId());
			card.setImageName(formBean.getImageName());
			card.setLayout(formBean.getLayout());
			card.setLife(formBean.getLife());
			card.setLoyalty(formBean.getLoyalty());
			card.setManaCost(formBean.getManaCost());
			card.setMultiverseid(formBean.getMultiverseid());
			card.setName(formBean.getName());
			card.setNames(formBean.getNames());
			card.setNumber(formBean.getNumber());
			card.setPower(formBean.getPower());
			card.setRarity(formBean.getRarity());
			card.setReserved(formBean.isReserved());
			card.setSubtypes(formBean.getSubtypes());
			card.setSupertypes(formBean.getSupertypes());
			card.setText(formBean.getText());
			card.setTimeshifted(formBean.isTimeshifted());
			card.setToughness(formBean.getToughness());
			card.setType(formBean.getType());
			card.setTypes(formBean.getTypes());
			card.setVariations(formBean.getVariations());
			card.setWatermark(formBean.getWatermark());
		}
		
		return card;
	}

	@Override
	public CardFormBean toFormBean(CardBean bean) {
		CardFormBean cardForm = null;
		
		if (bean != null) {
			cardForm = new CardFormBean();
			
			cardForm.setArtist(bean.getArtist());
			cardForm.setBorder(bean.getBorder());
			cardForm.setCmc(bean.getCmc());
			cardForm.setColors(bean.getColors());
			cardForm.setFlavor(bean.getFlavor());
			cardForm.setHand(bean.getHand());
			cardForm.setId(bean.getId());
			cardForm.setImageName(bean.getImageName());
			cardForm.setLayout(bean.getLayout());
			cardForm.setLife(bean.getLife());
			cardForm.setLoyalty(bean.getLoyalty());
			cardForm.setManaCost(bean.getManaCost());
			cardForm.setMultiverseid(bean.getMultiverseid());
			cardForm.setName(bean.getName());
			cardForm.setNames(bean.getNames());
			cardForm.setNumber(bean.getNumber());
			cardForm.setPower(bean.getPower());
			cardForm.setRarity(bean.getRarity());
			cardForm.setReserved(bean.isReserved());
			cardForm.setSubtypes(bean.getSubtypes());
			cardForm.setSupertypes(bean.getSupertypes());
			cardForm.setText(bean.getText());
			cardForm.setTimeshifted(bean.isTimeshifted());
			cardForm.setToughness(bean.getToughness());
			cardForm.setType(bean.getType());
			cardForm.setTypes(bean.getTypes());
			cardForm.setVariations(bean.getVariations());
			cardForm.setWatermark(bean.getWatermark());
			
		}
		
		return cardForm;
	}
}
