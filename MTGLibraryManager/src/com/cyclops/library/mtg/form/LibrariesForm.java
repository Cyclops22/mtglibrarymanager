package com.cyclops.library.mtg.form;

import java.util.ArrayList;
import java.util.List;

import com.cyclops.library.mtg.form.bean.LibraryFormBean;

public class LibrariesForm {

	private List<LibraryFormBean> libraries = new ArrayList<>();

	public List<LibraryFormBean> getLibraries() {
		return libraries;
	}

	public void setLibraries(List<LibraryFormBean> libraries) {
		this.libraries = libraries;
	}
}
