package com.cyclops.library.mtg.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public class ReleaseDateLibrarySetFormBeanComparator implements Comparator<LibrarySetFormBean> {
	
	@Override
	public int compare(LibrarySetFormBean o1, LibrarySetFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		ctb.append(o1.getReferencedSet().getReleaseDate(), o2.getReferencedSet().getReleaseDate());
		
		return ctb.toComparison();
	}

}
