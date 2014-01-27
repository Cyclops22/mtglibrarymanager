package com.cyclops.library.mtg.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class LibraryBean {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name="library_id", referencedColumnName="id")
	private List<LibrarySetBean> sets;

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

	public List<LibrarySetBean> getSets() {
		return sets;
	}

	public void setSets(List<LibrarySetBean> sets) {
		this.sets = sets;
	}
	
	
}
