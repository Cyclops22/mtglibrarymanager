package com.cyclops.library.mtg.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import com.cyclops.library.mtg.comparator.DisplayLibrarySetFormBeanSorter;
import com.cyclops.library.mtg.domain.SetBean;
import com.cyclops.library.mtg.form.LibrariesForm;
import com.cyclops.library.mtg.form.bean.LibraryFormBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;
import com.cyclops.library.mtg.form.mapper.LibraryFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.LibrarySetFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.SetFormBeanMapper;
import com.cyclops.library.mtg.service.LibraryMgtService;
import com.cyclops.library.mtg.service.SetMgtService;

@Controller
public class LibraryManagementController {
	
	private LibraryMgtService libraryMgtService;
	private SetMgtService setMgtService;
	
	private LibraryFormBeanMapper libraryBeanMapper = new LibraryFormBeanMapper();
	private SetFormBeanMapper setBeanMapper = new SetFormBeanMapper();
	private LibrarySetFormBeanMapper librarySetBeanMapper = new LibrarySetFormBeanMapper();
	
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
	public String displayLibrariesNavigation(Model model) {
		
		LibrariesForm librariesForm = new LibrariesForm();
		librariesForm.setLibraries(libraryBeanMapper.toFormBean(libraryMgtService.findAllLibraries()));
		
		model.addAttribute("form", librariesForm);
		
		return "librarymgt/manageLibraries";
	}
	
	@RequestMapping(value = "/librarymgt/createLibrary", method = RequestMethod.GET)
	public String createLibraryNavigation(Model model) {
		
		model.addAttribute("form", new LibraryFormBean());
		
		return "librarymgt/createLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/createLibrary", method = RequestMethod.POST)
	public String createLibrary(@ModelAttribute("form") LibraryFormBean form, BindingResult result, Model model) {
		
		libraryMgtService.addLibrary(libraryBeanMapper.toBean(form));
		
		return "redirect:/librarymgt/manageLibraries.html";
	}
	
	@RequestMapping(value = "/librarymgt/removeLibrary", method = RequestMethod.POST)
	public String deleteLibrary(@ModelAttribute("form") LibrariesForm form, BindingResult result, Model model) {
		for (LibraryFormBean currLibraryFormBean : form.getLibraries()) {
			if (currLibraryFormBean.isSelected()) {
				System.out.println("Removing library having id " + currLibraryFormBean.getId() + " [To implement]");
			}
		}
		
		return "redirect:/librarymgt/manageLibraries.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/editLibrary", method = RequestMethod.GET)
	public String editLibraryNavigation(@PathVariable("libraryId") String libraryId, Model model) {
		
		LibraryFormBean form = libraryBeanMapper.toFormBean(libraryMgtService.findLibraryById(Integer.parseInt(libraryId)));
		
		form.setSets(DisplayLibrarySetFormBeanSorter.sortForDisplay(form.getSets()));
		
		model.addAttribute("form", form);
		
		return "librarymgt/editLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/submitEditLibrary", params = "AddSets", method = RequestMethod.POST)
	public String addSetsNavigation(@PathVariable("libraryId") String libraryId, @ModelAttribute("form") LibraryFormBean form, BindingResult result, Model model) {
		List<String> usedSets = new ArrayList<>();
		List<LibrarySetFormBean> availableSets = new ArrayList<>();
		
		List<LibrarySetFormBean> setsInLibrary = librarySetBeanMapper.toFormBean(libraryMgtService.findLibraryById(Integer.parseInt(libraryId)).getSets());
		for (LibrarySetFormBean currLibrarySetFormBean : setsInLibrary) {
			usedSets.add(currLibrarySetFormBean.getReferencedSet().getName());
		}
		
		for (SetBean currSetBean : setMgtService.findAll()) {
			if (!usedSets.contains(currSetBean.getName())) {
				LibrarySetFormBean lsfb = new LibrarySetFormBean();
				
				lsfb.setReferencedSet(setBeanMapper.toFormBean(currSetBean));
				availableSets.add(lsfb);
			}
		}
		
		form.setSets(DisplayLibrarySetFormBeanSorter.sortForDisplay(availableSets));
		
		model.addAttribute("form", form);
		
		return "librarymgt/addSets";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/submitEditLibrary", params = "RemoveSelected", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/librarymgt/{libraryId}/addSets", method = RequestMethod.POST)
	public String addSets(@PathVariable("libraryId") String libraryId, @ModelAttribute("form") LibraryFormBean form, BindingResult result, Model model) {
		
		for (LibrarySetFormBean currLibrarySetFormBean : form.getSets()) {
			if (currLibrarySetFormBean.isSelected()) {
				libraryMgtService.addSetToLibrary(Integer.valueOf(libraryId), Integer.valueOf(currLibrarySetFormBean.getReferencedSet().getId()));
			}
		}
		
		model.addAttribute("form", form);
		
		return "redirect:/librarymgt/" + libraryId + "/editLibrary.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/{librarySetId}/editSetLibrary", method = RequestMethod.GET)
	public String editSetLibraryNavigation(@PathVariable("libraryId") String libraryId, @PathVariable("librarySetId") String librarySetId, Model model) {
		
		LibrarySetFormBean form = librarySetBeanMapper.toFormBean(libraryMgtService.findLibrarySetById(Integer.parseInt(librarySetId)));
		
		model.addAttribute("form", form);
		
		return "librarymgt/editSetLibrary";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/{librarySetId}/submitSetLibrary", method = RequestMethod.POST)
	public String submitSetLibrary(@PathVariable("libraryId") String libraryId, @PathVariable("librarySetId") String librarySetId, @ModelAttribute("form") LibrarySetFormBean form, BindingResult result, Model model) {
		
		libraryMgtService.updateLibrarySetQuantities(librarySetBeanMapper.toBean(form));
		
		return "redirect:/librarymgt/" + libraryId + "/editLibrary.html";
	}
	
	@RequestMapping(value = "/librarymgt/{libraryId}/exportlibrary", method = RequestMethod.GET)
	public void exportLibrary(@PathVariable("libraryId") String libraryId, HttpServletResponse res) {
		try {
			OutputStream os = libraryMgtService.exportLibrary(Integer.parseInt(libraryId));
			
			ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
			byte[] baosArray = baos.toByteArray();
			
			res.setContentType("application/xls");
	        res.setContentLength(baosArray.length);
	        res.setHeader("Content-Disposition", "attachment; filename=Library.xls");
			
			IOUtils.write(baosArray, res.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
