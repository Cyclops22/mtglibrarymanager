package com.cyclops.library.mtg.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.service.bean.EditionCardBean;

public class EditionCardBeanCardNameComparator implements Comparator<EditionCardBean> {

	@Override
	public int compare(EditionCardBean o1, EditionCardBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();

		ctb.append(o1.getCard().getName().replaceAll("Æ", "Ae").toLowerCase(), o2.getCard().getName().replaceAll("Æ", "Ae").toLowerCase());
		
		return ctb.toComparison();
	}

}
