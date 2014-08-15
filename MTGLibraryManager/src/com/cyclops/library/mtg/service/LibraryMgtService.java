package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.cyclops.library.mtg.form.bean.LibraryFormBean;
import com.cyclops.library.mtg.form.bean.LibrarySetFormBean;

public interface LibraryMgtService {

	List<LibraryFormBean> findAllLibraries();

	void addLibrary(LibraryFormBean libraryFormBean);

	LibraryFormBean findLibraryById(int id);

	void addSetToLibrary(int libraryId, int setId);

	void removeSetFromLibrary(int libraryId, int setId);

	LibrarySetFormBean findLibrarySetById(int id);

	OutputStream exportLibrary(int id) throws IOException;

	void updateLibrarySetQuantities(LibrarySetFormBean bean);
}
