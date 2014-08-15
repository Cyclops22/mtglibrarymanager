package com.cyclops.library.mtg.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.form.bean.SetFormBean;

public class ReleaseDateSetFormBeanComparator implements Comparator<SetFormBean> {
	
	@Override
	public int compare(SetFormBean o1, SetFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		ctb.append(o1.getReleaseDate(), o2.getReleaseDate());
		
		return ctb.toComparison();
	}

}
