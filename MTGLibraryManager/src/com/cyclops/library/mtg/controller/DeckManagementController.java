package com.cyclops.library.mtg.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cyclops.library.mtg.form.DecksForm;
import com.cyclops.library.mtg.form.bean.CardFormBean;
import com.cyclops.library.mtg.form.bean.DeckFormBean;
import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;
import com.cyclops.library.mtg.form.bean.LibraryCardSetFormBean;
import com.cyclops.library.mtg.form.bean.LibraryFormBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.form.mapper.DeckFormBeanMapper;
import com.cyclops.library.mtg.service.DeckMgtService;
import com.cyclops.library.mtg.service.LibraryMgtService;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class DeckManagementController {
	
	private DeckMgtService deckMgtService;
	private SetMgtService setMgtService;
	private LibraryMgtService libraryMgtService;
	
	private DeckFormBeanMapper deckFormBeanMapper = new DeckFormBeanMapper();
	
	@Autowired
	public DeckManagementController(DeckMgtService deckMgtService, SetMgtService setMgtService, LibraryMgtService libraryMgtService) {
		this.deckMgtService = deckMgtService;
		this.setMgtService = setMgtService;
		this.libraryMgtService = libraryMgtService;
	}

	@RequestMapping(value = "/deckmgt/manageDecks", method = RequestMethod.GET)
	public String navigateToManageDecks(Model model) {
		
		DecksForm form = new DecksForm();
		
		form.setDecks(deckFormBeanMapper.toFormBean(deckMgtService.findAll()));
		
		model.addAttribute("form", form);
		
		return "deckmgt/manageDecks";
	}
	
	@RequestMapping(value = "/deckmgt/createDeck", method = RequestMethod.GET)
	public String navigateToCreateDeck(Model model) {
		
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
		Map<String, Map<SetFormBean, LibraryCardFormBean>> libraryCardSetByCardName = new HashMap<String, Map<SetFormBean, LibraryCardFormBean>>();
		Map<String, CardFormBean> cardFormBeansByCardName = new HashMap<>();
		
		DeckFormBean form = deckMgtService.findById(Integer.parseInt(deckId));
		
		List<CardFormBean> cardFormBeans = setMgtService.findAllCards();
		MapUtils.populateMap(cardFormBeansByCardName, cardFormBeans, new Transformer<CardFormBean, String>() {
			@Override
			public String transform(CardFormBean input) {
				return input.getName();
			}
		});
		
		for (LibraryFormBean currLibraryFormBean : libraryMgtService.findAllLibraries()) {
			for (LibrarySetFormBean currLibrarySetFormBean : currLibraryFormBean.getSets()) {
				for (LibraryCardFormBean currLibraryCardFormBean : currLibrarySetFormBean.getCards()) {
					Map<SetFormBean, LibraryCardFormBean> libraryCardBySetName = libraryCardSetByCardName.get(currLibraryCardFormBean.getReferencedCard().getName());
					
					if (libraryCardBySetName == null) {
						libraryCardBySetName = new HashMap<>();
					}
					
					LibraryCardFormBean libraryCardFormBean = libraryCardBySetName.get(currLibrarySetFormBean.getReferencedSet().getName());
					
					if (libraryCardFormBean == null) {
						libraryCardFormBean = currLibraryCardFormBean;
						
					} else {
						libraryCardFormBean.setQuantity(currLibraryCardFormBean.getQuantity());
						libraryCardFormBean.setFoilQuantity(currLibraryCardFormBean.getFoilQuantity());
					}
					
					libraryCardBySetName.put(currLibrarySetFormBean.getReferencedSet(), libraryCardFormBean);
					libraryCardSetByCardName.put(currLibraryCardFormBean.getReferencedCard().getName(), libraryCardBySetName);
				}
			}
		}
		
		List<LibraryCardSetFormBean> libraryCardSetFormBeans = new ArrayList<>();
		for (String currCardName : libraryCardSetByCardName.keySet()) {
			Map<SetFormBean, LibraryCardFormBean> libraryCardBySetName = libraryCardSetByCardName.get(currCardName);
			
			LibraryCardSetFormBean libraryCardSetFormBean = new LibraryCardSetFormBean();
			
			libraryCardSetFormBean.setCard(cardFormBeansByCardName.get(currCardName));
			libraryCardSetFormBean.setLibraryCardFormBeansBySet(libraryCardBySetName);
			
			libraryCardSetFormBeans.add(libraryCardSetFormBean);
		}
		
		Collections.sort(libraryCardSetFormBeans, new Comparator<LibraryCardSetFormBean>() {
			@Override
			public int compare(LibraryCardSetFormBean o1, LibraryCardSetFormBean o2) {
				CompareToBuilder ctb = new CompareToBuilder();
				
				ctb.append(o1.getCard().getName(), o2.getCard().getName());
				
				return ctb.toComparison();
			}
		});
		
//		
//		Collections.sort(libraryCardFormBeans, new CardNameLibraryCardFormBeanComparator());
		
		model.addAttribute("form", form);
		model.addAttribute("cards", libraryCardSetFormBeans);
		
		return "deckmgt/editDeck";
	}
}
