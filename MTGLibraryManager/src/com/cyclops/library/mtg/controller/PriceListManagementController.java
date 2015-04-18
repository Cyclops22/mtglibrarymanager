package com.cyclops.library.mtg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.bean.SinglePriceFormBean;
import com.cyclops.library.mtg.service.PriceMgtService;

@Controller
public class PriceListManagementController {
	
	private PriceMgtService priceMgtService;
	
	@Autowired
	public PriceListManagementController(PriceMgtService priceMgtService) {
		this.priceMgtService = priceMgtService;
	}
	
	@RequestMapping(value = "/pricelistmgt/getPrices", method = RequestMethod.GET)
	public @ResponseBody Map<String, SinglePriceFormBean> getPrices(@RequestParam String setName) {
		SetFormBean setFormBean = new SetFormBean();
		setFormBean.setName(setName);
		
		Map<String, SinglePriceFormBean> prices = priceMgtService.getPrices(setFormBean);
		
		return prices;
	}
}
