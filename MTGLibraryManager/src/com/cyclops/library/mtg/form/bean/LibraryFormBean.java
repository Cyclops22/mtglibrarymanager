package com.cyclops.library.mtg.form.bean;

import java.util.ArrayList;
import java.util.List;


public class LibraryFormBean {
	
	private boolean selected;
	private int id;
	private String name;
	private List<LibrarySetFormBean> sets = new ArrayList<>();

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LibrarySetFormBean> getSets() {
		return sets;
	}

	public void setSets(List<LibrarySetFormBean> sets) {
		this.sets = sets;
	}
}
