package com.cyclops.library.mtg.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.bean.SinglePriceFormBean;
import com.cyclops.library.mtg.form.mapper.SetFormBeanMapper;
import com.cyclops.library.mtg.html.bean.MTGPriceSinglePriceBean;
import com.cyclops.library.mtg.html.bean.MTGPriceURLSetPriceBean;
import com.cyclops.library.mtg.html.parsing.MTGPriceSetParser;
import com.cyclops.library.mtg.html.parsing.MTGPriceSinglePriceParser;

@Service("priceMgtService")
public class PriceMgtServiceImpl implements PriceMgtService {
	
	@Autowired
	private SetFormBeanMapper setBeanMapper;
	
	@Autowired
	private MTGPriceSetParser mtgPriceSetParser;
	
	@Autowired
	private MTGPriceSinglePriceParser mtgPriceSinglePriceParser;
	
	private Map<String, Map<String, SinglePriceFormBean>> setPriceBySetName = new HashMap<>();
	
	public Map<String, SinglePriceFormBean> getPrices(SetFormBean setFormBean) {
		Map<String, SinglePriceFormBean> pricesByCardName = setPriceBySetName.get(setFormBean.getName());
		
		if (pricesByCardName == null) {
			pricesByCardName = new HashMap<String, SinglePriceFormBean>();
			
			MTGPriceURLSetPriceBean mtgPriceURLSetPriceDetail = mtgPriceSetParser.matchSetWithSinglePricePage(setFormBean.getName(), setFormBean.getSetType(), setFormBean.getCode());
			List<MTGPriceSinglePriceBean> mtgPriceSinglePriceBeans = mtgPriceSinglePriceParser.getSinglePrices(mtgPriceURLSetPriceDetail);
			
			for (Iterator<MTGPriceSinglePriceBean> iter = mtgPriceSinglePriceBeans.iterator(); iter.hasNext(); ) {
				MTGPriceSinglePriceBean currMTGPriceSinglePriceBean = iter.next();
				
				SinglePriceFormBean singlePriceFormBean = new SinglePriceFormBean();
				singlePriceFormBean.setPrice(currMTGPriceSinglePriceBean.getPrice());
				singlePriceFormBean.setFoilPrice(currMTGPriceSinglePriceBean.getFoilPrice());
				
				pricesByCardName.put(currMTGPriceSinglePriceBean.getCardName(), singlePriceFormBean);
			}
			
			setPriceBySetName.put(setFormBean.getName(), pricesByCardName);
		}
		
		return pricesByCardName;
	}

}
