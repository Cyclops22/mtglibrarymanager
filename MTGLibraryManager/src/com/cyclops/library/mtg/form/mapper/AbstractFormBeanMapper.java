package com.cyclops.library.mtg.form.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public abstract class AbstractFormBeanMapper<S, T> {

	public abstract S toBean(T formBean);
	
	public abstract T toFormBean(S bean);
	
	public List<S> toBean(List<T> formBeans) {
		List<S> beans = new ArrayList<>();
		
		for (int i = 0; i < CollectionUtils.size(formBeans); i++) {
			beans.add(toBean(formBeans.get(i)));
		}
		
		return beans;
	}
	
	public List<T> toFormBean(List<S> beans) {
		List<T> formBeans = new ArrayList<>();
		
		for (int i = 0; i < CollectionUtils.size(beans); i++) {
			formBeans.add(toFormBean(beans.get(i)));
		}
		
		return formBeans;
	}
}
