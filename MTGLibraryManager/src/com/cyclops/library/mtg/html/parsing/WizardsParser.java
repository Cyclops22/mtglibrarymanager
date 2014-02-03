package com.cyclops.library.mtg.html.parsing;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cyclops.library.mtg.Constants;
import com.cyclops.library.mtg.domain.SetBean;

public class WizardsParser {
	
	private Map<String, SetBean> formSetByName = new LinkedHashMap<>();
	private Map<String, SetBean> formSetByAlias = new LinkedHashMap<>();
	
	public List<SetBean> retrieveSetsDetails(List<SetBean> sets) throws IOException {
		initMaps(sets);
		
		retrieveReleaseDate();
		
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

	private void retrieveReleaseDate() throws IOException {
		SetBean setBean = null;
		
		Document doc = Jsoup.connect("http://www.wizards.com/magic/tcg/article.aspx?x=mtg/tcg/products/allproducts").get();

		Elements elements = doc.select("table[background]");
		for (int i = 0; i < elements.size(); i++) {
			Element currElement = elements.get(i);
			
			Elements tdElements = currElement.select("td");
			
			String setName = tdElements.get(1).text();
			
			setBean = getSetBean(setName);
			Date releaseDate = null;
			if (setBean != null) {
				releaseDate = extractReleaseDate(tdElements.get(3).text());
				setBean.setReleaseDate(releaseDate);
				
			}
			
			if ("Time Spiral".equals(setName)) {
				setBean = getSetBean("Timeshifted");
				
				if (setBean != null) {
					setBean.setReleaseDate(releaseDate);
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
	
	private SetBean getSetBean(String setName) {
		SetBean setBean = formSetByName.get(setName);
		if (setBean == null) {
			setBean = formSetByAlias.get(setName);
		}
		
		return setBean;
	}
}
