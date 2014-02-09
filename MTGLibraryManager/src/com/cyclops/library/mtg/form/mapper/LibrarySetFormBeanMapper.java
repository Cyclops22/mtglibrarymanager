package com.cyclops.library.mtg.form.mapper;

import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public class LibrarySetFormBeanMapper extends AbstractFormBeanMapper<LibrarySetBean, LibrarySetFormBean> {

	private LibraryCardFormBeanMapper libraryCardFormBeanMapper = new LibraryCardFormBeanMapper();
	private SetFormBeanMapper setFormBeanMapper = new SetFormBeanMapper();
	
	@Override
	public LibrarySetBean toBean(LibrarySetFormBean formBean) {
		LibrarySetBean bean = null;
		
		if (formBean != null) {
			bean = new LibrarySetBean();
			
			bean.setCards(libraryCardFormBeanMapper.toBean(formBean.getCards()));
			bean.setId(formBean.getId());
			bean.setReferencedSet(setFormBeanMapper.toBean(formBean.getReferencedSet()));
		}
		
		return bean;
	}

	@Override
	public LibrarySetFormBean toFormBean(LibrarySetBean bean) {
		LibrarySetFormBean formBean = null;
		
		if (bean != null) {
			formBean = new LibrarySetFormBean();
			
			formBean.setCards(libraryCardFormBeanMapper.toFormBean(bean.getCards()));
			formBean.setId(bean.getId());
			formBean.setReferencedSet(setFormBeanMapper.toFormBean(bean.getReferencedSet()));
		}
		
		return formBean;
	}
}
