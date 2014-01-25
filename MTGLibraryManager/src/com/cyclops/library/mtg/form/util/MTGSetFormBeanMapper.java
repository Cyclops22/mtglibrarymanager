package com.cyclops.library.mtg.form.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.cyclops.library.mtg.domain.AliasBean;
import com.cyclops.library.mtg.domain.MTGSetBean;
import com.cyclops.library.mtg.form.bean.MTGSetFormBean;

public class MTGSetFormBeanMapper extends FormBeanMapper<MTGSetBean, MTGSetFormBean> {

	@Override
	public MTGSetBean toBean(MTGSetFormBean formBean) {
		MTGSetBean set = new MTGSetBean();
		
		set.setAbbreviation(formBean.getAbbreviation());
		set.setId(formBean.getId());
		set.setImageUrl(formBean.getImageUrl());
		set.setLanguage(formBean.getLanguage());
		set.setName(formBean.getName());
		set.setUrl(formBean.getUrl());
		
		String[] aliasesArray = StringUtils.split(formBean.getAliases(), ',');
		List<AliasBean> aliasBeans = new ArrayList<>();
		
		for (int i = 0; i < aliasesArray.length; i++) {
			AliasBean aliasBean = new AliasBean();
			aliasBean.setAlias(aliasesArray[i]);
			
			aliasBeans.add(aliasBean);
		}
		
		set.setAliases(aliasBeans);
		
		
		return set;
	}

	@Override
	public MTGSetFormBean toFormBean(MTGSetBean bean) {
		MTGSetFormBean setForm = new MTGSetFormBean();
		
		setForm.setAbbreviation(bean.getAbbreviation());
		setForm.setId(bean.getId());
		setForm.setImageUrl(bean.getImageUrl());
		setForm.setLanguage(bean.getLanguage());
		setForm.setName(bean.getName());
		setForm.setUrl(bean.getUrl());
		
		StrBuilder strBuilder = new StrBuilder();
		
		
		for (int i = 0; i < CollectionUtils.size(bean.getAliases()); i++) {
			strBuilder.append(bean.getAliases().get(i).getAlias());
			strBuilder.appendSeparator(", ", i);
		}
		
		setForm.setAliases(strBuilder.toString());
		
		return setForm;
	}

}
