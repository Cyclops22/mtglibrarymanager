package com.cyclops.library.mtg.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.domain.MTGSetBean;
import com.cyclops.library.mtg.form.MTGSetsForm;
import com.cyclops.library.mtg.form.bean.MTGSetFormBean;
import com.cyclops.library.mtg.form.util.MTGSetFormBeanMapper;
import com.cyclops.library.mtg.service.MTGLibraryService;

@Controller
public class ManageSetsController {
	
	private MTGLibraryService mtgLibraryService;
	
	private MTGSetsForm form;
	
	@Autowired
	public ManageSetsController(MTGLibraryService mtgLibraryService) {
		this.mtgLibraryService = mtgLibraryService;
	}

	@RequestMapping(value = "/manageSets", method = RequestMethod.GET)
	public String displaySets(Model model) {
		
		form = new MTGSetsForm();
		form.setSets(new MTGSetFormBeanMapper().toFormBean(getMtgLibraryService().findAll()));
		
		model.addAttribute("form", form);
		
		return "manageSets";
	}
	
	@RequestMapping(value = "/updateSetsFromTCGSite", method = RequestMethod.GET)
	public String scanSite(Model model) {
		
		form = new MTGSetsForm();
		
		try {
			form.setSets(new MTGSetFormBeanMapper().toFormBean(getMtgLibraryService().retrieveAllSets()));
			
			model.addAttribute("form", form);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "manageSets";
	}
	
	@RequestMapping(value = "/{setName}/displaySet", method = RequestMethod.GET)
	public String displaySet(@PathVariable("setName") String setName, Model model) {
		
		for (MTGSetFormBean currSet : form.getSets()) {
			if (setName.equals(currSet.getName())) {
				model.addAttribute("cards", currSet.getCards());
				break;
			}
		}
		
		return "setDetails";
	}
	
	@RequestMapping(value = "/submitMTGSets", params = "Update", method = RequestMethod.POST)
	public String editMTGSets(@ModelAttribute("form") MTGSetsForm form, BindingResult result, Model model) throws IOException {
		
		form.setSets(getMtgLibraryService().populateSets(form.getSets()));
		
		model.addAttribute("form", form);
		
		this.form = form;
		
		return "manageSets";
	}
	
	@RequestMapping(value = "/submitMTGSets", params = "Save", method = RequestMethod.POST)
	public String saveMTGSets(@ModelAttribute("form") MTGSetsForm form, BindingResult result, Model model) {
		MTGSetFormBeanMapper mtgSetFormBeanMapper = new MTGSetFormBeanMapper();
		
		for (MTGSetBean currSet : mtgSetFormBeanMapper.toBean(form.getSets())) {
			getMtgLibraryService().addMTGSet(currSet);
		}
		
		this.form.setSets(new MTGSetFormBeanMapper().toFormBean(getMtgLibraryService().findAll()));
		
		model.addAttribute("form", this.form);
		
		return "manageSets";
	}
	
	public MTGLibraryService getMtgLibraryService() {
		return mtgLibraryService;
	}
}
