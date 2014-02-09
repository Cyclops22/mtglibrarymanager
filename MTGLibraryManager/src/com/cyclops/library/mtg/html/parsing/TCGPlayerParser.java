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
		SetCategory category = SetCategory.EXPANSION;
		SetBean mtgSet = null;
		List<SetBean> mtgSets = new ArrayList<>();

		Document doc = Jsoup.connect(SITE_INFO_URL).get();

		Elements elements = doc.select("form + table td img, form + table td a, form + table td strong");
		
		for (int i = 0; i < elements.size(); i++) {
			Element currElement = elements.get(i);
			
			switch (currElement.tagName()) {
			case "img":
				mtgSet = new SetBean();
				
				mtgSet.setLogoUrl(currElement.attr("src"));
				mtgSet.setLanguage(Locale.ENGLISH.getLanguage());
				mtgSet.setCategory(category);
				
				mtgSets.add(mtgSet);
				
				break;

			case "a":
				if (StringUtils.isNotBlank(currElement.text())) {
					mtgSet.setName(currElement.text());
					
					mtgSet.setCategory(fromName(mtgSet.getName(), category));
					
					if (getUnwantedSetsName().contains(mtgSet.getName())) {
						mtgSets.remove(mtgSets.size() - 1);
					}
				}
				
				break;
				
			case "strong":
				category = fromText(currElement.text());
				break;

			default:
				break;
			}
		}
		
		return mtgSets;
	}
	
	private SetCategory fromText(String setCategoryDescription) {
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
	
	private SetCategory fromName(String setName, SetCategory initialCategory) {
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
