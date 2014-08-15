package com.cyclops.library.mtg.repository;

import java.util.List;

import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;
import com.cyclops.library.mtg.domain.SetBean;

public interface LibraryMgtDAO {

	void createLibrary(LibraryBean libraryBean);

	List<LibraryBean> findAllLibraries();

	LibraryBean findLibraryById(int id);

//	SetBean findSetById(int id);

	void addSetToLibrary(int libraryId, int setId);

	void removeSetFromLibrary(int libraryId, int setId);

	LibrarySetBean findLibrarySetById(int id);

	void updateLibrarySetQuantities(LibrarySetBean bean);

}
