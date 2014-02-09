package com.cyclops.library.mtg.form.mapper;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

import com.cyclops.library.mtg.Constants;
import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.domain.SetCategory;
import com.cyclops.library.mtg.form.bean.SetFormBean;

public class SetFormBeanMapper extends AbstractFormBeanMapper<SetBean, SetFormBean> {
	
	private CardFormBeanMapper cardFormBeanMapper = new CardFormBeanMapper();

	@Override
	public SetBean toBean(SetFormBean formBean) {
		SetBean set = null;
		
		if (formBean != null) {
			set = new SetBean();
			
			set.setAbbreviation(formBean.getAbbreviation());
			set.setCategory(SetCategory.valueOf(formBean.getCategory()));
			set.setId(formBean.getId());
			set.setLogoUrl(formBean.getImageUrl());
			set.setLanguage(formBean.getLanguage());
			set.setName(formBean.getName());
			set.setUrl(formBean.getUrl());
			
			if (StringUtils.isNotBlank(formBean.getReleaseDate())) {
				try {
					set.setReleaseDate(Constants.RELEASE_DATE_DATEFORMAT.parse(formBean.getReleaseDate()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}	
			}
			
			set.setCards(cardFormBeanMapper.toBean(formBean.getCards()));			
		}
		
		return set;
	}

	@Override
	public SetFormBean toFormBean(SetBean bean) {
		SetFormBean setForm = null;
		
		if (bean != null) {
			setForm = new SetFormBean();
			
			setForm.setAbbreviation(bean.getAbbreviation());
			setForm.setCategory(bean.getCategory().toString());
			setForm.setId(bean.getId());
			setForm.setImageUrl(bean.getLogoUrl());
			setForm.setLanguage(bean.getLanguage());
			setForm.setName(bean.getName());
			setForm.setUrl(bean.getUrl());
			
			if (bean.getReleaseDate() != null) {
				setForm.setReleaseDate(Constants.RELEASE_DATE_DATEFORMAT.format(bean.getReleaseDate()));
			}
			
			setForm.setCards(cardFormBeanMapper.toFormBean(bean.getCards()));
		}
		
		return setForm;
	}
}
