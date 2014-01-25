package com.cyclops.library.mtg.form.util;

import java.util.ArrayList;
import java.util.List;

public abstract class FormBeanMapper<S, T> {

	public abstract S toBean(T formBean);
	
	public abstract T toFormBean(S bean);
	
	public List<S> toBean(List<T> formBeans) {
		List<S> beans = new ArrayList<>();
		
		for (T currFormBean : formBeans) {
			beans.add(toBean(currFormBean));
		}
		
		return beans;
	}
	
	public List<T> toFormBean(List<S> beans) {
		List<T> formBeans = new ArrayList<>();
		
		for (S currBean : beans) {
			formBeans.add(toFormBean(currBean));
		}
		
		return formBeans;
	}
}
