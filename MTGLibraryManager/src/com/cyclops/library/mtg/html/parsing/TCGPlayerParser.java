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

import com.cyclops.library.mtg.domain.MTGSetBean;

public class TCGPlayerParser {

	public List<MTGSetBean> retrieveAllSets() throws IOException {
		MTGSetBean mtgSet = null;
		List<MTGSetBean> mtgSets = new ArrayList<>();

		Document doc = Jsoup.connect("http://magic.tcgplayer.com/all_magic_sets.asp").get();

		Elements elements = doc.select("form + table td img, form + table td a, form + table td strong");
		
		boolean continueAdding = true;
		for (int i = 0; i < elements.size() && continueAdding; i++) {
			Element currElement = elements.get(i);
			
			switch (currElement.tagName()) {
			case "img":
				mtgSet = new MTGSetBean();
				
				mtgSet.setImageUrl(currElement.attr("src"));
				mtgSet.setLanguage(Locale.ENGLISH.getLanguage());
				
				mtgSets.add(mtgSet);
				
				break;

			case "a":
				if (StringUtils.isNotBlank(currElement.text())) {
					mtgSet.setName(currElement.text());
				}
				
				break;

			case "strong":
				if ("Promos".equals(currElement.text())) {
					continueAdding = false;
				}
			default:
				break;
			}
		}
		
		return mtgSets;
	}
}
