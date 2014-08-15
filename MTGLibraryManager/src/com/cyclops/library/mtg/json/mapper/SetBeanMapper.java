package com.cyclops.library.mtg.json.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.json.bean.SetJsonBean;

@Component
public class SetBeanMapper {
	
	@Autowired
	private CardBeanMapper cardBeanMapper;

	public SetBean toSetBean(SetJsonBean setJsonBean) {
		SetBean setBean = new SetBean();
		
		setBean.setBlock(setJsonBean.getBlock());
		setBean.setBorder(setJsonBean.getBorder());
		
		setBean.setCards(cardBeanMapper.toCardBeans(Arrays.asList(setJsonBean.getCards())));
		
		setBean.setCode(setJsonBean.getCode());
		setBean.setGathererCode(setJsonBean.getGathererCode());
		setBean.setName(setJsonBean.getName());
		setBean.setOldCode(setJsonBean.getOldCode());
		
		try {
			setBean.setReleaseDate(DateUtils.parseDate(setJsonBean.getReleaseDate(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		setBean.setSetType(setJsonBean.getType());
		
		return setBean;
	}
	
	public List<SetBean> toSetBeans(List<SetJsonBean> setJsonBeans) {
		List<SetBean> setBeans = new ArrayList<>();
		
		for (SetJsonBean currSetJsonBean : setJsonBeans) {
			setBeans.add(toSetBean(currSetJsonBean));
		}
		
		return setBeans;
	}
}
