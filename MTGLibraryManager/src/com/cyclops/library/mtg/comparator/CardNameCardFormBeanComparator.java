package com.cyclops.library.mtg.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.form.bean.CardFormBean;

public class CardNameCardFormBeanComparator implements Comparator<CardFormBean> {
	
	@Override
	public int compare(CardFormBean o1, CardFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		ctb.append(o1.getName(), o2.getName());
		
		return ctb.toComparison();
	}

}
