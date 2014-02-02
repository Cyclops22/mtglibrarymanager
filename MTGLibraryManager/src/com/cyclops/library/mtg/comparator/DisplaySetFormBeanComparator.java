package com.cyclops.library.mtg.comparator;

import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.Constants;
import com.cyclops.library.mtg.form.bean.SetFormBean;

public class DisplaySetFormBeanComparator implements Comparator<SetFormBean> {
	
	@Override
	public int compare(SetFormBean o1, SetFormBean o2) {
		CompareToBuilder ctb = new CompareToBuilder();
		
		try {
			String o1ReleaseDate = StringUtils.defaultString(o1.getReleaseDate(), "January 1900");
			String o2ReleaseDate = StringUtils.defaultString(o2.getReleaseDate(), "January 1900");
			
			ctb.append(Constants.RELEASE_DATE_DATEFORMAT.parse(o2ReleaseDate), Constants.RELEASE_DATE_DATEFORMAT.parse(o1ReleaseDate));
			
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		
		ctb.append(o1.getName(), o2.getName());
		
		return ctb.toComparison();
	}

}
