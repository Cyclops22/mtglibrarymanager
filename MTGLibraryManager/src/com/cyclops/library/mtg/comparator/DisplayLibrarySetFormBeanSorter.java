package com.cyclops.library.mtg.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyclops.library.mtg.domain.SetCategory;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public class DisplayLibrarySetFormBeanSorter {
	
	private static final DisplayLibrarySetFormBeanComparator LIBRARY_SET_FORM_BEAN_COMPARATOR = new DisplayLibrarySetFormBeanComparator();

	public static List<LibrarySetFormBean> sortForDisplay(List<LibrarySetFormBean> beansToSort) {
		Map<SetCategory, List<LibrarySetFormBean>> setByCategory = new HashMap<>();
		for (SetCategory currSetCategory : SetCategory.values()) {
			setByCategory.put(currSetCategory, new ArrayList<LibrarySetFormBean>());
		}
		
		for (LibrarySetFormBean currLibrarySetFormBean : beansToSort) {
			setByCategory.get(SetCategory.valueOf(currLibrarySetFormBean.getReferencedSet().getCategory())).add(currLibrarySetFormBean);
		}
		
		for (List<LibrarySetFormBean> currList : setByCategory.values()) {
			Collections.sort(currList, LIBRARY_SET_FORM_BEAN_COMPARATOR);			
		}
		
		List<LibrarySetFormBean> result = new ArrayList<>();
		
		result.addAll(setByCategory.get(SetCategory.EXPANSION));
		result.addAll(setByCategory.get(SetCategory.CORE_SET));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET));
		result.addAll(setByCategory.get(SetCategory.OTHER));
		
		return result;
	}
}
