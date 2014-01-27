package com.cyclops.library.mtg.form.mapper;

import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public class LibrarySetFormBeanMapper extends AbstractFormBeanMapper<LibrarySetBean, LibrarySetFormBean> {

	private LibraryCardFormBeanMapper libraryCardBeanMapper = new LibraryCardFormBeanMapper();
	private SetFormBeanMapper setBeanMapper = new SetFormBeanMapper();
	
	@Override
	public LibrarySetBean toBean(LibrarySetFormBean formBean) {
		LibrarySetBean bean = null;
		
		if (formBean != null) {
			bean = new LibrarySetBean();
			
			bean.setCards(libraryCardBeanMapper.toBean(formBean.getCards()));
			bean.setId(formBean.getId());
			bean.setReferencedSet(setBeanMapper.toBean(formBean.getReferencedSet()));
		}
		
		return bean;
	}

	@Override
	public LibrarySetFormBean toFormBean(LibrarySetBean bean) {
		LibrarySetFormBean formBean = null;
		
		if (bean != null) {
			formBean = new LibrarySetFormBean();
			
			formBean.setCards(libraryCardBeanMapper.toFormBean(bean.getCards()));
			formBean.setId(bean.getId());
			formBean.setReferencedSet(setBeanMapper.toFormBean(bean.getReferencedSet()));
		}
		
		return formBean;
	}

}
