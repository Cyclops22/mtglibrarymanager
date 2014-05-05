package com.cyclops.library.mtg.html.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import com.cyclops.library.mtg.domain.SetBean;

public class SiteParser {
	
	private Map<String, SetBean> formSetByName = new LinkedHashMap<>();
	private List<String> unwantedSets = new ArrayList<>();

	protected SiteParser() {
		
	}
	
	protected void initMaps(List<SetBean> sets) {
		formSetByName.clear();
		
		for (SetBean currSet : sets) {
			formSetByName.put(currSet.getName(), currSet);
		}
	}
	
	protected void initUnwantedSets(String key) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("com/cyclops/library/mtg/html/parsing/parsers.properties");
			
			unwantedSets.addAll(Arrays.asList(config.getStringArray(key)));
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	protected List<String> getSetsName() {
		return new ArrayList<>(formSetByName.keySet());
	}
	
	protected List<SetBean> getSets() {
		return new ArrayList<>(formSetByName.values());
	}
	
	protected List<String> getUnwantedSetsName() {
		return new ArrayList<>(unwantedSets);
	}
	
	protected SetBean getSetBean(String setName) {
		SetBean setBean = formSetByName.get(setName);
		
		if (setBean == null) {
			Map<Integer, List<SetBean>> setByLevenshtein = new TreeMap<>();
			
			for (SetBean currSetBean : formSetByName.values()) {
				if (currSetBean.getName() != null) {
					int distance = StringUtils.getLevenshteinDistance(setName, currSetBean.getName());
					
					List<SetBean> setsWithDistance = setByLevenshtein.get(distance);
					if (setsWithDistance == null) {
						setsWithDistance = new ArrayList<>();
					}
					
					setsWithDistance.add(currSetBean);
					
					setByLevenshtein.put(distance, setsWithDistance);
				}
			}
			
			
			List<SetBean> nearestSets = setByLevenshtein.values().iterator().next();
			if (nearestSets.size() == 1) {
				setBean = nearestSets.get(0);
				
			} else {
				List<SetBean> candidates = new ArrayList<>();
				for (SetBean currSet : nearestSets) {
					if (currSet.getName().contains(setName) || setName.contains(currSet.getName())) {
						candidates.add(currSet);
					}
				}
				
				if (candidates.size() == 1) {
					setBean = candidates.iterator().next();
				}
			}
		}
		
		return setBean;
	}
}
