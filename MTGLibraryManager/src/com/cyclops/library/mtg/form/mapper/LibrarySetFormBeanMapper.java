package com.cyclops.library.mtg.form.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

@Component("librarySetFormBeanMapper")
public class LibrarySetFormBeanMapper extends AbstractFormBeanMapper<LibrarySetBean, LibrarySetFormBean> {

	@Autowired
	private LibraryCardFormBeanMapper libraryCardFormBeanMapper;
	
	@Autowired
	private SetFormBeanMapper setFormBeanMapper;
	
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
