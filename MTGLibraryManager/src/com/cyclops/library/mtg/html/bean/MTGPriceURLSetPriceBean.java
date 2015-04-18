package com.cyclops.library.mtg.html.bean;

import com.cyclops.library.mtg.domain.SetBean;

public class MTGPriceURLSetPriceBean {
	private SetBean set;
	private String priceListURL;
	private String foilPriceListURL;
	
	public SetBean getSet() {
		return set;
	}
	
	public void setSet(SetBean set) {
		this.set = set;
	}
	
	public String getPriceListURL() {
		return priceListURL;
	}
	
	public void setPriceListURL(String priceListURL) {
		this.priceListURL = priceListURL;
	}
	
	public String getFoilPriceListURL() {
		return foilPriceListURL;
	}
	
	public void setFoilPriceListURL(String foilPriceListURL) {
		this.foilPriceListURL = foilPriceListURL;
	}
}
