package com.cyclops.library.mtg.service;

import java.io.IOException;
import java.io.OutputStream;
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

	OutputStream exportLibrary(int id) throws IOException;
}
