package com.cyclops.library.mtg.service;

import java.util.List;

import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;

public interface LibraryMgtService {

	void addLibrary(LibraryBean libraryBean);

	List<LibraryBean> findAllLibraries();

	LibraryBean findLibraryById(int id);

	void addSetToLibrary(int libraryId, int setId);

	void removeSetFromLibrary(int libraryId, int setId);

	LibrarySetBean findLibrarySetById(int id);

	void updateLibrarySetQuantities(LibrarySetBean bean);


}
