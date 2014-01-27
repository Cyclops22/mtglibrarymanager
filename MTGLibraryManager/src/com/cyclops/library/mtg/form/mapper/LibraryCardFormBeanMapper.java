package com.cyclops.library.mtg.form.mapper;

import com.cyclops.library.mtg.domain.LibraryCardBean;
import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;

public class LibraryCardFormBeanMapper extends AbstractFormBeanMapper<LibraryCardBean, LibraryCardFormBean> {

	private CardFormBeanMapper cardBeanMapper = new CardFormBeanMapper();
	
	@Override
	public LibraryCardBean toBean(LibraryCardFormBean formBean) {
		LibraryCardBean bean = null;
		
		if (formBean != null) {
			bean = new LibraryCardBean();
			
			bean.setFoilQuantity(formBean.getFoilQuantity());
			bean.setId(formBean.getId());
			bean.setQuantity(formBean.getQuantity());
			bean.setReferencedCard(cardBeanMapper.toBean(formBean.getReferencedCard()));			
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
			formBean.setReferencedCard(cardBeanMapper.toFormBean(bean.getReferencedCard()));			
		}
		
		return formBean;
	}

}
