package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.html.bean.MTGPriceSinglePriceBean;
import com.cyclops.library.mtg.html.bean.MTGPriceURLSetPriceBean;

@Component
public class MTGPriceSinglePriceParser {
	
	private static Pattern CARD_PRICE_DATA_PATTERN = Pattern.compile("\\{.+?\"name\":\"([\\w\\s-',]+)\".+?\"fair_price\":(\\d+\\.\\d+).+?\\}");
	
	public List<MTGPriceSinglePriceBean> getSinglePrices(MTGPriceURLSetPriceBean mtgPriceURLSetPriceBean) {
		Map<String, MTGPriceSinglePriceBean> mtgPriceSinglePriceBeanBySingleName = new HashMap<>();
		
		try {
			retrieveSinglePrice(mtgPriceURLSetPriceBean.getPriceListURL(), false, mtgPriceSinglePriceBeanBySingleName);
			
			if (StringUtils.isNotBlank(mtgPriceURLSetPriceBean.getFoilPriceListURL())) {
				retrieveSinglePrice(mtgPriceURLSetPriceBean.getFoilPriceListURL(), true, mtgPriceSinglePriceBeanBySingleName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<>(mtgPriceSinglePriceBeanBySingleName.values());
	}
	
	private void retrieveSinglePrice(String priceURL, boolean foilPrice, Map<String, MTGPriceSinglePriceBean> mtgPriceSinglePriceBeanBySingleName) throws IOException {
		Document dom = Jsoup.connect(priceURL).timeout(0).get();
		
		Elements elements = dom.select("body script");
		for (Iterator<Element> iter = elements.iterator(); iter.hasNext(); ) {
			Element element = iter.next();
			
			for (Iterator<Node> childIter = element.childNodes().iterator(); childIter.hasNext(); ) {
				Node childElement = childIter.next();
				
				String attribute = StringEscapeUtils.unescapeJava(childElement.attr("data"));
				
				Matcher matcher = CARD_PRICE_DATA_PATTERN.matcher(attribute);
				while (matcher.find()) {
					MTGPriceSinglePriceBean mtgPriceSinglePriceBean = mtgPriceSinglePriceBeanBySingleName.get(matcher.group(1));
					
					if (mtgPriceSinglePriceBean == null) {
						mtgPriceSinglePriceBean = new MTGPriceSinglePriceBean();
					}
					
					mtgPriceSinglePriceBean.setCardName(matcher.group(1));
					
					if (foilPrice) {
						mtgPriceSinglePriceBean.setFoilPrice(matcher.group(2));
						
					} else  {
						mtgPriceSinglePriceBean.setPrice(matcher.group(2));
						
					}
					
					mtgPriceSinglePriceBeanBySingleName.put(matcher.group(1), mtgPriceSinglePriceBean);
				}
			}
		}
	}
}
