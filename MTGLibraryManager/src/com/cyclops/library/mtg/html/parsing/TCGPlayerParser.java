package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.domain.SetCategory;

@Component("tcgPlayerParser")
public class TCGPlayerParser extends SiteParser {
	
	private static final String SITE_INFO_URL = "http://magic.tcgplayer.com/all_magic_sets.asp";
	
	private static final String UNWANTED_SETS_KEY = "parser.tcg.unwanted.sets";
	
	public TCGPlayerParser() {
		initUnwantedSets(UNWANTED_SETS_KEY);
	}

	public List<SetBean> retrieveAllSets() throws IOException {
		List<SetBean> mtgSets = new ArrayList<>();

		Document doc = Jsoup.connect(SITE_INFO_URL).get();

		Elements tdsElement = doc.select("form + table tbody tr td");
		
		// First 2 tds contains Expansions
		for (int i = 0; i < tdsElement.size(); i++) {
			Element currTdElement = tdsElement.get(i);
			if (i < 2) {
				processUntitledTd(currTdElement, mtgSets);
				
			} else {
				processTitledTd(currTdElement, mtgSets);
				
			}
		}
		
		return mtgSets;
	}
	
	private void processUntitledTd(Element tdElement, List<SetBean> mtgSets) {
		Elements tdContentElements = tdElement.select("img, a");
		for (int i = 0; i < tdContentElements.size(); i++) {
			if ("img".equals(tdContentElements.get(i).tagName()) && "a".equals(tdContentElements.get(i + 1).tagName())) {
				processSet(tdContentElements.get(i), tdContentElements.get(++i), mtgSets, SetCategory.EXPANSION);
				
			}
		}
	}
	
	private void processTitledTd(Element tdElement, List<SetBean> mtgSets) {
		int index = 0;
		Elements tdContentElements = tdElement.select("img, a, strong");
		
		// Navigate to the first category
		while (!"strong".equals(tdContentElements.get(index).tagName())) {
			index++;
		}
		
		SetCategory category = null;
		for (int i = index; i < tdContentElements.size(); i++) {
			if ("strong".equals(tdContentElements.get(i).tagName())) {
				category = determineCategoryFromText(tdContentElements.get(i).text());
				
			} else if ("img".equals(tdContentElements.get(i).tagName()) && "a".equals(tdContentElements.get(i + 1).tagName())) {
				processSet(tdContentElements.get(i), tdContentElements.get(++i), mtgSets, category);
				
			}
		}
	}
	
	private void processSet(Element imageElement, Element aElement, List<SetBean> mtgSets, SetCategory category) {
		String setName = aElement.text();
		
		if (StringUtils.isNotBlank(setName) && !getUnwantedSetsName().contains(setName)) {
			SetBean mtgSet = new SetBean();
			
			mtgSet.setName(setName);
			
			mtgSet.setLogoUrl(imageElement.attr("src"));
			mtgSet.setLanguage(Locale.ENGLISH.getLanguage());
			mtgSet.setCategory(determineCategoryFromName(setName, category));
			
			mtgSets.add(mtgSet);
		}
	}
	
	private SetCategory determineCategoryFromText(String setCategoryDescription) {
		SetCategory setCategory = null;
		
		switch (setCategoryDescription) {
		case "Core Sets":
			setCategory = SetCategory.CORE_SET;
			break;
		
		case "Special sets":
			setCategory = SetCategory.SPECIAL_SET;
			break;
			
		default:
			setCategory = SetCategory.OTHER;
		}
		
		return setCategory;
	}
	
	private SetCategory determineCategoryFromName(String setName, SetCategory initialCategory) {
		SetCategory setCategory = initialCategory;
		
		if (setName.contains("From the Vault")) {
			setCategory = SetCategory.SPECIAL_SET_FTV;
			
		} else if (setName.contains("vs")) {
			setCategory = SetCategory.SPECIAL_SET_DUEL;
			
		} else if (setName.contains("PDS")) {
			setCategory = SetCategory.SPECIAL_SET_PDS;
			
		} else if (setName.contains("Commander") || setName.contains("Planechase")) {
			setCategory = SetCategory.SPECIAL_SET_EDH;
			
		}
		
		return setCategory;
	}
}
