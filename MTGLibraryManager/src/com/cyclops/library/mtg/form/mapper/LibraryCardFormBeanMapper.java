package com.cyclops.library.mtg.form.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.LibraryCardBean;
import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;

@Component("libraryCardFormBeanMapper")
public class LibraryCardFormBeanMapper extends AbstractFormBeanMapper<LibraryCardBean, LibraryCardFormBean> {

	@Autowired
	private CardFormBeanMapper cardFormBeanMapper;
	
	@Override
	public LibraryCardBean toBean(LibraryCardFormBean formBean) {
		LibraryCardBean bean = null;
		
		if (formBean != null) {
			bean = new LibraryCardBean();
			
			bean.setFoilQuantity(formBean.getFoilQuantity());
			bean.setId(formBean.getId());
			bean.setQuantity(formBean.getQuantity());
			bean.setReferencedCard(cardFormBeanMapper.toBean(formBean.getReferencedCard()));			
		}
		
		return bean;
	}

	@Override
	public LibraryCardFormBean toFormBean(LibraryCardBean bean) {
		LibraryCardFormBean formBean = null;
		
		if (bean != null) {
			formBean = new LibraryCardFormBean();
			
			formBean.setFoilQuantity(bean.getFoilQuantity());
			formBean.setId(bean.getId());
			formBean.setQuantity(bean.getQuantity());
			formBean.setReferencedCard(cardFormBeanMapper.toFormBean(bean.getReferencedCard()));			
		}
		
		return formBean;
	}
}
