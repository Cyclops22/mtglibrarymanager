package com.cyclops.library.mtg.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cyclops.library.mtg.bean.ExportedLibraryBean;
import com.cyclops.library.mtg.comparator.CardNumberLibraryCardFormBeanComparator;
import com.cyclops.library.mtg.comparator.ReleaseDateLibrarySetFormBeanComparator;
import com.cyclops.library.mtg.comparator.ReleaseDateSetFormBeanComparator;
import com.cyclops.library.mtg.form.LibrariesForm;
import com.cyclops.library.mtg.form.SetsForm;
import com.cyclops.library.mtg.form.bean.LibraryCardFormBean;
import com.cyclops.library.mtg.form.bean.LibraryFormBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;
import com.cyclops.library.mtg.form.bean.SetFormBean;
import com.cyclops.library.mtg.service.LibraryMgtService;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class LibraryManagementController {
	
	private LibraryMgtService libraryMgtService;
	private SetMgtService setMgtService;
	
	@Autowired
	public LibraryManagementController(LibraryMgtService libraryMgtService, SetMgtService setMgtService) {
		this.libraryMgtService = libraryMgtService;
		this.setMgtService = setMgtService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setAutoGrowCollectionLimit(500);
	}

	@RequestMapping(value = "/librarymgt/manageLibraries", method = RequestMethod.GET)
	public String navigateToDisplayLibraries(Model model) {
		
		LibrariesForm librariesForm = new LibrariesForm();
		librariesForm.setLibraries(libraryMgtService.findAllLibraries());
		
		model.addAttribute("form", librariesForm);
		
		return "librarymgt/manageLibraries";
	}
	
	@RequestMapping(value = "/librarymgt/createLibrary", method = RequestMethod.GET)
	public String navigateToCreateLibrary(Model model) {
		
		model.addAttribute("form", new LibraryFormBean());
		
		return "librarymgt/createLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/createLibrary", method = RequestMethod.POST)
	public String createLibrary(@ModelAttribute("form") LibraryFormBean form, BindingResult result, Model model) {
		
		libraryMgtService.addLibrary(form);
		
		return "redirect:/librarymgt/manageLibraries.html";
	}
	
	@RequestMapping(value = "/librarymgt/deleteLibrary", method = RequestMethod.POST)
	public String deleteLibrary(@ModelAttribute("form") LibrariesForm form, BindingResult result, Model model) {
		for (LibraryFormBean currLibraryFormBean : form.getLibraries()) {
			if (currLibraryFormBean.isSelected()) {
				System.out.println("Removing library having id " + currLibraryFormBean.getId() + " [To implement]");
			}
		}
		
		return "redirect:/librarymgt/manageLibraries.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/editLibrary", method = RequestMethod.GET)
	public String navigateToEditLibrary(@PathVariable("libraryId") String libraryId, Model model) {
		
		LibraryFormBean form = libraryMgtService.findLibraryById(Integer.parseInt(libraryId));
		List<SetFormBean> setFormBeans = setMgtService.findAll();
		
		// Remove already present sets
		for (Iterator<SetFormBean> iter = setFormBeans.iterator(); iter.hasNext(); ) {
			SetFormBean currSetFormBean = iter.next();
			
			for (LibrarySetFormBean currLibrarySetFormBean : form.getSets()) {
				if (currLibrarySetFormBean.getReferencedSet().getCode().equals(currSetFormBean.getCode())) {
					iter.remove();
					break;
				}
			}
		}
		
		Collections.sort(setFormBeans, new ReleaseDateSetFormBeanComparator());
		Collections.sort(form.getSets(), new ReleaseDateLibrarySetFormBeanComparator());
		
		SetsForm setsForm = new SetsForm();
		setsForm.setSets(setFormBeans);
		
		model.addAttribute("form", form);
		model.addAttribute("setsForm", setsForm);
		
		return "librarymgt/editLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/addSets", method = RequestMethod.POST)
	public String addSets(@PathVariable("libraryId") String libraryId, @ModelAttribute("setsForm") SetsForm form, BindingResult result, Model model) {
		
		for (SetFormBean currSetFormBean : form.getSets()) {
			if (currSetFormBean.isSelected()) {
				libraryMgtService.addSetToLibrary(Integer.valueOf(libraryId), currSetFormBean.getId());
			}
		}
		
		return "redirect:/librarymgt/" + libraryId + "/editLibrary.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/removeSets", method = RequestMethod.POST)
	public String removeSelectedSets(@PathVariable("libraryId") String libraryId, @ModelAttribute("form") LibraryFormBean form, BindingResult result, Model model) {
		
		for (Iterator<LibrarySetFormBean> iter = form.getSets().iterator(); iter.hasNext(); ) {
			LibrarySetFormBean currLibrarySetFormBean = iter.next();
			
			if (currLibrarySetFormBean.isSelected()) {
				libraryMgtService.removeSetFromLibrary(Integer.parseInt(libraryId), currLibrarySetFormBean.getReferencedSet().getId());
			}
		}
		
		model.addAttribute("form", form);
		
		return "redirect:/librarymgt/" + libraryId + "/editLibrary.html";
	}
	
	
	
	@RequestMapping(value = "/librarymgt/{libraryId}/{librarySetId}/editSetLibrary", method = RequestMethod.GET)
	public String editSetLibraryNavigation(@PathVariable("libraryId") String libraryId, @PathVariable("librarySetId") String librarySetId, Model model) {
		
		LibrarySetFormBean form = libraryMgtService.findLibrarySetById(Integer.parseInt(librarySetId));
		
		Collections.sort(form.getCards(), new CardNumberLibraryCardFormBeanComparator());
		
		model.addAttribute("form", form);
		
		return "librarymgt/editSetLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/{librarySetId}/editSetLibrary", method = RequestMethod.POST)
	public String editSetLibrary(@PathVariable("libraryId") String libraryId, @PathVariable("librarySetId") String librarySetId, @ModelAttribute("form") LibrarySetFormBean form, BindingResult result, Model model) {
		
		libraryMgtService.updateLibrarySetQuantities(form);
		
		return "redirect:/librarymgt/" + libraryId + "/editLibrary.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/exportlibrary", method = RequestMethod.GET)
	public void exportLibrary(@PathVariable("libraryId") String libraryId, HttpServletResponse res) {
		try {
			ExportedLibraryBean exportedLibraryBean = libraryMgtService.exportLibrary(Integer.parseInt(libraryId));
			
			ByteArrayOutputStream baos = (ByteArrayOutputStream) exportedLibraryBean.getLibraryOutputStream();
			byte[] baosArray = baos.toByteArray();
			
			res.setContentType("application/xls");
	        res.setContentLength(baosArray.length);
	        res.setHeader("Content-Disposition", "attachment; filename=" + exportedLibraryBean.getLibraryName() + ".xls");
			
			IOUtils.write(baosArray, res.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/librarymgt/import", method = RequestMethod.POST)
	public String importIntoLibrary(@RequestParam("libraryName") String libraryName, @RequestParam("importText") String importText, Model model) {
		Map<String, Map<String, ImportLine>> importLinesBySetCode = new HashMap<>();
		
		for (String currImportLine : StringUtils.split(importText, SystemUtils.LINE_SEPARATOR)) {
			ImportLine importLine = new ImportLine(currImportLine);
			
			Map<String, ImportLine> importLinesByCardName = importLinesBySetCode.get(importLine.getSetCode());
			if (importLinesByCardName == null) {
				importLinesByCardName = new HashMap<>();
			}
			
			importLinesByCardName.put(importLine.getCardName(), importLine);
			importLinesBySetCode.put(importLine.getSetCode(), importLinesByCardName);
		}
		
		for (LibraryFormBean currLibraryFormBean : libraryMgtService.findAllLibraries()) {
			if (libraryName.equals(currLibraryFormBean.getName())) {
				for (LibrarySetFormBean currLibrarySetFormBean : currLibraryFormBean.getSets()) {
					if (importLinesBySetCode.containsKey(currLibrarySetFormBean.getReferencedSet().getCode())) {
						Map<String, ImportLine> importLinesByCardName = importLinesBySetCode.get(currLibrarySetFormBean.getReferencedSet().getCode());
						
						for (LibraryCardFormBean currLibraryCardFormBean : currLibrarySetFormBean.getCards()) {
							ImportLine importLine = importLinesByCardName.get(currLibraryCardFormBean.getReferencedCard().getName());
							
							if (importLine != null) {
								currLibraryCardFormBean.setQuantity(Integer.valueOf(importLine.getQty()));
								currLibraryCardFormBean.setFoilQuantity(Integer.valueOf(importLine.getFoilQty()));
							}
						}
					}
					
					libraryMgtService.updateLibrarySetQuantities(currLibrarySetFormBean);
				}
			}
		}
		
		return "redirect:/librarymgt/manageLibraries.html";
	}
	
	private class ImportLine {
		private String cardName;
		private String setCode;
		private String qty;
		private String foilQty;
		
		public ImportLine(String importLine) {
			String[] importLineArray = StringUtils.splitPreserveAllTokens(importLine, ';');
			
			this.cardName = importLineArray[0];
			this.setCode = importLineArray[1];
			this.qty = StringUtils.defaultString(StringUtils.trimToNull(importLineArray[2]), "0");
			this.foilQty = StringUtils.defaultString(StringUtils.trimToNull(importLineArray[3]), "0");
		}
		
		
		public String getCardName() {
			return cardName;
		}
		
		public String getSetCode() {
			return setCode;
		}
		
		public String getQty() {
			return qty;
		}
		
		public String getFoilQty() {
			return foilQty;
		}
	}
}
