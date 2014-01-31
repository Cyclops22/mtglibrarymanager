package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.SetBean;

public class MagicCardsInfoParser {
	
	private static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
	private static final String SITE_ROOT = "http://magiccards.info";
	private static final String SCAN_ROOT = SITE_ROOT + "/scans";
	private static final String SCAN_ROOT_URL_TEMPLATE = SCAN_ROOT + "/{0}/{1}/{2}.jpg";
	
	private static final List<String> CARDS_TO_IGNORE = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");
	
	private Map<String, SetBean> formSetByName = new LinkedHashMap<>();
	private Map<String, SetBean> formSetByAlias = new LinkedHashMap<>();

	public List<SetBean> retrieveSetsDetails(List<SetBean> sets) throws IOException {
		initMaps(sets);
		
		Document sitemapDocument = Jsoup.connect("http://magiccards.info/sitemap.html").get();
		
		retrieveExpansionsDetails(sitemapDocument);
		retrieveCoreSetsDetails(sitemapDocument);
		retrieveSpecialSetsDetails(sitemapDocument);
		
		extractExpansionCards();
		
		return new ArrayList<>(formSetByName.values());
	}
	
	private void initMaps(List<SetBean> sets) {
		formSetByName.clear();
		formSetByAlias.clear();
		
		for (SetBean currSet : sets) {
			formSetByName.put(currSet.getName(), currSet);
			
			for (int i = 0; i < CollectionUtils.size(currSet.getAliases()); i++) {
				formSetByAlias.put(currSet.getAliases().get(i).getAlias(), currSet);
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
		SetBean setBean = null;
		boolean processSet = true;
		
		for (int i = 0; i < setElements.size(); i++) {
			Element currElement = setElements.get(i);
			
			if (i % 2 == 0) {
				processSet = true;
				String setName = currElement.text();
				
				setBean = getSetBean(setName);
				if (setBean != null) {
					setBean.setLanguage(StringUtils.defaultString(setBean.getLanguage(), DEFAULT_LANGUAGE));
					setBean.setUrl(SITE_ROOT + currElement.attr("href"));
				
				} else {
					processSet = false;
					
				}
			} else {
				if (processSet) {
					setBean.setAbbreviation(currElement.text());
				}
			}
		}
	}
	
	private SetBean getSetBean(String setName) {
		SetBean setBean = formSetByName.get(setName);
		if (setBean == null) {
			setBean = formSetByAlias.get(setName);
		}
		
		return setBean;
	}
	
	private void extractExpansionCards() throws IOException {
		int fetchSize = 25;
		int fetchIdx = 0;
		
		for (String currSet : formSetByName.keySet()) {
			List<CardBean> cardsInSet = new ArrayList<>();
			
			if (StringUtils.isNotBlank(formSetByName.get(currSet).getUrl()))  {
				Document doc = Jsoup.connect(formSetByName.get(currSet).getUrl()).get();
				
				Elements cards = doc.select("table tr[class]");
				for (Element currCard : cards) {
					String cardName = currCard.child(1).text();
							
					if (!CARDS_TO_IGNORE.contains(cardName)) {
						String cardUrl = currCard.child(1).select("a").attr("href");
						String[] cardUrlPart = StringUtils.split(currCard.child(1).select("a").attr("href"), '/');
						
						String editionAbbreviation = cardUrlPart[0];
						String language = cardUrlPart[1];
						
						CardBean cardBean = new CardBean();
						
						cardBean.setNumber(currCard.child(0).text());
						cardBean.setName(cardName);
						cardBean.setType(currCard.child(2).text());
						cardBean.setMana(currCard.child(3).text());
						cardBean.setRarity(currCard.child(4).text());
						
						cardBean.setUrl(SITE_ROOT + cardUrl);
						cardBean.setImageUrl(MessageFormat.format(SCAN_ROOT_URL_TEMPLATE, language, editionAbbreviation, currCard.child(0).text()));
						
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
