package com.cyclops.library.mtg.form.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;

@Component
@Qualifier("setFormBeanMapper")
public class SetFormBeanMapper extends AbstractFormBeanMapper<SetBean, SetFormBean> {
	
	@Autowired
	private CardFormBeanMapper cardFormBeanMapper;

	@Override
	public SetBean toBean(SetFormBean formBean) {
		SetBean set = null;
		
		if (formBean != null) {
			set = new SetBean();
			
			set.setBlock(formBean.getBlock());
			set.setBorder(formBean.getBorder());
			set.setCards(cardFormBeanMapper.toBean(formBean.getCards()));
			set.setCode(formBean.getCode());
			set.setGathererCode(formBean.getGathererCode());
			set.setId(formBean.getId());
			set.setName(formBean.getName());
			set.setOldCode(formBean.getOldCode());
			set.setReleaseDate(formBean.getReleaseDate());
			set.setSetType(formBean.getSetType());
		}
		
		return set;
	}

	@Override
	public SetFormBean toFormBean(SetBean bean) {
		SetFormBean setForm = null;
		
		if (bean != null) {
			setForm = new SetFormBean();
			
			setForm.setBlock(bean.getBlock());
			setForm.setBorder(bean.getBorder());
			setForm.setCards(cardFormBeanMapper.toFormBean(bean.getCards()));
			setForm.setCode(bean.getCode());
			setForm.setGathererCode(bean.getGathererCode());
			setForm.setId(bean.getId());
			setForm.setName(bean.getName());
			setForm.setOldCode(bean.getOldCode());
			setForm.setReleaseDate(bean.getReleaseDate());
			setForm.setSetType(bean.getSetType());
		}
		
		return setForm;
	}
}
