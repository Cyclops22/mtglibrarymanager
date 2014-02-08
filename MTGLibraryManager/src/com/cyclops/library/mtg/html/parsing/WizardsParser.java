package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyclops.library.mtg.Constants;
import com.cyclops.library.mtg.domain.SetBean;

public class WizardsParser extends SiteParser {
	
	private static final String SITE_INFO_URL = "http://www.wizards.com/magic/tcg/article.aspx?x=mtg/tcg/products/allproducts";
	
	private static final String UNWANTED_SETS_KEY = "parser.wizards.unwanted.sets";
	
	public WizardsParser() {
		initUnwantedSets(UNWANTED_SETS_KEY);
	}
	
	public List<SetBean> retrieveSetsDetails(List<SetBean> sets) throws IOException {
		initMaps(sets);
		
		retrieveReleaseDate();
		
		return getSets();
	}
	
	private void retrieveReleaseDate() throws IOException {
		SetBean setBean = null;
		
		Document doc = Jsoup.connect(SITE_INFO_URL).get();

		Elements elements = doc.select("table[background]");
		for (int i = 0; i < elements.size(); i++) {
			Element currElement = elements.get(i);
			
			Elements tdElements = currElement.select("td");
			
			String setName = tdElements.get(1).text();
			
			if (!getUnwantedSetsName().contains(setName)) {
				setBean = getSetBean(setName);
				Date releaseDate = extractReleaseDate(tdElements.get(3).text());
				
				if (setBean != null) {
					setBean.setReleaseDate(releaseDate);
					
				}
				
				if ("Alpha, Beta, and Unlimited".equals(setName)) {
					setBean = getSetBean("Beta Edition");
					
					if (setBean != null) {
						setBean.setReleaseDate(releaseDate);
					}
					
					setBean = getSetBean("Unlimited Edition");
					
					if (setBean != null) {
						setBean.setReleaseDate(releaseDate);
					}
				}
				
				if ("Revised".equals(setName)) {
					setBean = getSetBean("Revised Edition");
					
					if (setBean != null) {
						setBean.setReleaseDate(releaseDate);
					}
				}
				
				if ("Time Spiral".equals(setName)) {
					setBean = getSetBean("Timeshifted");
					
					if (setBean != null) {
						setBean.setReleaseDate(releaseDate);
					}
				}
			}
		}
	}
	
	private Date extractReleaseDate(String releaseDateText) {
		String releaseDateStr;
		Date releaseDate = null;
		
		try {
			if (StringUtils.contains(releaseDateText, "Coming")) {
				releaseDateStr = StringUtils.substringAfter(releaseDateText, "Coming ");
				Date comingReleaseDate = Constants.RELEASE_DATE_DATEFORMAT.parse(releaseDateStr);
				
				if (comingReleaseDate.before(new Date())) {
					releaseDate = comingReleaseDate;
				}
				
			} else {
				releaseDateStr = StringUtils.substringAfter(releaseDateText, "Released ");
				releaseDate = Constants.RELEASE_DATE_DATEFORMAT.parse(releaseDateStr);
				
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return releaseDate;
	}
}
