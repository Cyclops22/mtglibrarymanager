package com.cyclops.library.mtg.form.mapper;

import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.form.bean.LibraryFormBean;

@Component
public class LibraryFormBeanMapper extends AbstractFormBeanMapper<LibraryBean, LibraryFormBean> {

	private LibrarySetFormBeanMapper librarySetFormBeanMapper = new LibrarySetFormBeanMapper();
	
	@Override
	public LibraryBean toBean(LibraryFormBean formBean) {
		LibraryBean bean = null;
		
		if (formBean != null) {
			bean = new LibraryBean();
			
			bean.setId(formBean.getId());
			bean.setName(formBean.getName());
			bean.setSets(librarySetFormBeanMapper.toBean(formBean.getSets()));			
		}
		
		return bean;
	}

	@Override
	public LibraryFormBean toFormBean(LibraryBean bean) {
		LibraryFormBean formBean = null;
		
		if (bean != null) {
			formBean = new LibraryFormBean();
			
			formBean.setId(bean.getId());
			formBean.setName(bean.getName());
			formBean.setSets(librarySetFormBeanMapper.toFormBean(bean.getSets()));
		}
		
		return formBean;
	}
}
