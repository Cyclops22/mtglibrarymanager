package com.cyclops.library.mtg.html.bean;


public class MTGPriceSinglePriceBean {
	private String cardName;
	private String price;
	private String foilPrice;
	
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getFoilPrice() {
		return foilPrice;
	}
	
	public void setFoilPrice(String foilPrice) {
		this.foilPrice = foilPrice;
	}
}
