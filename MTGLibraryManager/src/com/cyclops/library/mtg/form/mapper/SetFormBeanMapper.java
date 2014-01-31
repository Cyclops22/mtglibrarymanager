package com.cyclops.library.mtg.form.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.cyclops.library.mtg.domain.AliasBean;
import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;

public class SetFormBeanMapper extends AbstractFormBeanMapper<SetBean, SetFormBean> {
	
	private static final DateFormat RELEASE_DATE_FORMAT = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
	
	private CardFormBeanMapper cardBeanMapper = new CardFormBeanMapper();

	@Override
	public SetBean toBean(SetFormBean formBean) {
		SetBean set = null;
		
		if (formBean != null) {
			set = new SetBean();
			
			set.setAbbreviation(formBean.getAbbreviation());
			set.setId(formBean.getId());
			set.setLogoUrl(formBean.getImageUrl());
			set.setLanguage(formBean.getLanguage());
			set.setName(formBean.getName());
			set.setUrl(formBean.getUrl());
			
			if (StringUtils.isNotBlank(formBean.getReleaseDate())) {
				try {
					set.setReleaseDate(RELEASE_DATE_FORMAT.parse(formBean.getReleaseDate()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}	
			}
			
			String[] aliasesArray = StringUtils.split(formBean.getAliases(), ',');
			List<AliasBean> aliasBeans = new ArrayList<>();
			
			for (int i = 0; i < aliasesArray.length; i++) {
				AliasBean aliasBean = new AliasBean();
				aliasBean.setAlias(aliasesArray[i]);
				
				aliasBeans.add(aliasBean);
			}
			
			set.setAliases(aliasBeans);
			
			set.setCards(cardBeanMapper.toBean(formBean.getCards()));			
		}
		
		return set;
	}

	@Override
	public SetFormBean toFormBean(SetBean bean) {
		SetFormBean setForm = null;
		
		if (bean != null) {
			setForm = new SetFormBean();
			
			setForm.setAbbreviation(bean.getAbbreviation());
			setForm.setId(bean.getId());
			setForm.setImageUrl(bean.getLogoUrl());
			setForm.setLanguage(bean.getLanguage());
			setForm.setName(bean.getName());
			setForm.setUrl(bean.getUrl());
			
			if (bean.getReleaseDate() != null) {
				setForm.setReleaseDate(RELEASE_DATE_FORMAT.format(bean.getReleaseDate()));
			}
			
			
			StrBuilder strBuilder = new StrBuilder();
			
			
			for (int i = 0; i < CollectionUtils.size(bean.getAliases()); i++) {
				strBuilder.append(bean.getAliases().get(i).getAlias());
				strBuilder.appendSeparator(", ", i);
			}
			
			setForm.setAliases(strBuilder.toString());
			
			setForm.setCards(cardBeanMapper.toFormBean(bean.getCards()));
		}
		
		return setForm;
	}

}
