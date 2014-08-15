package com.cyclops.library.mtg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.comparator.CardNumberCardFormBeanComparator;
import com.cyclops.library.mtg.comparator.ReleaseDateSetFormBeanComparator;
import com.cyclops.library.mtg.form.ImportSetsForm;
import com.cyclops.library.mtg.form.SetsForm;
import com.cyclops.library.mtg.form.bean.ImportSetFormBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class SetManagementController {
	
	private SetMgtService setMgtService;
	
	@Autowired
	public SetManagementController(SetMgtService mtgLibraryService) {
		this.setMgtService = mtgLibraryService;
	}

	@RequestMapping(value = "/setmgt/manageSets", method = RequestMethod.GET)
	public String navigateToDisplaySets(Model model) {
		
		List<SetFormBean> setFormBeans = setMgtService.findAll();
		List<ImportSetFormBean> importSetFormBeans = setMgtService.getAvailableSets();
		
		for (Iterator<ImportSetFormBean> iter = importSetFormBeans.iterator(); iter.hasNext(); ) {
			ImportSetFormBean currImportSetFormBean = iter.next();
			
			for (SetFormBean currSetFormBean : setFormBeans) {
				if (currSetFormBean.getCode().equals(currImportSetFormBean.getCode())) {
					iter.remove();
					break;
				}
			}
		}
		
		Collections.sort(setFormBeans, new ReleaseDateSetFormBeanComparator());
				
		ImportSetsForm importSetsForm = new ImportSetsForm();
		importSetsForm.setImportSetFormBeans(importSetFormBeans);
		
		SetsForm setsForm = new SetsForm();
		setsForm.setSets(setFormBeans);
		
		model.addAttribute("setsForm", setsForm);
		model.addAttribute("importSetsForm", importSetsForm); 
		
		return "setmgt/manageSets";
	}
	
	@RequestMapping(value = "/setmgt/importSets", method = RequestMethod.POST)
	public String importSets(@ModelAttribute("importSetsForm") ImportSetsForm importSetsForm, BindingResult result, Model model) {
		List<ImportSetFormBean> setBeans = new ArrayList<>();
		
		for (ImportSetFormBean currImportSetFormBean : importSetsForm.getImportSetFormBeans()) {
			if (currImportSetFormBean.isSelected()) {
				setBeans.add(currImportSetFormBean);
			}
		}
		
		if (!setBeans.isEmpty()) {
			setMgtService.addSets(setBeans);
		}
		
		return "redirect:/setmgt/manageSets.html";
	}
	
	@RequestMapping(value = "/setmgt/{setCode}/displaySet", method = RequestMethod.GET)
	public String navigateToDisplaySet(@PathVariable("setCode") String setCode, Model model) {
		SetFormBean setFormBean = setMgtService.getSetByCode(setCode);
		
		Collections.sort(setFormBean.getCards(), new CardNumberCardFormBeanComparator());
		
		model.addAttribute("set", setFormBean);
		
		return "setmgt/setDetails";
	}
}
