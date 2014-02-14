package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.SetBean;

@Component("magicCardsInfoParser")
public class MagicCardsInfoParser extends SiteParser {
	
	private static final String SITE_INFO_URL = "http://magiccards.info/sitemap.html";
	
	private static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
	private static final String SITE_ROOT = "http://magiccards.info";
	private static final String SCAN_ROOT = SITE_ROOT + "/scans";
	private static final String SCAN_ROOT_URL_TEMPLATE = SCAN_ROOT + "/{0}/{1}/{2}.jpg";
	
	private static final List<String> CARDS_TO_IGNORE = Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest");
	
	private static final String UNWANTED_SETS_KEY = "parser.magiccardsinfo.unwanted.sets";
	
	private List<String> processedSets = new ArrayList<>();
	
	public MagicCardsInfoParser() {
		initUnwantedSets(UNWANTED_SETS_KEY);
	}
	
	public List<SetBean> retrieveSetsDetails(List<SetBean> sets) throws IOException {
		initMaps(sets);
		processedSets.clear();
		
		Document sitemapDocument = Jsoup.connect(SITE_INFO_URL).get();
		
		retrieveExpansionsDetails(sitemapDocument);
		retrieveCoreSetsDetails(sitemapDocument);
		retrieveSpecialSetsDetails(sitemapDocument);
		
		extractExpansionCards();
		
		return getSets();
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
				String setName = currElement.text();
				
				if (!getUnwantedSetsName().contains(setName)) {
					processSet = true;
					
					if ("Time Spiral \"Timeshifted\"".equals(setName)) {
						setName = "Timeshifted";
						
					} else if ("Limited Edition Beta".equals(setName)) {
						setName = "Beta Edition";
						
					} else if ("Limited Edition Alpha".equals(setName)) {
						setName = "Alpha Edition";
					}
					
					setBean = getSetBean(setName);
					if (setBean != null && !processedSets.contains(setBean.getName())) {
						setBean.setLanguage(StringUtils.defaultString(setBean.getLanguage(), DEFAULT_LANGUAGE));
						setBean.setUrl(SITE_ROOT + currElement.attr("href"));
						
						processedSets.add(setBean.getName());
					
					} else {
						processSet = false;
						
					}
				}
				
			} else {
				if (processSet) {
					setBean.setAbbreviation(currElement.text());
				}
			}
		}
	}
	
	private void extractExpansionCards() throws IOException {
		int fetchSize = 25;
		int fetchIdx = 0;
		
		for (String currSetName : getSetsName()) {
			List<CardBean> cardsInSet = new ArrayList<>();
			
			SetBean setBean = getSetBean(currSetName);
			if (StringUtils.isNotBlank(setBean.getUrl()))  {
				Document doc = Jsoup.connect(setBean.getUrl()).get();
				
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
				
				setBean.setCards(cardsInSet);
				
				fetchIdx++;
				
				// To limit the fetch for testing purpose
				if (fetchIdx == fetchSize) {
//					break;
				}
			}
		}
	}
}
