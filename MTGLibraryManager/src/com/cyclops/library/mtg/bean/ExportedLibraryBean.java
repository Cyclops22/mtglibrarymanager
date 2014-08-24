package com.cyclops.library.mtg.bean;

import java.io.OutputStream;

public class ExportedLibraryBean {

	private String libraryName;
	private OutputStream libraryOutputStream;
	
	public ExportedLibraryBean(String libraryName, OutputStream libraryOutputStream) {
		this.libraryName = libraryName;
		this.libraryOutputStream = libraryOutputStream;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public OutputStream getLibraryOutputStream() {
		return libraryOutputStream;
	}
}
