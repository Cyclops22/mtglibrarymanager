package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.html.bean.MTGPriceURLSetPriceBean;

@Component
public class MTGPriceSetParser {
	private static final Pattern LEGACY_CORE_EDITION_PATTERN = Pattern.compile("^(\\d{1,2})(ED|E)$");
	
	private static final String MTGPRICE_BROWSE_PRICES_URL = "http://www.mtgprice.com/magic-the-gathering-prices.jsp";
	
	public MTGPriceURLSetPriceBean matchSetWithSinglePricePage(String setName, String setType, String setCode) {
		String providerSetName = matchSetNameWithPriceProvider(setName, setType, setCode);
		
		MTGPriceURLSetPriceBean mtgPriceURLSetPriceDetail = new MTGPriceURLSetPriceBean();
		
		try {
			Document dom = Jsoup.connect(MTGPRICE_BROWSE_PRICES_URL).timeout(0).get();
			Elements elements = dom.select("table#setTable tbody td a:contains(" + providerSetName + ")");
			for (Iterator<Element> iter = elements.iterator(); iter.hasNext(); ) {
				Element element = iter.next();
				
				String url = element.attr("href");
				String name = WordUtils.capitalize(element.text());
				
				boolean isFoil = name.contains(" (Foil)");
				if (isFoil) {
					mtgPriceURLSetPriceDetail.setFoilPriceListURL("http://www.mtgprice.com" + url);
					
				} else  {
					mtgPriceURLSetPriceDetail.setPriceListURL("http://www.mtgprice.com" + url);
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mtgPriceURLSetPriceDetail;
	}
	
	private String matchSetNameWithPriceProvider(String setName, String setType, String setCode) {
		String currSetName = WordUtils.capitalize(setName.replaceAll("[:'.]", StringUtils.EMPTY));
		
		if ("Ravnica City Of Guilds".equals(currSetName)) {
			currSetName = "Ravnica";
			
		} else if ("Time Spiral \"Timeshifted\"".equals(currSetName)) {
			currSetName = "Timespiral Timeshifted";
			
		} else {
			if ("core".equals(setType)) {
				if (setCode.charAt(0) == 'M') {
					currSetName = setCode;
					
				} else {
					Matcher matcher = LEGACY_CORE_EDITION_PATTERN.matcher(setCode);
					
					if (matcher.matches()) {
						String editionValue = matcher.group(1);
						
						if (Integer.valueOf(editionValue) <= 3) {
							currSetName = currSetName.replace(" Edition", StringUtils.EMPTY);
							
						} else {
							currSetName = editionValue + "th Edition";
							
						}
					}
				}
			} else if (currSetName.endsWith(" Edition")) {
				currSetName = currSetName.replace(" Edition", StringUtils.EMPTY);
				
			} else if (currSetName.startsWith("Magic The Gathering")) {
				boolean foundAsciiAlpha = true;
				int index = currSetName.length() - 1;
				
				while (foundAsciiAlpha && index >= 0) {
					char ch = currSetName.charAt(index);
					foundAsciiAlpha = CharUtils.isAsciiAlpha(ch);
					index--;
				}
				
				currSetName = currSetName.substring(index + 2);
				
			}
		}
		
		return currSetName;
	}
}
