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

import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.SetsForm;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.mapper.CardFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.SetFormBeanMapper;
import com.cyclops.library.mtg.html.parsing.WizardsParser;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class ManageSetsController {
	
	private SetMgtService setMgtService;
	
	private SetsForm workForm;
	private Map<String, SetFormBean> setFormBeanBySetName = new LinkedHashMap<>();
	
	@Autowired
	public ManageSetsController(SetMgtService mtgLibraryService) {
		this.setMgtService = mtgLibraryService;
	}

	@RequestMapping(value = "/setmgt/manageSets", method = RequestMethod.GET)
	public String displaySets(Model model) {
		
		workForm = new SetsForm();
		workForm.setSets(new SetFormBeanMapper().toFormBean(setMgtService.findAll()));
		
		model.addAttribute("form", workForm);
		model.addAttribute("newSetsForm", new SetsForm());
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/test", method = RequestMethod.GET)
	public String test(Model model) throws IOException {
		
		workForm = new SetsForm();
		workForm.setSets(new SetFormBeanMapper().toFormBean(new WizardsParser().retrieveSetsDetails(setMgtService.retrieveAllSets())));
		
		model.addAttribute("form", new SetsForm());
		model.addAttribute("newSetsForm", workForm);
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/updateSetsFromTCGSite", method = RequestMethod.GET)
	public String scanSite(Model model) {
		List<String> persistedSets = new ArrayList<>();
		SetsForm newSetsForm = new SetsForm();
		
		for (SetFormBean currSet : workForm.getSets()) {
			persistedSets.add(currSet.getName());
		}
		
		try {
			newSetsForm.setSets(new SetFormBeanMapper().toFormBean(setMgtService.retrieveAllSets()));
			for (Iterator<SetFormBean> iter = newSetsForm.getSets().iterator(); iter.hasNext(); ) {
				SetFormBean currSetFormBean = iter.next();
				
				if (persistedSets.contains(currSetFormBean.getName())) {
					iter.remove();
				}
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
	
	@RequestMapping(value = "/setmgt/submitMTGSets", params = "Retrieve", method = RequestMethod.POST)
	public String editMTGSets(@ModelAttribute("newSetsForm") SetsForm newSetsForm, BindingResult result, Model model) throws IOException {
		SetFormBeanMapper beanMapper = new SetFormBeanMapper();
		
		newSetsForm.setSets(beanMapper.toFormBean(setMgtService.populateSets(beanMapper.toBean(newSetsForm.getSets()))));
		
		setFormBeanBySetName.clear();
		for (SetFormBean currSetFormBean : newSetsForm.getSets()) {
			setFormBeanBySetName.put(currSetFormBean.getName(), currSetFormBean);
		}
		
		model.addAttribute("form", workForm);
		model.addAttribute("newSetsForm", newSetsForm);
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/submitMTGSets", params = "Save", method = RequestMethod.POST)
	public String saveMTGSets(@ModelAttribute("newSetsForm") SetsForm newSetsForm, BindingResult result, Model model) {
		SetFormBeanMapper mtgSetFormBeanMapper = new SetFormBeanMapper();
		CardFormBeanMapper mtgCardFormBeanMapper = new CardFormBeanMapper();
		
		for (SetBean currSet : mtgSetFormBeanMapper.toBean(newSetsForm.getSets())) {
			currSet.getCards().addAll(mtgCardFormBeanMapper.toBean(setFormBeanBySetName.get(currSet.getName()).getCards()));
			
			setMgtService.addMTGSet(currSet);
		}
		
		return "redirect:/setmgt/manageSets.html";
	}
}
