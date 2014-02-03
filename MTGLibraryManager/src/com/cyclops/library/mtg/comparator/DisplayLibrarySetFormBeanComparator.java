package com.cyclops.library.mtg.comparator;

import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.Constants;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public class DisplayLibrarySetFormBeanComparator implements Comparator<LibrarySetFormBean> {
	
	@Override
	public int compare(LibrarySetFormBean o1, LibrarySetFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		try {
			String o1ReleaseDate = StringUtils.defaultString(o1.getReferencedSet().getReleaseDate(), "January 1900");
			String o2ReleaseDate = StringUtils.defaultString(o2.getReferencedSet().getReleaseDate(), "January 1900");
			
			ctb.append(Constants.RELEASE_DATE_DATEFORMAT.parse(o2ReleaseDate), Constants.RELEASE_DATE_DATEFORMAT.parse(o1ReleaseDate));
			
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		
		ctb.append(o1.getReferencedSet().getName(), o2.getReferencedSet().getName());
		
		return ctb.toComparison();
	}

}
