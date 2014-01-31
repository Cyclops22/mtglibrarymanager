package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyclops.library.mtg.domain.SetBean;

public class TCGPlayerParser {
	
	private static final String UNWANTED_SETS_KEY = "parser.wizards.unwanted.sets";
	private static final List<String> UNWANTED_SETS = new ArrayList<>();
	
	static {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("com/cyclops/library/mtg/html/parsing/parsers.properties");
			
			UNWANTED_SETS.addAll(Arrays.asList(config.getStringArray(UNWANTED_SETS_KEY)));
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public List<SetBean> retrieveAllSets() throws IOException {
		SetBean mtgSet = null;
		List<SetBean> mtgSets = new ArrayList<>();

		Document doc = Jsoup.connect("http://magic.tcgplayer.com/all_magic_sets.asp").get();

		Elements elements = doc.select("form + table td img, form + table td a, form + table td strong");
		
		for (int i = 0; i < elements.size(); i++) {
			Element currElement = elements.get(i);
			
			switch (currElement.tagName()) {
			case "img":
				mtgSet = new SetBean();
				
				mtgSet.setLogoUrl(currElement.attr("src"));
				mtgSet.setLanguage(Locale.ENGLISH.getLanguage());
				
				mtgSets.add(mtgSet);
				
				break;

			case "a":
				if (StringUtils.isNotBlank(currElement.text())) {
					mtgSet.setName(currElement.text());
					
					if (UNWANTED_SETS.contains(mtgSet.getName())) {
						mtgSets.remove(mtgSets.size() - 1);
					}
				}
				
				break;

			default:
				break;
			}
		}
		
		return mtgSets;
	}
}
