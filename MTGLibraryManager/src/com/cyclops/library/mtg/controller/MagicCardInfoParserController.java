package com.cyclops.library.mtg.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.bean.MTGCardBean;

@Controller
public class MagicCardInfoParserController {
	
	private static final String ROOT = "http://magiccards.info";
	private static final String SCAN_ROOT = ROOT + "/scans";
	private static final String SCAN_ROOT_URL_TEMPLATE = SCAN_ROOT + "/{0}/{1}/{2}.jpg";
	
	private static final List<String> CARDS_TO_IGNORE = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");
	
	private Map<String, String> setURLByName;
	private Map<String, List<MTGCardBean>> cardsBySet;

	@RequestMapping(value = "/mci", method = RequestMethod.GET)
	public String display(Model model) {
		return "magiccardinfo";
	}
	
	@RequestMapping(value = "/scanmagiccardsinfo", method = RequestMethod.GET)
	public String scanSite(Model model) {
		setURLByName = new LinkedHashMap<>();
		cardsBySet = new LinkedHashMap<>();
		
		try {
			
			long start = System.currentTimeMillis();
			Document sitemapDoc = Jsoup.connect(ROOT + "/sitemap.html").get();
			extractExpansions(sitemapDoc);
			extractCoreSets(sitemapDoc);
			extractSpecialSets(sitemapDoc);
			long stop = System.currentTimeMillis();
			
			System.out.println("Time to extract expansions: " + (stop - start) + " ms.");
			
			extractExpansionCards();
			long stop2 = System.currentTimeMillis();
			
			System.out.println("Time to extract expansions cards: " + (stop2 - stop) + " ms.");
			
			int totalCards = 0;
			for (List<MTGCardBean> currList : cardsBySet.values()) {
				totalCards += currList.size();
			}
			
			System.out.println("Total number of cards extracted: " + totalCards);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		model.addAttribute("sets", setURLByName);
		model.addAttribute("cardsBySet", cardsBySet);
		
		return "magiccardinfo";
	}
	
	@RequestMapping(value = "/{setName}/displaySet", method = RequestMethod.GET)
	public String displaySet(@PathVariable("setName") String setName, Model model) {
		
		model.addAttribute("cards", cardsBySet.get(setName));
		
		return "setDetails";
	}
	
	private void extractExpansions(Document sitemapDoc) throws IOException {
		Elements sets = sitemapDoc.select("h2:contains(English) + table h3:contains(Expansions) + ul a");
		for (Element currSet : sets) {
			String setName = currSet.text();
			String setLink = currSet.attr("href");
			
			setURLByName.put(setName, setLink);
		}
	}
	
	private void extractCoreSets(Document sitemapDoc) throws IOException {
		Elements sets = sitemapDoc.select("h2:contains(English) + table h3:contains(Core Sets) + ul a");
		for (Element currSet : sets) {
			String setName = currSet.text();
			String setLink = currSet.attr("href");
			
			setURLByName.put(setName, setLink);
		}
	}
	
	private void extractSpecialSets(Document sitemapDoc) throws IOException {
		Elements sets = sitemapDoc.select("h2:contains(English) + table h3:contains(Special Sets) + ul a");
		for (Element currSet : sets) {
			String setName = currSet.text();
			String setLink = currSet.attr("href");
			
			setURLByName.put(setName, setLink);
		}
	}
	
	private void extractExpansionCards() throws IOException {
		for (String currSet : setURLByName.keySet()) {
			List<MTGCardBean> cardsInSet = new ArrayList<>();
			
			Document doc = Jsoup.connect(ROOT + setURLByName.get(currSet)).get();
			
			Elements cards = doc.select("table tr[class]");
			for (Element currCard : cards) {
				String cardName = currCard.child(1).text();
						
				if (!CARDS_TO_IGNORE.contains(cardName)) {
					String cardUrl = currCard.child(1).select("a").attr("href");
					String[] cardUrlPart = StringUtils.split(currCard.child(1).select("a").attr("href"), '/');
					
					MTGCardBean cardBean = new MTGCardBean();
					cardBean.setEditionName(currSet);
					cardBean.setEditionAbbreviation(cardUrlPart[0]);
					cardBean.setLanguage(cardUrlPart[1]);
					
					cardBean.setName(cardName);
					cardBean.setType(currCard.child(2).text());
					cardBean.setMana(currCard.child(3).text());
					cardBean.setRarity(currCard.child(4).text());
					
					cardBean.setUrl(ROOT + cardUrl);
					cardBean.setImageUrl(MessageFormat.format(SCAN_ROOT_URL_TEMPLATE, cardBean.getLanguage(), cardBean.getEditionAbbreviation(), currCard.child(0).text()));
					
					cardsInSet.add(cardBean);
				}
			}
			
			cardsBySet.put(currSet, cardsInSet);
			
			break;
		}
	}
}
