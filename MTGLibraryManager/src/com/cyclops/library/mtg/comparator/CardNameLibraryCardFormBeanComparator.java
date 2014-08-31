package com.cyclops.library.mtg.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;

public class CardNameLibraryCardFormBeanComparator implements Comparator<LibraryCardFormBean> {
	
	@Override
	public int compare(LibraryCardFormBean o1, LibraryCardFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		ctb.append(o1.getReferencedCard().getName(), o2.getReferencedCard().getName());
		
		return ctb.toComparison();
	}

}
