package com.cyclops.library.mtg.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyclops.library.mtg.domain.SetCategory;
import com.cyclops.library.mtg.form.bean.SetFormBean;

public class DisplaySetFormBeanSorter {
	
	private static final DisplaySetFormBeanComparator SET_FORM_BEAN_COMPARATOR = new DisplaySetFormBeanComparator();

	public static List<SetFormBean> sortForDisplay(List<SetFormBean> beansToSort) {
		Map<SetCategory, List<SetFormBean>> setByCategory = new HashMap<>();
		for (SetCategory currSetCategory : SetCategory.values()) {
			setByCategory.put(currSetCategory, new ArrayList<SetFormBean>());
		}
		
		for (SetFormBean currSetFormBean : beansToSort) {
			setByCategory.get(SetCategory.valueOf(currSetFormBean.getCategory())).add(currSetFormBean);
		}
		
		for (List<SetFormBean> currList : setByCategory.values()) {
			Collections.sort(currList, SET_FORM_BEAN_COMPARATOR);			
		}
		
		List<SetFormBean> result = new ArrayList<>();
		
		result.addAll(setByCategory.get(SetCategory.EXPANSION));
		result.addAll(setByCategory.get(SetCategory.CORE_SET));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET_FTV));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET_DUEL));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET_PDS));
		result.addAll(setByCategory.get(SetCategory.SPECIAL_SET_EDH));
		
		result.addAll(setByCategory.get(SetCategory.OTHER));
		
		return result;
	}
}
