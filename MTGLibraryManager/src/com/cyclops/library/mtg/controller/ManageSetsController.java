package com.cyclops.library.mtg.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
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
import com.cyclops.library.mtg.service.MTGLibraryService;

@Controller
public class ManageSetsController {
	
	private MTGLibraryService mtgLibraryService;
	
	private SetsForm workForm;
	private Map<String, SetFormBean> setFormBeanBySetName = new LinkedHashMap<>();
	
	@Autowired
	public ManageSetsController(MTGLibraryService mtgLibraryService) {
		this.mtgLibraryService = mtgLibraryService;
	}

	@RequestMapping(value = "/setmgt/manageSets", method = RequestMethod.GET)
	public String displaySets(Model model) {
		
		workForm = new SetsForm();
		workForm.setSets(new SetFormBeanMapper().toFormBean(getMtgLibraryService().findAll()));
		
		model.addAttribute("form", workForm);
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/updateSetsFromTCGSite", method = RequestMethod.GET)
	public String scanSite(Model model) {
		
		workForm = new SetsForm();
		
		try {
			workForm.setSets(new SetFormBeanMapper().toFormBean(getMtgLibraryService().retrieveAllSets()));
			
			model.addAttribute("form", workForm);
			
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
	public String editMTGSets(@ModelAttribute("form") SetsForm form, BindingResult result, Model model) throws IOException {
		SetFormBeanMapper beanMapper = new SetFormBeanMapper();
		
		form.setSets(beanMapper.toFormBean(getMtgLibraryService().populateSets(beanMapper.toBean(form.getSets()))));
		
		setFormBeanBySetName.clear();
		for (SetFormBean currSetFormBean : form.getSets()) {
			setFormBeanBySetName.put(currSetFormBean.getName(), currSetFormBean);
		}
		
		model.addAttribute("form", form);
		
		this.workForm = form;
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/submitMTGSets", params = "Save", method = RequestMethod.POST)
	public String saveMTGSets(@ModelAttribute("form") SetsForm form, BindingResult result, Model model) {
		SetFormBeanMapper mtgSetFormBeanMapper = new SetFormBeanMapper();
		CardFormBeanMapper mtgCardFormBeanMapper = new CardFormBeanMapper();
		
		for (SetBean currSet : mtgSetFormBeanMapper.toBean(form.getSets())) {
			currSet.getCards().addAll(mtgCardFormBeanMapper.toBean(setFormBeanBySetName.get(currSet.getName()).getCards()));
			
			getMtgLibraryService().addMTGSet(currSet);
		}
		
		this.workForm.setSets(new SetFormBeanMapper().toFormBean(getMtgLibraryService().findAll()));
		
		model.addAttribute("form", this.workForm);
		
		return "setmgt/manageSets";
	}
	
	public MTGLibraryService getMtgLibraryService() {
		return mtgLibraryService;
	}
}
