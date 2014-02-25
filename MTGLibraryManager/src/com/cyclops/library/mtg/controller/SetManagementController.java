package com.cyclops.library.mtg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.comparator.DisplaySetFormBeanSorter;
import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.SetsForm;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.mapper.CardFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.SetFormBeanMapper;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class SetManagementController {
	
	private SetMgtService setMgtService;
	
	private SetFormBeanMapper setFormBeanMapper = new SetFormBeanMapper();
	private CardFormBeanMapper cardFormBeanMapper = new CardFormBeanMapper();
	
	private SetsForm workForm;
	private Map<String, SetFormBean> setFormBeanBySetName = new LinkedHashMap<>();
	
	@Autowired
	public SetManagementController(SetMgtService mtgLibraryService) {
		this.setMgtService = mtgLibraryService;
	}

	@RequestMapping(value = "/setmgt/manageSets", method = RequestMethod.GET)
	public String displaySets(Model model) {
		
		workForm = new SetsForm();
		workForm.setSets(DisplaySetFormBeanSorter.sortForDisplay(setFormBeanMapper.toFormBean(setMgtService.findAll())));
		
		model.addAttribute("form", workForm);
		model.addAttribute("newSetsForm", new SetsForm());
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/retrieveNewSets", method = RequestMethod.GET)
	public String retrieveNewSets(Model model) {
		List<String> persistedSets = new ArrayList<>();
		SetsForm newSetsForm = new SetsForm();
		
		for (SetFormBean currSet : workForm.getSets()) {
			persistedSets.add(currSet.getName());
		}
		
		try {
			List<SetFormBean> sets = setFormBeanMapper.toFormBean(setMgtService.retrieveAllSets());
			for (Iterator<SetFormBean> iter = sets.iterator(); iter.hasNext(); ) {
				SetFormBean currSetFormBean = iter.next();
				
				if (persistedSets.contains(currSetFormBean.getName())) {
					iter.remove();
				}
			}
			
			newSetsForm.setSets(DisplaySetFormBeanSorter.sortForDisplay(sets));
			
			setFormBeanBySetName.clear();
			for (SetFormBean currSetFormBean : newSetsForm.getSets()) {
				setFormBeanBySetName.put(currSetFormBean.getName(), currSetFormBean);
			}
			
			model.addAttribute("form", workForm);
			model.addAttribute("newSetsForm", newSetsForm);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/{setName}/displaySet", method = RequestMethod.GET)
	public String displaySet(@PathVariable("setName") String setName, Model model) {
		
		for (SetFormBean currSet : workForm.getSets()) {
			if (setName.equals(currSet.getName())) {
				model.addAttribute("cards", currSet.getCards());
				break;
			}
		}
		
		return "setmgt/setDetails";
	}
	
	@RequestMapping(value = "/setmgt/submitMTGSets", params = "Save", method = RequestMethod.POST)
	public String saveMTGSets(@ModelAttribute("newSetsForm") SetsForm newSetsForm, BindingResult result, Model model) {
		for (SetBean currSet : setFormBeanMapper.toBean(newSetsForm.getSets())) {
			currSet.getCards().addAll(cardFormBeanMapper.toBean(setFormBeanBySetName.get(currSet.getName()).getCards()));
			
			setMgtService.addMTGSet(currSet);
		}
		
		return "redirect:/setmgt/manageSets.html";
	}

	public SetFormBeanMapper getSetFormBeanMapper() {
		return setFormBeanMapper;
	}

	public void setSetFormBeanMapper(SetFormBeanMapper setFormBeanMapper) {
		this.setFormBeanMapper = setFormBeanMapper;
	}

	public CardFormBeanMapper getCardFormBeanMapper() {
		return cardFormBeanMapper;
	}

	public void setCardFormBeanMapper(CardFormBeanMapper cardFormBeanMapper) {
		this.cardFormBeanMapper = cardFormBeanMapper;
	}
}
