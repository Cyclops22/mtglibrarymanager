package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyclops.library.mtg.form.bean.LibraryFormBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;
import com.cyclops.library.mtg.form.mapper.LibraryFormBeanMapper;
import com.cyclops.library.mtg.form.mapper.LibrarySetFormBeanMapper;
import com.cyclops.library.mtg.repository.LibraryMgtDAO;

@Service("libraryMgtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LibraryMgtServiceImpl implements LibraryMgtService {

	private LibraryMgtDAO libraryMgtDAO;
	
	@Autowired
	private LibraryFormBeanMapper libraryBeanMapper;
	
	@Autowired
	private LibrarySetFormBeanMapper librarySetBeanMapper;
	
	@Autowired
	public LibraryMgtServiceImpl(LibraryMgtDAO libraryMgtDAO) {
		this.libraryMgtDAO = libraryMgtDAO;
	}
	
	@Override
	public List<LibraryFormBean> findAllLibraries() {
		return libraryBeanMapper.toFormBean(libraryMgtDAO.findAllLibraries());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void addLibrary(LibraryFormBean libraryFormBean) {
		libraryMgtDAO.createLibrary(libraryBeanMapper.toBean(libraryFormBean));
	}
	
	@Override
	public void addSetToLibrary(int libraryId, int setId) {
		libraryMgtDAO.addSetToLibrary(libraryId, setId);
	}
	
	@Override
	public LibraryFormBean findLibraryById(int id) {
		return libraryBeanMapper.toFormBean(libraryMgtDAO.findLibraryById(id));
	}
	
	@Override
	public void removeSetFromLibrary(int libraryId, int setId) {
		libraryMgtDAO.removeSetFromLibrary(libraryId, setId);
	}
	
	@Override
	public LibrarySetFormBean findLibrarySetById(int id) {
		return librarySetBeanMapper.toFormBean(libraryMgtDAO.findLibrarySetById(id));
	}

	@Override
	public void updateLibrarySetQuantities(LibrarySetFormBean bean) {
		libraryMgtDAO.updateLibrarySetQuantities(librarySetBeanMapper.toBean(bean));
	}

	@Override
	public OutputStream exportLibrary(int id) throws IOException {
//		return ExcelLibraryExporter.export(findLibraryById(id));
		return null;
	}
}
