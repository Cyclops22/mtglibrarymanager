package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyclops.library.mtg.form.bean.MTGCardFormBean;
import com.cyclops.library.mtg.form.bean.MTGSetFormBean;

public class MagicCardsInfoParser {
	
	private static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
	private static final String SITE_ROOT = "http://magiccards.info";
	private static final String SCAN_ROOT = SITE_ROOT + "/scans";
	private static final String SCAN_ROOT_URL_TEMPLATE = SCAN_ROOT + "/{0}/{1}/{2}.jpg";
	
	private static final List<String> CARDS_TO_IGNORE = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");
	
	private Map<String, MTGSetFormBean> formSetByName = new LinkedHashMap<>();
	private Map<String, MTGSetFormBean> formSetByAlias = new LinkedHashMap<>();

	public List<MTGSetFormBean> retrieveSetsDetails(List<MTGSetFormBean> sets) throws IOException {
		initMaps(sets);
		
		Document sitemapDocument = Jsoup.connect("http://magiccards.info/sitemap.html").get();
		
		retrieveExpansionsDetails(sitemapDocument);
		retrieveCoreSetsDetails(sitemapDocument);
		retrieveSpecialSetsDetails(sitemapDocument);
		
		extractExpansionCards();
		
		return new ArrayList<>(formSetByName.values());
	}
	
	private void initMaps(List<MTGSetFormBean> sets) {
		formSetByName.clear();
		formSetByAlias.clear();
		
		for (MTGSetFormBean currSet : sets) {
			formSetByName.put(currSet.getName(), currSet);
			
			String[] aliasArray = StringUtils.split(currSet.getAliases(), ',');
			for (int i = 0; i < aliasArray.length; i++) {
				formSetByAlias.put(aliasArray[i], currSet);
			}
		}
	}
	
	private void retrieveExpansionsDetails(Document sitemapDoc) throws IOException {
		Elements expansionSets = sitemapDoc.select("h2:contains(English) + table h3:contains(Expansions) + ul a, h2:contains(English) + table h3:contains(Expansions) + ul small");
		extractSets(expansionSets);
	}
	
	private void retrieveCoreSetsDetails(Document sitemapDoc) throws IOException {
		Elements coreSets = sitemapDoc.select("h2:contains(English) + table h3:contains(Core Set) + ul a, h2:contains(English) + table h3:contains(Core Set) + ul small");
		extractSets(coreSets);
	}
	
	private void retrieveSpecialSetsDetails(Document sitemapDoc) throws IOException {
		Elements specialSets = sitemapDoc.select("h2:contains(English) + table h3:contains(Special Set) + ul a, h2:contains(English) + table h3:contains(Special Set) + ul small");
		extractSets(specialSets);
	}
	
	private void extractSets(Elements setElements) throws IOException {
		MTGSetFormBean formSetBean = null;
		boolean processSet = true;
		
		for (int i = 0; i < setElements.size(); i++) {
			Element currElement = setElements.get(i);
			
			if (i % 2 == 0) {
				processSet = true;
				String setName = currElement.text();
				
				formSetBean = getSetFormBean(setName);
				if (formSetBean != null) {
//					formSetBean.setName(setName);
					formSetBean.setLanguage(StringUtils.defaultString(formSetBean.getLanguage(), DEFAULT_LANGUAGE));
					formSetBean.setUrl(SITE_ROOT + currElement.attr("href"));
				
				} else {
					processSet = false;
					
				}
			} else {
				if (processSet) {
					formSetBean.setAbbreviation(currElement.text());
				}
			}
		}
	}
	
	private MTGSetFormBean getSetFormBean(String setName) {
		MTGSetFormBean formSetBean = formSetByName.get(setName);
		if (formSetBean == null) {
			formSetBean = formSetByAlias.get(setName);
		}
		
		return formSetBean;
	}
	
	private void extractExpansionCards() throws IOException {
		int fetchSize = 25;
		int fetchIdx = 0;
		
		for (String currSet : formSetByName.keySet()) {
			List<MTGCardFormBean> cardsInSet = new ArrayList<>();
			
			if (StringUtils.isNotBlank(formSetByName.get(currSet).getUrl()))  {
				Document doc = Jsoup.connect(formSetByName.get(currSet).getUrl()).get();
				
				Elements cards = doc.select("table tr[class]");
				for (Element currCard : cards) {
					String cardName = currCard.child(1).text();
							
					if (!CARDS_TO_IGNORE.contains(cardName)) {
						String cardUrl = currCard.child(1).select("a").attr("href");
						String[] cardUrlPart = StringUtils.split(currCard.child(1).select("a").attr("href"), '/');
						
						MTGCardFormBean cardBean = new MTGCardFormBean();
						cardBean.setEditionName(currSet);
						cardBean.setEditionAbbreviation(cardUrlPart[0]);
						cardBean.setLanguage(cardUrlPart[1]);
						
						cardBean.setNumber(currCard.child(0).text());
						cardBean.setName(cardName);
						cardBean.setType(currCard.child(2).text());
						cardBean.setMana(currCard.child(3).text());
						cardBean.setRarity(currCard.child(4).text());
						
						cardBean.setUrl(SITE_ROOT + cardUrl);
						cardBean.setImageUrl(MessageFormat.format(SCAN_ROOT_URL_TEMPLATE, cardBean.getLanguage(), cardBean.getEditionAbbreviation(), currCard.child(0).text()));
						
						cardsInSet.add(cardBean);
					}
				}
				
				formSetByName.get(currSet).setCards(cardsInSet);
				
				fetchIdx++;
				
				// To limit the fetch for testing purpose
				if (fetchIdx == fetchSize) {
					break;
				}
			}
		}
	}
}
