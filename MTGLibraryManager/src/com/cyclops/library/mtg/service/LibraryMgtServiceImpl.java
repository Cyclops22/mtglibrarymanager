package com.cyclops.library.mtg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.repository.LibraryMgtDAO;

@Service("libraryMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LibraryMgtServiceImpl implements LibraryMgtService {

	private LibraryMgtDAO libraryMgtDAO;
	
	@Autowired
	public LibraryMgtServiceImpl(LibraryMgtDAO libraryMgtDAO) {
		this.libraryMgtDAO = libraryMgtDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addLibrary(LibraryBean libraryBean) {
		libraryMgtDAO.createLibrary(libraryBean);
	}
	
	@Override
	public void addSetToLibrary(int libraryId, int setId) {
		libraryMgtDAO.addSetToLibrary(libraryId, setId);
	}
	
	@Override
	public List<LibraryBean> findAllLibraries() {
		return libraryMgtDAO.findAllLibraries();
	}

	@Override
	public LibraryBean findLibraryById(int id) {
		return libraryMgtDAO.findLibraryById(id);
	}
	
	@Override
	public void removeSetFromLibrary(int libraryId, int setId) {
		libraryMgtDAO.removeSetFromLibrary(libraryId, setId);
	}
	
	@Override
	public LibrarySetBean findLibrarySetById(int id) {
		return libraryMgtDAO.findLibrarySetById(id);
	}

	@Override
	public void updateLibrarySetQuantities(LibrarySetBean bean) {
		libraryMgtDAO.updateLibrarySetQuantities(bean);
	}
}
