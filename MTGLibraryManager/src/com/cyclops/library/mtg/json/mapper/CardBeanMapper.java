package com.cyclops.library.mtg.json.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.json.bean.CardJsonBean;

@Component
public class CardBeanMapper {

	public CardBean toCardBean(CardJsonBean cardJsonBean) {
		CardBean cardBean = new CardBean();
		
		cardBean.setArtist(cardJsonBean.getArtist());
		cardBean.setBorder(cardJsonBean.getBorder());
		cardBean.setCmc(cardJsonBean.getCmc());
		cardBean.setColors(StringUtils.join(cardJsonBean.getColors(), ','));
		cardBean.setFlavor(cardJsonBean.getFlavor());
		cardBean.setHand(cardJsonBean.getHand());
		cardBean.setImageName(cardJsonBean.getImageName());
		cardBean.setLayout(cardJsonBean.getLayout());
		cardBean.setLife(cardJsonBean.getLife());
		cardBean.setLoyalty(cardJsonBean.getLoyalty());
		cardBean.setManaCost(cardJsonBean.getManaCost());
		cardBean.setMultiverseid(cardJsonBean.getMultiverseid());
		cardBean.setName(cardJsonBean.getName());
		cardBean.setNames(StringUtils.join(cardJsonBean.getNames(), ','));
		cardBean.setNumber(cardJsonBean.getNumber());
		cardBean.setPower(cardJsonBean.getPower());
		cardBean.setRarity(cardJsonBean.getRarity());
		cardBean.setReserved(Boolean.valueOf(cardJsonBean.getReserved()));
		cardBean.setSubtypes(StringUtils.join(cardJsonBean.getSubtypes(), ','));
		cardBean.setSupertypes(StringUtils.join(cardJsonBean.getSupertypes(), ','));
		cardBean.setText(cardJsonBean.getText());
		cardBean.setTimeshifted(Boolean.valueOf(cardJsonBean.getTimeshifted()));
		cardBean.setToughness(cardJsonBean.getToughness());
		cardBean.setType(cardJsonBean.getType());
		cardBean.setTypes(StringUtils.join(cardJsonBean.getTypes(), ','));
		cardBean.setVariations(StringUtils.join(cardJsonBean.getVariations(), ','));
		cardBean.setWatermark(cardJsonBean.getWatermark());
		
		return cardBean;
	}
	
	public List<CardBean> toCardBeans(List<CardJsonBean> cardJsonBeans) {
		List<CardBean> cardBeans = new ArrayList<>();
		
		for (CardJsonBean currCardJsonBean : cardJsonBeans) {
			cardBeans.add(toCardBean(currCardJsonBean));
		}
		
		return cardBeans;
	}
}
