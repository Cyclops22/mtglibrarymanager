package com.cyclops.library.mtg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.form.DecksForm;
import com.cyclops.library.mtg.form.bean.DeckFormBean;
import com.cyclops.library.mtg.form.bean.EditionCardFormBean;
import com.cyclops.library.mtg.form.mapper.DeckFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.EditionCardFormBeanMapper;
import com.cyclops.library.mtg.service.DeckMgtService;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class DeckManagementController {
	
	private DeckMgtService deckMgtService;
	private SetMgtService setMgtService;
	
	private DeckFormBeanMapper deckFormBeanMapper = new DeckFormBeanMapper();
	private EditionCardFormBeanMapper editionCardFormBeanMapper = new EditionCardFormBeanMapper();
	
//	private SetFormBeanMapper setFormBeanMapper = new SetFormBeanMapper();
//	private CardFormBeanMapper cardFormBeanMapper = new CardFormBeanMapper();
//	
//	private SetsForm workForm;
//	private Map<String, SetFormBean> setFormBeanBySetName = new LinkedHashMap<>();
	
	@Autowired
	public DeckManagementController(DeckMgtService deckMgtService, SetMgtService setMgtService) {
		this.deckMgtService = deckMgtService;
		this.setMgtService = setMgtService;
	}

	@RequestMapping(value = "/deckmgt/manageDecks", method = RequestMethod.GET)
	public String displayDecksNavigation(Model model) {
		
		DecksForm form = new DecksForm();
		
		form.setDecks(deckFormBeanMapper.toFormBean(deckMgtService.findAll()));
		
		model.addAttribute("form", form);
		
		return "deckmgt/manageDecks";
	}
	
	@RequestMapping(value = "/deckmgt/createDeck", method = RequestMethod.GET)
	public String createDeckNavigation(Model model) {
		
		model.addAttribute("form", new DeckFormBean());
		
		return "deckmgt/createDeck";
	}
	
	@RequestMapping(value = "/deckmgt/createDeck", method = RequestMethod.POST)
	public String createDeck(@ModelAttribute("form") DeckFormBean form, BindingResult result, Model model) {
		
		deckMgtService.addDeck(deckFormBeanMapper.toBean(form));
		
		return "redirect:/deckmgt/manageDecks.html";
	}
	
	@RequestMapping(value = "/deckmgt/removeDeck", method = RequestMethod.POST)
	public String deleteDeck(@ModelAttribute("form") DecksForm form, BindingResult result, Model model) {
		for (DeckFormBean currDeckFormBean : form.getDecks()) {
			if (currDeckFormBean.isSelected()) {
				System.out.println("Removing deck having id " + currDeckFormBean.getId() + " [To implement]");
			}
		}
		
		return "redirect:/deckmgt/manageDecks.html";
	}
	
	@RequestMapping(value = "/deckmgt/{deckId}/editDeck", method = RequestMethod.GET)
	public String editDeckNavigation(@PathVariable("deckId") String deckId, Model model) {
		
		DeckFormBean form = deckFormBeanMapper.toFormBean(deckMgtService.findById(Integer.parseInt(deckId)));
		
		List<EditionCardFormBean> editionsCards = editionCardFormBeanMapper.toFormBean(setMgtService.getAllCards());
		
		model.addAttribute("form", form);
		model.addAttribute("cards", editionsCards);
		
		return "deckmgt/editDeck";
	}
}
