package com.cyclops.library.mtg.comparator;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;

public class CardNumberLibraryCardFormBeanComparator implements Comparator<LibraryCardFormBean> {
	
	private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("(\\d+)([a-z]?)");
	
	@Override
	public int compare(LibraryCardFormBean o1, LibraryCardFormBean o2) {
		Integer o1NumberPart = 0;
		Integer o2NumberPart = 0;
		String o1StringPart = StringUtils.EMPTY;
		String o2StringPart = StringUtils.EMPTY;
		
		CompareToBuilder ctb = new CompareToBuilder();
		
		Matcher o1Matcher = CARD_NUMBER_PATTERN.matcher(o1.getReferencedCard().getNumber());
		if (o1Matcher.matches()) {
			o1NumberPart = Integer.valueOf(o1Matcher.group(1));
			
			if (o1Matcher.groupCount() == 2) {
				o1StringPart = o1Matcher.group(2);
			}
		}
		
		Matcher o2Matcher = CARD_NUMBER_PATTERN.matcher(o2.getReferencedCard().getNumber());
		if (o2Matcher.matches()) {
			o2NumberPart = Integer.valueOf(o2Matcher.group(1));
			
			if (o2Matcher.groupCount() == 2) {
				o2StringPart = o2Matcher.group(2);
			}
		}
		
		ctb.append(o1NumberPart, o2NumberPart);
		ctb.append(o1StringPart, o2StringPart);
		
		return ctb.toComparison();
	}

}
