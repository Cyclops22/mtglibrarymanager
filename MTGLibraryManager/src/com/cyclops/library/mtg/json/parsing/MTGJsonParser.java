package com.cyclops.library.mtg.json.parsing;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.cyclops.library.mtg.json.bean.SetJsonBean;

@Component("mtgjsonparser")
public class MTGJsonParser {

	private static final String AVAILABLE_SETS_SOURCE = "http://mtgjson.com/json/SetList.json";
	private static final String FULL_SET_SOURCE_URL = "http://mtgjson.com/json/AllSets.json";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public List<SetJsonBean> getAvailableSets() {
		List<SetJsonBean> availableSets = new ArrayList<>();
		
		try {
			List<SetJsonBean> allSets = mapper.readValue(new URL(AVAILABLE_SETS_SOURCE), new TypeReference<List<SetJsonBean>>() {});
			
			availableSets.addAll(allSets);
	 
		} catch (JsonGenerationException e) {
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
			e.printStackTrace();
	 
		} catch (IOException e) {
			e.printStackTrace();
	 
		}
		
		return availableSets;
	}
	
	public List<SetJsonBean> getSets(List<String> setCodes) {
		List<SetJsonBean> availableSets = new ArrayList<>();
		
		try {
			Map<String, SetJsonBean> allSetsMap = mapper.readValue(new URL(FULL_SET_SOURCE_URL), new TypeReference<Map<String, SetJsonBean>>() {});
			
			for (String currSetCode : allSetsMap.keySet()) {
				if (setCodes.contains(currSetCode)) {
					availableSets.add(allSetsMap.get(currSetCode));
				}
			}
	 
		} catch (JsonGenerationException e) {
	 
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
	 
			e.printStackTrace();
	 
		} catch (IOException e) {
	 
			e.printStackTrace();
	 
		}
		
		return availableSets;
	}
	
	
}
